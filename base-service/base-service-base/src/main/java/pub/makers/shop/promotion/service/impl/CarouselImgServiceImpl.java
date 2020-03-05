package pub.makers.shop.promotion.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.promotion.dao.CarouselImgDao;
import pub.makers.shop.promotion.entity.CarouselImg;
import pub.makers.shop.promotion.service.CarouselImgService;

/**
 * Created by kok on 2017/6/2.
 */
@Service
public class CarouselImgServiceImpl extends BaseCRUDServiceImpl<CarouselImg, String, CarouselImgDao> implements CarouselImgService {
}
