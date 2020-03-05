package pub.makers.shop.marketing.service;

import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.marketing.entity.Toutiao;

import java.util.List;

public interface ToutiaoBizService {

	List<Toutiao> listByParams(Toutiao params);
	
	List<Toutiao> list(Toutiao params);
	
	Toutiao merge(Toutiao toutiao);
	
	void deleteById(String id);
	
	/**
	 * 查询用户的头条信息
	 * @param userId
	 * @param classifys 逗号连接，支持多个classify拼接:如good,new
	 * @param pi
	 * @return
	 */
	List<Toutiao> listByUser(String userId, String target, String classifys, Paging pi);
	
	/**
	 * 用户删除一条头条信息
	 * @param userId
	 * @param tag
	 * @param toutiaoId
	 */
	void delUserToutiao(String userId, String classify, String toutiaoId);
	
	
	/**
	 * 删除用户的所有头条信息
	 * @param userId
	 * @param tag
	 */
	void delAllUserToutiao(String userId, String classifys);
	
	
	/**
	 * 根据主键查询一条头条详情
	 * @param toutiaoId
	 * @return
	 */
	Toutiao getById(String toutiaoId);
	
	
	/**
	 * 查询未读的消息数
	 * @param userId
	 * @param tag
	 * @return
	 */
	int countUnreaded(String userId, String target, String classifys);
	
	
	/**
	 * 标记一条为已读
	 * @param userId
	 * @param classify
	 */
	void markAsReaded(String userId, String toutiaoId, String classify);

	/**
	 * 头条列表
	 * @param tag gg：公告 cx：促销
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	ResultData getToutiaoList(String tag, Integer pageNum, Integer pageSize);
}
