package pub.makers.shop.marketing.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.util.SqlHelper;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.marketing.entity.OnlineStudy;
import pub.makers.shop.marketing.entity.OnlineStudyType;
import pub.makers.shop.marketing.vo.OnlineStudyVo;
import pub.makers.shop.tradeGoods.entity.Image;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/5/3.
 */
@Service(version = "1.0.0")
public class OnlineStudyAdminServiceImpl implements OnlineStudyMgrBizService {

//    private final String listOnlineStudy = "select ID, title, study_type as studyType, is_share as isShare, label, (select t.name from event_online_study_type t where t.id=study_type) " +
//            "as studyTypeName, study_child_type as studyChildType, `type`, read_num as readNum, author, video_url as videoUrl, label, study_tag as studyTag, file, cove_pic as covePic, " +
//            "(select i.pic_url from Image i where i.id=cove_pic) as covePicUrl, create_time as createTime, date_format(create_time, '%Y-%c-%d') " +
//            "as createTime1, create_by as createBy, update_time as updateTime, update_by as updateBy, study_tag as studyTag, content from event_online_study " +
//            "where 1=1 AND video_show=0 AND (title like ? OR label like ? ) and study_type like ? and study_child_type like ? and type like ? order by ? ? limit ?, ?";
//    private final String countOnlineStudy = "select count(1) as count from event_online_study where 1=1 AND video_show=0 AND (title like ? OR label like ?) and study_type like ? and study_child_type like ? and type like ?";
    @Reference(version = "1.0.0")
    private OnlineStudyImageBizService onlineStudyImageBizService;
    @Autowired
    private OnlineStudyService onlineStudyService;
    @Reference(version = "1.0.0")
    private VtwoStudyGoodsBizService vtwoStudyGoodsBizService;
    @Autowired
    private OnlineStudyTypeService onlineStudyTypeService;
    @Autowired
    private VtwoStudyGoodsMgrBizService vtwoStudyGoodsMgrBizService;
    @Autowired
    private VtwoStudyReplyMgrBizService vtwoStudyReplyMgrBizService;
    @Autowired
    private VtwoStudyVisitorMgrBizService vtwoStudyVisitorMgrBizService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public OnlineStudyVo saveOnlineStudy(OnlineStudyVo onlineStudyVo) {
        // 如果传过来的图片不为空。则保存记录
        if (StringUtils.isNotBlank(onlineStudyVo.getCovePic())) {
            Image imageVo = onlineStudyImageBizService.saveStudyImage(onlineStudyVo.getCovePic());
            onlineStudyVo.setCovePic(imageVo.getId() + "");
        }
        onlineStudyVo.setCreateTime(new Date());
        onlineStudyVo.setUpdateTime(new Date());
        onlineStudyVo.setReadNum(0L);
        onlineStudyVo.setVideoShow(0);
        onlineStudyVo.setUpdateBy(onlineStudyVo.getCreateBy());

        OnlineStudy onlineStudy = new OnlineStudy();
        onlineStudy.setID(IdGenerator.getDefault().nextId());
        BeanUtils.copyProperties(onlineStudyVo, onlineStudy);
        onlineStudy.setStudyType(Long.parseLong(onlineStudyVo.getStudyType()));
        onlineStudyService.insert(onlineStudy);
        onlineStudyVo.setID(onlineStudy.getID() + "");

        //保存文章商品关联
        onlineStudyVo.setID(onlineStudyVo.getID().toString());
        if (StringUtils.isNotBlank(onlineStudyVo.getLinkGoods())) {
            vtwoStudyGoodsBizService.saveStutyGoods(onlineStudyVo);
        }

        return onlineStudyVo;
    }

    @Override
    public OnlineStudyVo updateOnlineStudy(OnlineStudy oldOnlineStudy, OnlineStudyVo onlineStudyVo) {

        // 如果之前图片为空
        if (StringUtils.isBlank(oldOnlineStudy.getCovePic())) {
            // 如果传过来的图片不为空。则保存记录
            if (StringUtils.isNotBlank(onlineStudyVo.getCovePic())) {
                Image imageVo = onlineStudyImageBizService.saveStudyImage(onlineStudyVo.getCovePic());
                oldOnlineStudy.setCovePic(imageVo.getId() + "");
            }
        } else {
            // 如果传过来的图片不为空。则更新记录
            if (StringUtils.isNotBlank(onlineStudyVo.getCovePic())) {
                // 查询图片记录并更新
                Image image = onlineStudyImageBizService.getStudyImageById(oldOnlineStudy.getCovePic());
                image.setPicUrl(onlineStudyVo.getCovePic());
                onlineStudyImageBizService.updateStudyImage(image);
            } else {
                // 如果传过来的图片为空，则删除记录
                onlineStudyImageBizService.deleteStudyImageById(Long.parseLong(oldOnlineStudy.getCovePic()));
                oldOnlineStudy.setCovePic("");
            }
        }

        oldOnlineStudy.setFile(onlineStudyVo.getFile());
        oldOnlineStudy.setTitle(onlineStudyVo.getTitle());
        oldOnlineStudy.setStudyType(Long.parseLong(onlineStudyVo.getStudyType()));
        oldOnlineStudy.setStudyChildType(onlineStudyVo.getStudyChildType());
        oldOnlineStudy.setType(onlineStudyVo.getType());
        oldOnlineStudy.setAuthor(onlineStudyVo.getAuthor());
        oldOnlineStudy.setVideoUrl(onlineStudyVo.getVideoUrl());
        oldOnlineStudy.setContent(onlineStudyVo.getContent());
        oldOnlineStudy.setIsShare(onlineStudyVo.getIsShare());
        oldOnlineStudy.setUpdateBy(oldOnlineStudy.getCreateBy());
        oldOnlineStudy.setStudyTag(onlineStudyVo.getStudyTag());
        oldOnlineStudy.setLabel(onlineStudyVo.getLabel());
        oldOnlineStudy.setMaterial(onlineStudyVo.getMaterial());
        onlineStudyService.update(oldOnlineStudy); //更新文章

        onlineStudyVo.setID(oldOnlineStudy.getID().toString());
        //删除旧的商品文章关联信息
        vtwoStudyGoodsBizService.deleteLinkGoodByStudyId(oldOnlineStudy.getID());
        if(StringUtils.isNotEmpty(onlineStudyVo.getLinkGoods())) {
            vtwoStudyGoodsBizService.saveStutyGoods(onlineStudyVo);
        }

        return onlineStudyVo;
    }

    @Override
    public ResultList<OnlineStudyVo> listOnlineStudyByConditions(OnlineStudyVo onlineStudyVo, Paging pg) {

//        String title = StringUtils.isNotBlank(onlineStudyVo.getTitle()) ? "%" + onlineStudyVo.getTitle() + "%" : "%%";
//        String label = StringUtils.isNotBlank(onlineStudyVo.getLabel()) ? "%" + onlineStudyVo.getLabel() + "%" : "%%";
//        String studyType = StringUtils.isNotBlank(onlineStudyVo.getStudyType()) && !"0".equals(onlineStudyVo.getStudyType()) ? onlineStudyVo.getStudyType() : "%%";
//        String studyChildType = StringUtils.isNotBlank(onlineStudyVo.getStudyChildType()) && !"0".equals(onlineStudyVo.getStudyChildType()) ? "%" + onlineStudyVo.getStudyChildType() + "%" : "%%";
//        String type = onlineStudyVo.getType() != null ? onlineStudyVo.getType().toString() : "%%";
//        String order = StringUtils.isNotBlank(onlineStudyVo.getOrder()) ? onlineStudyVo.getOrder() : "create_time";
//        String orderType = StringUtils.isNotBlank(onlineStudyVo.getOrderType()) ? onlineStudyVo.getOrderType() : "desc";
        Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(onlineStudyVo.getTitle())) {
            params.put("title", onlineStudyVo.getTitle());
        }
        if (StringUtils.isNotBlank(onlineStudyVo.getLabel())) {
            params.put("label", onlineStudyVo.getLabel());
        }
        if (onlineStudyVo.getType() != null) {
            params.put("type", onlineStudyVo.getType());
        }
        if (StringUtils.isNotBlank(onlineStudyVo.getOrder())) {
            params.put("order", onlineStudyVo.getOrder());
        } else {
            params.put("order", "create_time");
        }
        if (StringUtils.isNotBlank(onlineStudyVo.getOrderType())) {
            params.put("orderType", onlineStudyVo.getOrderType());
        } else {
            params.put("orderType", "desc");
        }

        if (StringUtils.isNotBlank(onlineStudyVo.getStudyType()) && !"0".equals(onlineStudyVo.getStudyType())) {
            params.put("studyType", onlineStudyVo.getStudyType());
        }
        if (StringUtils.isNotBlank(onlineStudyVo.getStudyChildType()) && !"0".equals(onlineStudyVo.getStudyChildType())) {
            params.put("studyChildType", onlineStudyVo.getStudyChildType());
        }

        String listOnlineStudyStmt = FreeMarkerHelper.getValueFromTpl("sql/onlineStudy/listOnlineStudyByConditions.sql", params);

        RowMapper<OnlineStudyVo> onlineStudyVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(OnlineStudyVo.class);

        List<OnlineStudyVo> queryOnlineStudyVoList = jdbcTemplate.query(listOnlineStudyStmt + " limit ?, ?", onlineStudyVoRowMapper, pg.getPs(), pg.getPn());

        List<OnlineStudyVo> resultOnlineStudyVoList = Lists.newArrayList();

        for (OnlineStudyVo studyVo : queryOnlineStudyVoList) {
            if (StringUtils.isNotBlank(studyVo.getStudyChildType()) && !"0".equals(studyVo.getStudyChildType())) {
                String[] studyChildTypeArr = studyVo.getStudyChildType().toString().split(",");
                String studyChildTypeName = "";
                for (String studyChildTypeId : studyChildTypeArr) {
                    OnlineStudyType onlineStudyType = onlineStudyTypeService.getById(studyChildTypeId);
                    studyChildTypeName += onlineStudyType.getName() + ",";
                }
                if (studyChildTypeName.indexOf(",") > 0) {
                    studyChildTypeName = studyChildTypeName.substring(0, studyChildTypeName.length() - 1);
                }
                studyVo.setStudyChildTypeName(studyChildTypeName);
            }
            resultOnlineStudyVoList.add(studyVo);
        }

        String countOnlineStudyStmt = "select count(1) from (" + listOnlineStudyStmt + ") a";
        Long total = jdbcTemplate.queryForObject(countOnlineStudyStmt, Long.class);
        ResultList<OnlineStudyVo> result = new ResultList<OnlineStudyVo>();
        result.setTotalRecords(total != null ? total.intValue() : 0);
        result.setResultList(resultOnlineStudyVoList);
        return result;
    }

    @Override
    public Map<String, Object> optOnlineStudyShare(String idStr, Integer isShare) {
        Map<String, Object> result = Maps.newHashMap();
        if (StringUtils.isBlank(idStr)) {
            result.put("success", false);
            result.put("msg", "请选择文章！");
            return result;
        }
        String[] idArr = idStr.split(",");
        for (String id : idArr) {
            OnlineStudy onlineStudy = onlineStudyService.getById(id);
            onlineStudy.setIsShare(isShare);
            onlineStudyService.update(onlineStudy);
        }
        result.put("success", true);
        return result;
    }

    @Override
    public Map<String, Object> findOnlineStudyDetail(String id, Long userId, String userType) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isBlank(id)) {
            result.put("code", 0);
            result.put("msg", "参数错误！");
            return result;
        }
        OnlineStudy onlineStudy = onlineStudyService.getById(id);
        if (onlineStudy == null) {
            result.put("code", 0);
            result.put("msg", "文章已被删除");
            return result;
        }
        //浏览数加1
        onlineStudy.setReadNum(onlineStudy.getReadNum() + 1);
        //更新文章
        onlineStudyService.update(onlineStudy);
        Map<String,Object> data = new HashMap<String, Object>();

        //文章主题信息
        data.put("detail", getOnlineStudyInfo(id, userId));
        //关联商品数量
        data.put("commandNum", vtwoStudyGoodsMgrBizService.getStudyGoodsCountsById(Long.parseLong(id)));
        //文章回复数量
        data.put("replyNum", vtwoStudyReplyMgrBizService.getStudyReplyCountByStudyId(userType, Long.parseLong(id)));
        //访客数量
        data.put("visitorNum", vtwoStudyVisitorMgrBizService.getStudyVisitorCountByStudyId(Long.parseLong(id), userType));

        //增加访客
        if (userId != null && userId != -1L) {
            vtwoStudyVisitorMgrBizService.saveOrUpdateStudyVisitor(Long.parseLong(id), userId, userType);
        }
        result.put("code", 1);
        result.put("msg", "成功");
        result.put("data", data);
        return result;
    }

    @Override
    public OnlineStudyVo getOnlineStudyInfo(String id, Long userId) {
        String getOnlineStudyInfoStmt = SqlHelper.getSql("sql/onlineStudy/getOnlineStudyInfo.sql");
        RowMapper<OnlineStudyVo> onlineStudyVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(OnlineStudyVo.class);
        OnlineStudyVo onlineStudyVo = jdbcTemplate.queryForObject(getOnlineStudyInfoStmt, onlineStudyVoRowMapper, userId, userId, id);

        //判断是否有学习子分类，组装学习子分类名称
        if (StringUtils.isNotBlank(onlineStudyVo.getStudyChildType()) && !"0".equals(onlineStudyVo.getStudyChildType())) {
            String[] studyChildTypeArr = onlineStudyVo.getStudyChildType().toString().split(",");
            String studyChildTypeName = "";
            for (String studyChildTypeId : studyChildTypeArr) {
                OnlineStudyType onlineStudyType = onlineStudyTypeService.getById(studyChildTypeId);
                studyChildTypeName += onlineStudyType.getName() + ",";
            }
            if (studyChildTypeName.indexOf(",") > 0) {
                studyChildTypeName = studyChildTypeName.substring(0, studyChildTypeName.length() - 1);
            }
            onlineStudyVo.setStudyChildTypeName(studyChildTypeName);
        }
        return onlineStudyVo;
    }

}
