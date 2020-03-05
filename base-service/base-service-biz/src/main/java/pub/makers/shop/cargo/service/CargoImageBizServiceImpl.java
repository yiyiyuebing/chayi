package pub.makers.shop.cargo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lantu.base.util.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.cargo.entity.Cargo;
import pub.makers.shop.cargo.entity.vo.ImageGroupVo;
import pub.makers.shop.store.vo.ImageVo;
import pub.makers.shop.tradeGoods.entity.Image;
import pub.makers.shop.tradeGoods.service.ImageService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service(version = "1.0.0")
public class CargoImageBizServiceImpl implements CargoImageBizService {
    @Autowired
    private CargoService cargoService;
    @Autowired
    private ImageService imageService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Image> listDetailImageByGoodId(String goodId) {

        String stmt = "select * from image where groupId = (select show_image_group_id from cargo where id = (select cargo_id from trade_good where id = ?))";
        List<Image> imageList = jdbcTemplate.query(stmt, new BeanPropertyRowMapper<Image>(Image.class), goodId);

        return imageList;
    }

    @Override
    public ImageVo getCargoImage(String cargoId, ClientType type) {
        Cargo cargo = cargoService.getById(cargoId);
        if (cargo == null) {
            return new ImageVo();
        }
        Long groupId;
        if (ClientType.pc.equals(type)) {
            groupId = cargo.getPcAlbumId();
        } else {
            groupId = cargo.getMobileAlbumId();
        }
        if (groupId == null) {
            return new ImageVo();
        }
        List<Image> imageList = imageService.list(Conds.get().eq("group_id", groupId));
        if (imageList.isEmpty()) {
            return new ImageVo();
        } else {
            return ImageVo.fromImage(imageList.get(0));
        }
    }

    @Override
    public Map<String, ImageVo> getCargoImage(List<String> cargoIdList, ClientType type) {
        List<Cargo> cargoList = cargoService.list(Conds.get().in("id", cargoIdList));
        Map<String, String> groupIdMap = Maps.newHashMap();
        if (ClientType.pc.equals(type)) {
            for (Cargo cargo : cargoList) {
                if (cargo.getPcAlbumId() != null) {
                    groupIdMap.put(cargo.getId().toString(), cargo.getPcAlbumId().toString());
                }
            }
        } else {
            for (Cargo cargo : cargoList) {
                if (cargo.getMobileAlbumId() != null) {
                    groupIdMap.put(cargo.getId().toString(), cargo.getMobileAlbumId().toString());
                }
            }
        }
        List<Image> imageList = imageService.list(Conds.get().in("group_id", groupIdMap.values()).order("id desc"));
        Map<String, Image> imageMap = ListUtils.toKeyMap(imageList, "groupId");
        Map<String, ImageVo> imageVoMap = Maps.newHashMap();
        for (String id : groupIdMap.keySet()) {
            if (imageMap.get(groupIdMap.get(id)) != null) {
                imageVoMap.put(id, ImageVo.fromImage(imageMap.get(groupIdMap.get(id))));
            }
        }
        return imageVoMap;
    }

    @Override
    public ImageGroupVo getGoodsAlbum(String cargoId, ClientType type) {
        Cargo cargo = cargoService.getById(cargoId);
        if (cargo == null) {
            return new ImageGroupVo();
        }
        Long groupId;
        if (ClientType.pc.equals(type)) {
            groupId = cargo.getPcAlbumId();
        } else {
            groupId = cargo.getMobileAlbumId();
        }
        if (groupId == null) {
            return new ImageGroupVo();
        }
        // 轮播图
        return getImageGroup(groupId.toString());
    }

    @Override
    public ImageGroupVo getImageGroup(String groupId) {
        List<Image> imageList = imageService.list(Conds.get().eq("groupId", groupId));
        List<ImageVo> imageVoList = Lists.newArrayList();
        for (Image image : imageList) {
            ImageVo vo = ImageVo.fromImage(image);
            imageVoList.add(vo);
        }
        return new ImageGroupVo(groupId, imageVoList);
    }

    @Override
    public Long saveGroupImage(List<String> imageUrlList, String userId) {
        Long groupId = IdGenerator.getDefault().nextId();
        for (String url : imageUrlList) {
            Image image = new Image();
            image.setId(IdGenerator.getDefault().nextId() + "");
            image.setGroupId(groupId + "");
            image.setPicUrl(url);
            image.setCreateTime(new Date());
            image.setCreateBy(Long.valueOf(userId));
            imageService.insert(image);
        }
        return groupId;
    }

    @Override
    public Map<String, ImageGroupVo> getImageGroup(List<String> groupIdList) {
        List<Image> imageList = imageService.list(Conds.get().in("group_id", groupIdList));
        Map<String, ImageGroupVo> groupVoMap = Maps.newHashMap();
        for (Image image : imageList) {
            ImageGroupVo groupVo = groupVoMap.get(image.getGroupId().toString()) == null ? new ImageGroupVo() : groupVoMap.get(image.getGroupId().toString());
            groupVo.addImages(ImageVo.fromImage(image));
            groupVoMap.put(image.getGroupId().toString(), groupVo);
        }
        return groupVoMap;
    }

    @Override
    public Map<String, ImageVo> getImageByGroup(List<String> groupIdList) {
        List<Image> imageList = imageService.list(Conds.get().in("group_id", groupIdList));
        Map<String, ImageVo> imageVoMap = Maps.newHashMap();
        int i = 0;
        for (Image image : imageList) {
            System.out.println(image.getGroupId() + "=" + i);
            i++;
            if (image.getGroupId() == null) {
                continue;
            }
            if (imageVoMap.get(image.getGroupId().toString()) != null) {
                continue;
            }
            ImageVo imageVo = ImageVo.fromImage(image);
            imageVoMap.put(image.getGroupId().toString(), imageVo);
        }
        return imageVoMap;
    }


    @Override
    public Map<String, ImageVo> getImageById(List<String> idList) {
        List<Image> imageList = imageService.list(Conds.get().in("id", idList));
        Map<String, ImageVo> imageVoMap = Maps.newHashMap();
        for (Image image : imageList) {
            ImageVo imageVo = ImageVo.fromImage(image);
            imageVoMap.put(imageVo.getId(), imageVo);
        }
        return imageVoMap;
    }

    @Override
    public void deleteImgByGroupId(String groupId) {
        imageService.delete(Conds.get().eq("group_id", groupId));
    }

    @Override
    public ImageVo getImageByImageId(String imageid){
        Image image = imageService.get(Conds.get().eq("id", imageid));
        ImageVo imageVo = new ImageVo();
        if(image != null){
            imageVo.setId(image.getId());
            imageVo.setPicUrl(image.getPicUrl());
        }
        return imageVo;
    }
    @Override
    public String saveImage(ImageVo imageVo,String userId){
        Long groupId = IdGenerator.getDefault().nextId();
            Image image = new Image();
            image.setId(IdGenerator.getDefault().nextId() + "");
            image.setGroupId(groupId + "");
            image.setPicUrl(imageVo.getPicUrl());
            image.setCreateTime(new Date());
            image.setCreateBy(Long.valueOf(userId));
            imageService.insert(image);
        return image.getId();
    }

    @Override
    public boolean deleteImage(String ImageId){
        imageService.deleteById(ImageId);
        return true;
    }
}
