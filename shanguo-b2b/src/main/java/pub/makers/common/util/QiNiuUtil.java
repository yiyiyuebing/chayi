package pub.makers.common.util;

import com.lantu.base.constant.CfgConstants;
import com.lantu.base.qiniu.QiniuUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import pub.makers.common.util.vo.QiNiuResponse;

import java.io.File;
import java.io.IOException;
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
    public static final String preUrl = CfgConstants.getProperties().get("qiniu.preurl");
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
    public static QiNiuResponse uploadFile(File file, String key, String fileName){
        try {
            uptoken=getUptoken();
            Response response = uploadManager.put(file, fileName, uptoken);
            QiNiuResponse qiNiuResponse = new QiNiuResponse();
            if (response != null && response.statusCode == 200) {
                qiNiuResponse.setId(key);
                qiNiuResponse.setSuccess(true);
                qiNiuResponse.setUrl(preUrl + fileName);
            }
            return qiNiuResponse;
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static QiNiuResponse uploadFileByByte(byte[] file, String key, String fileName) throws IOException {
        try {
            //调用put方法上传
            Response response = uploadManager.put(file, key, getUptoken());
            //打印返回的信息
            QiNiuResponse qiNiuResponse = new QiNiuResponse();
            if (response != null && response.statusCode == 200) {
                qiNiuResponse.setId(key);
                qiNiuResponse.setSuccess(true);
                qiNiuResponse.setUrl(preUrl + fileName);
            }
            return qiNiuResponse;
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        }
        return null;
    }
}
