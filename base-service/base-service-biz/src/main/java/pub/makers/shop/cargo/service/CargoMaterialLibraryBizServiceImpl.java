package pub.makers.shop.cargo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.cargo.entity.CargoMaterialLibrary;
import pub.makers.shop.cargo.entity.CargoMaterialLibraryRel;
import pub.makers.shop.cargo.entity.vo.CargoMaterialLibraryVo;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoods;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsService;
import pub.makers.shop.store.vo.ImageVo;
import pub.makers.shop.tradeGoods.entity.Image;
import pub.makers.shop.tradeGoods.entity.TradeGood;
import pub.makers.shop.tradeGoods.service.ImageService;
import pub.makers.shop.tradeGoods.service.TradeGoodService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service(version = "1.0.0")
public class CargoMaterialLibraryBizServiceImpl implements CargoMaterialLibraryBizService{
	@Autowired
	private TradeGoodService tradeGoodService;
	@Autowired
	private PurchaseGoodsService purchaseGoodsService;
	@Autowired
	private CargoMaterialLibraryService cargoMaterialLibraryService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private CargoMaterialLibraryRelService cargoMaterialLibraryRelService;

	@Override
	public List<CargoMaterialLibraryVo> getMaterialByGoodsId(String goodsId, OrderBizType orderBizType) {
		Long cargoId = null;
		if (OrderBizType.trade.equals(orderBizType)) {
			TradeGood good = tradeGoodService.getById(goodsId);
			cargoId = good.getCargoId();
		} else if (OrderBizType.purchase.equals(orderBizType)) {
			PurchaseGoods good = purchaseGoodsService.getById(goodsId);
			cargoId = good.getCargoId();
		}
		if(cargoId == null){
			return Lists.newArrayList();
		}

		List<CargoMaterialLibraryRel> libraryRelList = cargoMaterialLibraryRelService.list(Conds.get().eq("cargo_id", cargoId).order("create_time desc"));
		List<CargoMaterialLibrary> librarys = cargoMaterialLibraryService.list(Conds.get().in("id", ListUtils.getIdSet(libraryRelList, "meterialLibraryId")).eq("is_valid", BoolType.T.name()));
		Map<String, CargoMaterialLibrary> libraryMap = ListUtils.toKeyMap(librarys, "id");
		List<CargoMaterialLibrary> libraryList = Lists.newArrayList();
		for (CargoMaterialLibraryRel libraryRel : libraryRelList) {
			CargoMaterialLibrary library = libraryMap.get(libraryRel.getMeterialLibraryId().toString());
			if (library == null) {
				continue;
			}
			libraryList.add(library);
		}

		List<CargoMaterialLibraryVo> libraryVoList = Lists.newArrayList();
		if (libraryList.isEmpty()) {
			return libraryVoList;
		}
		Set<String> imageIds = Sets.newHashSet();
		for (CargoMaterialLibrary library : libraryList) {
			CargoMaterialLibraryVo vo = CargoMaterialLibraryVo.fromCargoMaterialLibrary(library);
			imageIds.addAll(Arrays.asList(StringUtils.split(vo.getImage(), ",")));
			libraryVoList.add(vo);
		}
		List<Image> imageList = imageService.list(Conds.get().in("id", imageIds));
		Map<String, Image> imageMap = ListUtils.toKeyMap(imageList, "id");
		for (CargoMaterialLibraryVo libraryVo : libraryVoList) {
			List<ImageVo> imageVoList = Lists.newArrayList();
			for (String imageId : Arrays.asList(StringUtils.split(libraryVo.getImage(), ","))) {
				imageVoList.add(ImageVo.fromImage(imageMap.get(imageId)));
			}
			libraryVo.setImageVos(imageVoList);
		}
		return libraryVoList;
	}

}
