package pub.makers.shop.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.store.pojo.StoreLabelParam;
import pub.makers.shop.store.service.StoreLabelAppService;
import pub.makers.shop.store.vo.StoreLabelVo;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dy on 2017/10/8.
 */
@Controller
@RequestMapping("store/label")
public class StoreLableController {

    @Autowired
    private StoreLabelAppService storeLabelAppService;

    @RequestMapping("getAllFansList")
    @ResponseBody
    public ResultData getAllFansList(HttpServletRequest request, StoreLabelVo storeLabelVo) {
        ValidateUtils.notNull(storeLabelVo, "参数为空");
        ValidateUtils.notNull(storeLabelVo.getStoreId(), "参数为空");
        Paging paging = Paging.build2(request);
        return storeLabelAppService.getAllFansList(storeLabelVo, paging);
    }


    @RequestMapping("getLabelInfo")
    @ResponseBody
    public ResultData getLabelInfo(HttpServletRequest request, StoreLabelVo storeLabelVo) {
        ValidateUtils.notNull(storeLabelVo, "参数为空");
        ValidateUtils.notNull(storeLabelVo.getStoreId(), "参数为空");
        ValidateUtils.notNull(storeLabelVo.getId(), "参数为空");
        return storeLabelAppService.getLabelInfo(storeLabelVo);
    }

    @RequestMapping("saveLabel")
    @ResponseBody
    public ResultData saveLabel(HttpServletRequest request, StoreLabelVo storeLabelVo) {
        ValidateUtils.notNull(storeLabelVo, "参数为空");
        ValidateUtils.notNull(storeLabelVo.getStoreId(), "参数为空");
        return storeLabelAppService.saveLabel(storeLabelVo);
    }

    @RequestMapping("fansLabelList")
    @ResponseBody
    public ResultData fansLabelList(HttpServletRequest request, StoreLabelParam storeLabelParam) {
        ValidateUtils.notNull(storeLabelParam, "参数为空");
        ValidateUtils.notNull(storeLabelParam.getShopId(), "参数为空");
        ValidateUtils.notNull(storeLabelParam.getFansId(), "参数为空");
        return storeLabelAppService.fansLabelList(storeLabelParam);
    }

    @RequestMapping("storeLabelList")
    @ResponseBody
    public ResultData labelList(HttpServletRequest request, StoreLabelParam storeLabelParam) {
        ValidateUtils.notNull(storeLabelParam, "参数为空");
        ValidateUtils.notNull(storeLabelParam.getShopId(), "参数为空");
        Paging paging = Paging.build2(request);
        return storeLabelAppService.storeLabelList(storeLabelParam, paging);
    }

    @RequestMapping("editFansLabel")
    @ResponseBody
    public ResultData editFansLabel(HttpServletRequest request, StoreLabelParam storeLabelParam) {
        ValidateUtils.notNull(storeLabelParam, "参数为空");
        ValidateUtils.notNull(storeLabelParam.getShopId(), "参数为空");
        ValidateUtils.notNull(storeLabelParam.getFansId(), "参数为空");
        return storeLabelAppService.editFansLabel(storeLabelParam);
    }

    @RequestMapping("editLabel")
    public ResultData editLabel(HttpServletRequest request, String fansId, String shopId, String label) {
        ValidateUtils.notNull(shopId, "参数为空");
        StoreLabelParam storeLabelParam = new StoreLabelParam();
        storeLabelParam.setFansId(fansId);
        storeLabelParam.setShopId(shopId);
        storeLabelParam.setLabel(label);
        return storeLabelAppService.editLabel(storeLabelParam);
    }


}
