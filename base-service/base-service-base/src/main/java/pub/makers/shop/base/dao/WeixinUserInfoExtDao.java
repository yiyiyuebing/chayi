package pub.makers.shop.base.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.base.entity.SysDict;
import pub.makers.shop.user.entity.WeixinUserInfoExt;

/**
 * Created by devpc on 2017/7/25.
 */
@Repository
public class WeixinUserInfoExtDao extends BaseCRUDDaoImpl<WeixinUserInfoExt, Long> {
    @Override
    protected String getTableName() {

        return "weixin_user_info_ext";
    }

    @Override
    protected String getKeyName() {

        return "id";
    }

    @Override
    protected boolean autoGenerateKey() {

        return true;
    }
}
