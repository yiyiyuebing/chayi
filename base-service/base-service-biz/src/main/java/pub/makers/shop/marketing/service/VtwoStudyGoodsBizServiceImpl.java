package pub.makers.shop.marketing.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.marketing.entity.VtwoStudyGoods;
import pub.makers.shop.marketing.vo.OnlineStudyVo;

import java.util.Date;

/**
 * Created by dy on 2017/5/3.
 */
@Service(version = "1.0.0")
public class VtwoStudyGoodsBizServiceImpl implements VtwoStudyGoodsBizService {

    @Autowired
    private VtwoStudyGoodsService vtwoStudyGoodsService;

    @Override
    public void saveStutyGoods(OnlineStudyVo onlineStudyVo) {
        String linkGoods = onlineStudyVo.getLinkGoods();
        String[] linkGoodsArr = linkGoods.split(",");
        for (String goodId : linkGoodsArr) {
            VtwoStudyGoods vtwoStudyGoods = new VtwoStudyGoods();
            vtwoStudyGoods.setCreateTime(new Date());
            vtwoStudyGoods.setGoodsId(goodId);
            vtwoStudyGoods.setId(IdGenerator.getDefault().nextId());
            vtwoStudyGoods.setStudyId(Long.parseLong(onlineStudyVo.getID()));
            vtwoStudyGoodsService.insert(vtwoStudyGoods);
        }
    }

    @Override
    public void deleteLinkGoodByStudyId(Long studyId) {
        vtwoStudyGoodsService.delete(Conds.get().eq("studyId", studyId));
    }
}
