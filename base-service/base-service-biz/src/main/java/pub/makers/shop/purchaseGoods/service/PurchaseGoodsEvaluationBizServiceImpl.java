package pub.makers.shop.purchaseGoods.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.lantu.base.util.ListUtils;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang.StringUtils;
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
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodEvaluation;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSku;
import pub.makers.shop.purchaseGoods.pojo.PurchaseGoodsEvaluationQuery;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsEvaluationCountVo;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsEvaluationVo;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrder;
import pub.makers.shop.purchaseOrder.service.PurchaseOrderService;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.service.SubbranchBizService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by kok on 2017/6/1.
 */
@Service(version = "1.0.0")
public class PurchaseGoodsEvaluationBizServiceImpl implements PurchaseGoodsEvaluationBizService {
    @Autowired
    private PurchaseGoodEvaluationService purchaseGoodEvaluationService;
    @Autowired
    private PurchaseOrderListService purchaseOrderListService;
    @Autowired
    private PurchaseOrderService purchaseOrderService;
    @Autowired
    private CargoImageBizService cargoImageBizService;
    @Autowired
    private PurchaseGoodsSkuService purchaseGoodsSkuService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Reference(version = "1.0.0")
    private SubbranchBizService subbranchBizService;

    @Override
    public void addEvaluation(PurchaseGoodsEvaluationVo purchaseGoodsEvaluationVo) {
        check(purchaseGoodsEvaluationVo);
        // 评论订单
        PurchaseOrder purchaseOrder = purchaseOrderService.getById(purchaseGoodsEvaluationVo.getOrderId());
        ValidateUtils.notNull(purchaseOrder, "订单不存在");
        ValidateUtils.isTrue(OrderStatus.done.getDbData() != purchaseOrder.getStatus(), "订单已评价");
        ValidateUtils.isTrue(OrderStatus.evaluate.getDbData() == purchaseOrder.getStatus(), "订单无法评价");
        // 评论商品
        PurchaseGoodsSku sku = purchaseGoodsSkuService.getById(purchaseGoodsEvaluationVo.getGoodSkuId());
        ValidateUtils.notNull(sku, "商品不存在");
        // 评论用户
        Subbranch subbranch = subbranchBizService.getById(purchaseGoodsEvaluationVo.getUser());
        ValidateUtils.notNull(subbranch, "用户不存在");
        ValidateUtils.isTrue(purchaseOrder.getBuyerId().equals(purchaseGoodsEvaluationVo.getUser()), "只能评价自己的订单");
        // 创建评论
        PurchaseGoodEvaluation evaluation = purchaseGoodsEvaluationVo.toPurchaseGoodEvaluation();
        evaluation.setId(IdGenerator.getDefault().nextId());
        evaluation.setGoodName(sku.getCargoSkuName());
        evaluation.setPurGoodsId(sku.getPurGoodsId());
        evaluation.setEvaluateTime(new Date());
        evaluation.setUserName(subbranch.getName());
        evaluation.setHeadImgUrl(subbranch.getHeadImgUrl());
        evaluation.setIsHide("1");
        // 评论图片
        if (purchaseGoodsEvaluationVo.getImageUrlList() != null && !purchaseGoodsEvaluationVo.getImageUrlList().isEmpty()) {
            evaluation.setImage(cargoImageBizService.saveGroupImage(purchaseGoodsEvaluationVo.getImageUrlList(), purchaseGoodsEvaluationVo.getUser()).toString());
        }
        purchaseGoodEvaluationService.insert(evaluation);

        // 更新订单商品已评价
        String sql = "update purchase_order_list set is_evalution = 1, last_updated = '%s' where order_id = '%s' and pur_goods_sku_id = '%s'";
        jdbcTemplate.update(String.format(sql, DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"), evaluation.getOrderId(), evaluation.getGoodSkuId()));

        // 所有商品都评价后更新订单已评价
        Long count = purchaseOrderListService.count(Conds.get().eq("order_id", evaluation.getOrderId()).eq("is_sample", 0).eq("is_evalution", 0));
        if (count.equals(0l)) {
            purchaseOrderService.update(Update.byId(evaluation.getOrderId()).set("status", OrderStatus.done.getDbData()).set("evaluate_time", new Date()));
        }
    }

    private void check(PurchaseGoodsEvaluationVo purchaseGoodsEvaluationVo) {
        ValidateUtils.notNull(purchaseGoodsEvaluationVo, "评论不能为空");
        ValidateUtils.notNull(purchaseGoodsEvaluationVo.getOrderId(), "评论订单不能为空");
        ValidateUtils.notNull(purchaseGoodsEvaluationVo.getGoodSkuId(), "评论商品不能为空");
        ValidateUtils.notNull(purchaseGoodsEvaluationVo.getUser(), "评论用户不能为空");
        ValidateUtils.notNull(purchaseGoodsEvaluationVo.getScore(), "评分不能为空");
    }

    @Override
    public PurchaseGoodsEvaluationVo getEvaluation(String id) {
        PurchaseGoodEvaluation evaluation = purchaseGoodEvaluationService.getById(id);
        ValidateUtils.notNull(evaluation, "评论不存在");
        // 评论图片
        Map<String, ImageGroupVo> imageGroupVoMap = cargoImageBizService.getImageGroup(Lists.newArrayList(evaluation.getImage()));
        // 回复评论
        PurchaseGoodEvaluation replyEvaluation = purchaseGoodEvaluationService.get(Conds.get().eq("replied_id", evaluation.getId()).eq("is_hide", 1));
        // 评论订单
        PurchaseOrder purchaseOrder = purchaseOrderService.get(Conds.get().eq("id", evaluation.getOrderId()));
        // 评论详情
        PurchaseGoodsEvaluationVo vo = PurchaseGoodsEvaluationVo.fromPurchaseGoodEvaluation(evaluation);
        vo.setImages(imageGroupVoMap.get(evaluation.getImage()));
        if (replyEvaluation != null) {
            vo.setReplied(PurchaseGoodsEvaluationVo.fromPurchaseGoodEvaluation(replyEvaluation));
        }
        vo.setBuyTime(purchaseOrder == null ? null : purchaseOrder.getCreateTime());
        return vo;
    }

    @Override
    public List<PurchaseGoodsEvaluationVo> getEvaluationList(PurchaseGoodsEvaluationQuery query) {
        ValidateUtils.notNull(query, "查询条件错误");
        Conds conds = Conds.get();
        if (StringUtils.isNotEmpty(query.getUserId())) {
            conds.eq("user", query.getUserId());
        }
        if (StringUtils.isNotEmpty(query.getGoodId())) {
            conds.eq("pur_goods_id", query.getGoodId());
        }
        if (StringUtils.isNotEmpty(query.getOrderId())) {
            conds.eq("order_id", query.getOrderId());
        }

        switch (query.getType()) {
            // 晒图
            case 2:
                conds = Conds.get().addAll(conds.getCondList()).addAll(Lists.newArrayList(Cond.notNull("image"))).ne("image", "");
                break;
            // 好评
            case 3:
                conds.in("score", Lists.newArrayList(4, 5));
                break;
            // 中评
            case 4:
                conds.in("score", Lists.newArrayList(2, 3));
                break;
            // 差评
            case 5:
                conds.eq("score", 1);
                break;
        }
        // 评论列表
        List<PurchaseGoodEvaluation> evaluationList = purchaseGoodEvaluationService.list(conds.eq("is_hide", 1).addAll(Lists.newArrayList(Cond.isNull("replied_id"))).order("evaluate_time desc").page((query.getPageNum() - 1) * query.getPageSize(), query.getPageSize()));
        // 评论图片
        List<String> imageGroupIdList = Lists.newArrayList(ListUtils.getIdSet(evaluationList, "image"));
        Map<String, ImageGroupVo> imageGroupVoMap = cargoImageBizService.getImageGroup(imageGroupIdList);
        // 回复评论
        List<PurchaseGoodEvaluation> replyEvaluationList = purchaseGoodEvaluationService.list(Conds.get().in("replied_id", ListUtils.getIdSet(evaluationList, "id")).eq("is_hide", 1));
        Map<String, PurchaseGoodEvaluation> repliedEvaluationMap = ListUtils.toKeyMap(replyEvaluationList, "repliedId");
        // 评论订单
        List<PurchaseOrder> purchaseOrderList = purchaseOrderService.list(Conds.get().in("id", ListUtils.getIdSet(evaluationList, "orderId")));
        Map<String, PurchaseOrder> purchaseOrderMap = ListUtils.toKeyMap(purchaseOrderList, "id");
        List<PurchaseGoodsEvaluationVo> voList = Lists.newArrayList();
        for (PurchaseGoodEvaluation evaluation : evaluationList) {
            PurchaseGoodsEvaluationVo vo = PurchaseGoodsEvaluationVo.fromPurchaseGoodEvaluation(evaluation);
            vo.setImages(imageGroupVoMap.get(evaluation.getImage()));
            if (repliedEvaluationMap.get(evaluation.getId().toString()) != null) {
                vo.setReplied(PurchaseGoodsEvaluationVo.fromPurchaseGoodEvaluation(repliedEvaluationMap.get(evaluation.getId().toString())));
            }
            vo.setBuyTime(purchaseOrderMap.get(vo.getOrderId()) == null ? null : purchaseOrderMap.get(vo.getOrderId()).getCreateTime());
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public PurchaseGoodsEvaluationCountVo getEvaluationCount(PurchaseGoodsEvaluationQuery query) {
        PurchaseGoodsEvaluationCountVo vo = new PurchaseGoodsEvaluationCountVo();
        Conds conds = Conds.get().eq("is_hide", 1);
        String sql = "select AVG(score) from purchase_goods_evaluation where is_hide = 1";
        if (StringUtils.isNotEmpty(query.getUserId())) {
            conds.eq("user", query.getUserId());
            sql += " and user = " + query.getUserId();
        }
        if (StringUtils.isNotEmpty(query.getGoodId())) {
            conds.eq("pur_goods_id", query.getGoodId());
            sql += " and pur_goods_id = " + query.getGoodId();
        }

        // 平均评分
        BigDecimal avgScore = jdbcTemplate.queryForObject(sql, BigDecimal.class);
        avgScore = avgScore != null ? avgScore : new BigDecimal(0);
        vo.setAvgScore(avgScore.setScale(2, BigDecimal.ROUND_HALF_UP).toString());

        // 评论总数
        Long totalCount = purchaseGoodEvaluationService.count(conds);
        vo.setTotalCount(totalCount);

        //带图片评论数
        Long imageCount = purchaseGoodEvaluationService.count(Conds.get().addAll(conds.getCondList()).addAll(Lists.newArrayList(Cond.notNull("image"))).ne("image", ""));
        vo.setImageCount(imageCount);

        //好评数
        Long goodCount = purchaseGoodEvaluationService.count(Conds.get().addAll(conds.getCondList()).in("score", Lists.newArrayList(4, 5)));
        vo.setGoodCount(goodCount);

        //中评数
        Long middleCount = purchaseGoodEvaluationService.count(Conds.get().addAll(conds.getCondList()).in("score", Lists.newArrayList(2, 3)));
        vo.setMiddleCount(middleCount);

        //差评数
        Long badCount = purchaseGoodEvaluationService.count(Conds.get().addAll(conds.getCondList()).eq("score", 1));
        vo.setBadCount(badCount);
        return vo;
    }
}
