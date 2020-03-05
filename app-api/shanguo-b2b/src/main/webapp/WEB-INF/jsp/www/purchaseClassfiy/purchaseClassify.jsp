<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/6/2
  Time: 16:59
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="pull-left nav-product">
    <a class="nav-product-title" href="/purchase">所有商品类别<i></i></a>
    <div class="nav-product-wrap clearfix">
        <div class="nav-product-aside-wrap pull-left">
        </div>
        <div class="nav-product-content pull-right">
        </div>
    </div>
</div>


<%--<script src="/static/js/purchaseClassify/purchaseClassify.js"></script>--%>



<script id="tpl-nav-product-aside-group" type="text/html">
  <div class="nav-product-aside-group">
    <a href="/purchase/list?classifyId={{id}}" class="nav-product-aside-title" data-id="{{id}}">{{name}}</a>
    <ul class="clearfix">
      {{if children}}
        {{each children as childClassify index}}
          {{if index < 3}}
            <li class="pull-left"><a href="/purchase/list?classifyId={{childClassify.id}}">{{childClassify.name}}</a></li>
          {{/if}}
        {{/each}}
      {{/if}}
    </ul>
  </div>
</script>

<script id="tpl-nav-product-content-group" type="text/html">
    <div class="nav-product-content-group clearfix">
        <div class="nav-product-content-group-txt pull-left">
            {{if children}}
                {{each children as childClassify}}
                    <div class="product-category-group clearfix">
                        <label class="pull-left"><a href="/purchase/list?classifyId={{childClassify.id}}">{{childClassify.name}}<i></i></a></label>
                        <div class="product-category-link pull-left clearfix">
                            {{if childClassify.children}}
                                {{each childClassify.children as thridClassify}}
                                    <a href="/purchase/list?classifyId={{thridClassify.id}}" class="pull-left">{{thridClassify.name}}</a>
                                {{/each}}
                            {{/if}}
                        </div>
                    </div>
                {{/each}}
            {{/if}}
            {{if brandList && brandList.length > 0}}
                <div class="product-category-group clearfix">
                    <label class="pull-left">品牌<i></i></label>
                    <div class="product-category-link pull-left clearfix">
                        {{each brandList as brand index}}
                            <a href="/purchase/list?classifyId={{id}}&brandIds={{brand.id}}"  class="pull-left">{{brand.name}}</a>
                        {{/each}}
                    </div>
                </div>
            {{/if}}
        </div>
        {{if indexAdImages}}
            <div class="nav-product-content-group-img pull-right">
                {{if indexAdImages.linkType == 'diy'}}
                    {{if indexAdImages.linkDescribe}}
                        <a href="{{indexAdImages.linkDescribe}}" target="_blank">
                            <img src="{{indexAdImages.imageUrl}}">
                        </a>
                    {{else}}
                        <a href="javascript:void(0);">
                            <img src="{{indexAdImages.imageUrl}}">
                        </a>
                    {{/if}}

                {{else if indexAdImages.linkType == 'good'}}
                    <a href="/purchase/detail/{{indexAdImages.linkGoodId}}.html">
                        <img src="{{indexAdImages.imageUrl}}">
                    </a>
                {{else if indexAdImages.linkType == 'classify'}}
                    <a href="/purchase/list?classifyId={{indexAdImages.lnkUrl}}">
                        <img src="{{indexAdImages.imageUrl}}">
                    </a>
                {{else}}
                    <a href="javascript:void(0);">
                        <img src="{{indexAdImages.imageUrl}}">
                    </a>
                {{/if}}
            </div>
        {{/if}}

    </div>
</script>
