package pub.makers.shop.base.util.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.service.StoreSubbranchExtAdminService;
import pub.makers.shop.base.service.StoreSubbranchExtService;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.store.entity.StoreSubbranchExt;


import java.util.Arrays;
import java.util.List;


/**
 * Created by devpc on 2017/7/25.
 */
@Service(version = "1.0.0")
public class StoreSubbranchExtAdminServiceImpl implements StoreSubbranchExtAdminService{

    @Autowired
    private StoreSubbranchExtService storeSubbranchExtService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 增加用户标签
     * @param
     * @return
     */
    @Override
    public  boolean addWeixinUserLabel(StoreSubbranchExt storeSubbranchExt){
        StoreSubbranchExt storeSubbranchExt1 = storeSubbranchExtService.get(Conds.get().eq("store_id", storeSubbranchExt.getStoreId()));
        if(storeSubbranchExt1 != null) {
            String[] backlabel = storeSubbranchExt1.getLabel().split(",");
            List<String> backlabellist = Arrays.asList(backlabel);
            String[] fontlabel = storeSubbranchExt.getLabel().split(",");
            List<String> fontlist = Arrays.asList(fontlabel);
            for (int i = 0; i < fontlabel.length; i++) {
                if (backlabellist.contains(fontlabel[i])) {
                    fontlist.remove(fontlabel[i]);
                }
            }
            backlabellist.addAll(fontlist);
            String str = StringUtils.join(backlabellist.toArray(),",");
            storeSubbranchExtService.update(Update.byId(storeSubbranchExt1.getId()).set("label",str));
        }else {
            Long id = IdGenerator.getDefault().nextId();
            storeSubbranchExt.setId(id);
            storeSubbranchExtService.insert(storeSubbranchExt);
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
        StoreSubbranchExt storeSubbranchExt = storeSubbranchExtService.get(Conds.get().eq("store_id", ids));
        if(storeSubbranchExt != null) {
            storeSubbranchExtService.update(Update.byId(storeSubbranchExt.getId()).set("remark",remarks).set("label",labels));
        }else {
           StoreSubbranchExt storeSubbranchExt1 = new StoreSubbranchExt();
            Long theid = IdGenerator.getDefault().nextId();
            storeSubbranchExt1.setId(theid);
            storeSubbranchExt1.setStoreId(Long.parseLong(id));
            storeSubbranchExt1.setRemark(remarks);
            storeSubbranchExt1.setLabel(labels);
            storeSubbranchExtService.insert(storeSubbranchExt1);
        }
        return true;
    }


}
