package pub.makers.shop.store.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.store.entity.SalesReturnReason;
import pub.makers.shop.store.vo.SalesReturnReasonVo;

import java.util.*;

/**
 * Created by devpc on 2017/10/9.
 */
@Service(version = "1.0.0")
public class SalesReturnReasonBizAdminServiceImpl implements SalesReturnReasonBizDifService {


    @Autowired
    private SalesReturnReasonService salesReturnReasonService;

    @Override
    public SalesReturnReason getById(String id) {
        return null;
    }

    @Override
    public List<SalesReturnReasonVo> getReasonList() {
        return null;
    }

    /**
     * 查询售前售后原因列表
     * @param pg
     * @return
     */
    @Override
    public  ResultList<SalesReturnReasonVo> getSalesReturnReason(Paging pg) {
        ResultList<SalesReturnReasonVo> salesReturnReasonResultList = new ResultList<SalesReturnReasonVo>();

        List<SalesReturnReasonVo> salesReturnReasonVoList = new ArrayList<SalesReturnReasonVo>();
        List<SalesReturnReason> salesReturnReasonList = salesReturnReasonService.list(Conds.get().order("rank+0 desc").page(pg.getPs(), pg.getPn()));
        Long count = salesReturnReasonService.count(Conds.get().order("rank desc"));

        if (count>0&&salesReturnReasonList.size()>0) {
            for (SalesReturnReason salesReturnReason:salesReturnReasonList) {
                SalesReturnReasonVo salesReturnReasonVo=new SalesReturnReasonVo();
                BeanUtils.copyProperties(salesReturnReason, salesReturnReasonVo);
                if (!StringUtils.isEmpty(salesReturnReason.getId())) {
                    salesReturnReasonVo.setCopyId(salesReturnReason.getId() + "");
                    salesReturnReasonVoList.add(salesReturnReasonVo);
                }
            }
        }
        salesReturnReasonResultList.setTotalRecords(count.intValue());
        salesReturnReasonResultList.setResultList(salesReturnReasonVoList);
        return salesReturnReasonResultList;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @Override
    public Map<String, Object> deletSalesReturnReason(String ids) {
        Map<String, Object> result = new HashMap<String, Object>();
        String[] idList=ids.split(",");
        try {
            for (String id:idList) {
                if (!StringUtils.isEmpty(id)) {
                    salesReturnReasonService.delete(Conds.get().eq("id",id));
                    result.put("success", true);
                    result.put("msg", "删除成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    /**
     * 增加或修改
     * @param salesReturnReason
     * @return
     */
    @Override
    public Map<String, Object> saveOrUpdateSalesReturnReason(SalesReturnReason salesReturnReason) {
        Map<String, Object> result = new HashMap<String, Object>();
        Long count = salesReturnReasonService.count(Conds.get().eq("reason",salesReturnReason.getReason()));

            if (!StringUtils.isEmpty(salesReturnReason.getId())) {
                salesReturnReason.setUpdateTime(new Date());
                salesReturnReasonService.update(salesReturnReason);
            }else{
                if (count > 0) {
                    result.put("success", false);
                    result.put("msg", "该原因已有记录");
                    return result;
                }
                salesReturnReason.setId(IdGenerator.getDefault().nextId());
                salesReturnReason.setCreateTime(new Date());
                salesReturnReason.setUpdateTime(new Date());
                salesReturnReasonService.insert(salesReturnReason);
            }
            result.put("success", true);
            result.put("msg", "保存成功");

        return result;
    }

}
