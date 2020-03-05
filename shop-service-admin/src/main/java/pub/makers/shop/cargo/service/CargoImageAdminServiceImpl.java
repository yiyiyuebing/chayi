package pub.makers.shop.cargo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.tradeGoods.entity.Image;
import pub.makers.shop.tradeGoods.service.ImageService;

import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2017/7/3.
 */
@Service
public class CargoImageAdminServiceImpl implements CargoImageMgrBizService {

    @Autowired
    private ImageService imageService;

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
    public void deleteImgByGroupId(String groupId) {
        imageService.delete(Conds.get().eq("group_id", groupId));
    }
}
