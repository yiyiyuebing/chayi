package pub.makers.shop.base.controller;

import com.dev.base.json.JsonUtils;
import com.lantu.base.qiniu.QiniuResult;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.common.util.QiNiuUtil;
import pub.makers.common.util.vo.QiNiuResponse;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseGood.pojo.TplQuery;
import pub.makers.shop.cargo.vo.GoodPageTplVo;
import sun.rmi.rmic.iiop.IDLGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * Created by dy on 2017/6/13.
 */
@Controller
@RequestMapping("/uploader")
public class UploaderController {

    /**
     * 查询页面模版
     * @return
     */
    @RequestMapping("/updloaderImg")
    @ResponseBody
    public ResultData updloaderImg(@RequestParam("file") MultipartFile multipartFile, String imgName,
                                   HttpServletRequest request, HttpServletResponse response) throws IOException {
        QiNiuResponse qiNiuResponse = new QiNiuResponse();
        CommonsMultipartFile cf = (CommonsMultipartFile) multipartFile;
        String fileName = multipartFile.getOriginalFilename();
        byte[] fileByte = multipartFile.getBytes();
        String suffix = "";
        suffix = fileName.substring(fileName.lastIndexOf("."));
        String key = new Date().getTime() + "";
        fileName = key + suffix;
        qiNiuResponse = QiNiuUtil.uploadFileByByte(fileByte, fileName, fileName);
        if (qiNiuResponse == null && StringUtils.isBlank(qiNiuResponse.getUrl())) {
            return ResultData.createFail();
        }
        return ResultData.createSuccess(qiNiuResponse);
    }
}
