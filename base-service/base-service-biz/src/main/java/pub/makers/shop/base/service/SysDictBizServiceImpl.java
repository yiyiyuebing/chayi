package pub.makers.shop.base.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.lantu.base.common.entity.BoolType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.daotemplate.vo.Cond;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.entity.SysDict;

import java.util.List;

@Service(version="1.0.0")
public class SysDictBizServiceImpl implements SysDictBizService{
	
	@Autowired
	private SysDictService sysDictService;

	//列表查询
	public List<SysDict> list(String conditions,int start,int limit) {
		List<Cond> condList = Lists.newArrayList();
		if(StringUtils.isNotEmpty(conditions)){
			condList.add(Cond.like("value", conditions));
		}
		return sysDictService.list(Conds.get().addAll(condList).page(start, limit));
	}

	@Override
	public List<SysDict> list(String dictTyp, String code) {
		Conds conds = Conds.get();
		if (StringUtils.isNotEmpty(dictTyp)) {
			conds.eq("dict_type", dictTyp);
		}
		if (StringUtils.isNoneEmpty(code)) {
			conds.eq("code", code);
		}
		return sysDictService.list(conds.eq("is_valid", BoolType.T.name()).order("order_num desc"));
	}

	public void deleteById(String id) {
		sysDictService.deleteById(id);
		
	}

}
