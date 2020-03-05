package com.yaoming.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * @preserve public
 */
public class StringUtil {

	public static long[] toLongArray(String str, String splitRegex){
		if(str==null || str.trim().equals(""))
			return new long[0];
		String[] temps = str.split(splitRegex);
		long[] longs = new long[temps.length];
		for (int i=0; i<temps.length; i++)
			longs[i] = Long.parseLong(temps[i]);
		return longs;
	}
	
	public static String randomABC(int size){
		String str="";
		for (int i=0;i<size;i++){
			   char c='a';
			   c=(char)(c+(int)(Math.random()*26));
			   str=str+c;
		}
		return str;
	}
	/**
	 * format string
	 */
	public static String formatStringTrim(Object obj){
		if(obj==null)return "";
		String temp = obj.toString().trim();
		if("".equals(temp) || "null".equals(temp)){
			return "";
		}
		return temp;
	}
	/**
	 * format string
	 */
	public static String ToString(Object obj){
		if(obj==null)return "";
		String temp = obj.toString().trim();
		return temp;
	}
	/**
	 * format string
	 */
	public static String ToStringParam(Object obj){
		if(obj==null)return "";
		String temp = obj.toString().trim();
		//temp = temp.replaceAll("'", "\\\\'");
		return temp;
	}
	public static String formatStringNotTrim(Object obj){
		if(obj==null)return "";
		String temp = obj.toString();
		if("".equals(temp) || "null".equals(temp)){
			return "";
		}
		return temp;
	}
	
	/**
	 * 判断字符串数组 String[]是否为空
	 */
	public static boolean isStringArrayEmpty(Object obj){
		if(obj==null)return true;
		try{
			String[] strs = (String[])obj;
			if(strs.length>0)
				return false;
			else
				return true;
		}catch(Exception e){
			return true;
		}
	}
	public static boolean isNotStringArrayEmpty(Object obj){
		return !isStringArrayEmpty(obj);
	}
	
	/**
	 * 判断两个字符串是否相等
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean isEquals(Object obj1,Object obj2){
		if(ToString(obj1).equals(ToString(obj2))){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断两个字符串是否不相等
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean isNotEquals(Object obj1,Object obj2){
		return !isEquals(obj1,obj2);
	}
	
	/**
	 * 判断是否为空
	 */
	public static boolean isEmpty(String string){
		return (string==null|| string.trim().equals(""));
	}
	/**
	 * 判断是否不为空
	 */
	public static boolean isNotEmpty(String string){
		return !isEmpty(string);
	}
	/**
	 * 获取唯一数
	 */
	public static String  getDateRandow(){
		SimpleDateFormat tempDate = new SimpleDateFormat("yyMMdd" + "" + "hhmmssSS");
		String datetime = tempDate.format(new java.util.Date());    //12位
		int randomInt = (int)(Math.random()*10000);
		datetime =  datetime+randomInt;
		return datetime;
	}
	
	/**
	 * 生成大写数字
	 */
	public static String getBigWriteForTicket(double ap){
		String temp = "00000"+String.valueOf(ap)+"0";
		String[] strs = temp.split("\\.");
		temp = strs[0].substring(strs[0].length()-6, strs[0].length())+strs[1].substring(0, 2);
		char[] cha = temp.toCharArray();
		String[] str = new String[8];
		for(int i=0;i<8;i++){
			if(cha[i]=='0')str[i]="零";
			else if(cha[i]=='1')str[i]="壹";
			else if(cha[i]=='2')str[i]="贰";
			else if(cha[i]=='3')str[i]="叁";
			else if(cha[i]=='4')str[i]="肆";
			else if(cha[i]=='5')str[i]="伍";
			else if(cha[i]=='6')str[i]="陆";
			else if(cha[i]=='7')str[i]="柒";
			else if(cha[i]=='8')str[i]="捌";
			else str[i]="玖";
		}
		return "<b>ⓧ</b>"+str[0]+"<b>拾</b>&nbsp;"+str[1]+"<b>万</b>&nbsp;"+str[2]+"<b>仟</b>&nbsp;"
		+str[3]+"<b>佰</b>&nbsp;"+str[4]+"<b>拾</b>&nbsp;"+str[5]+"<b>元</b>&nbsp;"+str[6]+"<b>角</b>&nbsp;"+str[7]+"<b>分</b>";
//				"￥:</b>&nbsp;"+ap;
	
	}
	
	/**
	 * 获取MD5加密串
	 * create by beeyon
	 * 2012-07-31
	 * @param value
	 * @return
	 */
	public static String getMD5Str(String value) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.reset();
			md5.update(value.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] bytearr = md5.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytearr.length; i++) {
			if (Integer.toHexString(0xFF & bytearr[i]).length() == 1) {
				sb.append("0").append(Integer.toHexString(0xFF & bytearr[i]));
			} else
				sb.append(Integer.toHexString(0xFF & bytearr[i]));
		}
		return sb.toString();
	}
	
	/**
	 * 格式化图片地址  数组
	 * @param obj
	 * @return
	 */
	public static String[] formatImageUrlArray(String url){
		String[] result = null;
		try{
			if(isNotEmpty(url)){
				String[] array = ToString(url).split(",");
				if(array!=null && array.length>0){
					result = new String[array.length];
					for(int i=0;i<array.length;i++){
						String str = array[i];
						//url[i] = formatImageUrl(str);
						result[i] = str;
					}
				}else{
					result = new String[0];
				}
			}else{
				result = new String[0];
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 将字符串数组转为字符串
	 * @param statusArray
	 * @return
	 */
	public static String getStringArrayToString(String[] statusArray) {
		String status = "";
		if(statusArray!=null && statusArray.length>0){
			for(int i=0;i<statusArray.length;i++){
				if(isNotEmpty(statusArray[i])){
					status += "," + statusArray[i];
				}
			}
		}
		if(isNotEmpty(status)){
			status = status.substring(1);
		}
		return status;
	}
	
	/**
	 * 将List数组转为字符串
	 * @param statusArray
	 * @return
	 */
	public static String getListArrayToString(List<String> statusArray) {
		String status = "";
		if(statusArray!=null && statusArray.size()>0){
			for(int i=0;i<statusArray.size();i++){
				if(isNotEmpty(statusArray.get(i))){
					status += "," + statusArray.get(i);
				}
			}
		}
		if(isNotEmpty(status)){
			status = status.substring(1);
		}
		return status;
	}
	
	/**
	 * 将字符串转为字符串数组
	 * @param statusArray
	 * @return
	 */
	public static String[] stringToArray(String string) {
		String[] array = null;
		try{
			if(isNotEmpty(string)){
				String[] to_array = string.split(",");
				if(to_array!=null && to_array.length>0){
					array = new String[to_array.length];
					for(int i=0;i<to_array.length;i++){
						String str = to_array[i];
						array[i] = str;
					}
				}else{
					array = new String[0];
				}
			}else{
				array = new String[0];
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return array;
	}
	
	public static String trim(String str){
		return isEmpty(str) ? "" : str.trim();
	}
	
	/**
	 * 将字符串转为List数组
	 * @param statusArray
	 * @return
	 */
	public static List<String> stringToListArray(String string) {
		List<String> array = new ArrayList<String>();
		try{
			if(isNotEmpty(string)){
				String[] to_array = string.split(",");
				if(to_array!=null && to_array.length>0){
					for(int i=0;i<to_array.length;i++){
						String str = to_array[i];
						array.add(str);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return array;
	}
	
	/**
	 * 将object数组转为string数组
	 * @param obj
	 * @return
	 */
	public static String[] objectArrayToStringArray(Object[] obj){
		String[] str = null;
		try{
			if(obj!=null && obj.length>0){
				str = new String[obj.length];
				for(int i=0;i<obj.length;i++){
					str[i] = ToString(obj[i]);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return str;
	}
	
	public static boolean isImg(String ext){
		if(ext==null || ext.equals(""))
			return false;
		if(ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg")
				|| ext.equalsIgnoreCase("gif") || ext.equalsIgnoreCase("bmp")
				|| ext.equalsIgnoreCase("png"))
			return true;
		return false;
	}
	
	/**
	 * 泛型方法(通用)，把list转换成以“,”相隔的字符串 调用时注意类型初始化（申明类型） 如：List<Integer> intList = new ArrayList<Integer>(); 调用方法：StringUtil.listTtoString(intList); 效率：list中4条信息，1000000次调用时间为850ms左右
	 * 
	 * @param <T> 泛型
	 * @param list list列表
	 * @return 以“,”相隔的字符串
	 */
	public static <T> String listTtoString(List<T> list) {
		if (list == null || list.size() < 1)
			return "";
		Iterator<T> i = list.iterator();
		if (!i.hasNext())
			return "";
		StringBuilder sb = new StringBuilder();
		for (;;) {
			T e = i.next();
			sb.append(e);
			if (!i.hasNext())
				return sb.toString();
			sb.append(",");
		}
	}
}