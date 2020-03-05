package pub.makers.shop.promotion.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.shop.base.util.SqlHelper;
import pub.makers.shop.promotion.vo.SalePromotionActivityVo;

import java.util.List;
import java.util.Set;

/**
 * Created by kok on 2017/8/21.
 */
@Service(version = "1.0.0")
public class SaleBizServiceImpl implements SaleBizService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<SalePromotionActivityVo> listForGoodsSku(Set<String> goodSkuIdSet) {
        String stmt = SqlHelper.getSql("sql/promotion/sale/listForGoods.sql");
        String inStr = SqlHelper.getInStr(goodSkuIdSet);

        List<SalePromotionActivityVo> resultList = jdbcTemplate.query(String.format(stmt, inStr), ParameterizedBeanPropertyRowMapper.newInstance(SalePromotionActivityVo.class));
        return resultList;
    }
}
