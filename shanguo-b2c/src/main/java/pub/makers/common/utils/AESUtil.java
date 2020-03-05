package pub.makers.common.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
* @Title: AESUtil.java 
* @Package com.club.web.weixin.util 
* @Description: TODO(AES加解密) 
* @author 柳伟军   
* @date 2016年5月6日 下午4:36:13 
* @version V1.0
 */
public class AESUtil {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//String str = "52BDE3B02CCC1076BACA5C0CD8A5B5A65846676B46CFB073798C4D6C6A57C93F2CD2998CF562E9968682B567A4215A4C891068F4E5EBE8FFE0761EFFEDA77E9822114D79EE5DC406297761611886EB5E5F71AA197D4E37F276EC847C01D7391DDA3933549AF5AABBBB53C891D3F8FA3E";

        String str = "";
        System.out.println("加密后：" + aesEncrypt(str));

		System.out.println("解密后：" + aesDecrypt(str));

	}

	public static String aesEncrypt(String str) throws Exception {
		String key = "12345678";
		String encrytStr = null;
		byte[] encrytByte;

		byte[] byteRe = enCrypt(str, key);

		// 加密过的二进制数组转化成16进制的字符串
		encrytStr = parseByte2HexStr(byteRe);
		System.out.println("加密后：" + encrytStr);

		return encrytStr;
	}

	public static String aesDecrypt(String str) throws Exception {

		String key = "12345678";
		String encrytStr = null;
		byte[] encrytByte;

		// 加密过的16进制的字符串转化成二进制数组
		encrytByte = parseHexStr2Byte(str);
		encrytStr = deCrypt(encrytByte, key);
		System.out.println("解密后：" + encrytStr);

		return encrytStr;
	}

	/**
	 * 加密函数
	 * 
	 * @param content
	 *            加密的内容
	 * @param strKey
	 *            密钥
	 * @return 返回二进制字符数组
	 * @throws Exception
	 */
	public static byte[] enCrypt(String content, String strKey) throws Exception {
		KeyGenerator keygen;
		SecretKey desKey;
		Cipher c;
		byte[] cByte;
		String str = content;
		
		keygen = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");  
		secureRandom.setSeed(strKey.getBytes());                      
		keygen.init(128, secureRandom);
		desKey = keygen.generateKey();
		
		c = Cipher.getInstance("AES");

		c.init(Cipher.ENCRYPT_MODE, desKey);

		cByte = c.doFinal(str.getBytes("UTF-8"));

		return cByte;
	}

	/**
	 * 解密函数
	 * 
	 * @param src
	 *            加密过的二进制字符数组
	 * @param strKey
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static String deCrypt(byte[] src, String strKey) throws Exception {
		KeyGenerator keygen;
		SecretKey desKey;
		Cipher c;
		byte[] cByte;

		keygen = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");  
		secureRandom.setSeed(strKey.getBytes());                      
		keygen.init(128, secureRandom);
		desKey = keygen.generateKey();
		
		c = Cipher.getInstance("AES");

		c.init(Cipher.DECRYPT_MODE, desKey);

		cByte = c.doFinal(src);

		return new String(cByte, "UTF-8");
	}

	/**
	 * 2进制转化成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

}