package pub.makers.shop.tradeGoods.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.tradeGoods.dao.ImageDao;
import pub.makers.shop.tradeGoods.service.ImageService;
import pub.makers.shop.tradeGoods.entity.Image;

import java.util.List;

@Service
public class ImageServiceImpl extends BaseCRUDServiceImpl<Image, String, ImageDao>
										implements ImageService {
	@Override
	public List<Image> list(Conds conds) {
		Conds newConds = Conds.get("id, group_id as groupId, pic_url as picUrl, create_time as createTime, create_by as createBy").addAll(conds.getCondList());
		if (StringUtils.isNotEmpty(conds.getOrderRule())) {
			newConds.order(conds.getOrderRule());
		}
		if (conds.getPageStart() != null && conds.getPageSize() != null) {
			newConds.page(conds.getPageStart(), conds.getPageSize());
		}
		return super.list(newConds);
	}
}
