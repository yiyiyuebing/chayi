package pub.makers.shop.cargo.service;

import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;

import java.util.Map;

/**
 * Created by daiwenfa on 2017/5/24.
 */
public interface CargosMgrBizService {

    ResultList<Map<String,Object>> getListPage(Map<String, Object> param, Paging pg);
}
