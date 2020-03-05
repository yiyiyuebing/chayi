package pub.makers.shop.base.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by dy on 2017/6/24.
 */
public class HttpClientUtil {
    public static InputStream String2Inputstream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }
}
