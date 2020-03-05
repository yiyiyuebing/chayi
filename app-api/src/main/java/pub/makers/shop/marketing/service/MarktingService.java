package pub.makers.shop.marketing.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.marketing.param.StudyParam;
import pub.makers.shop.marketing.vo.OnlineStudyVo;

import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/9/30.
 */
@Service
public class MarktingService {

    @Reference(version = "1.0.0")
    private OnlineStudyBizService onlineStudyBizService;

    /**
     * 客户端请求营销退款文章列表信息
     * @param studyParam
     * @return
     */
    public ResultData findStudyAllList(StudyParam studyParam, Paging paging) {

        OnlineStudyVo onlineStudyVo = new OnlineStudyVo();

        if(StringUtils.isNotBlank(studyParam.getConditions())){
            onlineStudyVo.setTitle(studyParam.getConditions());
            onlineStudyVo.setLabel(studyParam.getConditions());
        }
        if (StringUtils.isNotBlank(studyParam.getStudyType())) {
            onlineStudyVo.setStudyType(studyParam.getStudyType());
        }
        if (StringUtils.isNotBlank(studyParam.getStudyChildType())) {
            onlineStudyVo.setStudyChildType(studyParam.getStudyChildType());
        }

        if (StringUtils.isNotBlank(studyParam.getType())) {
            onlineStudyVo.setType(Long.parseLong(studyParam.getType()));
        }

        if (StringUtils.isNotBlank(studyParam.getCreateTimeOrder())) {
            onlineStudyVo.setOrder("create_time");
            onlineStudyVo.setOrderType(studyParam.getCreateTimeOrder());
        }

        if (StringUtils.isNotBlank(studyParam.getReadNumOrder())) {
            onlineStudyVo.setOrder("read_num");
            onlineStudyVo.setOrderType(studyParam.getReadNumOrder());
        }

        ResultList<OnlineStudyVo> onlineStudyVoResultList = onlineStudyBizService.findStudyAllList(onlineStudyVo, paging);

        List<OnlineStudyVo> onlineStudyVos = onlineStudyVoResultList.getResultList();

        ResultData resultData = new ResultData();
        resultData.put("resultList", onlineStudyVos);
        resultData.put("start", (paging.getPs() -1 ) * paging.getPageSize());
        resultData.put("limit", paging.getPageSize());
        resultData.put("totalRecords", onlineStudyVoResultList.getTotalRecords());
        return resultData;
    }


}
