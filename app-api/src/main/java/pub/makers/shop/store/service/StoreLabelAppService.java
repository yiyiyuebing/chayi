package pub.makers.shop.store.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.store.pojo.StoreLabelParam;
import pub.makers.shop.store.vo.StoreLabelVo;

/**
 * Created by dy on 2017/10/8.
 */
@Service
public class StoreLabelAppService {


    @Reference(version = "1.0.0")
    private StoreLabelBizService storeLabelBizService;

    public ResultData fansLabelList(StoreLabelParam storeLabelParam) {
        return storeLabelBizService.fansLabelList(storeLabelParam);
    }

    public ResultData editLabel(StoreLabelParam storeLabelParam) {
        return null;
    }

    public ResultData editFansLabel(StoreLabelParam storeLabelParam) {
        return storeLabelBizService.editFansLabel(storeLabelParam);
    }

    public ResultData storeLabelList(StoreLabelParam storeLabelParam, Paging paging) {
        return storeLabelBizService.storeLabelList(storeLabelParam, paging);
    }

    public ResultData saveLabel(StoreLabelVo storeLabelVo) {
        return storeLabelBizService.saveLabel(storeLabelVo);
    }

    public ResultData getLabelInfo(StoreLabelVo storeLabelVo) {
        return storeLabelBizService.getLabelInfo(storeLabelVo);
    }

    public ResultData getAllFansList(StoreLabelVo storeLabelVo, Paging paging) {
        return storeLabelBizService.getAllFansList(storeLabelVo, paging);
    }
}
