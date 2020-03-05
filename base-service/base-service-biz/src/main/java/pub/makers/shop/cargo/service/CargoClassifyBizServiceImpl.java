package pub.makers.shop.cargo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.cargo.entity.CargoClassify;
import pub.makers.shop.cargo.entity.vo.CargoClassifyVo;
import pub.makers.shop.purchaseGoods.entity.PurchaseClassify;

import java.util.List;
import java.util.Set;

/**
 * Created by kok on 2017/5/25.
 */
@Service(version = "1.0.0")
public class CargoClassifyBizServiceImpl implements CargoClassifyBizService {
    @Autowired
    private CargoClassifyService cargoClassifyService;

    @Override
    public List<CargoClassifyVo> getTypeListByParentId(Long parentId, Integer status) {
        List<CargoClassifyVo> vos = Lists.newArrayList();
        List<CargoClassify> cargoClassifies = cargoClassifyService.list(Conds.get().eq("parent_id", parentId).eq("status", status).order("order_index desc,update_time desc"));
        for (CargoClassify cargoClassify : cargoClassifies) {
            CargoClassifyVo vo = CargoClassifyVo.fromCargoClassify(cargoClassify);
            List<CargoClassify> childrenClassifies = cargoClassifyService.list(Conds.get().eq("parent_id", cargoClassify.getId()).eq("status", status).order("order_index desc,update_time desc"));
            List<CargoClassifyVo> childrenVos = Lists.transform(childrenClassifies, new Function<CargoClassify, CargoClassifyVo>() {
                @Override
                public CargoClassifyVo apply(CargoClassify cargoClassify) {
                    return CargoClassifyVo.fromCargoClassify(cargoClassify);
                }
            });
            vo.setChildren(childrenVos);
            vos.add(vo);
        }
        return vos;
    }

    @Override
    public CargoClassify getCargoClassifyById(String classifyId) {
        return cargoClassifyService.getById(classifyId);
    }

    @Override
    public Set<String> findAllIdByParentId(Set<String> parentIdList) {
        Conds conds = Conds.get();

        List<CargoClassify> classifyList = cargoClassifyService.list(conds.in("parent_id", parentIdList));
        List<String> idList = Lists.transform(classifyList, new Function<CargoClassify, String>() {
            @Override
            public String apply(CargoClassify cargoClassify) {
                return cargoClassify.getId().toString();
            }
        });
        Integer length = parentIdList.size();
        parentIdList.addAll(idList);
        if (parentIdList.size() == length) {
            return parentIdList;
        }
        return findAllIdByParentId(parentIdList);
    }
}
