package pub.makers.shop.cargo.service;

import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.cargo.entity.Cargo;
import pub.makers.shop.cargo.entity.vo.CargoInfoVo;
import pub.makers.shop.cargo.entity.vo.CargoParam;
import pub.makers.shop.cargo.entity.vo.CargoSaveVo;
import pub.makers.shop.cargo.entity.vo.CargoVo;

import java.util.List;

/**
 * Created by dy on 2017/5/22.
 */
public interface CargoMgrBizService {

    /**
     * 获取赠品数据
     * @return
     */
    List<Cargo> findGiftCargoList();

    /**
     * 保存货品信息
     * @param userId
     * @param cargoSaveVo
     */
    ResultData saveCargo(long userId, CargoSaveVo cargoSaveVo) throws Exception ;

    /**
     * 获取货品列表数据
     * @param queryKeyword
     * @param paging
     * @return
     */
    ResultList<CargoVo> cargoList(CargoParam queryKeyword, Paging paging);

    /**
     * 删除货品
     * @param ids
     * @return
     */
    boolean deleteCargoByIds(Long[] ids);


    /**
     * 获取货品详情
     * @param cargoId
     * @return
     */
    CargoInfoVo getCargoInfo(long cargoId);

    /**
     * 复制货品
     * @param cargoIds
     * @return
     */
    boolean copyCargos(Long[] cargoIds);
}
