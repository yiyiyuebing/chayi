package pub.makers.shop.marketing.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.dev.base.utils.UUIDUtils;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import pub.makers.daotemplate.vo.Cond;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.marketing.dao.ToutiaoUserRelDao;
import pub.makers.shop.marketing.entity.Toutiao;
import pub.makers.shop.marketing.entity.ToutiaoUserRel;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(version="1.0.0")
public class ToutiaoBizServiceImpl implements ToutiaoBizService{

	@Autowired
	private ToutiaoService toutiaoService;
	@Autowired
	private ToutiaoUserRelDao userRelDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static final Log logs = LogFactory.getLog(ToutiaoBizService.class);

	public List<Toutiao> listByParams(Toutiao params) {

		List<Cond> condList = Lists.newArrayList();
		if (StringUtils.isNotBlank(params.getTarget())){
			condList.add(Cond.eq("target", params.getTarget()));
		}
		// 标题模糊查询
		if (StringUtils.isNotBlank(params.getTitle())){
			condList.add(Cond.like("title", params.getTitle()));
		}

		if (params.getValidStart() != null){
			condList.add(Cond.gte("validStart", params.getValidStart()));
		}
		if (params.getValidEnd() != null){
			condList.add(Cond.lte("validEnd", params.getValidEnd()));
		}
		if (StringUtils.isNotBlank(params.getTag())){
			condList.add(Cond.eq("tag", params.getTag()));
		}
		if (StringUtils.isNoneBlank(params.getClassify())) {
			condList.add(Cond.eq("classify", params.getClassify()));
		}

		return toutiaoService.list(Conds.get().addAll(condList).order("sort desc"));
	}

	public List<Toutiao> list(Toutiao params) {

		List<Cond> condList = Lists.newArrayList();
		if (StringUtils.isNotBlank(params.getTarget())){
			condList.add(Cond.eq("target", params.getTarget()));
		}

		return toutiaoService.list(Conds.get().addAll(condList).eq("is_valid", params.getIsValid()).page(0, 20).order("valid_start desc"));
	}

	public Toutiao merge(Toutiao toutiao) {

		if (StringUtils.isBlank(toutiao.getToutiaoId())){
			toutiao.setToutiaoId(UUIDUtils.uuid3());

			toutiaoService.insert(toutiao);
		}
		else {
			toutiaoService.update(toutiao);
		}

		return toutiao;
	}

	public void deleteById(String id) {

		toutiaoService.deleteById(id);
	}

	public List<Toutiao> listByUser(String userId, String target,String classifys, Paging pi) {

		String stmt = "select * from ba_toutiao where toutiao_id not in (select toutiao_id from ba_toutiao_user_rel where user_id = ? and del_flag = 'T') and target = ? and classify in (%s) limit ?, ?";
		String stmt1 = processTags(stmt, classifys);

		List<Toutiao> toutiaoList = jdbcTemplate.query(stmt1,
				new BeanPropertyRowMapper<Toutiao>(Toutiao.class),
				userId,target, pi.getPs(), pi.getPn());

		// 如果头条的userRel不为空，则为已读
		List<ToutiaoUserRel> relList = userRelDao.list(Conds.get("toutiao_id").in("toutiao_id", ListUtils.getIdSet(toutiaoList, "toutiaoId")).eq("readFlag", BoolType.T.name()).eq("userId", userId));
		ListUtils.join(toutiaoList, relList, "toutiaoId", "toutiaoId", "userRel");

		return toutiaoList;
	}

	private String processTags(String stmt, String classifys){
		String[] tarArr = classifys.split(",");
		for (int i = 0; i < tarArr.length; i++){
			tarArr[i] = String.format("'%s'", tarArr[i]);
		}

		String tagsIncond = Joiner.on(",").join(tarArr);

		return String.format(stmt, tagsIncond);
	}

	public void delUserToutiao(String userId, String classify, String toutiaoId) {

		// 查询是否有删除记录
		ToutiaoUserRel rel = userRelDao.get(Conds.get().eq("userId", userId).eq("toutiaoId", toutiaoId));
		// 如果没有则新增一条删除记录
		if (rel != null){
			userRelDao.update(Update.byId(rel.getRelId()).set("del_flag", BoolType.T.name()));
		}
		else {
			rel = new ToutiaoUserRel();
			rel.setRelId(UUIDUtils.uuid3());
			rel.setUserId(userId);
			rel.setToutiaoId(toutiaoId);
			rel.setDelFlag(BoolType.T.name());
			rel.setClassify(classify);

			userRelDao.add(rel);
		}
	}

	public void delAllUserToutiao(String userId, String classifys) {

		// 将已关联的头条删除状态设置为T
		String updateStmt = "update ba_toutiao_user_rel set del_flag = 'T' where user_id = ? and  classify in (%s)";
		String updateStmt1 = processTags(updateStmt, classifys);
		jdbcTemplate.update(updateStmt1, userId);

		String stmt = "select toutiao_id, classify from ba_toutiao where toutiao_id not in (select toutiao_id from ba_toutiao_user_rel where user_id = ?) and classify in (%s)";
		String stmt1 = processTags(stmt, classifys);
		// 查询用户关联头条
		List<Toutiao> toutiaoList = jdbcTemplate.query(stmt1,
				new BeanPropertyRowMapper<Toutiao>(Toutiao.class), userId);
		List<ToutiaoUserRel> relList = Lists.newArrayList();
		for (Toutiao t : toutiaoList){
			ToutiaoUserRel rel = new ToutiaoUserRel();
			rel.setRelId(UUIDUtils.uuid3());
			rel.setUserId(userId);
			rel.setToutiaoId(t.getToutiaoId());
			rel.setDelFlag(BoolType.T.name());
			rel.setClassify(t.getClassify());

			relList.add(rel);
		}

		// 添加删除记录
		userRelDao.batchInsert(relList);
	}

	public Toutiao getById(String toutiaoId) {

		return toutiaoService.getById(toutiaoId);
	}

	public int countUnreaded(String userId, String target, String classifys) {

		String stmt = "select count(1) from ba_toutiao where toutiao_id not in (select toutiao_id from ba_toutiao_user_rel where user_id = ?) and classify in (%s) and target=? and now() between valid_start and valid_end";
		String stmt1 = processTags(stmt, classifys);
		logs.info(stmt1);
		//int count = jdbcTemplate.queryForInt(stmt1, userId);
		Number count = jdbcTemplate.queryForObject(stmt1,Integer.class, userId, target);
		return (count != null ? count.intValue() : 0);
	}

	public void markAsReaded(String userId, String toutiaoId, String classify) {

		ToutiaoUserRel rel = userRelDao.get(Conds.get().eq("userId", userId).eq("toutiaoId", toutiaoId));
		// 如果没有则新增一条删除记录
		if (rel != null){
			userRelDao.update(Update.byId(rel.getRelId()).set("read_flag", BoolType.T.name()));
		}
		else {
			rel = new ToutiaoUserRel();
			rel.setRelId(UUIDUtils.uuid3());
			rel.setUserId(userId);
			rel.setToutiaoId(toutiaoId);
			rel.setReadFlag(BoolType.T.name());
			rel.setClassify(classify);

			userRelDao.add(rel);
		}

	}

	@Override
	public ResultData getToutiaoList(String tag, Integer pageNum, Integer pageSize) {
		Map<String, Object> map = Maps.newHashMap();

		// 标签
        if ("gg".equals(tag)) {
            map.put("tag", "公告");
        } else if ("cx".equals(tag)) {
            map.put("tag", "促销");
        }
        // 促销总数
        Long count = countByTag(tag);
		map.put("count", count);

		// 促销列表
		List<Toutiao> toutiaoList = listByTag(tag, pageNum, pageSize);
		map.put("toutiaoList", toutiaoList);

		ResultData resultData = new ResultData(0, map);
		return resultData;
	}

	private Long countByTag(String tag) {
		Long count = toutiaoService.count(Conds.get().eq("target", "bizcp").eq("is_valid", BoolType.T.name()).lte("valid_start", new Date())
				.gte("valid_end", new Date()).eq("tag", tag));
		return count;
	}

	private List<Toutiao> listByTag(String tag, Integer pageNum, Integer pageSize) {
		List<Toutiao> toutiaoList = toutiaoService.list(Conds.get().eq("target", "bizcp").eq("is_valid", BoolType.T.name())
				.lte("valid_start", new Date()).gte("valid_end", new Date()).eq("tag", tag).page((pageNum - 1) * pageSize, pageSize)
				.order("sort desc"));
		return toutiaoList;
	}

}
