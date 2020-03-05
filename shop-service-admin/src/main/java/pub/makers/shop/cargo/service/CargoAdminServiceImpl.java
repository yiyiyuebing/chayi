package pub.makers.shop.cargo.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.cargo.entity.*;
import pub.makers.shop.cargo.entity.vo.*;
import pub.makers.shop.logistics.entity.FreightTplGoodRel;
import pub.makers.shop.logistics.service.FreightTplGoodRelService;
import pub.makers.shop.store.vo.ImageVo;
import pub.makers.shop.tradeGoods.entity.Image;
import pub.makers.shop.tradeGoods.entity.TradeGiftRule;
import pub.makers.shop.tradeGoods.service.ImageService;
import pub.makers.shop.tradeGoods.service.TradeGiftRuleService;
import pub.makers.shop.tradeGoods.vo.TradeGiftRuleVo;
import pub.makers.shop.u8.service.U8MgrBizSerivce;

import java.util.*;

/**
 * Created by dy on 2017/5/22.
 */
@Service(version = "1.0.0")
public class CargoAdminServiceImpl implements CargoMgrBizService {

    @Autowired
    private CargoService cargoService;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private CargoSkuService cargoSkuService;
    @Autowired
    private CargoSkuStockService cargoSkuStockService;
    @Autowired
    private CargoBasePropertysService cargoBasePropertysService;
    @Autowired
    private TradeGiftRuleService tradeGiftRuleService;
    @Autowired
    private CargoSkuTypeService cargoSkuTypeService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private FreightTplGoodRelService freightTplGoodRelService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CargoSkuSupplyPriceService cargoSkuSupplyPriceService;
    @Autowired
    private CargoSkuItemService cargoSkuItemService;
    @Autowired
    private CargoBaseSkuItemService cargoBaseSkuItemService;
    @Autowired
    private CargoClassifyService cargoClassifyService;
    @Autowired
    private CargoSkuOptionService cargoSkuOptionService;
    @Autowired
    private CargoImageMgrBizService cargoImageMgrBizService;

    @Reference(version = "1.0.0")
    private CargoImageBizService cargoImageBizService;
    @Reference(version = "1.0.0")
    private CargoClassifyBizService cargoClassifyBizService;
    @Reference(version = "1.0.0")
    private CargoSkuStockBizService cargoSkuStockBizService;
    @Reference(version = "1.0.0")
    private U8MgrBizSerivce u8MgrBizSerivce;

    @Autowired
    private CargoClassifyPropertiesService cargoClassifyPropertiesService;


    @Override
    public ResultList<CargoVo> cargoList(CargoParam queryKeyword, Paging paging) {

        if (StringUtils.isNotBlank(queryKeyword.getClassifyId())) {
            Set<String> queryClassifyId = new HashSet<String>();
            queryClassifyId.add(queryKeyword.getClassifyId());
            Set<String> classifyIds = cargoClassifyBizService.findAllIdByParentId(queryClassifyId);
            if (!classifyIds.isEmpty()) {
                queryKeyword.setClassifyId(StringUtils.join(classifyIds.toArray(), ","));
            }
        }

        String cargoListStmt = FreeMarkerHelper.getValueFromTpl("sql/cargo/queryCargoList.sql", queryKeyword);
        RowMapper<CargoVo> cargoVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(CargoVo.class);
        List<CargoVo> cargoVoList = jdbcTemplate.query(cargoListStmt, cargoVoRowMapper, paging.getPs(), paging.getPn());

        List<String> groupIdList = new ArrayList<>();
        for (CargoVo cargoVo : cargoVoList) {
            if (StringUtils.isNotBlank(cargoVo.getPcAlbumId())) {
                groupIdList.add(cargoVo.getPcAlbumId());
            }
        }

        Map<String, ImageVo> map = cargoImageBizService.getImageByGroup(groupIdList);
        List<CargoVo> cargoVos = Lists.newArrayList();
        for (CargoVo cargoVo : cargoVoList) {
            ImageVo imageVo = map.get(cargoVo.getPcAlbumId());
            if(imageVo != null) {
                cargoVo.setSmallImage(imageVo.getUrl());
            }
            cargoVos.add(cargoVo);
        }

//        findCargoSkuByCargo(cargoVoList, queryKeyword);
        String countCargoListStmt = FreeMarkerHelper.getValueFromTpl("sql/cargo/countCargoList.sql", queryKeyword);
        Integer totalCount = jdbcTemplate.queryForObject(countCargoListStmt, Integer.class);

        ResultList<CargoVo> cargoVoResultList = new ResultList<CargoVo>();
        cargoVoResultList.setResultList(cargoVoList);
        cargoVoResultList.setTotalRecords(totalCount);
        return cargoVoResultList;
    }

    @Override
    public boolean deleteCargoByIds(Long[] ids) {
        for (Long id : ids) {
            cargoService.update(Update.byId(id).set("del_flag", "T"));
        }
        return true;
    }

    @Override
    public CargoInfoVo getCargoInfo(long cargoId) {
        Cargo cargo = cargoService.getById(cargoId);
        CargoInfoVo cargoInfoVo = new CargoInfoVo();
        cargoInfoVo.setId(cargo.getId() + "");
        cargoInfoVo.setName(cargo.getName());
        cargoInfoVo.setCargoNo(cargo.getCargoNo());
        if (StringUtils.isBlank(cargo.getPcDetailInfo()) && cargo.getDetailImageGroupId() != null) {
            ImageGroupVo imageGroupVo = cargoImageBizService.getImageGroup(cargo.getDetailImageGroupId() + "");
            String detailInfo = "";
            for (ImageVo image : imageGroupVo.getImages()) {
                if (StringUtils.isNotBlank(image.getUrl())) {
                    detailInfo += "<p><img src=\""+ image.getUrl() + "\"/></p>";
                }
            }
            cargoInfoVo.setPcDetailInfo(detailInfo);
            cargoInfoVo.setMobileDetailInfo(detailInfo);
        } else {
            cargoInfoVo.setMobileDetailInfo(cargo.getMobileDetailInfo());
            cargoInfoVo.setPcDetailInfo(cargo.getPcDetailInfo());
        }

        cargoInfoVo.setBrandId(cargo.getBrandId() + "");
        cargoInfoVo.setSupplierId(cargo.getSupplierId() + "");

        cargoInfoVo.setVolume(cargo.getVolume());
        cargoInfoVo.setVolumeUnit(cargo.getVolumeUnit());

        cargoInfoVo.setWeight(cargo.getWeight());
        cargoInfoVo.setWeightUnit(cargo.getWeightUnit());

        cargoInfoVo.setAfterSell(cargo.getAfterSell());
        cargoInfoVo.setIsSancha(cargo.getIsSancha());
        cargoInfoVo.setCargoType(cargo.getCargoType());

//        <p><img src="http://o7o0uv2j1.bkt.clouddn.com/@/ueditor/jsp/upload/image/20170628/1498650026741030997.jpg"/></p>
        List<CargoClassifySampleVo> cargoClassifySampleVos = getClassifyList(cargo.getClassifyId());
        cargoInfoVo.setClassifyList(cargoClassifySampleVos);
        cargoInfoVo.setGiftGoods(getCargoGiftGoodsListByCargoId(cargo.getId(), "cargo")); //赠品

        CargoClassifySampleVo cargoClassifySampleVo = new CargoClassifySampleVo();

        if (!cargoClassifySampleVos.isEmpty()) {
            for (CargoClassifySampleVo classifySampleVo : cargoClassifySampleVos) {
                if (classifySampleVo.getParentId() != null && classifySampleVo.getParentId() == 1L) {
                    cargoClassifySampleVo = classifySampleVo;
                }
            }
        }

        cargoInfoVo.setCargoBasePropertyses(getCargoBasePropertyesByCargoId(cargo.getId(), cargoClassifySampleVo)); //公共参数
        cargoInfoVo.setCargoSkus(getCargoSkuListByCargoId(cargo.getId())); //SKU数据
        cargoInfoVo.setCargoSkuTypes(getCargoSkuTypeList(cargo.getId()));
        cargoInfoVo.setMobileSwiperImgs(getSwiperImgs(cargo.getMobileAlbumId())); //移动端轮播图片
        cargoInfoVo.setPcSwiperImgs(getSwiperImgs(cargo.getPcAlbumId())); //PC端轮播图片
        cargoInfoVo.setFreightTplId(getFreightTplIdByCargoSku(cargoInfoVo.getCargoSkus(), "cargo"));
        return cargoInfoVo;
    }

    @Override
    public boolean copyCargos(Long[] cargoIds) {

        for (Long cargoId : cargoIds) {
            Cargo cargo = cargoService.getById(cargoId);
            copyCargoInfo(cargo);
        }
        return false;
    }

    /**
     * 复制货品信息
     * @param cargo
     */
    private void copyCargoInfo(final Cargo cargo) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                Cargo newCargo = new Cargo();
                BeanUtils.copyProperties(cargo, newCargo, "id");
                Long mobileAlbumId = copyCargoImgs(cargo.getMobileAlbumId());
                newCargo.setMobileAlbumId(mobileAlbumId);
                Long pcAlbumId = copyCargoImgs(cargo.getPcAlbumId());
                newCargo.setPcAlbumId(pcAlbumId);
                newCargo.setId(IdGenerator.getDefault().nextId());
                newCargo.setCreateTime(new Date());
                cargoService.insert(newCargo); //复制货品信息

                copyCargoSkuList(cargo, newCargo); //复制货品SKU cargoSku
                copyCargoBasePropertys(cargo, newCargo); //复制货品基础属性
                copyCargoGiftGoods(cargo, newCargo); //复制赠品
                copyCargoSkuType(cargo, newCargo); //复制货品规格相关
            }
        });
    }

    /**
     * 复制货品规格相关
     * @param cargo
     * @param newCargo
     */
    private void copyCargoSkuType(Cargo cargo, Cargo newCargo) {
        List<CargoSkuType> cargoSkuTypes = cargoSkuTypeService.list(Conds.get().eq("cargo_id", cargo.getId()));
        for (CargoSkuType cargoSkuType : cargoSkuTypes) {
            CargoSkuType newCargoSkuType = new CargoSkuType();
            BeanUtils.copyProperties(cargoSkuType, newCargoSkuType, "id", "cargo_id");
            newCargoSkuType.setId(IdGenerator.getDefault().nextId());
            newCargoSkuType.setCargoId(newCargo.getId());
            newCargoSkuType.setCreateTime(new Date());
            cargoSkuTypeService.insert(newCargoSkuType);

            List<CargoSkuItem> cargoSkuItems = cargoSkuItemService.list(Conds.get().eq("cargo_sku_type_id", cargoSkuType.getId()));
            for (CargoSkuItem cargoSkuItem : cargoSkuItems) {
                CargoSkuItem newCargoSkuItem = new CargoSkuItem();
                BeanUtils.copyProperties(cargoSkuItem, newCargoSkuItem, "id", "cargoSkuTypeId");
                newCargoSkuItem.setId(IdGenerator.getDefault().nextId());
                newCargoSkuItem.setCargoId(newCargo.getId());
                newCargoSkuItem.setCargoSkuTypeId(newCargoSkuType.getId());
                newCargoSkuItem.setCreateTime(new Date());
                cargoSkuItemService.insert(newCargoSkuItem);
            }

        }
    }

    /**
     * 复制赠品
     * @param cargo
     * @param newCargo
     */
    private void copyCargoGiftGoods(Cargo cargo, Cargo newCargo) {
        List<TradeGiftRule> tradeGiftRules = tradeGiftRuleService.list(Conds.get().eq("good_id", cargo.getId()).eq("good_type", "cargo"));
        for (TradeGiftRule tradeGiftRule : tradeGiftRules) {
            TradeGiftRule newTradeGiftRule = new TradeGiftRule();
            BeanUtils.copyProperties(tradeGiftRule, newTradeGiftRule, "ruleId", "goodId");
            newTradeGiftRule.setRuleId(IdGenerator.getDefault().nextId());
            newTradeGiftRule.setGoodId(newCargo.getId());
            newTradeGiftRule.setDateCreated(new Date());
            tradeGiftRuleService.insert(newTradeGiftRule);
        }
    }

    /**
     * 复制货品基础属性
     * @param cargo
     * @param newCargo
     */
    private void copyCargoBasePropertys(Cargo cargo, Cargo newCargo) {
        List<CargoBasePropertys> cargoBasePropertyses = cargoBasePropertysService.list(Conds.get().eq("cargo_id", cargo.getId()));
        for (CargoBasePropertys cargoBasePropertyse : cargoBasePropertyses) {
            CargoBasePropertys newCargoBaseProperty = new CargoBasePropertys();
            BeanUtils.copyProperties(cargoBasePropertyse, newCargoBaseProperty, "id", "cargo_id");
            newCargoBaseProperty.setId(IdGenerator.getDefault().nextId());
            newCargoBaseProperty.setCargoId(newCargo.getId());
            newCargoBaseProperty.setDateCreated(new Date());
            cargoBasePropertysService.insert(newCargoBaseProperty);
        }
    }

    /**
     * 复制CargoSKU
     * @param cargo
     * @param newCargo
     */
    private void copyCargoSkuList(Cargo cargo, Cargo newCargo) {
        List<CargoSku> cargoSkuList = cargoSkuService.list(Conds.get().eq("cargo_id", cargo.getId()));
        for (CargoSku cargoSku : cargoSkuList) {
            CargoSku newCargoSku = new CargoSku();
            BeanUtils.copyProperties(cargoSku, newCargoSku, "id");
            newCargoSku.setId(IdGenerator.getDefault().nextId());
            newCargoSku.setCargoId(newCargo.getId());
            newCargoSku.setCreateTime(new Date());
            cargoSkuService.insert(newCargoSku);

            //复制购买数区间
            List<CargoSkuSupplyPrice> cargoSkuSupplyPriceList = cargoSkuSupplyPriceService.list(Conds.get().eq("cargo_sku_id", cargoSku.getId()).eq("cargo_id", cargo.getId()));
            for (CargoSkuSupplyPrice cargoSkuSupplyPrice : cargoSkuSupplyPriceList) {
                CargoSkuSupplyPrice newCargoSkuSupplyPrice = new CargoSkuSupplyPrice();
                BeanUtils.copyProperties(cargoSkuSupplyPrice, newCargoSkuSupplyPrice, "id", "cargoId", "cargoSkuId");
                newCargoSkuSupplyPrice.setCargoId(newCargo.getId());
                newCargoSkuSupplyPrice.setCargoSkuId(newCargoSku.getId());
                newCargoSkuSupplyPrice.setId(IdGenerator.getDefault().nextId());
                newCargoSkuSupplyPrice.setCreateTime(new Date());
                cargoSkuSupplyPriceService.insert(newCargoSkuSupplyPrice);
            }

            /**
             * 复制运费模版
             */
            List<FreightTplGoodRel> freightTplGoodRels = freightTplGoodRelService.list(Conds.get().eq("good_sku_id", cargoSku.getId()).eq("rel_type", "cargo"));
            for (FreightTplGoodRel freightTplGoodRel : freightTplGoodRels) {
                FreightTplGoodRel newFreightTplGoodRel = new FreightTplGoodRel();
                BeanUtils.copyProperties(freightTplGoodRel, newFreightTplGoodRel, "relId", "good_sku_id");
                newFreightTplGoodRel.setRelId(IdGenerator.getDefault().nextId() + "");
                newFreightTplGoodRel.setGoodSkuId(newCargoSku.getId() + "");
                newFreightTplGoodRel.setDateCreated(new Date());
                freightTplGoodRelService.insert(freightTplGoodRel);
            }


        }

    }

    /**
     * 复制图片
     * @param albumId
     * @return
     */
    private Long copyCargoImgs(Long albumId) {
        List<Image> images = imageService.list(Conds.get().eq("group_id", albumId));
        Long groupId = IdGenerator.getDefault().nextId();
        for (Image image : images) {
            Image newImage = new Image();
            BeanUtils.copyProperties(image, newImage, "id", "groupId");
            newImage.setId(IdGenerator.getDefault().nextId() + "");
            newImage.setGroupId(groupId + "");
            newImage.setCreateTime(new Date());
            imageService.insert(newImage);
        }
        return groupId;
    }


    private List<CargoClassifySampleVo> getClassifyList(Long classifyId) {
        List<CargoClassify> cargoClassifies = new ArrayList<CargoClassify>();
        CargoClassify cargoClassify = cargoClassifyService.getById(classifyId);
        if (cargoClassify != null) {
            cargoClassifies.add(cargoClassify);
        }

        while (cargoClassify != null && cargoClassify.getParentId() != null) {
            cargoClassify = cargoClassifyService.getById(cargoClassify.getParentId());
            if (cargoClassify != null && cargoClassify.getParentId() != null) {
                cargoClassifies.add(cargoClassify);
            }
        }

        List<CargoClassifySampleVo> cargoClassifySampleVos = Lists.newArrayList();

        for (int i = cargoClassifies.size() - 1; i >= 0; i--) {
            CargoClassifySampleVo cargoClassifySampleVo = new CargoClassifySampleVo(cargoClassifies.get(i));
            cargoClassifySampleVos.add(cargoClassifySampleVo);
        }
        return cargoClassifySampleVos;
    }

    /**
     *
     * @param cargoId
     * @return
     */
    private List<CargoSkuTypeVo> getCargoSkuTypeList(Long cargoId) {
        List<CargoSkuType> cargoSkuTypes = cargoSkuTypeService.list(Conds.get().eq("cargo_id", cargoId));
        List<CargoSkuTypeVo> cargoSkuTypeVos = Lists.newArrayList();
        for (CargoSkuType cargoSkuType : cargoSkuTypes) {
            CargoSkuTypeVo cargoSkuTypeVo = new CargoSkuTypeVo();
            BeanUtils.copyProperties(cargoSkuType, cargoSkuTypeVo);
            cargoSkuTypeVo.setCargoId(cargoSkuType.getCargoId() + "");
            cargoSkuTypeVo.setId(cargoSkuType.getId() + "");
            cargoSkuTypeVo.setCargoBaseSkuTypeId(cargoSkuType.getCargoBaseSkuTypeId() + "");
            cargoSkuTypeVo.setCargoSkuItemList(getCargoSkuItemList(cargoSkuType.getId(), cargoId));
            cargoSkuTypeVos.add(cargoSkuTypeVo);
        }
        return cargoSkuTypeVos;
    }

    private List<CargoSkuItemVo> getCargoSkuItemList(Long cargoSkuTypeId, Long cargoId) {
        List<CargoSkuItem> cargoSkuItems = cargoSkuItemService.list(Conds.get().eq("cargo_sku_type_id", cargoSkuTypeId).eq("cargo_id", cargoId));
        List<CargoSkuItemVo> cargoSkuItemVos = Lists.newArrayList();
        for (CargoSkuItem cargoSkuItem : cargoSkuItems) {
            CargoSkuItemVo cargoSkuItemVo = new CargoSkuItemVo();
            BeanUtils.copyProperties(cargoSkuItem, cargoSkuItemVo);
            cargoSkuItemVo.setId(cargoSkuItem.getId() + "");
            cargoSkuItemVo.setCargoBaseSkuItemId(cargoSkuItem.getCargoBaseSkuItemId() + "");
            cargoSkuItemVo.setCargoSkuTypeId(cargoSkuItem.getCargoSkuTypeId() + "");
            cargoSkuItemVo.setCargoId(cargoSkuItem.getCargoId() + "");
            cargoSkuItemVos.add(cargoSkuItemVo);
        }
        return cargoSkuItemVos;
    }

    /**
     * 获取运费模版
     * @param cargoSkus
     * @param relType
     * @return
     */
    private String getFreightTplIdByCargoSku(List<CargoSkuVo> cargoSkus, String relType) {
        if (cargoSkus == null || cargoSkus.size() < 1) {
            return null;
        }
        CargoSkuVo cargoSku = cargoSkus.get(0);
        FreightTplGoodRel freightTplGoodRel = freightTplGoodRelService.get(Conds.get().eq("good_sku_id", cargoSku.getId()).eq("rel_type", relType));
        if (freightTplGoodRel == null) {
            return null;
        }
        return freightTplGoodRel.getTplId();
    }


    /**
     * 获取图片
     * @param groupId
     * @return
     */
    private ImageGroupVo getSwiperImgs(Long groupId) {
        ImageGroupVo imageGroupVo = new ImageGroupVo();
        List<Image> images = new ArrayList<Image>();
        if (groupId != null) {
            images = imageService.list(Conds.get().eq("group_id", groupId));
        }
        imageGroupVo.setGroupId(groupId + "");
        imageGroupVo.setImageList(images);
        return imageGroupVo;
    }

    /**
     * 获取SKU数据
     * @param cargoId
     * @return
     */
    private List<CargoSkuVo> getCargoSkuListByCargoId(Long cargoId) {
        List<CargoSku> cargoSkus = cargoSkuService.list(Conds.get().eq("cargo_id", cargoId));
        List<CargoSkuVo> cargoSkuVos = new ArrayList<CargoSkuVo>();
        for (CargoSku cargoSku : cargoSkus) {
            CargoSkuVo cargoSkuVo = new CargoSkuVo();
            BeanUtils.copyProperties(cargoSku, cargoSkuVo);
            cargoSkuVo.setId(cargoSku.getId() + "");
            cargoSkuVo.setCargoId(cargoSku.getCargoId() + "");

            ImageGroupVo imageGroup = null;

            if (StringUtils.isNotBlank(cargoSku.getCoverImg()) && cargoSku.getCoverImg().indexOf("http") < 0) {
                imageGroup = cargoImageBizService.getImageGroup(cargoSku.getCoverImg());
            }
            if (!(imageGroup == null)) {
                if (!imageGroup.getImages().isEmpty()) {
                    cargoSkuVo.setCoverImg(imageGroup.getImages().get(0).getUrl());
                    cargoSkuVo.setCoverImgGroupId(imageGroup.getGroupId());
                }
            }
            List<CargoSkuSupplyPrice> skuSupplyPrices = cargoSkuSupplyPriceService.list(Conds.get().eq("cargo_sku_id", cargoSku.getId()).eq("cargo_id", cargoId).order("section_start asc"));
            cargoSkuVo.setCargoSkuSupplyPrices(skuSupplyPrices);
            cargoSkuVos.add(cargoSkuVo);
        }
        return cargoSkuVos;
    }

    /**
     * 公共参数
     * @param cargoId
     * @param cargoClassifySampleVo
     * @return
     */
    private List<CargoBasePropertys> getCargoBasePropertyesByCargoId(Long cargoId, CargoClassifySampleVo cargoClassifySampleVo) {

        List<CargoBasePropertys> cargoBasePropertysList = cargoBasePropertysService.list(Conds.get().eq("cargo_id", cargoId));

        List<CargoClassifyProperties> cargoClassifyPropertieses = cargoClassifyPropertiesService.list(Conds.get().eq("cargo_classify_id", cargoClassifySampleVo.getId()).eq("del_flag", BoolType.F.name()).eq("is_valid", BoolType.T.name()));

        if (cargoBasePropertysList.isEmpty()) {
            for (CargoClassifyProperties cargoClassifyPropertiese : cargoClassifyPropertieses) {
                CargoBasePropertys cargoBasePropertys = new CargoBasePropertys();
                cargoBasePropertys.setPname(cargoClassifyPropertiese.getPname());
                cargoBasePropertysList.add(cargoBasePropertys);
            }
        } else {
            Set<String> classifyPnameSet = ListUtils.getIdSet(cargoClassifyPropertieses, "pname");
            classifyPnameSet = ListUtils.getIdSet(cargoBasePropertysList, "pname");
            List<CargoBasePropertys> cargoBasePropertyses = new ArrayList<CargoBasePropertys>();
            for (String pname : classifyPnameSet) {
                CargoBasePropertys cargoBasePropertys = new CargoBasePropertys();
                cargoBasePropertys.setPname(pname);
                for (CargoBasePropertys cargoBasePropertys1 : cargoBasePropertysList) {
                    if (!pname.equals(cargoBasePropertys1.getPname())) {
                        cargoBasePropertys.setPvalue(cargoBasePropertys1.getPvalue());
                    }
                }
                cargoBasePropertyses.add(cargoBasePropertys);
            }
        }
        return cargoBasePropertysList;
    }

    /**
     * 获取货品相关赠品
     * @param cargoId
     * @param goodsType
     * @return
     */
    private List<TradeGiftRuleVo> getCargoGiftGoodsListByCargoId(Long cargoId, String goodsType) {
        List<TradeGiftRuleVo> tradeGiftRuleVos = Lists.newArrayList();
        List<TradeGiftRule> tradeGiftRuleList = tradeGiftRuleService.list(Conds.get().eq("good_id", cargoId).eq("good_type", goodsType));
        for (TradeGiftRule tradeGiftRule : tradeGiftRuleList) {
            TradeGiftRuleVo tradeGiftRuleVo = new TradeGiftRuleVo(tradeGiftRule);
            tradeGiftRuleVos.add(tradeGiftRuleVo);
        }
        return tradeGiftRuleVos;
    }

    /**
     * 获取Sku信息
     * @param cargoVoList
     * @param queryKeyword
     */
    private void findCargoSkuByCargo(List<CargoVo> cargoVoList, String queryKeyword) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(queryKeyword)) {
            paramMap.put("keyword", queryKeyword);
        }
        RowMapper<CargoSku> cargoSkuRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(CargoSku.class);
        for (CargoVo cargoVo : cargoVoList) {
            paramMap.put("cargoId", cargoVo.getId());
            String queryCargoSkuListStmt = FreeMarkerHelper.getValueFromTpl("sql/cargo/countCargoList.sql", paramMap);
            List<CargoSku> cargoSkus = jdbcTemplate.query(queryCargoSkuListStmt, cargoSkuRowMapper);
            String skuValue = "";
            String skuName = "";
            String skuCode = "";
            String skuMemo = "";
            for (CargoSku cargoSku : cargoSkus) {
                skuValue += cargoSku.getSkuName() + "，";
                skuName += cargoSku.getSkuName() + "，";
                skuCode += cargoSku.getCode() + "，";
                skuMemo += cargoSku.getMemo() + "，";
            }

            if (skuValue.length() > 0) {
                skuValue.substring(0, skuValue.length() - 1);
            }
            if (skuName.length() > 0) {
                skuName.substring(0, skuName.length() - 1);
            }
            if (skuCode.length() > 0) {
                skuCode.substring(0, skuCode.length() - 1);
            }
            if (skuMemo.length() > 0) {
                skuMemo.substring(0, skuMemo.length() - 1);
            }
            cargoVo.setSkuValue(skuValue);
            cargoVo.setSkuName(skuName);
            cargoVo.setSkuCode(skuCode);
            cargoVo.setSkuMemo(skuMemo);
        }
    }


    @Override
    public List<Cargo> findGiftCargoList() {
        return cargoService.list(Conds.get().eq("classify_id", "364168645236166656"));
    }

    /**
     * 检测货品SKU编码是否重复
     * @param cargoSkuId
     * @param code
     * @return
     */
    private Boolean checkCargoSkuCode(String cargoSkuId, String code) {
        List<CargoSku> cargoSkuList = Lists.newArrayList();
        String sql = "select * from cargo_sku cs left join cargo c on c.id = cs.cargo_id where 1=1 and c.del_flag!=? and cs.code=?";
        if (StringUtils.isBlank(cargoSkuId)) {
            cargoSkuList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(CargoSku.class), BoolType.T.name(), code);
        } else {
            cargoSkuList = jdbcTemplate.query(sql + " and cs.id != ?", ParameterizedBeanPropertyRowMapper.newInstance(CargoSku.class), BoolType.T.name(), code, cargoSkuId);
        }
        return cargoSkuList.isEmpty();
    }

    /**
     * 检测货品编码是否重复
     * @param cargoId
     * @param cargoNo
     * @return
     */
    private Boolean checkCargoNo(Long cargoId, String cargoNo) {
        List<Cargo> cargoList = Lists.newArrayList();
        if (cargoId == null) {
            cargoList = cargoService.list(Conds.get().eq("cargo_no", cargoNo).ne("del_flag", BoolType.T.name()));
        } else {
            cargoList = cargoService.list(Conds.get().eq("cargo_no", cargoNo).ne("id", cargoId).ne("del_flag", BoolType.T.name()));
        }
        return cargoList.isEmpty();
    }

    private ResultData checkBeforeSave(CargoSaveVo cargoSaveVo) {
        //检查货品
        if (!checkCargoNo(cargoSaveVo.getId(), cargoSaveVo.getCargoNo())) {
            return ResultData.createFail("货品编码已重复");
        }
        //检查货品sku
        boolean flag = true;
        String msg = "";
        for (CargoSkuVo cargoSkuVo : cargoSaveVo.getSkuAdd()) {
            if (!checkCargoSkuCode(cargoSkuVo.getId(), cargoSkuVo.getCode())) {
                flag = false;
                msg = "SKU编码：" + cargoSkuVo.getCode() + "已重复";
                break;
            }
        }
        if (!flag) {
            return ResultData.createFail(msg);
        }
        for (CargoSkuVo cargoSkuVo : cargoSaveVo.getSkuChange()) {
            if (!checkCargoSkuCode(cargoSkuVo.getId(), cargoSkuVo.getCode())) {
                flag = false;
                msg = "SKU编码：" + cargoSkuVo.getCode() + "已重复";
                break;
            }
        }
        if (!flag) {
            return ResultData.createFail(msg);
        }
        return ResultData.createSuccess();
    }

    @Override
    public ResultData saveCargo(final long userId, final CargoSaveVo cargoSaveVo) throws Exception {
        //检测编码重复性
        ResultData resultData = checkBeforeSave(cargoSaveVo);
        if (!resultData.isSuccess()) {
            return resultData;
        }

        if (cargoSaveVo.getId() == null) {
            final Cargo cargo = new Cargo();

//            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//                @Override
//                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
            BeanUtils.copyProperties(cargoSaveVo, cargo);
            Long mobileSwiperGroupId = saveCargoImg(userId, cargoSaveVo.getMobileSwiperImgs());
            Long pcSwiperGroupId = saveCargoImg(userId, cargoSaveVo.getPcSwiperImgs());
            cargo.setMobileAlbumId(mobileSwiperGroupId);
            List<CargoClassifySampleVo> cargoClassifySampleVos = getClassifyList(cargoSaveVo.getClassifyId());
            if (cargoClassifySampleVos.size() > 0) {
                cargo.setParentClassifyId(Long.parseLong(cargoClassifySampleVos.get(0).getId()));
            }
            cargo.setPcAlbumId(pcSwiperGroupId);
            cargo.setId(IdGenerator.getDefault().nextId());
            cargo.setCreateBy(userId);
            cargo.setCreateTime(new Date());
            cargo.setDelFlag("F");
            cargoService.insert(cargo);
            saveCargoSku(userId, cargo.getId(), cargoSaveVo.getSkuAdd(), cargoSaveVo.getFreightTplId());  //新增SKU信息
            saveCargoSku(userId, cargo.getId(),cargoSaveVo.getSkuChange(), cargoSaveVo.getFreightTplId()); //更新SKU信息


//            ResultData addResult = checkCargoSkuCode(cargoSaveVo.getSkuAdd());
//            if (!addResult.isSuccess()) {
//                return addResult;
//            }
//            ResultData changeResult = checkCargoSkuCode(cargoSaveVo.getSkuChange());
//            if (!changeResult.isSuccess()) {
//                return changeResult;
//            }

            saveCargoBasePropertys(cargo.getId(), cargoSaveVo.getCargoBasePropertys()); //保存基础属性信息
            deleCargoSku(cargoSaveVo.getSkuDelete()); //删除SKU信息

//                    saveCargoGiftGodds(cargo.getId(), cargoSaveVo.getGiftGoods());



            saveCargoSkuType(userId, cargo.getId(), cargoSaveVo.getSkuTypes()); //保存SKU类型信息

            saveCargoSkuOption(cargo.getId());

            saveCargoSkuItem(cargo.getId());
//                }
//            });
        } else {
            final Cargo cargoOld = cargoService.getById(cargoSaveVo.getId());

//            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//                @Override
//                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
            BeanUtils.copyProperties(cargoSaveVo, cargoOld);
            fmtCargoInfo(cargoSaveVo, cargoOld);
            Long mobileSwiperGroupId = saveCargoImg(userId, cargoSaveVo.getMobileSwiperImgs());
            Long pcSwiperGroupId = saveCargoImg(userId, cargoSaveVo.getPcSwiperImgs());
            List<CargoClassifySampleVo> cargoClassifySampleVos = getClassifyList(cargoSaveVo.getClassifyId());
            if (cargoClassifySampleVos.size() > 0) {
                cargoOld.setParentClassifyId(Long.parseLong(cargoClassifySampleVos.get(0).getId()));
            }
            cargoOld.setMobileAlbumId(mobileSwiperGroupId);
            cargoOld.setPcAlbumId(pcSwiperGroupId);
            cargoOld.setUpdateTime(new Date());
            cargoOld.setUpdateBy(userId);
            cargoService.updateCargo(cargoOld, false);
            deleCargoSku(cargoSaveVo.getSkuDelete()); //删除SKU信息
            saveCargoSku(userId, cargoOld.getId(), cargoSaveVo.getSkuAdd(), cargoSaveVo.getFreightTplId());  //新增SKU信息
            saveCargoSku(userId, cargoOld.getId(), cargoSaveVo.getSkuChange(), cargoSaveVo.getFreightTplId()); //更新SKU信息

//            ResultData addResult = checkCargoSkuCode(cargoSaveVo.getSkuAdd());
//            if (!addResult.isSuccess()) {
//                return addResult;
//            }
//            ResultData changeResult = checkCargoSkuCode(cargoSaveVo.getSkuChange());
//            if (!changeResult.isSuccess()) {
//                return changeResult;
//            }

            saveCargoBasePropertys(cargoOld.getId(), cargoSaveVo.getCargoBasePropertys()); //保存基础属性信息
//                    saveCargoGiftGodds(cargoOld.getId(), cargoSaveVo.getGiftGoods());
            deleCargoSku(cargoSaveVo.getSkuDelete()); //删除SKU信息
            saveCargoSkuType(userId, cargoOld.getId(), cargoSaveVo.getSkuTypes()); //保存SKU类型信息

            saveCargoSkuOption(cargoOld.getId());

            saveCargoSkuItem(cargoOld.getId());
//                }
//            });
        }
        return ResultData.createSuccess();
    }

    private void fmtCargoInfo(CargoSaveVo cargoSaveVo, Cargo cargoOld) {
        cargoOld.setSupplierId(cargoSaveVo.getSupplierId() != null ? cargoSaveVo.getSupplierId() : 0L);
        cargoOld.setVolume(StringUtils.isNotBlank(cargoSaveVo.getVolume()) ? cargoSaveVo.getVolume() : "");
        cargoOld.setWeight(StringUtils.isNotBlank(cargoSaveVo.getWeight()) ? cargoSaveVo.getWeight() : "");
        cargoOld.setAfterSell(StringUtils.isNotBlank(cargoSaveVo.getAfterSell()) ? cargoSaveVo.getAfterSell() : "");
    }

    private void saveCargoSkuItem(Long cargoId) {
        List<CargoSku> cargoSkuList = cargoSkuService.list(Conds.get().eq("cargo_id", cargoId));
        for (CargoSku cargoSku : cargoSkuList) {
            List<String> skuValues = Arrays.asList(cargoSku.getSkuValue().split(","));
            List<CargoSkuItem> cargoSkuItems = cargoSkuItemService.list(Conds.get().in("cargo_base_sku_item_id", skuValues).eq("cargo_id", cargoId));
            Set<String> cargoSkuItemIds = ListUtils.getIdSet(cargoSkuItems, "id");
            cargoSku.setSkuItemValue(StringUtils.join(cargoSkuItemIds, ","));
            cargoSkuService.update(cargoSku);
        }

    }

    private void saveCargoSkuOption(Long cargoId) {
        List<CargoSku> cargoSkus = cargoSkuService.list(Conds.get().eq("cargo_id", cargoId));
        if (!cargoSkus.isEmpty()) {
            cargoSkuOptionService.delete(Conds.get().in("cargo_sku_id", ListUtils.getIdSet(cargoSkus, "id")));
        }
        for (CargoSku cargoSku : cargoSkus) {
            List<String> skuValues = Arrays.asList(cargoSku.getSkuValue().split(","));
            List<CargoSkuItem> cargoSkuItems = cargoSkuItemService.list(Conds.get().in("cargo_base_sku_item_id", skuValues).eq("cargo_id", cargoId));
            for (CargoSkuItem cargoSkuItem : cargoSkuItems) {
                CargoSkuOption cargoSkuOption = new CargoSkuOption();
                cargoSkuOption.setCargoSkuTypeId(cargoSkuItem.getCargoSkuTypeId());
                cargoSkuOption.setId(IdGenerator.getDefault().nextId());
                cargoSkuOption.setCargoSkuId(cargoSku.getId());
                cargoSkuOption.setCargoSkuItemId(cargoSkuItem.getId());
                cargoSkuOption.setCreateTime(new Date());
                cargoSkuOptionService.insert(cargoSkuOption);
            }
        }
    }


    /**
     * 保存
     *
     * @param userId
     * @param imageGroup
     * @return
     */
    private Long saveCargoImg(long userId, ImageGroupVo imageGroup) {
        ValidateUtils.notNull(imageGroup, "2", "图片保存错误");
        if(imageGroup.getGroupId() == null) {
            imageGroup.setGroupId(IdGenerator.getDefault().nextStringId());
        }
        imageService.delete(Conds.get().eq("group_id", imageGroup.getGroupId()));
        for (ImageVo imageVo : imageGroup.getImages()) {
            Image image = new Image();
            image.setId(IdGenerator.getDefault().nextId() + "");
            image.setGroupId(Long.valueOf(imageGroup.getGroupId()) + "");
            image.setPicUrl(imageVo.getUrl());
            image.setCreateBy(userId);
            imageService.insert(image);
        }
        return Long.valueOf(imageGroup.getGroupId());
    }

    /**
     * 保存SKU类型信息
     * @param userId
     * @param cargoId
     * @param skuTypes
     */
    private void saveCargoSkuType(long userId, Long cargoId, List<CargoSkuTypeSaveVo> skuTypes) {
        cargoSkuItemService.delete(Conds.get().eq("cargo_id", cargoId));
        cargoSkuTypeService.delete(Conds.get().eq("cargo_id", cargoId));
        for (CargoSkuTypeSaveVo skuTypeVo : skuTypes) {
            CargoSkuType skuType = new CargoSkuType();
            skuType.setId(IdGenerator.getDefault().nextId());
            skuType.setCargoId(cargoId);
            skuType.setName(skuTypeVo.getSkuTypeName());
            skuType.setCargoBaseSkuTypeId(skuTypeVo.getSkuTypeId());
            skuType.setCreateBy(userId);
            skuType.setType(1);
            cargoSkuTypeService.insert(skuType);


            List<Long> skuItemIds = skuTypeVo.getSkuItemIds();
            for (Long skuItemId : skuItemIds) {
                CargoBaseSkuItem cargoBaseSkuItem = cargoBaseSkuItemService.getById(skuItemId);
                CargoSkuItem cargoSkuItem = new CargoSkuItem();
                cargoSkuItem.setId(IdGenerator.getDefault().nextId());
                cargoSkuItem.setCargoBaseSkuItemId(skuItemId);
                cargoSkuItem.setCargoId(cargoId);
                cargoSkuItem.setCargoSkuTypeId(skuType.getId());
                cargoSkuItem.setCreateTime(new Date());
                cargoSkuItem.setCreateBy(userId);
                cargoSkuItem.setName(cargoBaseSkuItem.getName());
                cargoSkuItem.setValue(cargoBaseSkuItem.getValue());
                cargoSkuItemService.insert(cargoSkuItem);
            }

        }
    }

    /**
     * 保存货品赠品相关
     * @param cargoId
     * @param cargoGiftRules
     */
    private void saveCargoGiftGodds(Long cargoId, List<TradeGiftRuleVo> cargoGiftRules) {
        tradeGiftRuleService.delete(Conds.get().eq("good_id", cargoId).eq("good_type", "cargo"));
        if (cargoGiftRules == null || cargoGiftRules.size() < 1) {
            return;
        }
        for (TradeGiftRuleVo cargoGiftRule : cargoGiftRules) {
            if (cargoGiftRule.getRuleId() == null) {
                TradeGiftRule tradeGiftRule = new TradeGiftRule();
                tradeGiftRule.setGiftId(Long.parseLong(cargoGiftRule.getGiftId()));
                tradeGiftRule.setNum(cargoGiftRule.getNum());
                tradeGiftRule.setGiftName(cargoGiftRule.getGiftName());
                tradeGiftRule.setRuleId(IdGenerator.getDefault().nextId());
                tradeGiftRule.setDelFlag("F");
                tradeGiftRule.setIsValid("T");
                tradeGiftRule.setGoodId(cargoId);
                tradeGiftRule.setGoodType("cargo");
                tradeGiftRuleService.insert(tradeGiftRule);
            } else {
                TradeGiftRule cargoGiftRuleOld = tradeGiftRuleService.getById(cargoGiftRule.getRuleId());
                cargoGiftRuleOld.setGiftId(Long.parseLong(cargoGiftRule.getGiftId()));
                cargoGiftRuleOld.setGiftName(cargoGiftRule.getGiftName());
                cargoGiftRuleOld.setGoodId(cargoId);
                cargoGiftRuleOld.setNum(cargoGiftRule.getNum());
                cargoGiftRuleOld.setLastUpdated(new Date());
                tradeGiftRuleService.update(cargoGiftRuleOld);
            }
        }
    }


    private void saveCargoBasePropertys(Long cargoId, List<CargoBasePropertys> cargoBasePropertys) {
        cargoBasePropertysService.delete(Conds.get().eq("cargo_id", cargoId));
        for (CargoBasePropertys cargoBaseProperty : cargoBasePropertys) {
            cargoBaseProperty.setId(IdGenerator.getDefault().nextId());
            cargoBaseProperty.setCargoId(cargoId);
            cargoBaseProperty.setDelFlag("F");
            cargoBasePropertysService.insert(cargoBaseProperty);
        }
    }

    /**
     * 删除SKU信息和删除运费模版
     * @param skuDeleteIds
     */
    private void deleCargoSku(List<Long> skuDeleteIds) {
        if (skuDeleteIds.size() > 0) {
            freightTplGoodRelService.delete(Conds.get().in("good_sku_id", skuDeleteIds).eq("rel_type", "cargo")); //删除运费模版
            cargoSkuStockService.delete(Conds.get().in("cargo_sku_id", skuDeleteIds));
            cargoSkuService.delete(Conds.get().in("id", skuDeleteIds));
        }
    }

    private ResultData checkCargoSkuCode(List<CargoSkuVo> cargoSkuList) {

        Set<String> cargoCodes = ListUtils.getIdSet(cargoSkuList, "code");
        List<CargoSku> cargoSkus = cargoSkuService.list(Conds.get().in("code", cargoCodes));

        if (!cargoSkus.isEmpty()) {
            String codeStr = "";
            for (CargoSku cargoSku : cargoSkus) {
                codeStr += cargoSku.getCode() + "，";
            }
            if (codeStr.length() > 0) {
                codeStr = codeStr.substring(0, codeStr.length() - 1);
            }
            return ResultData.createFail("SKU编码不能重复：" + codeStr);
        }
        return ResultData.createSuccess();
    }

    /**
     * 保存货品SKU
     * @param userId
     * @param cargoId
     * @param cargoSkuList
     * @param freightTplId
     */
    private void saveCargoSku(long userId, Long cargoId, List<CargoSkuVo> cargoSkuList, Long freightTplId) {
        for (CargoSkuVo cargoSkuAddVo : cargoSkuList) {
            if (cargoSkuAddVo.getId() == null) {

                CargoSku cargoSku = new CargoSku();
                BeanUtils.copyProperties(cargoSkuAddVo, cargoSku);
                cargoSku.setId(IdGenerator.getDefault().nextId());
                cargoSku.setCreateBy(userId);
                cargoSku.setCargoId(cargoId);
                List<String> stringList = Lists.newArrayList();
                stringList.add(cargoSkuAddVo.getCoverImg());
                cargoSku.setCoverImg(cargoImageMgrBizService.saveGroupImage(stringList, "1") + "");
                cargoSkuService.insert(cargoSku);

                if (cargoSkuAddVo.getCargoSkuSupplyPrices() != null && cargoSkuAddVo.getCargoSkuSupplyPrices().size() > 0) {
                    saveCargoSkuSupplyPrices(cargoId, cargoSku.getId(), cargoSkuAddVo.getCargoSkuSupplyPrices());
                }
                //新增运费模版
                FreightTplGoodRel freightTplGoodRel = new FreightTplGoodRel();
                freightTplGoodRel.setRelId(IdGenerator.getDefault().nextId() + "");
                freightTplGoodRel.setRelType("cargo");
                freightTplGoodRel.setTplId(freightTplId + "");
                freightTplGoodRel.setIsValid("T");
                freightTplGoodRel.setGoodSkuId(cargoSku.getId() + "");
                freightTplGoodRel.setDelFlag("F");
                freightTplGoodRelService.insert(freightTplGoodRel);
                saveCargoSkuStock(cargoSku);
            } else {
                CargoSku cargoSkuOld = cargoSkuService.getById(cargoSkuAddVo.getId());
                BeanUtils.copyProperties(cargoSkuAddVo, cargoSkuOld, "id", "createTime", "cargoId");
                cargoSkuOld.setCreateBy(userId);
                cargoSkuOld.setCargoId(cargoId);
                cargoSkuOld.setUpdateTime(new Date());
                List<String> stringList = Lists.newArrayList();
                if (StringUtils.isNotBlank(cargoSkuAddVo.getCoverImgGroupId()) && !(cargoSkuAddVo.getCoverImgGroupId().indexOf("http") >=0)) {
                    cargoImageMgrBizService.deleteImgByGroupId(cargoSkuOld.getCoverImg());
                }
                stringList.add(cargoSkuAddVo.getCoverImg());
                cargoSkuOld.setCoverImg(cargoImageMgrBizService.saveGroupImage(stringList, "1") + "");
                cargoSkuService.update(cargoSkuOld);

                if (cargoSkuAddVo.getCargoSkuSupplyPrices() != null && cargoSkuAddVo.getCargoSkuSupplyPrices().size() > 0) {
                    saveCargoSkuSupplyPrices(cargoId, cargoSkuOld.getId(), cargoSkuAddVo.getCargoSkuSupplyPrices());
                }


                //更新货品运费模版
                FreightTplGoodRel freightTplGoodRel = freightTplGoodRelService.get(Conds.get().eq("good_sku_id", cargoSkuOld.getId()).eq("rel_type", "cargo"));

                if (freightTplGoodRel == null) {
                    //新增运费模版
                    freightTplGoodRel = new FreightTplGoodRel();
                    freightTplGoodRel.setRelId(IdGenerator.getDefault().nextId() + "");
                    freightTplGoodRel.setRelType("cargo");
                    freightTplGoodRel.setTplId(freightTplId + "");
                    freightTplGoodRel.setIsValid("T");
                    freightTplGoodRel.setGoodSkuId(cargoSkuOld.getId() + "");
                    freightTplGoodRel.setDelFlag("F");
                    freightTplGoodRelService.insert(freightTplGoodRel);
                } else {
                    freightTplGoodRel.setTplId(freightTplId + "");
                    freightTplGoodRel.setLastUpdated(new Date());
                    freightTplGoodRelService.update(freightTplGoodRel);
                }

                saveCargoSkuStock(cargoSkuOld);
            }
        }

    }

    private void saveCargoSkuStock(CargoSku cargoSku) {
        CargoSkuStock cargoSkuStock = cargoSkuStockBizService.getStockByCargoSkuId(cargoSku.getId());
        if (cargoSkuStock == null) {
            cargoSkuStock = new CargoSkuStock();
            cargoSkuStock.setId(IdGenerator.getDefault().nextId());
            cargoSkuStock.setCargoSkuId(cargoSku.getId());
            cargoSkuStock.setCreateTime(new Date());
            cargoSkuStockService.insert(cargoSkuStock);
        }
//        u8MgrBizSerivce.asychStockFromU8(cargoSku.getCode());
    }

    /**
     * 保存散伙类型货品价格
     * @param cargoId
     * @param cargoSkuId
     * @param cargoSkuSupplyPrices
     */
    private void saveCargoSkuSupplyPrices(Long cargoId, Long cargoSkuId, List<CargoSkuSupplyPrice> cargoSkuSupplyPrices) {
        cargoSkuSupplyPriceService.delete(Conds.get().eq("cargo_id", cargoId).eq("cargo_sku_id", cargoSkuId));
        for (CargoSkuSupplyPrice cargoSkuSupplyPrice : cargoSkuSupplyPrices) {
            cargoSkuSupplyPrice.setId(IdGenerator.getDefault().nextId());
            cargoSkuSupplyPrice.setCreateTime(new Date());
            cargoSkuSupplyPrice.setCargoId(cargoId);
            cargoSkuSupplyPrice.setCargoSkuId(cargoSkuId);
            cargoSkuSupplyPriceService.insert(cargoSkuSupplyPrice);
        }
    }


}
