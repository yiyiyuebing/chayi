package pub.makers.shop.evaluate.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lantu.base.util.ListUtils;
import org.springframework.stereotype.Service;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.cargo.entity.vo.ImageGroupVo;
import pub.makers.shop.purchaseGoods.pojo.PurchaseGoodsEvaluationQuery;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsEvaluationBizService;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsEvaluationVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderListVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;
import pub.makers.shop.store.vo.ImageVo;
import pub.makers.shop.user.utils.AccountUtils;

import java.util.*;

/**
 * Created by Administrator on 2017/7/7.
 */
@Service
public class EvaluateService {
     @Reference(version = "1.0.0")
     private PurchaseGoodsEvaluationBizService evaluationBizService;

     /***
     * 添加评论
     * @param purchaseGoodsEvaluationVos
     * @param userId
     */
     public void addEvaluation(List<PurchaseGoodsEvaluationVo> purchaseGoodsEvaluationVos, String userId) {
         for (PurchaseGoodsEvaluationVo purchaseGoodsEvaluationVo : purchaseGoodsEvaluationVos) {
             purchaseGoodsEvaluationVo.setUser(userId);
             evaluationBizService.addEvaluation(purchaseGoodsEvaluationVo);
         }
    }

    /***
     * 添加评论
     * @param purchaseGoodsEvaluationVo
     */
    public void addEvaluation(PurchaseGoodsEvaluationVo purchaseGoodsEvaluationVo) {
        evaluationBizService.addEvaluation(purchaseGoodsEvaluationVo);
    }

     /***
     * 添加评论
     */
     public void addEvaluation(List<PurchaseGoodsEvaluationVo> purchaseGoodsEvaluationVoList) {
         String userId = AccountUtils.getCurrShopId();
         ValidateUtils.isTrue(!purchaseGoodsEvaluationVoList.isEmpty(), "评价内容不能为空");
         for (PurchaseGoodsEvaluationVo purchaseGoodsEvaluationVo : purchaseGoodsEvaluationVoList) {
             purchaseGoodsEvaluationVo.setUser(userId);
             evaluationBizService.addEvaluation(purchaseGoodsEvaluationVo);
         }
    }

    /**
     * 查询评价列表
     */
   public List<PurchaseGoodsEvaluationVo> getEvaluationList(PurchaseGoodsEvaluationQuery query) {
       return evaluationBizService.getEvaluationList(query);
   }

    public void getEvaluationListByOrder(PurchaseOrderVo pov, String currShopId) {
        PurchaseGoodsEvaluationQuery query = new PurchaseGoodsEvaluationQuery();
        query.setUserId(currShopId);
        query.setOrderId(pov.getId());
        List<PurchaseGoodsEvaluationVo> purchaseGoodsEvaluationVos = evaluationBizService.getEvaluationList(query);

        Map<String, PurchaseGoodsEvaluationVo> evaluationVoMap = new HashMap<String, PurchaseGoodsEvaluationVo>();

        for (PurchaseGoodsEvaluationVo purchaseGoodsEvaluationVo : purchaseGoodsEvaluationVos) {
            if (evaluationVoMap.get(purchaseGoodsEvaluationVo.getGoodSkuId()) != null) {
                continue;
            }
            ImageGroupVo images = purchaseGoodsEvaluationVo.getImages();
            List<ImageVo> imgList = new ArrayList<ImageVo>();
            if (images != null) {
                imgList = images.getImages();
            }
            Set<String> stringSet = ListUtils.getIdSet(imgList, "url");
            List<String> imageUrlList = new ArrayList<String>();
            imageUrlList.addAll(stringSet);
            purchaseGoodsEvaluationVo.setImageUrlList(imageUrlList);
            evaluationVoMap.put(purchaseGoodsEvaluationVo.getGoodSkuId(), purchaseGoodsEvaluationVo);
        }

        List<PurchaseOrderListVo> orderListVos = pov.getOrderListVos();
        for (PurchaseOrderListVo orderListVo : orderListVos) {
            orderListVo.setEvaluationVo(evaluationVoMap.get(orderListVo.getGoodSkuId()));
        }
    }
}
