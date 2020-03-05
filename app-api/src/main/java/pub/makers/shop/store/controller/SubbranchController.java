package pub.makers.shop.store.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.base.utils.ValidateUtils;
import com.lantu.base.common.entity.BoolType;

import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.store.entity.GeoShopInfo;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.service.SubbranchAppService;
import pub.makers.shop.store.vo.SubbranchVo;
import pub.makers.shop.user.utils.AccountUtils;



@Controller
@RequestMapping("store/subbranch")
public class SubbranchController {

	@Autowired
	private SubbranchAppService subbranchAppService;
	
	/**
	 * 查询店铺经营概况
	 * {/store/subbranch/summary}
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("summary")
	@ResponseBody
	public ResultData summary(HttpServletRequest request, HttpServletResponse response){
		
		String shopId = AccountUtils.getCurrShopId();
		return subbranchAppService.querySummary(shopId);
	}
	
	/**
	 * 添加子账号
	 * {/store/subbranch/addSubAccount}
	 * @return
	 */
	@RequestMapping(value="addSubAccount")
	@ResponseBody
	public ResultData addSubAccount(HttpServletRequest request, HttpServletResponse response, String parentId, Subbranch subAccount){
		
		//
		ValidateUtils.notNull(subAccount.getUserName(), "名称不能为空");
		ValidateUtils.notNull(subAccount.getPassword(), "密码不能为空");
		ValidateUtils.notNull(subAccount.getPhone(), "手机号码不能为空");
//		ValidateUtils.notNull(subAccount.getDescription(), "手机号码不能为空");
//		ValidateUtils.notNull(subAccount.getProvince(), "职位号码不能为空");
		ValidateUtils.notNull(parentId, "所属店铺不能为空");
		
		
		Subbranch s= subbranchAppService.addSubAccount(parentId, subAccount);
		return ResultData.createSuccess("data", s);
	}
	
	
	/**
	 * 编辑子账号信息
	 * {/store/subbranch/editSubAccount}
	 * @param subAccount
	 * @return
	 */
	@RequestMapping("editSubAccount")
	@ResponseBody
	public ResultData editSubAccount(Subbranch subAccount){
		
		ValidateUtils.notNull(subAccount.getId(), "子账号ID不能为空");
		Subbranch s = subbranchAppService.editSubAccount(subAccount);
		
		return ResultData.createSuccess("data", s);
	}
	
	
	/**
	 * 更新子账号的否有效状态
	 * {/store/subbranch/updateIsValid}
	 * @param subAccountid
	 * @param isValid
	 * @return
	 */
	@RequestMapping("updateIsValid")
	@ResponseBody
	public ResultData updateIsValid(String id, String isValid){
		
		ValidateUtils.notNull(id, "子账号ID不能为空");
		ValidateUtils.isTrue(BoolType.T.name().equals(isValid) || BoolType.F.name().equals(isValid), "isValid参数错误");
		
		subbranchAppService.updateIsValid(id, isValid);
		
		return ResultData.createSuccess();
	}
	

	/**
	 * 查询店铺的子账号列表
	 * @param parentId
	 * @return
	 */
	@RequestMapping("listSubAccountByParent")
	@ResponseBody
     public ResultData listSubAccountByParent(String parentId){

		ValidateUtils.notNull(parentId, "店铺ID不能为空");
		List<Subbranch> slist= subbranchAppService.listSubAccountByParent(parentId);
	
		return ResultData.createSuccess("data", slist);
	}
	
	
    /**
     * 删除店员信息
     * @param delFlag
     * @return
     */
	@RequestMapping("delSubAccount")
	@ResponseBody
	public ResultData delete(String id){

		ValidateUtils.notNull(id, "子账号ID不能为空");
		subbranchAppService.delete(id);
		
		return ResultData.createSuccess();
		
		
	}
	
	/**
	 * 查找附近的店铺
	 * @param lat
	 * @param lng
	 * @return
	 */
	@RequestMapping("findNearBy")
	@ResponseBody
	public ResultData findNearBy(Double lat, Double lng, Integer distance){
		
		List<SubbranchVo> geoShopInfoList = subbranchAppService.findNearBy(lat,lng, distance);
		
		return ResultData.createSuccess("data", geoShopInfoList);
	}
	
}
