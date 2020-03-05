package pub.makers.shop.base.service;

import pub.makers.shop.base.entity.CommonText;

/**
 * Created by dy on 2017/7/28.
 */
public interface CommonTextBizService {

    /**
     * 根据使用类型获取相关内容
     * @param type
     * @return
     */
    CommonText getCommonTextByType(String type);

}
