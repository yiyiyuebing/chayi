package pub.makers.common.util;

import com.dev.base.json.JsonUtils;
import com.dev.base.utils.HttpClientUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import pub.makers.common.util.vo.CityVo;

import java.util.Map;

/**
 * Created by dy on 2017/6/8.
 */
public class IpUtil {
    /**
     * ip查询城市
     */
    public static CityVo getIpCity(String ip) {
        CityVo city = new CityVo();
        Map<String, Object> params = Maps.newHashMap();
        params.put("ip", ip);
        String resultStr = HttpClientUtils.doGet("http://ip.taobao.com/service/getIpInfo.php", params);
        if (StringUtils.isEmpty(resultStr)) {
            return null;
        }
        Map<String, Object> resultMap = JsonUtils.toObject(resultStr, Map.class);
        if (resultMap.get("data") == null) {
            return null;
        }
        Map<String, Object> data = (Map<String, Object>) resultMap.get("data");
        city.setProvince(data.get("region") == null ? null : data.get("region").toString());
        city.setCity(data.get("city") == null ? null : data.get("city").toString());
        city.setArea(data.get("county") == null ? null : data.get("region").toString());
        return city;
    }
}
