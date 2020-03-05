package pub.makers.shop.browseLog.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lantu.base.common.entity.BoolType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.util.IdGenerator;
import pub.makers.shop.base.utils.DateParseUtil;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderClientType;
import pub.makers.shop.browseLog.entity.GoodsBrowseLog;
import pub.makers.shop.browseLog.pojo.GoodsBrowseLogQuery;
import pub.makers.shop.browseLog.vo.GoodsBrowseLogVo;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by kok on 2017/6/24.
 */
@Service(version = "1.0.0")
public class GoodsBrowseLogBizServiceImpl implements GoodsBrowseLogBizService {
    @Resource(name = "tradeGoodsBrowseLogServiceImpl")
    private GoodsBrowseLogService tradeGoodsBrowseLogService;
    @Resource(name = "purchaseGoodsBrowseLogServiceImpl")
    private GoodsBrowseLogService purchaseGoodsBrowseLogService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addBrowseLog(GoodsBrowseLogVo logVo) {
        // 前置验证
        checkVo(logVo);
        Conds conds = Conds.get();
        if (OrderClientType.app.equals(logVo.getOrderClientType()) || OrderClientType.weixin.equals(logVo.getOrderClientType())) {
            ValidateUtils.notNull(logVo.getGoodsSkuId(), "商品sku不能为空");
            conds.eq("goods_sku_id", logVo.getGoodsSkuId());
        }

        GoodsBrowseLogService goodsBrowseLogService = getLogService(logVo.getOrderBizType());
        GoodsBrowseLog goodsBrowseLog = goodsBrowseLogService.get(conds.eq("goods_id", logVo.getGoodsId())
                .eq("user_id", logVo.getUserId()).eq("del_flag", BoolType.F.name()).eq("client_type", logVo.getOrderClientType().name()));
        if (goodsBrowseLog != null) {
            goodsBrowseLogService.update(Update.byId(goodsBrowseLog.getId()).set("update_date", new Date()));
        } else {
            GoodsBrowseLog log = logVo.toBrowseLog();
            log.setId(IdGenerator.getDefault().nextId());
            log.setCreateDate(new Date());
            log.setUpdateDate(new Date());
            log.setDelFlag(BoolType.F.name());
            goodsBrowseLogService.insert(log);
        }
    }

    @Override
    public List<GoodsBrowseLogVo> getBrowseLogList(GoodsBrowseLogQuery query) {
        // 前置验证
        checkQuery(query);

        GoodsBrowseLogService goodsBrowseLogService = getLogService(query.getOrderBizType());
        Conds conds = Conds.get();
        if (query.getClassifyIdList() != null && !query.getClassifyIdList().isEmpty()) {
            conds.in("classify_id", query.getClassifyIdList());
        }
        Date endDate = DateParseUtil.parseDate(DateParseUtil.formatDate(query.getEndDate()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DATE, 1);
        endDate = calendar.getTime();
        List<GoodsBrowseLog> logList = goodsBrowseLogService.list(conds.eq("user_id", query.getUserId()).eq("client_type", query.getOrderClientType().name())
                .gte("update_date", DateParseUtil.formatDate(query.getStartDate())).lt("update_date", DateParseUtil.formatDate(endDate))
                .eq("del_flag", BoolType.F.name()).order("update_date desc"));
        // 商品信息
        List<GoodsBrowseLogVo> logVoList = Lists.newArrayList();
        for (GoodsBrowseLog log : logList) {
            GoodsBrowseLogVo logVo = GoodsBrowseLogVo.fromBrowseLog(log);
            logVoList.add(logVo);
        }
        return logVoList;
    }

    @Override
    public void delBrowseLogById(GoodsBrowseLogQuery query) {
        // 前置验证
        ValidateUtils.notNull(query.getLogIdList(), "足迹列表不能为空");
        ValidateUtils.isTrue(!query.getLogIdList().isEmpty(), "足迹列表不能为空");
        ValidateUtils.notNull(query.getUserId(), "用户id不能为空");
        ValidateUtils.notNull(query.getOrderBizType(), "业务类型不能为空");
        ValidateUtils.notNull(query.getOrderClientType(), "客户端类型不能为空");

        GoodsBrowseLogService goodsBrowseLogService = getLogService(query.getOrderBizType());
        List<GoodsBrowseLog> logList = goodsBrowseLogService.list(Conds.get().eq("user_id", query.getUserId()).in("id", query.getLogIdList()).eq("del_flag", BoolType.F.name()));
        for (GoodsBrowseLog log : logList) {
            goodsBrowseLogService.update(Update.byId(log.getId()).set("del_flag", BoolType.T.name()));
        }
        Map<String, Object> data = Maps.newHashMap();
        data.put("table", getTable(query.getOrderBizType()));
        data.put("logIdList", StringUtils.join(query.getLogIdList(), ","));
        data.put("userId", query.getUserId());
        data.put("clientType", query.getOrderClientType().name());
        String sql = FreeMarkerHelper.getValueFromTpl("sql/browseLog/delBrowseLogById.sql", data);
        jdbcTemplate.update(sql);
    }

    @Override
    public void delBrowseLogByDate(GoodsBrowseLogQuery query) {
        // 前置验证
        checkQuery(query);

        Date endDate = DateParseUtil.parseDate(DateParseUtil.formatDate(query.getEndDate()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DATE, 1);
        endDate = calendar.getTime();
        Map<String, Object> data = Maps.newHashMap();
        data.put("table", getTable(query.getOrderBizType()));
        data.put("startDate", DateParseUtil.formatDate(query.getStartDate()));
        data.put("endDate", DateParseUtil.formatDate(endDate));
        data.put("userId", query.getUserId());
        data.put("clientType", query.getOrderClientType().name());
        String sql = FreeMarkerHelper.getValueFromTpl("sql/browseLog/delBrowseLogByDate.sql", data);
        jdbcTemplate.update(sql);
    }

    private void checkVo(GoodsBrowseLogVo logVo) {
        ValidateUtils.notNull(logVo, "足迹信息不能为空");
        ValidateUtils.notNull(logVo.getGoodsId(), "商品id不能为空");
        ValidateUtils.notNull(logVo.getClassifyId(), "分类id不能为空");
        ValidateUtils.notNull(logVo.getUserId(), "用户id不能为空");
        ValidateUtils.notNull(logVo.getOrderBizType(), "业务类型不能为空");
        ValidateUtils.notNull(logVo.getName(), "名称不能为空");
        ValidateUtils.notNull(logVo.getImageUrl(), "图片不能为空");
        ValidateUtils.notNull(logVo.getOrderClientType(), "客户端类型不能为空");
        if (OrderBizType.trade.equals(logVo.getOrderBizType())) {
            ValidateUtils.notNull(logVo.getShopId(), "店铺id不能为空");
        }
    }

    private void checkQuery(GoodsBrowseLogQuery query) {
        ValidateUtils.notNull(query, "查询信息不能为空");
        ValidateUtils.notNull(query.getUserId(), "用户id不能为空");
        ValidateUtils.notNull(query.getStartDate(), "开始时间不能为空");
        ValidateUtils.notNull(query.getEndDate(), "结束时间不能为空");
        ValidateUtils.notNull(query.getOrderBizType(), "业务类型不能为空");
        ValidateUtils.notNull(query.getOrderClientType(), "客户端类型不能为空");
    }

    private GoodsBrowseLogService getLogService(OrderBizType orderBizType) {
        if (OrderBizType.purchase.equals(orderBizType)) {
            return purchaseGoodsBrowseLogService;
        } else {
            return tradeGoodsBrowseLogService;
        }
    }

    private String getTable(OrderBizType orderBizType) {
        if (OrderBizType.purchase.equals(orderBizType)) {
            return "purchase_goods_browse_log";
        } else {
            return "trade_goods_browse_log";
        }
    }
}
