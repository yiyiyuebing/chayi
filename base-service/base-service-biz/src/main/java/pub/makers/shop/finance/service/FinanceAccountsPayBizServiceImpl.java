package pub.makers.shop.finance.service;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.dubbo.config.annotation.Service;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.utils.DateParseUtil;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.finance.entity.FinanceAccountsPay;
import pub.makers.shop.finance.vo.FinanceAccountsPayParams;
import pub.makers.shop.finance.vo.FinanceAccountspayVo;
import pub.makers.shop.marketing.service.ToutiaoBizService;


@Service(version="1.0.0")
public class FinanceAccountsPayBizServiceImpl implements FinanceAccountsPayBizService{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private FinanceAccountsPayService financeAccountsPayService;
	
	private final String listStmt = "SELECT fa.order_id as orderid, fa.u8_order_id as u8orderid, fa.u8_accounts_id as u8accountsid, fa.statue as statue, (case when i.name is null then po.order_no else i.name end) as name FROM finance_accountspay fa LEFT JOIN indent i on fa.order_id = i.id LEFT JOIN purchase_order po on po.id = fa.order_id where ((i.name like ? or po.order_no like ?) and fa.u8_order_id like ? and fa.u8_accounts_id like ?) order by fa.create_time desc limit ?,?";
	private final String countStmt = "SELECT count(*) FROM finance_accountspay fa LEFT JOIN indent i on fa.order_id = i.id LEFT JOIN purchase_order po on po.id = fa.order_id WHERE ((i.name like ? or po.order_no like ?) and fa.u8_order_id like ? and fa.u8_accounts_id like ?)";
	private final String listFinanceAbnormalStmt = "select fa.order_id as orderid, fa.u8_order_id as u8orderid, fa.u8_accounts_id as u8accountsid, fa.statue as statue, (case when i.name is null then po.order_no else i.name end) as name from finance_accountspay fa LEFT JOIN indent i on fa.order_id = i.id LEFT JOIN purchase_order po on po.id = fa.order_id where 1 = 1";
	private final String countFinanceAbnormallistStmt = "select count(*) from finance_accountspay fa LEFT JOIN indent i on fa.order_id = i.id LEFT JOIN purchase_order po on po.id = fa.order_id where 1 = 1";
	public ResultList<FinanceAccountspayVo> listByParams(FinanceAccountsPayParams params, Paging pg) {
		
		// 处理查询参数
		String name = StringUtils.isBlank(params.getName()) ? "%%" : "%" + params.getName() + "%";
		String order_no = StringUtils.isBlank(params.getName()) ? "%%" : "%" + params.getName() + "%";
		String u8orderId = StringUtils.isBlank(params.getU8orderId()) ? "%%" : "%" + params.getU8orderId() + "%";
		String u8AccountId = StringUtils.isBlank(params.getU8AccountId()) ? "%%" : "%" + params.getU8AccountId() + "%";

		RowMapper<FinanceAccountspayVo> financeAccountsPayRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(FinanceAccountspayVo.class);

		List<FinanceAccountspayVo> resultList = jdbcTemplate.query(listStmt, financeAccountsPayRowMapper, name, order_no, u8orderId, u8AccountId, pg.getPs(), pg.getPn());
		Number total = jdbcTemplate.queryForObject(countStmt, Integer.class, name, order_no, u8orderId, u8AccountId);
		ResultList<FinanceAccountspayVo> result = new ResultList<FinanceAccountspayVo>();
		result.setTotalRecords(total != null ? total.intValue() : 0);
		result.setResultList(resultList);
		return result; //ResultList.createSuccess(total != null ? total.intValue() : 0, resultList);
	}

	@Override
	public ResultList<FinanceAccountspayVo> listFinanceAbnormalParams(FinanceAccountsPayParams params, Paging paging) {

		RowMapper<FinanceAccountspayVo> financeAccountsPayRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(FinanceAccountspayVo.class);

		StringBuffer sql = new StringBuffer(listFinanceAbnormalStmt);
		StringBuffer sqlCount = new StringBuffer(countFinanceAbnormallistStmt);
		List paramLists = new ArrayList();
		if (params.getOrderType() == null) {
			sql.append(" and (fa.u8_order_id is null or fa.u8_order_id='' or fa.u8_accounts_id is null or fa.u8_accounts_id='')");
			sqlCount.append(" and (fa.u8_order_id is null or fa.u8_order_id='' or fa.u8_accounts_id is null or fa.u8_accounts_id='')");
		}
		if ("0".equals(params.getOrderType())) {
			sql.append(" and (fa.u8_order_id is null or fa.u8_order_id='')");
			sqlCount.append(" and (fa.u8_order_id is null or fa.u8_order_id='')");
		}
		if ("1".equals(params.getOrderType())) {
			sql.append(" and (fa.u8_accounts_id is null or fa.u8_accounts_id='')");
			sqlCount.append(" and (fa.u8_accounts_id is null or fa.u8_accounts_id='')");
		}
		if (params.getStatus() != null) {
			sql.append(" and fa.statue = ?");
			sqlCount.append(" and fa.statue = ?");
			paramLists.add(params.getStatus());
		}

		//查询数量
		Number total = jdbcTemplate.queryForObject(sqlCount.toString(), Integer.class, paramLists.toArray());

		sql.append(" order by fa.id desc");
		sql.append(" limit ?, ?");
		paramLists.add(paging.getPs());
		paramLists.add(paging.getPn());
		List<FinanceAccountspayVo> financeAccountsPays = jdbcTemplate.query(sql.toString(), paramLists.toArray(), financeAccountsPayRowMapper);

		ResultList<FinanceAccountspayVo> result = new ResultList<FinanceAccountspayVo>();
		result.setTotalRecords(total != null ? total.intValue() : 0);
		result.setResultList(financeAccountsPays);
		return result;
	}

	@Override
	public FinanceAccountsPay getByOrderId(String orderId) {
		FinanceAccountsPay financeAccountsPay = financeAccountsPayService.get(Conds.get().eq("order_id", orderId));
		return financeAccountsPay;
	}

	@Override
	public FinanceAccountsPay saveOrUpdate(FinanceAccountsPay financeAccountsPay) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (financeAccountsPay == null) {
			result.put("success", false);
			result.put("msg", "收款单不能为空！");
		}
		if (financeAccountsPay.getId() == null || "".equals(financeAccountsPay.getId())) {
			financeAccountsPay.setId(IdGenerator.getDefault().nextId());
			financeAccountsPay.setCode("skd" + DateParseUtil.formatDate(new Date(), "yyyyMMddHHmmssS"));
			financeAccountsPay.setCreateTime(new Date());
			financeAccountsPay.setUpdateTime(new Date());
			financeAccountsPayService.insert(financeAccountsPay);
			return financeAccountsPay;
		} else {
			financeAccountsPay.setU8AccountsId(financeAccountsPay.getU8AccountsId());
			financeAccountsPay.setU8OrderId(financeAccountsPay.getU8OrderId());
			financeAccountsPay.setStatue(financeAccountsPay.getStatue());
			financeAccountsPay.setUpdateTime(new Date());
			financeAccountsPayService.update(financeAccountsPay);
			return financeAccountsPay;
		}
	}



}
