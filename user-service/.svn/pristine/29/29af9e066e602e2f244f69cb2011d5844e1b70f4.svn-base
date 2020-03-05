package pub.makers.shop.base.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.google.common.base.Joiner;
import com.google.common.io.Files;
import com.lantu.base.constant.CfgConstants;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class SqlHelper {

	private static final String tplPath = CfgConstants.getProperties().get("tplPath");
	private static final Configuration config;
	
	static{
		Resource resource = new ClassPathResource("");
		config = new Configuration(Configuration.VERSION_2_3_22);
		config.setDefaultEncoding("UTF-8");
		config.setOutputEncoding("UTF-8");
		
		try {
			config.setDirectoryForTemplateLoading(resource.getFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
	
	
	public static String getValueFromTpl(String tplName, Object dataModel) {
		String value = null;
		Template template = null;
		try {
			template = config.getTemplate(tplName);
			value = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return value;
	}
}
