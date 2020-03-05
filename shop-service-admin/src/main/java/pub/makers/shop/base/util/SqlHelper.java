package pub.makers.shop.base.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.io.Files;
import com.lantu.base.constant.CfgConstants;

public class SqlHelper {

	private static final String tplPath = CfgConstants.getProperties().get("tplPath");
	
	public static String getSql(String path){
		
		String sql = "";
		
		try {
			List<String> strs = Files.readLines(new File(tplPath + path), Charset.forName("utf-8"));
			sql = Joiner.on(" ").join(strs);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		return sql;
		
	}
	
	public static String getInStr(Set idSet){
		
		String inStr = Joiner.on("','").join(idSet);
		if (inStr.length() > 0){
			inStr = String.format("'%s'", inStr);
		}
		else {
			inStr = "'-1'";
		}
		
		return inStr;
	}
}
