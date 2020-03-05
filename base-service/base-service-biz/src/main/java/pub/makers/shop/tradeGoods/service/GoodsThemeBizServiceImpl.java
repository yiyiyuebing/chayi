package pub.makers.shop.tradeGoods.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.tradeGoods.entity.GoodsTheme;
import pub.makers.shop.tradeGoods.vo.GoodsThemeVo;

import java.util.List;

/**
 * Created by kok on 2017/5/27.
 */
@Service(version = "1.0.0")
public class GoodsThemeBizServiceImpl implements GoodsThemeBizService {
    @Autowired
    private GoodsThemeService goodsThemeService;


    @Override
    public List<GoodsThemeVo> selectGoodsThemeForIndex(Integer limit) {
        List<GoodsTheme> themeList = goodsThemeService.list(Conds.get().eq("status", 1).order("sort desc, create_time desc").page(0, limit));
        List<GoodsThemeVo> voList = Lists.transform(themeList, new Function<GoodsTheme, GoodsThemeVo>() {
            @Override
            public GoodsThemeVo apply(GoodsTheme goodsTheme) {
                return GoodsThemeVo.fromGoodsTheme(goodsTheme);
            }
        });
        return voList;
    }
}
