package pub.makers.shop.purchaseGoods.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.daotemplate.vo.Cond;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.cargo.entity.vo.CargoBrandVo;
import pub.makers.shop.purchaseGoods.constans.PurchaseClassifyConstant;
import pub.makers.shop.purchaseGoods.entity.PurchaseClassify;
import pub.makers.shop.purchaseGoods.entity.PurchaseClassifyAttr;
import pub.makers.shop.purchaseGoods.vo.PurchaseClassifyAttrVo;
import pub.makers.shop.purchaseGoods.vo.PurchaseClassifyVo;
import pub.makers.shop.store.constant.StoreLevelConstant;

import java.util.List;
import java.util.Set;

/**
 * Created by kok on 2017/6/1.
 */
@Service(version = "1.0.0")
public class PurchaseClassifyBizServiceImpl implements PurchaseClassifyBizService {
    @Autowired
    private PurchaseClassifyService purchaseClassifyService;
    @Autowired
    private PurchaseBrandBizService purchaseBrandBizService;
    @Autowired
    private PurchaseClassifyAttrService purchaseClassifyAttrService;

    @Override
    public List<PurchaseClassifyVo> findByParentId(String parentId, Integer status, String storeLevel) {
        Conds conds = Conds.get();
        if (StringUtils.isNotEmpty(parentId)) {
            conds.eq("parent_id", parentId);
        }
        if (status != null) {
            conds.eq("status", status);
        }
        // 默认店铺等级
        if (StringUtils.isEmpty(storeLevel)) {
            storeLevel = StoreLevelConstant.DEFAULT_STORE_LEVEL;
        }
        List<PurchaseClassify> classifyList = purchaseClassifyService.list(conds.like("store_level", storeLevel).eq("status", "1").eq("del_flag", BoolType.F.name()).order("order_index desc,update_time desc"));
        List<PurchaseClassifyVo> classifyVoList = Lists.newArrayList();
        for (PurchaseClassify classify : classifyList) {
            PurchaseClassifyVo vo = new PurchaseClassifyVo();
            BeanUtils.copyProperties(classify, vo);
            classifyVoList.add(vo);
        }
        return classifyVoList;
    }

    @Override
    public List<PurchaseClassifyVo> indexClassifyList(String storeLevel) {
        // 默认店铺等级
        if (StringUtils.isEmpty(storeLevel)) {
            storeLevel = StoreLevelConstant.DEFAULT_STORE_LEVEL;
        }
        List<PurchaseClassifyVo> firstList = findByParentId(PurchaseClassifyConstant.CARGO_CLASSIFY, 1, storeLevel);
        for (PurchaseClassifyVo classifyVo : firstList) {
            // 子分类
            List<PurchaseClassifyVo> secondList = findByParentId(classifyVo.getId(), 1, storeLevel);
            for (PurchaseClassifyVo purchaseClassifyVo : secondList) {
                List<PurchaseClassifyVo> thirdList = findByParentId(purchaseClassifyVo.getId(), 1, storeLevel);
                purchaseClassifyVo.setChildren(thirdList);
            }
            classifyVo.setChildren(secondList);
            // 品牌
            List<String> classifyIds = Lists.newArrayList(findAllIdByParentId(Sets.newHashSet(classifyVo.getId()), storeLevel));
            List<CargoBrandVo> brandVoList = purchaseBrandBizService.getCargoBrandList(classifyIds);
            classifyVo.setBrandList(brandVoList);
        }
        return firstList;
    }

    @Override
    public List<PurchaseClassifyAttrVo> findAttrByClassifyId(List<String> classifyIdList) {
        List<PurchaseClassifyAttrVo> attrVoList = Lists.newArrayList();
        List<PurchaseClassifyAttr> attrList = purchaseClassifyAttrService.list(Conds.get().in("pur_classify_id", classifyIdList).addAll(Lists.newArrayList(Cond.isNull("parent_id"))));
        for (PurchaseClassifyAttr attr : attrList) {
            PurchaseClassifyAttrVo vo = new PurchaseClassifyAttrVo();
            BeanUtils.copyProperties(attr, vo);
            // 子类别
            List<PurchaseClassifyAttr> children = purchaseClassifyAttrService.list(Conds.get().eq("parent_id", attr.getId()));
            vo.setChildren(Lists.transform(children, new Function<PurchaseClassifyAttr, PurchaseClassifyAttrVo>() {
                @Override
                public PurchaseClassifyAttrVo apply(PurchaseClassifyAttr purchaseClassifyAttr) {
                    PurchaseClassifyAttrVo purchaseClassifyAttrVo = new PurchaseClassifyAttrVo();
                    BeanUtils.copyProperties(purchaseClassifyAttr, purchaseClassifyAttrVo);
                    return purchaseClassifyAttrVo;
                }
            }));
            attrVoList.add(vo);
        }
        return attrVoList;
    }

    @Override
    public Set<String> findAllIdByParentId(Set<String> parentIdList, String storeLevel) {
        Conds conds = Conds.get();
        if (StringUtils.isNotEmpty(storeLevel)) {
            conds.like("store_level", storeLevel);
        }
        List<PurchaseClassify> classifyList = purchaseClassifyService.list(conds.eq("status", "1").eq("del_flag", BoolType.F.name()).in("parent_id", parentIdList));
        List<String> idList = Lists.transform(classifyList, new Function<PurchaseClassify, String>() {
            @Override
            public String apply(PurchaseClassify purchaseClassify) {
                return purchaseClassify.getId();
            }
        });
        Integer length = parentIdList.size();
        parentIdList.addAll(idList);
        if (parentIdList.size() == length) {
            return parentIdList;
        }
        return findAllIdByParentId(parentIdList, storeLevel);
    }

    @Override
    public List<PurchaseClassifyVo> findAllAndParent(List<String> classifyIdList, String storeLevel) {
        List<PurchaseClassifyVo> classifyVoList = Lists.newArrayList();
        Conds conds = Conds.get();
        if (StringUtils.isNotEmpty(storeLevel)) {
            conds.like("store_level", storeLevel);
        }
        List<PurchaseClassify> classifyList = purchaseClassifyService.list(Conds.get().addAll(conds.getCondList()).eq("status", "1").eq("del_flag", BoolType.F.name()).in("id", classifyIdList));
        if (!classifyList.isEmpty()) {
            List<PurchaseClassify> parentList = purchaseClassifyService.list(Conds.get().addAll(conds.getCondList()).eq("status", "1").eq("del_flag", BoolType.F.name()).in("id", ListUtils.getIdSet(classifyList, "parentId")));
            if (!parentList.isEmpty()) {
                classifyList.addAll(parentList);
                List<PurchaseClassify> topList = purchaseClassifyService.list(Conds.get().addAll(conds.getCondList()).eq("status", "1").eq("del_flag", BoolType.F.name()).in("id", ListUtils.getIdSet(parentList, "parentId")));
                classifyList.addAll(topList);
            }
        }
        for (PurchaseClassify classify : classifyList) {
            PurchaseClassifyVo vo = new PurchaseClassifyVo();
            BeanUtils.copyProperties(classify, vo);
            classifyVoList.add(vo);
        }
        return classifyVoList;
    }

    @Override
    public List<PurchaseClassifyVo> findAllByParentId(List<String> classifyIdList, String storeLevel) {
        List<PurchaseClassifyVo> classifyVoList = Lists.newArrayList();
        Conds conds = Conds.get();
        if (StringUtils.isNotEmpty(storeLevel)) {
            conds.like("store_level", storeLevel);
        }
        List<PurchaseClassify> firstList = purchaseClassifyService.list(Conds.get().addAll(conds.getCondList()).eq("status", "1").eq("del_flag", BoolType.F.name()).in("id", classifyIdList));
        if (!firstList.isEmpty()) {
            List<PurchaseClassify> secondList = purchaseClassifyService.list(Conds.get().addAll(conds.getCondList()).eq("status", "1").eq("del_flag", BoolType.F.name()).in("parent_id", ListUtils.getIdSet(firstList, "id")));
            if (!secondList.isEmpty()) {
                firstList.addAll(secondList);
                List<PurchaseClassify> thirdList = purchaseClassifyService.list(Conds.get().addAll(conds.getCondList()).eq("status", "1").eq("del_flag", BoolType.F.name()).in("parent_id", ListUtils.getIdSet(secondList, "id")));
                firstList.addAll(thirdList);
            }
        }
        for (PurchaseClassify classify : firstList) {
            PurchaseClassifyVo vo = new PurchaseClassifyVo();
            BeanUtils.copyProperties(classify, vo);
            classifyVoList.add(vo);
        }
        return classifyVoList;
    }

    @Override
    public List<PurchaseClassify> getDisableClassifyList() {
        return purchaseClassifyService.list(Conds.get().eq("status", "0").eq("del_flag", BoolType.F.name()).order("order_index desc,update_time desc"));
    }
}
