package pub.makers.shop.evaluate.api;

import com.dev.base.json.JsonUtils;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.evaluate.service.EvaluateService;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsEvaluationVo;

import java.util.List;
import java.util.Map;

/**
 * Created by Think on 2017/7/19.
 */
@Controller
@RequestMapping("weixin/evaluate")
public class EvaluateApi {
    @Autowired
    private EvaluateService evaluateService;

    @RequestMapping("/addEvaluate")
    @ResponseBody
    public ResultData addEvaluate(String addJson, String tag) {
        if (StringUtils.isNotEmpty(addJson)) {
            ResultData.createFail();
        }

        List<PurchaseGoodsEvaluationVo> pevList = JsonUtils.toObject(addJson, ListUtils.getCollectionType(List.class, PurchaseGoodsEvaluationVo.class));
        if (tag != null) {
            for (PurchaseGoodsEvaluationVo pev : pevList) {
                pev.setUserName("匿名");
            }
        }
        evaluateService.addEvaluation(pevList);
        return ResultData.createSuccess();
    }
}
