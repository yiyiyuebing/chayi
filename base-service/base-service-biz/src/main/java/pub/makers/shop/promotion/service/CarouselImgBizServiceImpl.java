package pub.makers.shop.promotion.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.promotion.entity.CarouselImg;
import pub.makers.shop.promotion.vo.CarouselImgPrevVo;
import pub.makers.shop.tradeGoods.entity.Image;
import pub.makers.shop.tradeGoods.service.ImageService;

import java.util.List;

/**
 * Created by kok on 2017/6/2.
 */
@Service(version = "1.0.0")
public class CarouselImgBizServiceImpl implements CarouselImgBizService {
    @Autowired
    private CarouselImgService carouselImgService;
    @Autowired
    private ImageService imageService;

    @Override
    public List<CarouselImgPrevVo> getCarouselByCategory(String category) {
        List<CarouselImg> imgList = carouselImgService.list(Conds.get().eq("status", 1).eq("category", category).order("sort desc"));
        List<CarouselImgPrevVo> voList = Lists.transform(imgList, new Function<CarouselImg, CarouselImgPrevVo>() {
            @Override
            public CarouselImgPrevVo apply(CarouselImg carouselImg) {
                CarouselImgPrevVo vo = new CarouselImgPrevVo();
                vo.setId(carouselImg.getId().toString());
                vo.setLineStatus(carouselImg.getLineStatus());
                vo.setLineUrl(carouselImg.getRichText());
                Image image = imageService.getById(carouselImg.getPicUrl());
                vo.setPicUrl(image.getPicUrl());
                return vo;
            }
        });
        return voList;
    }
}
