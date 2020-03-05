package pub.makers.shop.base.util;

import com.lantu.base.constant.CfgConstants;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by dy on 2017/6/7.
 */
public class QiNiuUtil {

    public static final String ak = CfgConstants.getProperties().get("qiniu.access_key");
    public static final String sk = CfgConstants.getProperties().get("qiniu.secret_key");
    public static final String bucket = CfgConstants.getProperties().get("qiniu.bucketname");
    public static String uptoken = null;

    /**
     * 获取token
     * @return
     */
    public static String getUptoken(){
        return Auth.create(ak, sk).uploadToken(bucket);
    }

    /**
     * 生成uptoken
     */
    public static void genUptoken(){
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                uptoken = getUptoken();
            }
        }, 0, 3500, TimeUnit.SECONDS);
    }

    /**
     * 上传文件
     * @param path
     * @param file
     */
    public static UploadManager uploadManager = new UploadManager();
    public static void uploadFile(String path, String file){
        try {
            uptoken=getUptoken();
            uploadManager.put(file, path, uptoken);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }
    public static String uploadFile(File file, String key){
        try {
            uptoken=getUptoken();
            Response response = uploadManager.put(file, key, uptoken);
            return response.address;
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return null;
    }
}
