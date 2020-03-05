package pub.makers.shop.cargo.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.cargo.entity.CargoGiftRule;

/**
 * Created by dy on 2017/5/23.
 */
@Repository
public class CargoGiftRuleDao extends BaseCRUDDaoImpl<CargoGiftRule, String> {

    @Override
    protected String getTableName() {

        return "cargo_gift_rule";
    }

    @Override
    protected String getKeyName() {

        return "id";
    }

    @Override
    protected boolean autoGenerateKey() {

        return false;
    }

}
