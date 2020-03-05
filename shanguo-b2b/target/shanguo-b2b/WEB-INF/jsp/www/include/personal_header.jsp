<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<link rel="stylesheet" type="text/css" href="/static/css/person_header.css">
	<div class="trading-record-header">
        <div class="password-header clearfix">
            <a href="/" class="nav-logo pull-left">
                <img src="/static/images/logo.png">
            </a>
            <div class="trading-record-nav pull-left clearfix">
                <a href="/" class="record-index pull-left">首页</a>
                <div class="record-message pull-left">
                    <a href="/news">消息</a>
                    <i class="dot"></i>
                </div>
            </div>
            <div class="pull-right nav-input clearfix">
                <input class="pull-left person-keyword" type="text" placeholder="铁观音">
                <span class="nav-search pull-left" onclick="PersonCommon.searchBar(this);">搜索</span>
            </div>
        </div>
    </div>
