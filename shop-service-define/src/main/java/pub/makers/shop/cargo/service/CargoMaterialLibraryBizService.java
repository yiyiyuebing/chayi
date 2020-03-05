package pub.makers.shop.cargo.service;

import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.cargo.entity.vo.CargoMaterialLibraryVo;

import java.util.List;

public interface CargoMaterialLibraryBizService {
	/**
	 *  
	 * @param goodsId
	 * @param orderBizType
	 * @return
	 */
	List<CargoMaterialLibraryVo> getMaterialByGoodsId(String goodsId,OrderBizType orderBizType);
}
