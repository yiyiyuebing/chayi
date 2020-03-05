package pub.makers.shop.cargo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lantu.base.util.ListUtils;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cargo.dao.CargoDao;
import pub.makers.shop.cargo.entity.Cargo;
import pub.makers.shop.cargo.service.CargoService;

@Service
public class CargoServiceImpl extends BaseCRUDServiceImpl<Cargo, String, CargoDao>
										implements CargoService{

	
	public Cargo getBySkuId(String skuId) {
		
		List<Cargo> cargoList = dao.findBySql("select * from cargo where id = (select cargo_id from cargo_sku where id = (select cargo_sku_id from trade_good_sku where id = ?))", skuId);
		
		return ListUtils.getSingle(cargoList);
	}

	@Override
	public void updateCargo(Cargo cargo, boolean hasNull) {
		dao.update(cargo, hasNull);
	}

}
