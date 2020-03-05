//package pub.makers.shop.purchaseGoods.service;
//
//import pub.makers.daotemplate.service.BaseCRUDService;
//import pub.makers.shop.purchaseGoods.entity.PurchaseClassifyAttr;
//import pub.makers.shop.purchaseGoods.vo.PurchaseClassifyAttrVo;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by dy on 2017/5/26.
// */
//public interface PurchaseTradeClassifyAttrService extends BaseCRUDService<PurchaseClassifyAttr> {
//    List<PurchaseClassifyAttrVo> getByPurClassId(String purClassId,String parentId);
//
//    void insert(PurchaseClassifyAttrVo attrVo, String classId) throws Exception;
//
//    public void deleteByClassId(String classId)throws Exception;
//
//    public List<PurchaseClassifyAttrVo> findByClassIds(List<String> ids);
//
//    public PurchaseClassifyAttrVo getById(String id);
//
//    Map<String ,Object> getByPurClassIdForApp(String id);
//}
