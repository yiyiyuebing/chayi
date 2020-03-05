package pub.makers.shop.purchaseOrder.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kok on 2017/6/17.
 */
public class PurchaseOrderQuery implements Serializable {
    private String userId; //用户id
    private String status; //订单状态
    private String name; //订单编号
    private String orderType; //订单类型
    private String flowAsType; //售后类型
    private String orderClientType; //订单来源
    private List<String> flowStatusList; //售后状态列表
    private Integer flowStatusNum; //售后状态编号
    private String startDate; //开始时间
    private String endDate; //结束时间
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<String> getFlowStatusList() {
        return flowStatusList;
    }

    public void setFlowStatusList(List<String> flowStatusList) {
        this.flowStatusList = flowStatusList;
    }

    public String getFlowAsType() {
        return flowAsType;
    }

    public void setFlowAsType(String flowAsType) {
        this.flowAsType = flowAsType;
    }

    public Integer getFlowStatusNum() {
        return flowStatusNum;
    }

    public void setFlowStatusNum(Integer flowStatusNum) {
        this.flowStatusNum = flowStatusNum;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getOrderClientType() {
        return orderClientType;
    }

    public void setOrderClientType(String orderClientType) {
        this.orderClientType = orderClientType;
    }
}