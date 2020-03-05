package pub.makers.shop.finance.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.finance.entity.FinanceAccountspayItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dy on 2017/4/14.
 */
@Service(version = "1.0.0")
public class FinanceAccountspayItemBizServiceImpl implements FinanceAccountspayItemBizService {

    @Autowired
    private FinanceAccountspayItemService financeAccountspayItemService;

    @Override
    public Map<String, Object> saveOrUpdate(FinanceAccountspayItem financeAccountspayItem) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (financeAccountspayItem == null) {
            result.put("success", false);
            result.put("msg", "收款单明细不能为空！");
            return result;
        }
        if (financeAccountspayItem.getId() == null || "".equals(financeAccountspayItem.getId())) {
            financeAccountspayItem.setId(IdGenerator.getDefault().nextId());
			financeAccountspayItemService.insert(financeAccountspayItem);
        } else {
            financeAccountspayItemService.update(financeAccountspayItem);
        }
        result.put("success", true);
        return result;
    }

    @Override
    public FinanceAccountspayItem getFacpiByFacp(Long financeAccountspayId) {
        return financeAccountspayItemService.get(Conds.get().eq("mainid", financeAccountspayId));
    }
}
