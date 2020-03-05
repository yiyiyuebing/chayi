package pub.makers.shop.afterSale.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.lantu.base.util.ListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.afterSale.entity.OrderItemReply;
import pub.makers.shop.afterSale.entity.OrderItemReplyImg;
import pub.makers.shop.afterSale.enums.OperManType;
import pub.makers.shop.afterSale.vo.OrderItemReplyImgVo;
import pub.makers.shop.afterSale.vo.OrderItemReplyVo;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.utils.DateParseUtil;
import pub.makers.shop.baseOrder.enums.OrderBizType;

import java.util.*;

/**
 * Created by kok on 2017/6/18.
 */
@Service(version = "1.0.0")
public class OrderItemReplyQueryServiceImpl implements OrderItemReplyQueryService {
    @Autowired
    private OrderItemReplyService orderItemReplyService;
    @Autowired
    private OrderItemReplyImgService orderItemReplyImgService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<OrderItemReplyVo> getOrderItemReplyList(String orderId, String skuId) {
        // 协商信息列表
        List<OrderItemReply> replyList = orderItemReplyService.list(Conds.get().eq("order_id", orderId).eq("good_sku_id", skuId).order("date_created asc, id asc"));
        // 协商图片列表
        List<OrderItemReplyImg> imgList = orderItemReplyImgService.list(Conds.get().in("order_item_reply_id", ListUtils.getIdSet(replyList, "id")).order("date_created asc"));
        ListMultimap<String, OrderItemReplyImgVo> replyImgMultimap = ArrayListMultimap.create();
        for (OrderItemReplyImg img : imgList) {
            OrderItemReplyImgVo imgVo = new OrderItemReplyImgVo();
            BeanUtils.copyProperties(img, imgVo);
            replyImgMultimap.put(imgVo.getOrderItemReplyId(), imgVo);
        }
        List<OrderItemReplyVo> replyVoList = Lists.newArrayList();
        for (int i = 0; i < replyList.size(); i++) {
            OrderItemReply reply = replyList.get(i);
            OrderItemReplyVo replyVo = new OrderItemReplyVo();
            BeanUtils.copyProperties(reply, replyVo);
            replyVo.setImgList(replyImgMultimap.get(replyVo.getId()));
            if (OperManType.pending.name().equals(replyVo.getOperManType())) {
                if (i != (replyList.size() - 1)) {
                    continue;
                }
                Long second = (replyVo.getTimeout().getTime() - new Date().getTime()) / 1000;
                String timeout = String.format("%s天%s时%s分", second / 3600 / 24, second / 3600 % 24, second / 60 % 60);
                replyVo.setRemark(String.format(replyVo.getRemark(), DateParseUtil.formatDate(replyVo.getTimeout(), timeout)));
            } else if (!OperManType.buyer.name().equals(replyVo.getOrderType()) && replyVo.getBuyerTimeout() != null) {
                Long second = (replyVo.getBuyerTimeout().getTime() - new Date().getTime()) / 1000;
                String timeout = String.format("<p>退货时间：剩余%s天%s时%s分逾期未退货申请将自动关闭</p>", second / 3600 / 24, second / 3600 % 24, second / 60 % 60);
                replyVo.setRemark(timeout + replyVo.getRemark());
            }
            replyVoList.add(replyVo);
        }
        return replyVoList;
    }

    @Override
    public List<OrderItemReplyVo> getItemReplyVoList(String orderId, String skuId, OrderBizType orderBizType) {

        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("orderNo", orderId);
        queryMap.put("goodSkuId", skuId);
        queryMap.put("orderType", orderBizType.name());
        String getItemReplyVoListSql = FreeMarkerHelper.getValueFromTpl("sql/afterSale/getOrderItemReplyList.sql", queryMap);
        List<OrderItemReplyVo> orderItemReplyVoList = jdbcTemplate.query(getItemReplyVoListSql, ParameterizedBeanPropertyRowMapper.newInstance(OrderItemReplyVo.class));

        // 协商图片列表
        List<OrderItemReplyImg> imgList = orderItemReplyImgService.list(Conds.get().in("order_item_reply_id", ListUtils.getIdSet(orderItemReplyVoList, "id")).order("date_created asc"));
        ListMultimap<String, OrderItemReplyImgVo> replyImgMultimap = ArrayListMultimap.create();
        for (OrderItemReplyImg img : imgList) {
            OrderItemReplyImgVo imgVo = new OrderItemReplyImgVo();
            BeanUtils.copyProperties(img, imgVo);
            replyImgMultimap.put(imgVo.getOrderItemReplyId(), imgVo);
        }

        List<OrderItemReplyVo> replyVoList = Lists.newArrayList();
        for (OrderItemReplyVo reply : orderItemReplyVoList) {
            reply.setImgList(replyImgMultimap.get(reply.getId()));
            replyVoList.add(reply);
        }
        return replyVoList;
    }


}
