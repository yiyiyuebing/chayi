package pub.makers.shop.expressquery.service;

import com.alibaba.fastjson.JSONArray;
import com.dev.base.utils.HttpClientUtils;
import org.springframework.stereotype.Service;
import pub.makers.shop.expressquery.util.MD5;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dy on 2017/5/4.
 */
@Service
public class ExpressService {

    private static final String CUSTOMER ="6C08040BBA46F5EAB2AF6B139691036A";
    private static final String KEY = "hnAeEkKQ1864";

    public String getExpressInfo(String num) {
        String resp = "";
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("num", num);
            map.put("key", KEY);
            String relt = new HttpClientUtils().doPost("http://www.kuaidi100.com/autonumber/auto", map);
            JSONArray arr = JSONArray.parseArray(relt);
            String param = "{\"com\":\"" + arr.getJSONObject(0).get("comCode").toString() + "\",\"num\":\"" + num + "\"}";
            String sign = MD5.encode(param + KEY + CUSTOMER);
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("param", param);
            params.put("sign", sign);
            params.put("customer", CUSTOMER);
            resp = new HttpClientUtils().doPost("http://poll.kuaidi100.com/poll/query.do", params);
        } catch (Exception e) {
            e.printStackTrace();
            return resp;
        }
        return resp;
    }
}
