package pub.makers.shop.baseGood.service;

import pub.makers.shop.baseGood.pojo.TplQuery;
import pub.makers.shop.cargo.vo.GoodPageTplVo;

/**
 * 页面模板服务
 * @author apple
 *
 */
public interface PageTplBizService {

	GoodPageTplVo findMatchTpl(TplQuery query);
}
