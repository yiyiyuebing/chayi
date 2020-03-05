package pub.makers.shop.tradeGoods.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.tradeGoods.constans.ClassifyConstant;
import pub.makers.shop.tradeGoods.entity.TradeGoodsClassify;
import pub.makers.shop.tradeGoods.vo.TradeGoodsClassifyVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by kok on 2017/6/27.
 */
@Service(version = "1.0.0")
public class TradeGoodsClassifyBizServiceImpl implements TradeGoodsClassifyBizService {
    @Autowired
    private TradeGoodsClassifyService tradeGoodsClassifyService;

    @Override
    public Map<String, List<TradeGoodsClassifyVo>> getClassifyListByParentId(List<String> parentIdList) {
        List<TradeGoodsClassify> tradeGoodsClassifyList = tradeGoodsClassifyService.list(Conds.get().in("parent_id", parentIdList)
                .eq("status", 1).eq("del_flag", BoolType.F.name()).order("order_index desc,update_time desc"));
        Map<String, List<TradeGoodsClassifyVo>> classifyMap = Maps.newHashMap();
        for (TradeGoodsClassify classify : tradeGoodsClassifyList) {
            List<TradeGoodsClassifyVo> classifyVoList = classifyMap.get(classify.getParentId().toString()) == null ? Lists.newArrayList() : classifyMap.get(classify.getParentId().toString());
            classifyVoList.add(new TradeGoodsClassifyVo(classify));
            classifyMap.put(classify.getParentId().toString(), classifyVoList);
        }
        return classifyMap;
    }

    @Override
    public List<TradeGoodsClassifyVo> getClassifyListAll() {
        Map<String, List<TradeGoodsClassifyVo>> firstMap = getClassifyListByParentId(Lists.newArrayList(ClassifyConstant.CARGO_CLASSIFY + ""));
        List<TradeGoodsClassifyVo> firstList = firstMap.get(ClassifyConstant.CARGO_CLASSIFY + "");
        Map<String, List<TradeGoodsClassifyVo>> secondMap = getClassifyListByParentId(Lists.newArrayList(ListUtils.getIdSet(firstList, "id")));
        List<String> secondIdList = Lists.newArrayList();
        for (List<TradeGoodsClassifyVo> classifyVoList : secondMap.values()) {
            secondIdList.addAll(Lists.newArrayList(ListUtils.getIdSet(classifyVoList, "id")));
        }
        Map<String, List<TradeGoodsClassifyVo>> thirdMap = getClassifyListByParentId(secondIdList);
        for (TradeGoodsClassifyVo first : firstList) {
            List<TradeGoodsClassifyVo> secondList = secondMap.get(first.getId());
            if (secondList != null) {
                for (TradeGoodsClassifyVo second : secondList) {
                    second.setChildren(thirdMap.get(second.getId()));
                }
            }
            first.setChildren(secondList);
        }
        return firstList;
    }

    @Override
    public Set<String> findAllIdByParentId(Set<String> parentIdList) {
        List<TradeGoodsClassify> classifyList = tradeGoodsClassifyService.list(Conds.get().eq("status", 1).eq("del_flag", BoolType.F.name()).in("parent_id", parentIdList));
        Integer length = parentIdList.size();
        parentIdList.addAll(ListUtils.getIdSet(classifyList, "id"));
        if (parentIdList.size() == length) {
            return parentIdList;
        }
        return findAllIdByParentId(parentIdList);
    }

    @Override
    public List<TradeGoodsClassifyVo> findAllAndParent(List<String> classifyIdList) {
        List<TradeGoodsClassifyVo> classifyVoList = Lists.newArrayList();
        List<TradeGoodsClassify> classifyList = tradeGoodsClassifyService.list(Conds.get().eq("status", "1").eq("del_flag", BoolType.F.name()).in("id", classifyIdList));
        if (!classifyList.isEmpty()) {
            List<TradeGoodsClassify> parentList = tradeGoodsClassifyService.list(Conds.get().eq("status", "1").eq("del_flag", BoolType.F.name()).in("id", ListUtils.getIdSet(classifyList, "parentId")));
            if (!parentList.isEmpty()) {
                classifyList.addAll(parentList);
                List<TradeGoodsClassify> topList = tradeGoodsClassifyService.list(Conds.get().eq("status", "1").eq("del_flag", BoolType.F.name()).in("id", ListUtils.getIdSet(parentList, "parentId")));
                classifyList.addAll(topList);
            }
        }
        for (TradeGoodsClassify classify : classifyList) {
            TradeGoodsClassifyVo vo = new TradeGoodsClassifyVo();
            BeanUtils.copyProperties(classify, vo);
            vo.setId(classify.getId() + "");
            classifyVoList.add(vo);
        }
        return classifyVoList;
    }

    @Override
    public List<TradeGoodsClassifyVo> findAllByParentId(List<String> classifyIdList) {
        List<TradeGoodsClassifyVo> classifyVoList = Lists.newArrayList();
        List<TradeGoodsClassify> firstList = tradeGoodsClassifyService.list(Conds.get().eq("status", "1").eq("del_flag", BoolType.F.name()).in("id", classifyIdList));
        if (!firstList.isEmpty()) {
            List<TradeGoodsClassify> secondList = tradeGoodsClassifyService.list(Conds.get().eq("status", "1").eq("del_flag", BoolType.F.name()).in("parent_id", ListUtils.getIdSet(firstList, "id")));
            if (!secondList.isEmpty()) {
                firstList.addAll(secondList);
                List<TradeGoodsClassify> thirdList = tradeGoodsClassifyService.list(Conds.get().eq("status", "1").eq("del_flag", BoolType.F.name()).in("parent_id", ListUtils.getIdSet(secondList, "id")));
                firstList.addAll(thirdList);
            }
        }
        for (TradeGoodsClassify classify : firstList) {
            TradeGoodsClassifyVo vo = new TradeGoodsClassifyVo();
            BeanUtils.copyProperties(classify, vo);
            classifyVoList.add(vo);
        }
        return classifyVoList;
    }

    @Override
    public List<TradeGoodsClassify> getDisableClassifyList() {
        return tradeGoodsClassifyService.list(Conds.get().eq("status", 0).eq("del_flag", BoolType.F.name()).order("order_index desc,update_time desc"));
    }
}
