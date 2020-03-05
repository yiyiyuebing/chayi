package pub.makers.shop.logistics.service;


import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.logistics.entity.FreightPinkage;

public interface FreightPinkageService extends BaseCRUDService<FreightPinkage>{

    void delPinkageByTplId(String tplId);
}
