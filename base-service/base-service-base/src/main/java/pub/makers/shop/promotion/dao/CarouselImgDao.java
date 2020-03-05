package pub.makers.shop.promotion.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.promotion.entity.CarouselImg;

/**
 * Created by kok on 2017/6/2.
 */
@Repository
public class CarouselImgDao extends BaseCRUDDaoImpl<CarouselImg, String> {
    @Override
    protected String getTableName() {
        return "carousel_img";
    }

    @Override
    protected String getKeyName() {

        return "id";
    }

    @Override
    protected boolean autoGenerateKey() {

        return false;
    }
}
