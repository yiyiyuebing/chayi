package pub.makers.shop.cargo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Maps;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.cargo.entity.Cargo;
import pub.makers.shop.cargo.entity.CargoClassify;
import pub.makers.shop.cargo.entity.CargoMaterialLibrary;
import pub.makers.shop.cargo.entity.CargoMaterialLibraryRel;
import pub.makers.shop.cargo.entity.vo.CargoMaterialLibraryVo;
import pub.makers.shop.cargo.entity.vo.CargoRelevanceParam;
import pub.makers.shop.cargo.entity.vo.CargoRelevanceVo;
import pub.makers.shop.store.vo.ImageVo;
import pub.makers.shop.tradeGoods.entity.Image;
import pub.makers.shop.tradeGoods.service.ImageService;

import java.util.*;

/**
 * Created by daiwenfa on 2017/6/4.
 */
@Service(version="1.0.0")
public class CargoMaterialLibraryAdminServiceImpl implements CargoMaterialLibraryMgrBizService {
    @Autowired
    private CargoMaterialLibraryService cargoMaterialLibraryService;
    @Autowired
    private CargoMaterialLibraryRelService cargoMaterialLibraryRelService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CargoService cargoService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private CargoClassifyService cargoClassifyService;

    @Override
    public ResultList<CargoMaterialLibraryVo> getListPage(CargoMaterialLibrary cargoMaterialLibrary, Paging pg) {
        Map<String, Object> data = Maps.newHashMap();
        if(cargoMaterialLibrary!=null) {
            if (cargoMaterialLibrary.getMaterialName() != null && !"".equals(cargoMaterialLibrary.getMaterialName())) {
                data.put("materialName", cargoMaterialLibrary.getMaterialName());
            }
            if (cargoMaterialLibrary.getIsValid() != null && !"".equals(cargoMaterialLibrary.getIsValid())) {
                data.put("isValid", cargoMaterialLibrary.getIsValid());
            }
            if (cargoMaterialLibrary.getCargoName() != null && !"".equals(cargoMaterialLibrary.getCargoName())) {
                data.put("cargoName", cargoMaterialLibrary.getCargoName());
            }
            if (cargoMaterialLibrary.getCargoNum() != null && !"".equals(cargoMaterialLibrary.getCargoNum())) {
                data.put("cargoNum", cargoMaterialLibrary.getCargoNum());
            }
        }
        String sql = FreeMarkerHelper.getValueFromTpl("sql/cargo/queryCargoMaterialLibarayList.sql", data);
        RowMapper<CargoMaterialLibraryVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(CargoMaterialLibraryVo.class);
        List<CargoMaterialLibraryVo> list = jdbcTemplate.query(sql, rowMapper,pg.getPs(), pg.getPn()); //queryCargoName

        Map<String, Object> cargoIdMap = new HashMap<String,Object>();
        for (int j = 0; j < list.size(); j++) {           //获得关联商品名字
            if(StringUtils.isEmpty(list.get(j).getRelevanceCargoId())){
                  continue;
            }
            String[] cargoId = list.get(j).getRelevanceCargoId().split(",");
            String[] cargoName = new String[cargoId.length];
                for (int i = 0; i < cargoId.length; i++) {
                    cargoIdMap.put("ids", cargoId[i]);
                    String cargoNameSql = FreeMarkerHelper.getValueFromTpl("sql/cargo/queryCargoName.sql", cargoIdMap);
                    cargoName[i] = jdbcTemplate.queryForObject(cargoNameSql, java.lang.String.class);
                }
                list.get(j).setRelevanceCargoName(StringUtils.join(cargoName, ","));
            }

        String countSql = FreeMarkerHelper.getValueFromTpl("sql/cargo/countCargoMaterialLibarayList.sql", data);
        Number total = jdbcTemplate.queryForObject(countSql,null, Integer.class);
        ResultList<CargoMaterialLibraryVo> resultList = new ResultList<CargoMaterialLibraryVo>();
        resultList.setResultList(list);
        resultList.setTotalRecords(total != null ? total.intValue() : 0);
        return resultList;
    }

    @Override
    public void saveOrUpdateCargoMaterialLibrary(CargoMaterialLibrary cargoMaterialLibrary, String[] array, long userId) {
        //删除旧图片
        /*if(cargoMaterialLibrary.getId()!=null){
            CargoMaterialLibrary cargoMaterialLibraryOld = cargoMaterialLibraryService.get(Conds.get().eq("id", cargoMaterialLibrary.getId()));
            String[] oldImage = cargoMaterialLibraryOld.getImage().split(",");
            if (array != null && !array.equals("") && array.length != 0) {
                String deGroupSql = " delete from image where id = ? ";
                for (int i = 0; i < oldImage.length; i++) {
                    jdbcTemplate.update(deGroupSql, oldImage[i]);
                }
            }
        }*/
        //保存图片
        String image = "";//拼接图片id，用于关联
        if (array != null && !array.equals("") && array.length != 0) {
            String insertImage = "insert into image (id, pic_url,create_time, create_by) values (?,?,?,?) ";
            for (int i = 0; i < array.length; i++) {
                List imageParam = new ArrayList();
                String id = IdGenerator.getDefault().nextId() + "";
                imageParam.add(id);
                imageParam.add(array[i]);
                imageParam.add(new Date());
                imageParam.add(userId);
                jdbcTemplate.update(insertImage, imageParam.toArray());
                if (i == 0) {
                    image = id;
                } else {
                    image = image + "," + id;
                }
            }
        }
        if(cargoMaterialLibrary.getId()==null) {
            cargoMaterialLibrary.setId(IdGenerator.getDefault().nextId());
            cargoMaterialLibrary.setCreateBy(userId);
            cargoMaterialLibrary.setDateCreated(new Date());
            cargoMaterialLibrary.setImage(image);
            cargoMaterialLibraryService.insert(cargoMaterialLibrary);
        }else {
            cargoMaterialLibrary.setImage(image);
            cargoMaterialLibrary.setLastUpdated(new Date());
            cargoMaterialLibrary.setUpdateBy(userId);
            cargoMaterialLibraryService.update(cargoMaterialLibrary);
        }



    }

    @Override
    public boolean ableOrDisable(String id, String operation,Long userId) {
        CargoMaterialLibrary cargoMaterialLibraryOld = cargoMaterialLibraryService.get(Conds.get().eq("id", id));
        String flag = cargoMaterialLibraryOld.getIsValid();
        if(!flag.equals(operation)) {
            cargoMaterialLibraryService.update(Update.byId(id).set("is_valid", operation).set("update_by",userId).set("last_updated",new Date()));
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(String id) {
//        //删除旧图片
//        if(id!=null){
//            CargoMaterialLibrary cargoMaterialLibraryOld = cargoMaterialLibraryService.get(Conds.get().eq("id", id));
//            String[] oldImage = cargoMaterialLibraryOld.getImage().split(",");
//            String delImageSql = " delete from image where id = ? ";
//            for (int i = 0; i < oldImage.length; i++) {
//                jdbcTemplate.update(delImageSql, oldImage[i]);
//            }
//        }
        cargoMaterialLibraryService.delete(Conds.get().eq("id", id));
        return true;
    }

    /**
     * 关联和取消关联，relevance关联，cancel取消关联
     * @param cargoMaterialLibrary
     * @param operation
     * @param userId
     * @return
     */
    @Override
    public boolean relevanceOrCancel(CargoMaterialLibrary cargoMaterialLibrary, String operation, Long userId) {
        String cargoId = cargoMaterialLibrary.getRelevanceCargoId();
        String relevanceCargoId = "";
        Cargo cargo = cargoService.get(Conds.get().eq("id", cargoId));
        if(cargo==null){
            return false;
        }
        CargoMaterialLibrary cargoMaterialLibraryOld = cargoMaterialLibraryService.get(Conds.get().eq("id", cargoMaterialLibrary.getId()));
        if (cargoMaterialLibraryOld == null) {
            return false;
        }

        if ("relevance".equals(operation)) {
            CargoMaterialLibraryRel cargoMaterialLibraryRel = new CargoMaterialLibraryRel();
            cargoMaterialLibraryRel.setId(IdGenerator.getDefault().nextId());
            cargoMaterialLibraryRel.setCargoId(Long.parseLong(cargoId));
            cargoMaterialLibraryRel.setMeterialLibraryId(cargoMaterialLibrary.getId());
            cargoMaterialLibraryRel.setCreateTime(new Date());
            cargoMaterialLibraryRelService.insert(cargoMaterialLibraryRel);

        } else {
            //删除应用货品关联
            String deleteRelevanCargoIds = "delete from cargo_material_library_rel where meterial_library_id = ? and cargo_id = ?";
            jdbcTemplate.update(deleteRelevanCargoIds, cargoMaterialLibrary.getId(), cargoId);
        }
        List<CargoMaterialLibraryRel> cargoMaterialLibraryRels = cargoMaterialLibraryRelService.list(Conds.get().eq("meterial_library_id", cargoMaterialLibrary.getId()));
        cargoMaterialLibraryOld.setRelevanceCargoId(StringUtils.join(ListUtils.getIdSet(cargoMaterialLibraryRels, "cargoId"), ","));
        cargoMaterialLibraryOld.setUpdateBy(userId);
        cargoMaterialLibraryOld.setLastUpdated(new Date());
        cargoMaterialLibraryService.update(cargoMaterialLibraryOld);


        /*String relevanceCargoIdOld = cargoMaterialLibraryOld.getRelevanceCargoId();
        Set<String> cargoIds = new LinkedHashSet<String>();
        if (StringUtils.isNotBlank(relevanceCargoIdOld)) {
            String[] relevances = relevanceCargoIdOld.split(",");
            cargoIds = new LinkedHashSet<>(Arrays.asList(relevances));
            if(operation.equals("relevance")) {
                cargoIds.add(cargoId);
            } else {

            }
        } else {
            cargoIds.add(cargoId);
        }
        cargoMaterialLibrary.setUpdateBy(userId);
        cargoMaterialLibrary.setLastUpdated(new Date());
        cargoMaterialLibrary.setRelevanceCargoId(StringUtils.join(cargoIds, ","));






        if (StringUtils.isBlank(cargoMaterialLibrary.getRelevanceCargoId())) return true;
        int sort = 0;
        for (String newCargoId : cargoIds) {
            sort += 1;
            CargoMaterialLibraryRel cargoMaterialLibraryRel = new CargoMaterialLibraryRel();
            cargoMaterialLibraryRel.setId(IdGenerator.getDefault().nextId());
            cargoMaterialLibraryRel.setCargoId(Long.parseLong(newCargoId));
            cargoMaterialLibraryRel.setMeterialLibraryId(cargoMaterialLibrary.getId());
            cargoMaterialLibraryRel.setSort(sort);
            cargoMaterialLibraryRel.setCreateTime(new Date());
            cargoMaterialLibraryRelService.insert(cargoMaterialLibraryRel);
        }*/
        return true;
    }

    @Override
    public int countRelevanceNum(String id) {
        CargoMaterialLibrary cargoMaterialLibraryOld = cargoMaterialLibraryService.get(Conds.get().eq("id", id));
        if(cargoMaterialLibraryOld==null){
            return 0;
        }
        String relevanceCargoIdOld = cargoMaterialLibraryOld.getRelevanceCargoId();
        if(relevanceCargoIdOld!=null&&!"".equals(relevanceCargoIdOld)) {
            String[] relevance = relevanceCargoIdOld.split(",");
            return relevance.length;
        }
        return 0;
    }

    @Override
    public ResultList<CargoRelevanceVo> getCargoRelevanceListPage(CargoRelevanceParam cargoRelevanceParam, Paging pg) {
        List<String> allList = new ArrayList<>();//所有的分类
        if(StringUtils.isNotBlank(cargoRelevanceParam.getClassifyId())) {
            String classify = " or cargo.classify_id like ";

            List<CargoClassify> list = cargoClassifyService.list(Conds.get().eq("parent_id", cargoRelevanceParam.getClassifyId()));
            List<String> xiajiList = new ArrayList<>();//有下级的分类
            if (list.size() > 0) {
                for (CargoClassify cargoClassify : list) {
                    allList.add(classify + " '%"+cargoClassify.getId() + "%' ");
                    xiajiList.add(cargoClassify.getId() + "");
                }
            }
            List<CargoClassify> lists = cargoClassifyService.list(Conds.get().in("parent_id", xiajiList));//查询下级的下级
            if (lists.size() > 0) {
                for (CargoClassify cargoClassify : lists) {
                    allList.add(classify + " '%"+cargoClassify.getId() + "%' ");
                }
            }
            allList = new ArrayList(new HashSet(allList));//去重 allList.add();
            cargoRelevanceParam.setClassifyId(" cargo.classify_id like '%"+cargoRelevanceParam.getClassifyId()+"%'" + StringUtils.join(allList," "));
        }
        CargoMaterialLibrary cargoMaterialLibrary = cargoMaterialLibraryService.get(Conds.get().eq("id", cargoRelevanceParam.getMaterialId()));
        String[] relevanceCargoId = null;
        if(cargoMaterialLibrary.getRelevanceCargoId()!=null&&!"".equals(cargoMaterialLibrary.getRelevanceCargoId())) {
            relevanceCargoId = cargoMaterialLibrary.getRelevanceCargoId().split(",");
            cargoRelevanceParam.setCargoRelevanceId(cargoMaterialLibrary.getRelevanceCargoId());
        }
        String cargoListStmt = FreeMarkerHelper.getValueFromTpl("sql/cargo/queryCargoRelevanceList.sql", cargoRelevanceParam);
        RowMapper<CargoRelevanceVo> cargoVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(CargoRelevanceVo.class);
        List<CargoRelevanceVo> cargoVoList = jdbcTemplate.query(cargoListStmt, cargoVoRowMapper, pg.getPs(), pg.getPn());
        for (CargoRelevanceVo cargoRelevanceVo : cargoVoList) {
            if(relevanceCargoId!=null) {//判断是否关联
                for (String cargoId : relevanceCargoId) {
                    if(cargoRelevanceVo.getCargoId().equals(cargoId)){
                        cargoRelevanceVo.setIsRelevance("T");
                        break;
                    }
                }
                if(!"T".equals(cargoRelevanceVo.getIsRelevance())){
                    cargoRelevanceVo.setIsRelevance("F");
                }
            }else {
                cargoRelevanceVo.setIsRelevance("F");
            }
        }
        String countCargoListStmt = FreeMarkerHelper.getValueFromTpl("sql/cargo/countCargoRelevanceList.sql", cargoRelevanceParam);
        Integer totalCount = jdbcTemplate.queryForObject(countCargoListStmt, Integer.class);

        ResultList<CargoRelevanceVo> cargoVoResultList = new ResultList<CargoRelevanceVo>();
        cargoVoResultList.setResultList(cargoVoList);
        cargoVoResultList.setTotalRecords(totalCount);
        return cargoVoResultList;
    }

    @Override
    public List<ImageVo> queryImage(String id) {
        CargoMaterialLibrary cargoMaterialLibrary = cargoMaterialLibraryService.get(Conds.get().eq("id", id));
        if(cargoMaterialLibrary==null){
            return null;
        }else{
            if(cargoMaterialLibrary.getImage()==null||"".equals(cargoMaterialLibrary.getImage())){
                return null;
            }
        }
        String imageArray[] = cargoMaterialLibrary.getImage().split(",");
        String sql = "select id, groupId, pic_url, create_time, create_by from image where image.id = ?";
        RowMapper<ImageVo> ImageMapper = ParameterizedBeanPropertyRowMapper.newInstance(ImageVo.class);
        List<ImageVo> imageVoList=new ArrayList<ImageVo>();
        for(String imageId:imageArray){
            Image image = imageService.get(Conds.get().eq("id", imageId));
            ImageVo imageVo = new ImageVo();
            imageVo.setId(image.getId() + "");
            imageVo.setPicUrl(image.getPicUrl());
            imageVoList.add(imageVo);
        }
        return imageVoList;
    }
}
