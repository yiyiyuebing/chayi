package pub.makers.shop.purchaseClassify.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import pub.makers.shop.index.entity.IndexAdImages;
import pub.makers.shop.index.enums.IndexAdPlatform;
import pub.makers.shop.index.enums.IndexAdPost;
import pub.makers.shop.index.pojo.IndexAdImagesQuery;
import pub.makers.shop.index.service.IndexModuleBizService;
import pub.makers.shop.purchaseGoods.service.PurchaseClassifyBizService;
import pub.makers.shop.purchaseGoods.vo.PurchaseClassifyVo;

import java.util.List;

/**
 * Created by dy on 2017/6/2.
 */
@Service
public class PurchaseClassifyB2bService {

    @Reference(version = "1.0.0")
    private PurchaseClassifyBizService purchaseClassifyBizService;
    @Reference(version = "1.0.0")
    private IndexModuleBizService indexModuleBizService;

    public List<PurchaseClassifyVo> purchaseClassifyList(String storeLevel) {

        List<PurchaseClassifyVo> purchaseClassifyVos = purchaseClassifyBizService.indexClassifyList(storeLevel);

        for (PurchaseClassifyVo purchaseClassifyVo : purchaseClassifyVos) {
            List<IndexAdImages> indexAdImagesList = indexModuleBizService.getIndexAdImagesList(new IndexAdImagesQuery(IndexAdPost.classify, IndexAdPlatform.pc, null, 1));
            if (indexAdImagesList.isEmpty()) continue;
            purchaseClassifyVo.setIndexAdImages(indexAdImagesList.get(0));
        }

        return purchaseClassifyVos;
    }

}
