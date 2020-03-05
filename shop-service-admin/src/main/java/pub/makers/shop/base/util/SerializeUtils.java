package pub.makers.shop.base.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.com.caucho.hessian.io.HessianInput;
import com.alibaba.com.caucho.hessian.io.HessianOutput;

public class SerializeUtils {

	private static final Base64 base64 = new Base64();
	
	public static String hessianSerialize(Serializable obj){
		
		String result = null;
		ByteArrayOutputStream os = new ByteArrayOutputStream(); 
		HessianOutput ho = new HessianOutput(os);
		try {
			ho.writeObject(obj);
			result = base64.encodeAsString(os.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	public static <T>  T hessianDeSerialize(String ipt){
		
		T result = null;
		byte[] byteIpt = base64.decode(ipt);
		ByteArrayInputStream is = new ByteArrayInputStream(byteIpt);
		HessianInput hi = new HessianInput(is); 
		try {
			result = (T)hi.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		return result;
	}
	
	public static <T>  T cloneObject(T t){
		
		T result = null;
		ByteArrayOutputStream os = new ByteArrayOutputStream(); 
		HessianOutput ho = new HessianOutput(os);
		try {
			ho.writeObject(t);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
		HessianInput hi = new HessianInput(is); 
		try {
			result = (T)hi.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}
