package pub.makers.shop.cargo.service;


import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.cargo.entity.vo.ImageGroupVo;
import pub.makers.shop.store.vo.ImageVo;
import pub.makers.shop.tradeGoods.entity.Image;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CargoImageBizService {

	List<Image> listDetailImageByGoodId(String goodId);

	ImageVo getCargoImage(String cargoId, ClientType type);

	Map<String, ImageVo> getCargoImage(List<String> cargoIdList, ClientType type);

	ImageGroupVo getGoodsAlbum(String cargoId, ClientType type);

	ImageGroupVo getImageGroup(String groupId);

	/**
	 * 保存图片返回groupId
	 */
	Long saveGroupImage(List<String> imageUrlList, String userId);

	/**
	 * 批量查询图片组
	 */
	Map<String, ImageGroupVo> getImageGroup(List<String> groupIdList);

	/**
	 * 批量查询图片
	 */
	Map<String, ImageVo> getImageByGroup(List<String> groupIdList);

	void deleteImgByGroupId(String coverImg);

	/**
	 * id查询图片
	 */
	Map<String, ImageVo> getImageById(List<String> idList);

	ImageVo getImageByImageId(String imageid);

	String saveImage(ImageVo imageVo,String userId);

	boolean deleteImage(String ImageId);
}
