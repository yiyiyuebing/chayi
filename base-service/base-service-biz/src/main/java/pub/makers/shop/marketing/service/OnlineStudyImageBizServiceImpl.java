package pub.makers.shop.marketing.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.tradeGoods.service.ImageService;
import pub.makers.shop.tradeGoods.entity.Image;

import java.util.Date;

/**
 * Created by dy on 2017/5/3.
 */
@Service(version = "1.0.0")
public class OnlineStudyImageBizServiceImpl implements OnlineStudyImageBizService {

    @Autowired
    private ImageService imageService;

    @Override
    public Image saveStudyImage(String covePic) {
        Image image = new Image();
        image.setId(IdGenerator.getDefault().nextId() + "");
        image.setGroupId(IdGenerator.getDefault().nextId() + "");
        image.setCreateTime(new Date());
        image.setCreateBy(1L);
        image.setPicUrl(covePic);
        return imageService.insert(image);
    }

    @Override
    public Image updateStudyImage(Image image) {
        return imageService.update(image);
    }

    @Override
    public void deleteStudyImageById(long id) {
        imageService.deleteById(id);
    }

    @Override
    public Image getStudyImageById(String id) {
        return imageService.getById(id);
    }
}
