package pub.makers.shop.marketing.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.marketing.dao.OnlineStudyDao;
import pub.makers.shop.marketing.dao.OnlineStudyPreviewDao;
import pub.makers.shop.marketing.entity.OnlineStudy;
import pub.makers.shop.marketing.entity.OnlineStudyType;
import pub.makers.shop.marketing.vo.OnlineStudyVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(version="1.0.0")
public class OnlineStudyBizServiceImpl implements OnlineStudyBizService{

	@Autowired
	private OnlineStudyPreviewDao studyPreviewDao;
	@Autowired
	private OnlineStudyDao onlineStudyDao;
	@Autowired
	private OnlineStudyService onlineStudyService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private OnlineStudyTypeService onlineStudyTypeService;

	public OnlineStudy savePreview(OnlineStudy onlineStudy) {
		
		onlineStudy.setCreateTime(new Date());
		if (onlineStudy.getID() == null){
			onlineStudy.setID(new Date().getTime());
		}
		// 删除原有的
		studyPreviewDao.deleteById(onlineStudy.getID());
		return studyPreviewDao.add(onlineStudy);
	}

	public OnlineStudy getPreviewById(String id) {
		
		return jdbcTemplate.queryForObject("select e.*, t.NAME as study_type_name from event_online_study_preview e left join event_online_study_type t on e.study_type = t.id where e.id = ?", 
				new BeanPropertyRowMapper<OnlineStudy>(OnlineStudy.class), id);
	}

	@Override
	public List<OnlineStudy> queryOnlineStudyByTitle(String title) {
		return onlineStudyDao.list(Conds.get().eq("title", title));
	}

	@Override
	public OnlineStudy getOnlineStudyById(String id) {
		return onlineStudyDao.getById(id);
	}


	@Override
	public ResultData getOnlineStudyList(String tag, Integer pageNum, Integer pageSize) {
		Map<String, Object> map = Maps.newHashMap();

		// 类别
		OnlineStudyType onlineStudyType = onlineStudyTypeService.get(Conds.get().eq("tag", tag));
		map.put("type", onlineStudyType.getName());

		// 文章总数
		Long count = countByChildType(onlineStudyType.getId().toString());
		map.put("count", count);

		// 文章列表
		List<OnlineStudyVo> onlineStudyVoList = listByChildType(onlineStudyType.getId().toString(), pageNum, pageSize);
		map.put("studyList", onlineStudyVoList);

		ResultData resultData = new ResultData(0, map);
		return resultData;
	}

	@Override
	public OnlineStudyVo getById(String id) {
		OnlineStudy onlineStudy = onlineStudyService.getById(id);
		OnlineStudyVo vo = OnlineStudyVo.fromOnlineStudy(onlineStudy);
		OnlineStudyType type = onlineStudyTypeService.getById(vo.getStudyType());
		if (type != null) {
			vo.setStudyTypeName(type.getName());
		}
		return vo;
	}

	@Override
	public ResultList<OnlineStudyVo> findStudyAllList(OnlineStudyVo onlineStudyVo, Paging pg) {

		String listOnlineStudyStmt = FreeMarkerHelper.getValueFromTpl("sql/onlineStudy/listOnlineStudyByConditions.sql", onlineStudyVo);

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

	private Long countByChildType(String type) {
		Long count = onlineStudyService.count(Conds.get().like("study_child_type", type));
		return count;
	}

	private List<OnlineStudyVo> listByChildType(String type, Integer pageNum, Integer pageSize) {
		List<OnlineStudy> onlineStudyList = onlineStudyService.list(Conds.get().like("study_child_type", type).page((pageNum - 1) * pageSize, pageSize).order("create_time desc"));
		List<OnlineStudyVo> voList = Lists.transform(onlineStudyList, new Function<OnlineStudy, OnlineStudyVo>() {
			@Override
			public OnlineStudyVo apply(OnlineStudy onlineStudy) {
				return OnlineStudyVo.simpleFromOnlineStudy(onlineStudy);
			}
		});
		return voList;
	}

}
