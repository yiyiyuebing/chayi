package pub.makers.shop.logistics.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.logistics.service.FreightPinkageService;
import pub.makers.shop.logistics.dao.FreightPinkageDao;
import pub.makers.shop.logistics.entity.FreightPinkage;

@Service
public class FreightPinkageServiceImpl extends BaseCRUDServiceImpl<FreightPinkage, String, FreightPinkageDao>
										implements FreightPinkageService{

    @Override
    public void delPinkageByTplId(String tplId) {
        delete(Conds.get().eq("tplId", tplId));
    }
}
