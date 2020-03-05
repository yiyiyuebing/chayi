package pub.makers.shop.user.controller;

import com.dev.base.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.store.vo.GoodReceiptAddrVo;
import pub.makers.shop.user.service.GoodReceiptAddrAppService;

import java.util.List;

/**
 * Created by kok on 2017/8/2.
 */
@Controller
@RequestMapping("mobile/addr")
public class GoodReceiptAddrController {
    @Autowired
    private GoodReceiptAddrAppService goodReceiptAddrAppService;

    /**
     * 收货地址列表
     */
    @RequestMapping("list")
    @ResponseBody
    public ResultData listGoodReceiptAddr() {
        List<GoodReceiptAddrVo> list = goodReceiptAddrAppService.listGoodReceiptAddr();
        return ResultData.createSuccess("addrList", list);
    }

    /**
     * 默认收货地址
     */
    @RequestMapping("default")
    @ResponseBody
    public ResultData defaultGoodReceiptAddr() {
        GoodReceiptAddrVo vo = goodReceiptAddrAppService.defaultGoodReceiptAddr();
        return ResultData.createSuccess(vo);
    }

    /**
     * 收货地址详情
     */
    @RequestMapping("detail")
    @ResponseBody
    public ResultData getById(String id) {
        GoodReceiptAddrVo vo = goodReceiptAddrAppService.getById(id);
        return ResultData.createSuccess(vo);
    }

    /**
     * 创建收货地址
     */
    @RequestMapping("save")
    @ResponseBody
    public ResultData createGoodReceiptAddr(String modelJson) {
        ValidateUtils.notNull(modelJson, "参数不能为空");
        GoodReceiptAddrVo addr = JsonUtils.toObject(modelJson, GoodReceiptAddrVo.class);
        GoodReceiptAddrVo vo = goodReceiptAddrAppService.createGoodReceiptAddr(addr);
        return ResultData.createSuccess(vo);
    }

    /**
     * 更新收货地址
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultData updateGoodReceiptAddr(String modelJson) {
        ValidateUtils.notNull(modelJson, "参数不能为空");
        GoodReceiptAddrVo addr = JsonUtils.toObject(modelJson, GoodReceiptAddrVo.class);
        GoodReceiptAddrVo vo = goodReceiptAddrAppService.updateGoodReceiptAddr(addr);
        return ResultData.createSuccess(vo);
    }

    /**
     * 删除收货地址
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultData delGoodReceiptAddr(String id) {
        goodReceiptAddrAppService.delGoodReceiptAddr(id);
        return ResultData.createSuccess();
    }

    /**
     * 更新默认收货地址
     */
    @RequestMapping("updateDefault")
    @ResponseBody
    public ResultData updateDefaultAddr(String id) {
        goodReceiptAddrAppService.updateDefaultAddr(id);
        return ResultData.createSuccess();
    }
}
