package pub.makers.shop.cargo.service;

import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.cargo.entity.CargoMaterialLibrary;
import pub.makers.shop.cargo.entity.vo.CargoMaterialLibraryVo;
import pub.makers.shop.cargo.entity.vo.CargoParam;
import pub.makers.shop.cargo.entity.vo.CargoRelevanceParam;
import pub.makers.shop.cargo.entity.vo.CargoRelevanceVo;
import pub.makers.shop.store.vo.ImageVo;

import java.util.List;

/**
 * Created by daiwenfa on 2017/6/4.
 */
public interface CargoMaterialLibraryMgrBizService {
    ResultList<CargoMaterialLibraryVo> getListPage(CargoMaterialLibrary cargoMaterialLibrary, Paging pg);

    void saveOrUpdateCargoMaterialLibrary(CargoMaterialLibrary cargoMaterialLibrary, String[] array, long userId);

    boolean ableOrDisable(String id, String operation,Long userId);

    boolean remove(String id);

    boolean relevanceOrCancel(CargoMaterialLibrary cargoMaterialLibrary,String operation,Long userId);

    int countRelevanceNum(String id);

    ResultList<CargoRelevanceVo> getCargoRelevanceListPage(CargoRelevanceParam cargoRelevanceParam, Paging pg);

    List<ImageVo> queryImage(String id);
}
