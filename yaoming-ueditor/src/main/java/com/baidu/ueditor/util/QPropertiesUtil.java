package com.baidu.ueditor.util;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Properties;

/**
 * 资源工具类<br>
 * 1.读取jar包路径<br>
 * 2.读取配置文件<br>
 * 3.获取配置文件中的属性<br>
 * 4.获取配置文件中的属性,返回boolean值<br>
 * @author qiaowenbin
 * @version 0.0.2.20141220
 * @history
 * 	0.0.2.20141220<br>
 * 	0.0.1.20141117<br>
 */
public class QPropertiesUtil {
	
	public static final Properties config;
	static {
		Properties temp = readProperties("/config/qiniu.properties");
		try{
			if(temp.getProperty("ueditor.upload_to", null)==null)
				temp = readProperties("/config/qiniu.default.properties");
		} catch(Exception e){
			temp = readProperties("/config/qiniu.default.properties");
		}
		config = temp;
	}
	
	/**
	 * jar包得到自身的路径
	 * @return
	 */
	public static String getJarPath() {
		String res = null;
		
		try {
			res = URLDecoder.decode(QPropertiesUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	/**
	 * 读取配置文件
	 * @param in
	 * @return
	 */
	public static Properties readProperties(String path){
		Properties p = new Properties();
		try {
			p.load(QPropertiesUtil.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return p;
	}
	
	/**
	 * 获取config的value
	 * @param key
	 * @return
	 */
	public static String get(String key){
		return getProperty(config, key);
	}
	
	/**
	 * 获取配置文件中的属性
	 * @param properties
	 * @param key
	 * @return
	 */
	public static String getProperty(Properties properties, String key){
		if(properties != null && QStringUtil.notEmpty(key)){
			return properties.getProperty(key);
		}else{
			return null;
		}
	}
	
	/**
	 * 获取config的值
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(String key){
		return getPropertyToBoolean(config, key);
	}
	
	/**
	 * 获取配置文件中的属性,返回boolean值
	 * @param properties
	 * @param key
	 * @return
	 */
	public static boolean getPropertyToBoolean(Properties properties, String key){
		if(properties != null && QStringUtil.notEmpty(key)){
			return Boolean.parseBoolean(properties.getProperty(key));
		}else{
			return false;
		}
	}
	
}
