package pub.makers.shop.tradeGoods.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.lantu.base.util.ListUtils;
import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Cond;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.baseOrder.enums.OrderStatus;
import pub.makers.shop.cargo.entity.vo.ImageGroupVo;
import pub.makers.shop.cargo.service.CargoImageBizService;
import pub.makers.shop.tradeGoods.entity.GoodEvaluation;
import pub.makers.shop.tradeGoods.entity.TradeGoodSku;
import pub.makers.shop.tradeGoods.pojo.GoodsEvaluationQuery;
import pub.makers.shop.tradeGoods.vo.GoodEvaluationVo;
import pub.makers.shop.tradeOrder.entity.Indent;
import pub.makers.shop.tradeOrder.service.IndentListService;
import pub.makers.shop.tradeOrder.service.IndentService;
import pub.makers.shop.user.entity.WeixinUserInfo;
import pub.makers.shop.user.service.WeixinUserInfoBizService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by kok on 2017/7/3.
 */
@Service(version = "1.0.0")
public class GoodEvaluationBizServiceImpl implements GoodEvaluationBizService {
    @Autowired
    private GoodEvaluationService goodEvaluationService;
    @Autowired
    private IndentService indentService;
    @Autowired
    private IndentListService indentListService;
    @Autowired
    private TradeGoodSkuService tradeGoodSkuService;
    @Autowired
    private CargoImageBizService cargoImageBizService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Reference(version = "1.0.0")
    private WeixinUserInfoBizService weixinUserInfoBizService;

    @Override
    public void addEvaluation(GoodEvaluationVo goodEvaluationVo) {
        check(goodEvaluationVo);
        // 评论订单
        Indent indent = indentService.getById(goodEvaluationVo.getIndentId());
        ValidateUtils.notNull(indent, "订单不存在");
        ValidateUtils.isTrue(OrderStatus.evaluate.getDbData() == indent.getStatus(), "订单无法评价");
        // 评论商品
        TradeGoodSku sku = tradeGoodSkuService.getById(goodEvaluationVo.getGoodSkuid());
        ValidateUtils.notNull(sku, "商品不存在");
        // 评论用户
        WeixinUserInfo userInfo = weixinUserInfoBizService.getWxUserById(Long.valueOf(goodEvaluationVo.getUser()));
        ValidateUtils.notNull(userInfo, "用户不存在");
        ValidateUtils.isTrue(indent.getBuyerId().equals(goodEvaluationVo.getUser()), "只能评价自己的订单");
        // 创建评论
        GoodEvaluation evaluation = goodEvaluationVo.toGoodEvaluation();
        evaluation.setId(IdGenerator.getDefault().nextId());
        evaluation.setGoodName(sku.getCargoSkuName());
        evaluation.setGoodId(sku.getGoodId().toString());
        evaluation.setUser(userInfo.getID() + "");
        evaluation.setEvaluateTime(new Date());
        evaluation.setUserName(userInfo.getNickname());
        evaluation.setHeadImgUrl(userInfo.getHeadImgUrl());
        evaluation.setIsHide("1");
        // 评论图片
        if (goodEvaluationVo.getImageUrlList() != null && !goodEvaluationVo.getImageUrlList().isEmpty()) {
            evaluation.setImage(cargoImageBizService.saveGroupImage(goodEvaluationVo.getImageUrlList(), goodEvaluationVo.getUser()).toString());
        }
        goodEvaluationService.insert(evaluation);

        // 更新订单商品已评价
        String sql = "update indent_list set is_evalution = 1, last_updated = '%s' where indent_id = '%s' and trade_good_sku_id = '%s'";
        jdbcTemplate.update(String.format(sql, DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"), evaluation.getOrderId(), evaluation.getGoodSkuId()));

        // 所有商品都评价后更新订单已评价
        Long count = indentListService.count(Conds.get().eq("indent_id", evaluation.getOrderId()).eq("is_evalution", 0));
        if (count.equals(0l)) {
            indentService.update(Update.byId(evaluation.getOrderId()).set("status", OrderStatus.done.getDbData()).set("evaluate_time", new Date()));
        }
    }

    @Override
    public List<GoodEvaluationVo> getEvaluationList(GoodsEvaluationQuery query) {
        List<GoodEvaluation> goodEvaluationList = goodEvaluationService.list(Conds.get().eq("good_id", query.getGoodId()).eq("is_hide", "1").addAll(Lists.newArrayList(Cond.isNull("replied_id"))).order("evaluate_time desc"));
        // 评论图片
        List<String> imageGroupIdList = Lists.newArrayList(ListUtils.getIdSet(goodEvaluationList, "image"));
        Map<String, ImageGroupVo> imageGroupVoMap = cargoImageBizService.getImageGroup(imageGroupIdList);
        // 回复评论
        List<GoodEvaluation> replyEvaluationList = goodEvaluationService.list(Conds.get().in("replied_id", ListUtils.getIdSet(goodEvaluationList, "id")).eq("is_hide", 1));
        Map<String, GoodEvaluation> repliedEvaluationMap = ListUtils.toKeyMap(replyEvaluationList, "repliedId");
        // 评论订单
        List<Indent> indentList = indentService.list(Conds.get().in("id", ListUtils.getIdSet(goodEvaluationList, "orderId")));
        Map<String, Indent> indentMap = ListUtils.toKeyMap(indentList, "id");
        List<GoodEvaluationVo> goodEvaluationVoList = Lists.newArrayList();
        for (GoodEvaluation goodEvaluation : goodEvaluationList) {
            GoodEvaluationVo evaluationVo = GoodEvaluationVo.fromGoodEvaluation(goodEvaluation);
            evaluationVo.setImages(imageGroupVoMap.get(goodEvaluation.getImage()));
            if (repliedEvaluationMap.get(evaluationVo.getId()) != null) {
                evaluationVo.setReplied(GoodEvaluationVo.fromGoodEvaluation(repliedEvaluationMap.get(evaluationVo.getId())));
            }
            evaluationVo.setBuyTime(indentMap.get(goodEvaluation.getOrderId()) == null ? null : indentMap.get(goodEvaluation.getOrderId()).getCreateTime());
            goodEvaluationVoList.add(evaluationVo);
        }
        return goodEvaluationVoList;
    }

    private void check(GoodEvaluationVo goodEvaluationVo) {
        ValidateUtils.notNull(goodEvaluationVo, "评论不能为空");
        ValidateUtils.notNull(goodEvaluationVo.getIndentId(), "评论订单不能为空");
        ValidateUtils.notNull(goodEvaluationVo.getGoodSkuid(), "评论商品不能为空");
        ValidateUtils.notNull(goodEvaluationVo.getUser(), "评论用户不能为空");
        ValidateUtils.notNull(goodEvaluationVo.getContent(), "评论内容不能为空");
        ValidateUtils.notNull(goodEvaluationVo.getScore(), "评分不能为空");
    }
}
