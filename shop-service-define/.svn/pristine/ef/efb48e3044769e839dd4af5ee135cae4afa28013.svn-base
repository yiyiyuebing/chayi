package pub.makers.shop.cargo.service;

import pub.makers.shop.cargo.entity.CargoClassify;
import pub.makers.shop.cargo.entity.vo.CargoClassifyVo;

import java.util.List;
import java.util.Set;

/**
 * Created by kok on 2017/5/25.
 */
public interface CargoClassifyBizService {
    /**
     * 根据父id查找分类
     * @param parentId
     * @param status
     * @return
     */
    List<CargoClassifyVo> getTypeListByParentId(Long parentId, Integer status);

    /**
     * 获取货品分类信息
     * @param classifyId
     * @return
     */
    CargoClassify getCargoClassifyById(String classifyId);

    /**
     * 查询所有子分类id
     */
    Set<String> findAllIdByParentId(Set<String> parentIdList);


}
