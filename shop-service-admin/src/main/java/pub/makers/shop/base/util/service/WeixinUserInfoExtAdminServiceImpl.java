package pub.makers.shop.base.util.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.service.WeixinUserInfoExtAdminService;
import pub.makers.shop.base.service.WeixinUserInfoExtService;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.user.entity.WeixinUserInfoExt;

import java.util.Arrays;
import java.util.List;


/**
 * Created by devpc on 2017/7/25.
 */
@Service(version = "1.0.0")
public class WeixinUserInfoExtAdminServiceImpl implements WeixinUserInfoExtAdminService{

    @Autowired
    private WeixinUserInfoExtService weixinUserInfoExtService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 增加用户标签
     * @param weixinUserInfoExt
     * @return
     */
    @Override
    public  boolean addWeixinUserLabel(WeixinUserInfoExt weixinUserInfoExt){
        WeixinUserInfoExt getweixinUserInfoExt = weixinUserInfoExtService.get(Conds.get().eq("weixin_user_id", weixinUserInfoExt.getWeixinUserId()));
        if(getweixinUserInfoExt != null) {
            String[] backlabel = getweixinUserInfoExt.getLabel().split(",");
            List<String> backlabellist = Arrays.asList(backlabel);
            String[] fontlabel = weixinUserInfoExt.getLabel().split(",");
            List<String> fontlist = Arrays.asList(fontlabel);
            for (int i = 0; i < fontlabel.length; i++) {
                if (backlabellist.contains(fontlabel[i])) {
                    fontlist.remove(fontlabel[i]);
                }
            }
            backlabellist.addAll(fontlist);
            String str = StringUtils.join(backlabellist.toArray(),",");
            weixinUserInfoExtService.update(Update.byId(getweixinUserInfoExt.getId()).set("label",str));
        }else {
            Long id = IdGenerator.getDefault().nextId();
            weixinUserInfoExt.setId(id);
            weixinUserInfoExtService.insert(weixinUserInfoExt);
        };
        return  true;
    }


    /**
     * 修改用户标签
     * @param remarks
     * @param labels
     * @param id
     * @return
     */
    @Override
    public boolean updateUserLabel(String remarks , String labels , String id){
        Long ids = Long.parseLong(id);
        WeixinUserInfoExt getweixinUserInfoExt = weixinUserInfoExtService.get(Conds.get().eq("weixin_user_id", ids));
        if(getweixinUserInfoExt != null) {
            weixinUserInfoExtService.update(Update.byId(getweixinUserInfoExt.getId()).set("remark",remarks).set("label",labels));
        }else {
            WeixinUserInfoExt weixinUserInfoExt = new WeixinUserInfoExt();
            Long theid = IdGenerator.getDefault().nextId();
            weixinUserInfoExt.setId(theid);
            weixinUserInfoExt.setWeixinUserId(Long.parseLong(id));
            weixinUserInfoExt.setRemark(remarks);
            weixinUserInfoExt.setLabel(labels);
            weixinUserInfoExtService.insert(weixinUserInfoExt);
        }
        return true;
    }


}
