package pub.makers.shop.cargo.service;

import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.cargo.entity.CargoSuppliers;

import java.io.Serializable;
import java.util.List;

/**
 * Created by daiwenfa on 2017/5/22.
 */
public interface CargoSuppliersMgrBizService {
    ResultList<CargoSuppliers> cargoSuppliersPage(CargoSuppliers cargoSuppliers, Paging pg);

    CargoSuppliers delCargoSuppliersById(String id);

    CargoSuppliers saveOrUpdate(CargoSuppliers cargoSuppliers);

    boolean ableOrDisable(String id, String operation);

    CargoSuppliers getCargoSuppliersInfo(String id);
}
