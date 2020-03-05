package pub.makers.shop.tradeGoods.service;


import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.tradeGoods.entity.Image;

import java.util.List;

public interface ImageService extends BaseCRUDService<Image>{
    List<Image> list(Conds conds);
}
