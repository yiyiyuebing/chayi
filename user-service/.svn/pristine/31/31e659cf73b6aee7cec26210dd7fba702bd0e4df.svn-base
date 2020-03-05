package pub.makers.shop.user.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.user.entity.WeixinUserInfo;

/**
 * Created by dy on 2017/5/5.
 */
@Repository
public class WeixinUserInfoDao extends BaseCRUDDaoImpl<WeixinUserInfo, String> {
    @Override
    protected String getTableName() {

        return "weixin_user_info";
    }

    @Override
    protected String getKeyName() {

        return "ID";
    }

    @Override
    protected boolean autoGenerateKey() {

        return false;
    }
}
