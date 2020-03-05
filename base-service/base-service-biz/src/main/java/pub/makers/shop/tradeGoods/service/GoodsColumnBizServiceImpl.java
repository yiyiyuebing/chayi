package pub.makers.shop.tradeGoods.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.tradeGoods.entity.GoodsColumn;
import pub.makers.shop.tradeGoods.entity.Image;
import pub.makers.shop.tradeGoods.vo.GoodsColumnVo;

import java.util.List;
import java.util.Map;

/**
 * Created by kok on 2017/5/26.
 */
@Service(version = "1.0.0")
public class GoodsColumnBizServiceImpl implements GoodsColumnBizService {
    @Autowired
    private GoodsColumnService goodsColumnService;
    @Autowired
    private ImageService imageService;

    @Override
    public List<GoodsColumnVo> getAllGoodsColumn(Integer limit) {
        List<GoodsColumn> goodsColumnList = goodsColumnService.list(Conds.get().eq("status", 1).order("order_by desc").page(0, limit));
        List<GoodsColumnVo> columnVoList = Lists.newArrayList();
        List<Image> imageList = imageService.list(Conds.get().in("id", ListUtils.getIdSet(goodsColumnList, "showpicture")));
        Map<String, Image> imageMap = ListUtils.toKeyMap(imageList, "id");
        for (GoodsColumn goodsColumn : goodsColumnList) {
            GoodsColumnVo columnVo = new GoodsColumnVo(goodsColumn);
            Image image = imageMap.get(goodsColumn.getShowpicture());
            if (image != null) {
                columnVo.setShowpicture(image.getPicUrl());
            }
            columnVoList.add(columnVo);
        }
        return columnVoList;
    }

    @Override
    public GoodsColumnVo getGoodsColumnDetail(String id) {
        GoodsColumn goodsColumn = goodsColumnService.getById(id);
        ValidateUtils.notNull(goodsColumn, "促销区域不存在");
        GoodsColumnVo columnVo = new GoodsColumnVo(goodsColumn);
        if (StringUtils.isNotEmpty(goodsColumn.getShowpicture())) {
            Image showpicture = imageService.getById(goodsColumn.getShowpicture());
            if (showpicture != null) {
                columnVo.setShowpicture(showpicture.getPicUrl());
            }
        }
        if (goodsColumn.getIntroduceImage() != null) {
            Image introduceImage = imageService.getById(goodsColumn.getIntroduceImage());
            if (introduceImage != null) {
                columnVo.setIntroduceImage(introduceImage.getPicUrl());
            }
        }
        if (goodsColumn.getSlideshowImages() != null) {
            List<Image> slideshowImages = imageService.list(Conds.get().eq("group_id", goodsColumn.getSlideshowImages()));
            if (!slideshowImages.isEmpty()) {
                columnVo.setSlideshowImagesUrl(Lists.transform(slideshowImages, new Function<Image, String>() {
                    @Override

                    public String apply(Image image) {
                        return image.getPicUrl();
                    }
                }));

            }
        }
        return columnVo;
    }
}
