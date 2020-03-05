package pub.makers.shop.base.util;

import java.io.File;
import java.io.IOException;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.lantu.base.constant.CfgConstants;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerHelper {

	private static final Configuration config;
	
	static{
		config = new Configuration(Configuration.VERSION_2_3_22);
		config.setDefaultEncoding("UTF-8");
		config.setOutputEncoding("UTF-8");
		
		try {
//			System.out.println("===========" + new ClassPathResource("./classes").getFile().getAbsolutePath() + "===========");
//			System.out.println("===========" + new ClassPathResource("./sql").getFile().getAbsolutePath() + "===========");
//			System.out.println("===========" + new ClassPathResource("sql").getFile().getAbsolutePath() + "===========");
//			System.out.println("===========" + new ClassPathResource("").getFile().getAbsolutePath() + "===========");
//			System.out.println("===========" + new ClassPathResource("/").getFile().getAbsolutePath() + "===========");
//			System.out.println("===========" + new ClassPathResource(".").getFile().getAbsolutePath() + "===========");
			config.setDirectoryForTemplateLoading(new File(CfgConstants.getProperties().get("tplPath")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
