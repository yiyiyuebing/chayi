package pub.makers.shop.cargo.service;


import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.cargo.entity.Cargo;

public interface CargoService extends BaseCRUDService<Cargo>{
	
	Cargo getBySkuId(String skuId);

	void updateCargo(Cargo cargo, boolean hasNull);
}
