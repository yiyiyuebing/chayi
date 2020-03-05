package pub.makers.shop.store.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.store.entity.VtwoStoreRole;
import pub.makers.shop.store.vo.ImageVo;

import java.util.List;

import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/4/28.
 */
@Service(version="1.0.0")
public class VtwoStoreRoleMgrBizServiceImpl implements VtwoStoreRoleMgrBizService {

    @Autowired
    private VtwoStoreRoleService vtwoStoreRoleService;

    @Override
    public VtwoStoreRole getVtwoStoreRoleBySubbranchId(String subbranchId) {
        return vtwoStoreRoleService.get(Conds.get().eq("store_id", subbranchId));
    }

    @Override
    public Map<String, VtwoStoreRole> getVtwoStoreRoleByGroup(List<String> subbranchIdList) {
        List<VtwoStoreRole> vtwoStoreRoleList = vtwoStoreRoleService.list(Conds.get().in("store_id", subbranchIdList));
        Map<String, VtwoStoreRole> vtwoStoreRoleMap = Maps.newHashMap();
        for (VtwoStoreRole vtwoStoreRole : vtwoStoreRoleList) {
            if (vtwoStoreRoleMap.get(vtwoStoreRole.getStoreId().toString()) != null) {
                continue;
            }
            vtwoStoreRoleMap.put(vtwoStoreRole.getStoreId().toString(), vtwoStoreRole);
        }
        return vtwoStoreRoleMap;
    }

    @Override
    public List<VtwoStoreRole> getBySubbranchIds(List<String> shopIds) {
        return vtwoStoreRoleService.list(Conds.get().in("store_id", shopIds));
    }
}
