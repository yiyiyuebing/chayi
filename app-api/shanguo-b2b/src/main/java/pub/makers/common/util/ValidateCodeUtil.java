package pub.makers.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * Created by dy on 2017/6/10.
 */
public class ValidateCodeUtil {
    //图形验证码的字符集，系统将随机从这个字符串中选择一些字符作为验证码
    public static String codeChars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    //返回一个随机颜色
    public static Color getRandomColor(int minColor, int maxColor){
        Random random = new Random();
        if (minColor > 255) {
            minColor = 255;
        }
        if (maxColor > 255) {
            maxColor = 255;
        }
        //获得r的随机颜色值
        int red = minColor + random.nextInt(maxColor - minColor);
        //g
        int green = minColor + random.nextInt(maxColor - minColor);
        //b
        int blue = minColor + random.nextInt(maxColor - minColor);
        return new Color(red, green, blue);
    }
}
