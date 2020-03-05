package pub.makers.shop.tradeGoods.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.tradeGoods.entity.GoodLabels;
import pub.makers.shop.tradeGoods.entity.GoodsBaseLabel;

import java.util.List;

/**
 * Created by kok on 2017/5/26.
 */
@Service(version = "1.0.0")
public class GoodsBaseLabelBizServiceImpl implements GoodsBaseLabelBizService {
    @Autowired
    private GoodsBaseLabelService goodsBaseLabelService;
    @Autowired
    private GoodLabelsService goodLabelsService;

    @Override
    public List<GoodsBaseLabel> getListAll() {
        List<GoodLabels> goodLabelsList = goodLabelsService.list(Conds.get());
        List<String> ids = Lists.transform(goodLabelsList, new Function<GoodLabels, String>() {
            @Override
            public String apply(GoodLabels goodLabels) {
                return goodLabels.getGoodBaseLabelId().toString();
            }
        });
        return goodsBaseLabelService.list(Conds.get().eq("status", 1).in("id", ids).order("order_by desc"));
    }
}
