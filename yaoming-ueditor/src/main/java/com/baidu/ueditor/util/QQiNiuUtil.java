package com.baidu.ueditor.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

/**
 * 七牛云工具类<br>
 * 1.上传文件<br>
 * @author qiaowenbin
 * @version 0.0.2.20150803
 * @history
 * 	0.0.2.20150803<br>
 * 	0.0.1.20141209<br>
 */
public class QQiNiuUtil {
	
	public static final String ak = QPropertiesUtil.config.getProperty("qiniu.access_key");
	public static final String sk = QPropertiesUtil.config.getProperty("qiniu.secret_key");
	public static final String bucket = QPropertiesUtil.config.getProperty("qiniu.bucketname");
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
	
}
