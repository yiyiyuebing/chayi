package pub.makers.shop.base.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.base.entity.DeliveryAddress;

/**
 * Created by kok on 2017/7/13.
 */
@Repository
public class DeliveryAddressDao extends BaseCRUDDaoImpl<DeliveryAddress, String> {
    @Override
    protected String getTableName() {

        return "delivery_address";
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
