package pub.makers.shop.cargo.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lantu.base.common.entity.BoolType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
import pub.makers.shop.cargo.entity.CargoBrand;
import pub.makers.shop.cargo.entity.vo.CargoBrandVo;
import pub.makers.shop.store.vo.ImageVo;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/5/21.
 */
@Service(version = "1.0.0")
public class CargoBrandAdminServiceImpl implements CargoBrandMgrBizService {

    private Logger logger = LoggerFactory.getLogger(CargoBrandAdminServiceImpl.class);

    @Autowired
    private CargoBrandService cargoBrandService;

    @Reference(version = "1.0.0")
    private CargoImageBizService cargoImageBizService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<CargoBrandVo> findCargoBrandListAll() {
        List<CargoBrand> cargoBrands = cargoBrandService.list(Conds.get().eq("del_flag", BoolType.F.name()).order("sort DESC"));
        List<CargoBrandVo> cargoBrandVos = Lists.newArrayList();
        for (CargoBrand cargoBrand : cargoBrands) {
            CargoBrandVo cargoBrandVo = new CargoBrandVo();
            BeanUtils.copyProperties(cargoBrand, cargoBrandVo);
            cargoBrandVo.setId(cargoBrand.getId() + "");
            cargoBrandVos.add(cargoBrandVo);
        }
        return cargoBrandVos;
    }
    @Override
    public ResultList<CargoBrandVo> selectCargoBrandList(String cargoBrandName,Paging paging) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("cargoBrandName",cargoBrandName);
        String sql = FreeMarkerHelper.getValueFromTpl("sql/cargo/queryCargoBrandByName.sql", data);
        RowMapper<CargoBrandVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(CargoBrandVo.class);
        ResultList<CargoBrandVo> resultList = new ResultList<CargoBrandVo>();
        try {
            List<CargoBrandVo> list = jdbcTemplate.query(sql,rowMapper,paging.getPs(),paging.getPn());

            String countSql = FreeMarkerHelper.getValueFromTpl("sql/cargo/countCargoBrandByName.sql", data);
            Number total = jdbcTemplate.queryForObject(countSql,null,Integer.class);

            resultList.setResultList(list);
            resultList.setTotalRecords(total!=null?total.intValue():0);

        } catch (Exception e) {
            logger.error("查询商品栏目异常:", e);
        }
        return resultList;

    }

    @Override
    public Map<String,Object> saveOrUpdateCargoBrand(CargoBrandVo cargoBrand,String userId){

        Map<String, Object> result = new HashMap<String, Object>();
        CargoBrand cargoBrand1 = cargoBrandService.createEntity();
        if (cargoBrand != null) {
            if (null == cargoBrand.getName() || "".equals(cargoBrand.getName())) {
                result.put("success", false);
                result.put("msg", "请输入品牌名称");
                return result;
            }

            // TODO 这个变量名必须吐槽
            // 这个list仅仅使用了两次.size()方法
            // 可以考虑不返回list对象，返回count数量
            Map<String, Object> data = Maps.newHashMap();
            data.put("cargoBrandName",cargoBrand.getName());
            String countSql = FreeMarkerHelper.getValueFromTpl("sql/cargo/countBySameBrandName.sql", data);
            int count = jdbcTemplate.queryForObject(countSql,null,Integer.class);

          //  Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
            cargoBrand1.setName(cargoBrand.getName());

            if (cargoBrand.getId() == null ||  "".equals(cargoBrand.getId()) ) {
                if (count > 0) {
                    result.put("success", false);
                    result.put("msg", "该品牌名称已经存在!");
                    return result;
                }
                //cargoBrand.setId(IdGenerator.getDefault().nextId() + "");
                cargoBrand1.setId(IdGenerator.getDefault().nextId());
                // 如果传过来的图片不为空。则保存记录
                if (cargoBrand.getLogo() != null && !"".equals(cargoBrand.getLogo())) {
                    ImageVo imageVo1 = new ImageVo();
                    imageVo1.setPicUrl(cargoBrand.getLogo());
                    String poloId = cargoImageBizService.saveImage(imageVo1,userId);
                    cargoBrand1.setLogo(poloId);
                }

                // 如果传过来的图片不为空。则保存记录
                if (cargoBrand.getPcLogo() != null && !"".equals(cargoBrand.getPcLogo())) {
                    ImageVo imageVo1 = new ImageVo();
                    imageVo1.setPicUrl(cargoBrand.getPcLogo());
                    String pclLog = cargoImageBizService.saveImage(imageVo1,userId);
                    cargoBrand1.setPcLogo(pclLog);
                }

                cargoBrand.setCreateTime(new Date());
                if (userId != null && userId != "") {
                    cargoBrand1.setCreateBy(Long.parseLong(userId));
                    cargoBrand1.setUpdateBy(Long.parseLong(userId));
                }
                cargoBrand.setUpdateTime(new Date());
                /*CargoBrandDo cargoBrandDo = cargoBrandRepository.create(cargoBrand);            //增加
                cargoBrandDo.insert();*/
                cargoBrand1.setSort(cargoBrand.getSort());
                cargoBrand1.setUrl(cargoBrand.getUrl() != null && cargoBrand.getUrl() != "" ? cargoBrand.getUrl() : "");
                cargoBrand1.setSupplierName(cargoBrand.getSupplierName() != null && cargoBrand.getSupplierName() != "" ? cargoBrand.getSupplierName() : "");
                cargoBrand1.setBrandRecommendation(cargoBrand.getBrandRecommendation());
                cargoBrand1.setDelFlag("F");
                cargoBrandService.insert(cargoBrand1);
            } else {
                cargoBrand1.setId(Long.parseLong(cargoBrand.getId()));

                CargoBrand sameCargoBrand = cargoBrandService.get(Conds.get().eq("name",cargoBrand.getName()).eq("del_flag","F"));

                if ( !sameCargoBrand.getId().equals(cargoBrand1.getId()) && sameCargoBrand.getId() != null) {
                    result.put("success", false);
                    result.put("msg", "该品牌名称已经存在!");
                    return result;
                }
                CargoBrand oldcargoBrand = cargoBrandService.get(Conds.get().eq("id", cargoBrand.getId()).eq("del_flag", "F"));
                // 如果之前图片为空
                if (oldcargoBrand.getLogo() == null || "".equals(oldcargoBrand.getLogo())) {
                    // 如果传过来的图片不为空。则保存记录
                    if (cargoBrand.getLogo() != null && !"".equals(cargoBrand.getLogo())) {
                        ImageVo imageVo1 = new ImageVo();
                        imageVo1.setPicUrl(cargoBrand.getLogo());
                        String poloId = cargoImageBizService.saveImage(imageVo1,userId);
                        cargoBrand1.setLogo(poloId);
                    }
                } else {
                    // 如果传过来的图片不为空。则更新记录
                    if (cargoBrand.getLogo() != null && !"".equals(cargoBrand.getLogo())) {
                        // 查询图片记录并更新
                        cargoImageBizService.deleteImage(oldcargoBrand.getLogo());         //删除原有ID
                        ImageVo imageVo1 = new ImageVo();
                        imageVo1.setPicUrl(cargoBrand.getLogo());
                        String poloId = cargoImageBizService.saveImage(imageVo1,userId);   //保存新ID
                        cargoBrand1.setLogo(poloId);
                    } else {
                        // 如果传过来的图片为空，则删除记录
                        cargoImageBizService.deleteImage(oldcargoBrand.getLogo());
                        cargoBrand1.setLogo(null);
                    }
                }

                // 如果之前图片为空
                if (oldcargoBrand.getPcLogo() == null || "".equals(oldcargoBrand.getPcLogo())) {
                    // 如果传过来的图片不为空。则保存记录
                    if (cargoBrand.getPcLogo() != null && !"".equals(cargoBrand.getPcLogo())) {
                        ImageVo imageVo1 = new ImageVo();
                        imageVo1.setPicUrl(cargoBrand.getPcLogo());
                        String pclLog = cargoImageBizService.saveImage(imageVo1,userId);
                        cargoBrand1.setPcLogo(pclLog);
                    }
                } else {
                    // 如果传过来的图片不为空。则更新记录
                    if (cargoBrand.getPcLogo() != null && !"".equals(cargoBrand.getPcLogo())) {
                        // 查询图片记录并更新
                        cargoImageBizService.deleteImage(oldcargoBrand.getPcLogo());        //先删除

                        ImageVo imageVo1 = new ImageVo();
                        imageVo1.setPicUrl(cargoBrand.getPcLogo());
                        String pclLog = cargoImageBizService.saveImage(imageVo1,userId);  //再保存
                        cargoBrand1.setPcLogo(pclLog);
                    } else {
                        // 如果传过来的图片为空，则删除记录
                        cargoImageBizService.deleteImage(oldcargoBrand.getPcLogo());
                        cargoBrand1.setPcLogo(null);
                    }
                }
                cargoBrand1.setSort(cargoBrand.getSort());
                cargoBrand1.setUrl(cargoBrand.getUrl());
                cargoBrand1.setSupplierName(cargoBrand.getSupplierName());
                cargoBrand1.setBrandRecommendation(cargoBrand.getBrandRecommendation());
                cargoBrand1.setUpdateTime(new Date());
                cargoBrand1.setDelFlag("F");
                if (userId != null) {
                    cargoBrand1.setUpdateBy(Long.parseLong(userId));
                }
                cargoBrandService.update(cargoBrand1);
            }
            result.put("success", true);
        } else {
            result.put("success", false);
            result.put("msg", "品牌信息不能为空");
        }
        return result;
    }

    /**
     * 删除品牌信息
     */
    @Override
    public Map<String, Object> deleteCargoBrand(String idStr) {
        String[] Ids = idStr.split(",");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            for (String id : Ids) {
                // 根据id查询
            /*    CargoBrand oldcargoBrand = cargoBrandService.getById(id);
                // 删除图片
                if (oldcargoBrand.getLogo() != null && !"".equals(oldcargoBrand.getLogo())) {
                    cargoImageBizService.deleteImage(oldcargoBrand.getLogo());
                }
                if (oldcargoBrand.getPcLogo() != null && !"".equals(oldcargoBrand.getPcLogo())) {
                    cargoImageBizService.deleteImage(oldcargoBrand.getPcLogo());
                }*/
                // 删除
                cargoBrandService.update(Update.byId(id).set("del_flag","T"));
            }
            result.put("success", true);
        }catch (Exception e){
            result.put("success", e.getMessage());
        }

        return result;
    }

}
