<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>${goods.purGoodsName}【茶叶_茶具_茶礼_采购_批发_进货_正品】-优茶联</title>
  <meta name="keyword" content="${goods.purGoodsName}"/>
  <meta name="description" content="完善的茶叶商城,采购商城，茶具商城，茶具采购商城，品质保证,优质的铁观音、大红袍、普洱茶、红茶、绿茶、白茶、袋泡茶、礼品茶、茶礼、茶具、茶食品,尽在优茶联."/>
  <meta http-equiv="content-type" content="text/html;charset=utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
  <link rel="stylesheet" type="text/css"  href="/static/css/product_details.css">
  <link rel="stylesheet" href="/static/js/lib/kkpager/css/kkpager.css">
  <script>
    var purchaseGoodsId = "${goods.id}";
    var purchaseImage = "${goods.showImages.images[0].url}";
    var groupId = "${goods.groupId}";
    var isSancha = "${goods.isSancha}";
  </script>
</head>

<body>
<%--头部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/header.jsp"></jsp:include>
<%--头部 ending--%>

<%--查询 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/search_header.jsp"></jsp:include>
<%--查询 ending--%>

<%--导航栏 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/nav_top_bar.jsp"></jsp:include>
<%--导航栏 ending--%>

<%--左侧工具栏 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/nav_left_bar.jsp"></jsp:include>
<%--左侧工具栏 ending--%>


<div class="product-bread-nav clearfix">
  <span class="pull-left">当前位置：</span>
  <a class="pull-left" href="/purchase">商城</a>
  <i class="pull-left"> > </i>
  <%--<a class="pull-left" href="#">铁观音</a>--%>
  <%--<i class="pull-left"> > </i>--%>
  <a class="pull-left" href="/purchase/detail/${goods.id}.html"><span class="goodName">${goods.purGoodsName}</span></a>
  <input type="hidden" class="groupId" value="${goods.groupId}">
  <input type="hidden" class="goodId" name="product-id" value="${goods.id}">
  <input type="hidden" name="product-name" value="${goods.purGoodsName}">
  <input type="hidden" name="product-desc" value="${goods.purSubtitle}">
  <input type="hidden" name="product-price" value="${goods.minSupplyPrice}">
  <input type="hidden" name="product-img" value="${goods.showImages.images[0].url}">
</div>


<div class="product-info clearfix">
  <div class="product-zoom-wrap pull-left">
    <div class="product-img">
      <c:if test="${!empty goods.showImages.images}">
        <a href="${goods.showImages.images[0].url}" class="jqzoom" rel="gal">
          <img class="spic"  id="spic" src="${goods.showImages.images[0].url}">
        </a>
      </c:if>

      <c:if test="${!empty goodPromotionalInfo && !empty goodPromotionalInfo.tag1}">
        <div class="product-category">
          <img src="${goodPromotionalInfo.tag1}">
        </div>
      </c:if>
      <%--<i class="zoom-icon"></i>--%>
    </div>
    <div class="product-swiper clearfix">
      <div class="swiper-btn-prev pull-left"></div>
      <div class="swiper-container pull-left">
        <div class="swiper-wrapper">
          <c:forEach items="${goods.showImages.images}" var="img">
            <div class="swiper-slide">
              <a href='javascript:void(0);' rel="{gallery: 'gal', smallimage: '${img.url}',largeimage: '${img.url}'}">
                <img src='${img.url}'>
              </a>
            </div>
          </c:forEach>
        </div>
      </div>
      <div class="swiper-btn-next pull-right"></div>
    </div>
    <div class="product-share clearfix">
      <%--<span class="pull-left" id="shareBtn"><i class="product-share-icon"></i>分享</span>--%>
      <span class="pull-left" onclick="PurchaseGoodsDetail.addToFavorite(this)"><i class="product-collect-icon product-collect-${goods.id}"></i>收藏（<span class="favorite-count" style="margin-right:0px;">0</span>）</span>
    </div>
  </div>
  <input type="hidden" name="orderType" value="${goodType}">
  <c:if test="${goodType == 'normal'}">
    <script>
      var activityEnd = "";
      var activityStart = "";
    </script>
    <%--正常商品--%>
    <jsp:include page="/WEB-INF/jsp/www/purchaseGoods/good_normal.jsp"/>
  </c:if>
  <c:if test="${goodType == 'presell'}">

    <script>
      var activityEnd = "<fmt:formatDate value='${goodPromotionalInfo.activityEnd}' pattern="yyyy-MM-dd HH:mm:ss"/>";
      var activityStart = "<fmt:formatDate value='${goodPromotionalInfo.activityStart}' pattern="yyyy-MM-dd HH:mm:ss"/>";
    </script>

    <%--预售商品--%>
    <jsp:include page="/WEB-INF/jsp/www/purchaseGoods/good_presell.jsp"/>
  </c:if>
  <c:if test="${goodType == 'sale'}">
    <%--促销商品--%>
    <script>
      var activityEnd = "<fmt:formatDate value='${goodPromotionalInfo.activityEnd}' pattern="yyyy-MM-dd HH:mm:ss"/>";
      var activityStart = "<fmt:formatDate value='${goodPromotionalInfo.activityStart}' pattern="yyyy-MM-dd HH:mm:ss"/>";
    </script>
    <jsp:include page="/WEB-INF/jsp/www/purchaseGoods/good_sale.jsp"/>
  </c:if>
</div>

<!--分享弹窗-->
<%--<div class="popup-wrap hide" id="popup">
  <form>
    <div class="popup-bg">
      <div class="popup-box">
        <div class="pop-header clearfix">
          <span class="pull-left">分享一下</span>
          <i class="pop-close pull-right" id="popClose"></i>
        </div>
        <div class="pop-body">
          <h6>说说这个商品怎么样</h6>
          <div class="text-area clearfix">
            <textarea id="shareTxt" name="description" maxlength="72" placeholder="赶紧看看"></textarea>
            <span class="pull-right">还可以输入<i class="number-limit">72</i>个字</span>
          </div>
          <h6>选择配图</h6>
          <div class="share-swiper-wrap clearfix">
            <div class="share-img-prev pull-left"><i></i></div>
            <div class="share-swiper pull-left" id="shareImg">
              <div class="swiper-wrapper pull-left">
                <div class="swiper-slide">
                  <label>
                    <input type="radio" name="shareImg" value="img01">
                    <div class="share-img-box">
                      <img src="/static/images/product_details_img.png">
                      <i></i>
                    </div>
                  </label>
                </div>
                <div class="swiper-slide">
                  <label>
                    <input type="radio" name="shareImg" value="img02">
                    <div class="share-img-box">
                      <img src="/static/images/product_details_img.png">
                      <i></i>
                    </div>
                  </label>
                </div>
                <div class="swiper-slide">
                  <label>
                    <input type="radio" name="shareImg" value="img03">
                    <div class="share-img-box">
                      <img src="/static/images/product_details_img.png">
                      <i></i>
                    </div>
                  </label>
                </div>
                <div class="swiper-slide">
                  <label>
                    <input type="radio" name="shareImg" value="img04">
                    <div class="share-img-box">
                      <img src="/static/images/product_details_img.png">
                      <i></i>
                    </div>
                  </label>
                </div>
                <div class="swiper-slide">
                  <label>
                    <input type="radio" name="shareImg" value="img04">
                    <div class="share-img-box">
                      <img src="/static/images/product_details_img.png">
                      <i></i>
                    </div>
                  </label>
                </div>
                <div class="swiper-slide">
                  <label>
                    <input type="radio" name="shareImg" value="img04">
                    <div class="share-img-box">
                      <img src="/static/images/product_details_img.png">
                      <i></i>
                    </div>
                  </label>
                </div>
              </div>
            </div>
            <div class="share-img-next pull-right"><i></i></div>
          </div>
          <div class="share-position clearfix">
            <span class="pull-left">分享到</span>
            <div class="share-position-input pull-left clearfix" id="shareTo">
              <label class="share-position-group pull-left clearfix">
                <input class="pull-left" type="checkbox" name="shareTo" value="qqZone">
                <div class="share-input-mask pull-left clearfix">
                  <i class="pull-left"></i>
                  <img class="pull-left" src="/static/images/icon_qqZone.png">
                </div>
              </label>
              <label class="share-position-group pull-left clearfix">
                <input class="pull-left" type="checkbox" name="shareTo" value="weiblog">
                <div class="share-input-mask pull-left clearfix">
                  <i class="pull-left"></i>
                  <img class="pull-left" src="/static/images/icon_weiblog.png">
                </div>
              </label>
              <label class="share-position-group pull-left clearfix">
                <input class="pull-left" type="checkbox" name="shareTo" value="share03">
                <div class="share-input-mask pull-left clearfix">
                  <i class="pull-left"></i>
                  <img class="pull-left" src="/static/images/icon_share03.png">
                </div>
              </label>
              <label class="share-position-group pull-left clearfix">
                <input class="pull-left" type="checkbox" name="shareTo" value="weichart">
                <div class="share-input-mask pull-left clearfix">
                  <i class="pull-left"></i>
                  <img class="pull-left" src="/static/images/icon_weichart.png">
                </div>
              </label>
              <label class="share-position-group pull-left clearfix">
                <input class="pull-left" type="checkbox" name="shareTo" value="renren">
                <div class="share-input-mask pull-left clearfix">
                  <i class="pull-left"></i>
                  <img class="pull-left" src="/static/images/icon_renren.png">
                </div>
              </label>
              <label class="share-position-group pull-left clearfix">
                <input class="pull-left" type="checkbox" name="shareTo" value="share06">
                <div class="share-input-mask pull-left clearfix">
                  <i class="pull-left"></i>
                  <img class="pull-left" src="/static/images/icon_share06.png">
                </div>
              </label>
            </div>
          </div>
          <div class="share-submit">
            <input type="submit" value="分享">
          </div>
        </div>
      </div>
    </div>
  </form>
</div>--%>
<c:if test="${goodType == 'presell'}">
  <%--预售商品--%>
  <c:if test="${goodPromotionalInfo.presellType == 'second'}">
    <!--预售全过程-->
    <div class="presell clearfix">
      <div class="presell-title pull-left">预售全过程：</div>
      <div class="presell-content pull-left clearfix">
        <c:if test="${nowDate <= activityEnd}">
          <div class="presell-step presell-step01 pull-left clearfix">
            <i class="presell-step-active presell-icon01 pull-left"></i>
            <div class="presell-txt pull-left">
              <h5>付定金<mark>（限时中！）</mark></h5>
              <span class="activity-end-time"></span>
            </div>
            <div class="icon-right pull-left"></div>
          </div>
        </c:if>
        <c:if test="${nowDate > activityEnd}">
          <div class="presell-step presell-step01 pull-left clearfix">
            <i class="presell-icon01 pull-left"></i>
            <div class="presell-txt pull-left">
              <h5>付定金<mark>（限时中！）</mark></h5>
              <span class="activity-end-time"></span>
            </div>
            <div class="icon-right pull-left"></div>
          </div>
        </c:if>

        <fmt:formatDate value="${goodPromotionalInfo.paymentStart}"  type="both" dateStyle="long" pattern="yyyy-MM-dd" var="paymentStart"/>
        <fmt:formatDate value="${goodPromotionalInfo.paymentEnd}"  type="both" dateStyle="long" pattern="yyyy-MM-dd" var="paymentEnd"/>
        <c:if test="${nowDate <= paymentEnd && nowDate > paymentStart}">
          <div class="presell-step presell-step02 pull-left clearfix">
            <i class="pull-left presell-step-active presell-icon02"></i>
            <div class="presell-txt pull-left">
              <h5><mark>付尾款</mark></h5>
              <span><fmt:formatDate value="${goodPromotionalInfo.paymentStart}" pattern="yyyy-MM-dd"/>~<fmt:formatDate value="${goodPromotionalInfo.paymentEnd}" pattern="yyyy-MM-dd"/></span>
              <span>超出时间不负将不退还定金</span>
            </div>
            <div class="icon-right pull-left"></div>
          </div>
        </c:if>
        <c:if test="${nowDate > paymentEnd || nowDate < paymentStart}">
          <div class="presell-step presell-step02 pull-left clearfix">
            <i class="pull-left presell-icon02"></i>
            <div class="presell-txt pull-left">
              <h5><mark>付尾款</mark></h5>
              <span><fmt:formatDate value="${goodPromotionalInfo.paymentStart}" pattern="yyyy-MM-dd"/>~<fmt:formatDate value="${goodPromotionalInfo.paymentEnd}" pattern="yyyy-MM-dd"/></span>
              <span>超出时间不付尾款，将不予退还定金</span>
            </div>
            <div class="icon-right pull-left"></div>
          </div>
        </c:if>
          <%--<div class="presell-step presell-step02 pull-left clearfix">
              <i class="pull-left presell-icon02"></i>
              <div class="presell-txt pull-left">
                  <h5><mark>付尾款</mark></h5>
                  <span><fmt:formatDate value="${goodPromotionalInfo.paymentStart}" pattern="yyyy-MM-dd HH:mm:ss"/>~<fmt:formatDate value="${goodPromotionalInfo.paymentEnd}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                  <span>超出时间不负将不退还定金</span>
              </div>
              <div class="icon-right pull-left"></div>
          </div>--%>
        <div class="presell-step presell-step03 pull-left clearfix">
          <i class="pull-left presell-icon03"></i>
          <div class="presell-txt pull-left">
            <h5>组织生产</h5>
          </div>
          <div class="icon-right pull-left"></div>
        </div>
        <div class="presell-step presell-step04 pull-left clearfix">
          <i class="pull-left presell-icon04"></i>
          <div class="presell-txt pull-left">
            <h5>发货</h5>
              <%--<span>付尾款后7天发货</span>--%>
          </div>
        </div>
      </div>

    </div>
  </c:if>
</c:if>


<c:if test="${goods.isSample != '0' && !empty goods.sample}">
  <!--免费拿样-->
  <div class="sample clearfix">
    <div class="sample-txt01 pull-left">
      <span>立即拿样</span>
      <i>进货先拿样，批发有保障</i>
    </div>
    <div class="sample-txt02 pull-left">
      拿样支付后15天内，二次店铺进货2800元还样品费。样品随
      机发货，不携色，挑码，且每个买家限购1件。<%--<a href="#">拿样规则</a>--%>
    </div>
    <div class="sample-txt03 pull-left clearfix">
      <div class="sample-txt03-group pull-left clearfix">
        <label class="pull-left">拿样价</label>
        <span class="pull-left"><mark>${goods.sample.samplePrice}</mark>元/${goods.sample.unit}</span>
      </div>
      <div class="sample-txt03-group pull-left clearfix">
        <label class="pull-left">起订量</label>
        <span class="pull-left">${goods.sample.startNum}${goods.sample.unit}</span>
      </div>
      <div class="sample-txt03-group pull-left clearfix">
        <label class="pull-left">样品规格</label>
        <span class="pull-left"><mark>${goods.sample.sampleSku}</mark></span>
      </div>
      <div class="sample-txt03-group pull-left clearfix">
        <label class="pull-left">运费</label>
        <span class="pull-left">与产品运费一致</span>
      </div>
    </div>
    <div class="sample-btn pull-right">
      <a href="javascript:void(0);" onclick="PurchaseGoodsDetail.createSampleOrder(this)" data-sku-id="<c:if test='${!empty goods.goodSkuVoList}'>${goods.goodSkuVoList[0].id}</c:if>" data-good-id="${goods.id}" data-start-num="${goods.sample.startNum}">立即拿样</a>
    </div>
  </div>
</c:if>
<!--产品介绍-->
<div class="product-intro clearfix">
  <div class="intro-aside pull-left">
    <%--左部页面模版内容--%>
  </div>
  <div class="intro-section pull-right">

    <div class="intro-title clearfix" id="introTitle">
      <span class="intro-title-active pull-left">详情信息</span>
      <span class="pull-left intro-evaluat-count">评价（0）</span>
      <span class="pull-left">售后保障</span>
      <%--<span class="pull-left">订购说明</span>--%>
    </div>
    <div class="intro-content">
      <div class="intro-content-group recommend-details clearfix">
        <ul class="intro-info clearfix">
        </ul>
        <div class="center-xqad hide"></div>

        <div class="product-info-img">
          ${goods.pcDetailInfo}
        </div>
        <div class="bottom-xqad hide"></div>
      </div>
      <div class="intro-content-group recommend-list" style="display: none;">
        <div class="product-info-recommend">
          <h5>商品评价</h5>
          <div class="product-recommend-star clearfix">
            <span class="pull-left" id="avgScore">0</span>
            <div class="star-wrap recommend-star  pull-left">
              <div class="star-mask" style="width: 20%;"></div>
              <i class="recommend-star05 pull-left clearfix" data-star="0"></i>
            </div>
            <%--<i class="recommend-star recommend-star01 pull-left"></i>--%>
          </div>
          <div class="recommend-info">
            <div class="recommend-info-title clearfix">
              <a class="recommend-info-title-active pull-left" data-type="1" href="javascript:void(0);"> 全部评论（<span id="totalCount"></span>） </a>
              <a href="javascript:void(0);" data-type="2" class="pull-left"> 晒图（<span id="imageCount"></span>）</a>
              <a href="javascript:void(0);" data-type="3" class="pull-left"> 好评（<span id="goodCount"></span>）</a>
              <a href="javascript:void(0);" data-type="4" class="pull-left"> 中评（<span id="middleCount"></span>）</a>
              <a href="javascript:void(0);" data-type="5" class="pull-left"> 差评（<span id="badCount"></span>）</a>
            </div>
            <div class="recommend-info-content clearfix">
              <ul class="recommend-info-content-list">

              </ul>
              <div id="kkpager"></div>
            </div>
          </div>
          <%--<a class="intro-info-banner" style="margin-bottom: 20px" href="#">--%>
          <%--<img src="/static/images/product_details_banner_img03.png">--%>
          <%--</a>--%>
        </div>
      </div>
      <div class="intro-content-group recommend-details" style="display: none;">
        <div>
          ${goods.afterSell}
        </div>
      </div>
      <%--<div class="intro-content-group ordering" style="display: none;">
          <div class="ordering-group">
              <h4>批发说明</h4>
              <div class="ordering-line"></div>
              <h5>供应商支持混批，500元以上起批，或者6斤以上起批。</h5>
              <div class="ordering-txt">
                  <h6>什么是混批？</h6>
                  <p>是指不限产品的种类和样式，买家只要采购总价（或总量）达到或高于设置金额（或数量）即可享受批发价格。</p>
              </div>
          </div>
          <div class="ordering-group">
              <h4>交易流程</h4>
              <div class="ordering-line"></div>
              <div class="clearfix">
                  <div class="ordering-img pull-left">
                      <img src="/static/images/icon_alipay.png">
                  </div>
                  <div class="ordering-txt ordering-pay pull-left">
                      <h6>付款方式：</h6>
                      <p>买家先选择支付方式（路网上银行、快捷支付、支付宝余额等）付款到支付宝，支付宝担保货款安全；但买家收到货品并确认后，再由支付宝打款给供应商。如逾期未收到商品或商品不符合要求，买家可以提出退款申请以保障自身权益。</p>
                  </div>
              </div>
              <div class="ordering-route clearfix">
                  <h5 class="ordering-route-title">使用支付宝付款流程</h5>
                  <div class="ordering-route-img clearfix">
                      <span class="pull-left">1 选择商品</span>
                      <i class="icon-arrows-right pull-left"></i>
                      <span class="pull-left">2 确认订单信息</span>
                      <i class="icon-arrows-right pull-left"></i>
                      <span class="pull-left">3 选择付款方式并付款</span>
                      <i class="icon-arrows-right pull-left"></i>
                      <span class="pull-left">4 收到货并验货满意</span>
                      <i class="icon-arrows-right pull-left"></i>
                      <span class="pull-left">5 确认收货</span>
                      <i class="icon-arrows-right pull-left"></i>
                      <span class="pull-left">6 交易成功</span>
                  </div>
              </div>
          </div>
          <div class="ordering-group">
              <h4>保障说明</h4>
              <div class="ordering-line"></div>
              <div class="ordering-imgTxt clearfix">
                  <div class="ordering-img pull-left">
                      <img src="/static/images/icon_promise.png">
                  </div>
                  <div class="ordering-txt pull-left">
                      <h6>买家保障：</h6>
                      <p>卖家缴纳保证金为买家提供交易保障，若卖家发生违约或不诚信行为，买家可获得先行赔付。<a href="javascript:void(0);">了解详情 》</a></p>
                  </div>
              </div>
              <div class="problem-route clearfix">
                  <img src="/static/images/product_promise_router_img.png">
              </div>
          </div>
      </div>--%>
    </div>
  </div>
</div>



<div class="bottom-product-list hide">
  <%--底部商品列表--%>
</div>

<%--尾部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
<%--尾部 ending--%>
</body>
</html>
<script src="/static/js/lib/kkpager/kkpager.js"></script>
<script src="/static/js/purchaseGoods/product_details.js"></script>


<script id="tpl-details-evaluat" type="text/html">


  <li class="clearfix">
    <div class="recommend-userInfo pull-left clearfix">
      <div class="recommend-userImg pull-left">
        {{if headImgUrl}}
        <img src="{{headImgUrl}}">
        {{else}}
        <img src="/static/images/youchalian.png">
        {{/if}}
      </div>
      <span class="recommend-userName pull-left">{{userName}}</span>
    </div>
    <div class="recommend-userTxt pull-left">
      <div class="star-wrap recommend-userTxt-star">
        <div class="star-mask" style="width: {{score / 5 * 100}}%;"></div>
        <i class="recommend-star05 pull-left clearfix" data-star="0"></i>
      </div>
      <div class="recommend-txt">{{content}}</div>
      <ul class="recommend-img-list clearfix" id="imglist{{id}}">

        {{if images && images.images}}
        {{each images.images as img}}
          {{if img.url}}
          <li class="pull-left">
            <img src="{{img.url}}">
          </li>
          {{/if}}
        {{/each}}
        {{/if}}

      </ul>
      <div class="bigImgWrap" data-target="bigImg{{id}}">
        <div class="bigImg">
          <img class="showImg" src="">
          <div class="cursorBtn imgPrev"></div>
          <div class="cursorBtn imgHide"></div>
          <div class="cursorBtn imgNext"></div>
        </div>
      </div>
      <div class="recommend-product-info">
        <span>{{goodName}}</span>
        <%--<span>1</span>--%>
        <span>{{evaluateTime}}</span>
      </div>
      <div class="recommend-responseTxt">
        {{if repliedList}}
        {{each repliedList as reply}}
        解释：{{reply.content}}
        {{/each}}
        {{/if}}
      </div>
    </div>
  </li>
</script>

<script type="text/html" id="tpl-left-ad">
  {{if adUrl}}
  <a target="_blank" href="{{adUrl}}" class="intro-aside-header">
    <img src="{{adImgUrl}}">
  </a>
  {{else}}
  <a href="javascript:void(0);" style="pointer-events: none;" class="intro-aside-header">
    <img src="{{adImgUrl}}">
  </a>
  {{/if}}

</script>

<script type="text/html" id="tpl-info-banner-1">
  {{#detail}}
</script>

<script type="text/html" id="tpl-info-banner-2">
  {{#detail}}
</script>

<script type="text/html" id="tpl-left-product-list">
  <div class="intro-aside-body">
    <h6>{{modelName}}</h6>
    {{each goodList as good index}}
    <div class="intro-aside-group">
      <a class="intro-aside-img" href="/purchase/detail/{{good.id}}.html">
        {{if good.image && good.image.url}}
        <img src="{{good.image.url}}">
        {{else}}
        <img src="/static/images/product_list_aside_img.png">
        {{/if}}

      </a>
      <a class="intro-aside-txt" href="/purchase/detail/{{good.id}}.html">
        {{good.name}}
      </a>
                        <span class="intro-aside-price">
                            {{if shopId}}
                              供货价：¥{{good.price}}
                            {{else}}
                              供货价：询价
                            {{/if}}
                        </span>
                        <span class="intro-aside-sale">
                            {{if good.saleNum}}
                                销量：{{good.saleNum}}笔
                            {{/if}}
                        </span>
    </div>
    {{/each}}
  </div>
</script>
<%--右侧看一看--%>
<script id="tpl-right-slide" type="text/html">
  <div class="swiper-slide">
    <a href="/purchase/detail/{{id}}.html">
      <div class="img-wrap">
        {{if image && image.url}}
        <img src="{{image.url}}">
        {{else}}
        <img src="/static/images/product_list_recommend_img.png">
        {{/if}}
                <span>¥
                    {{if shopId}}
                       {{price}}
                    {{else}}
                      询价
                    {{/if}}
                </span>
      </div>
    </a>
  </div>
</script>
<%--底部模版--%>
<script id="tpl-bottom-tpl-dsp" type="text/html">
  <div class="product-may-like">
    <div class="product-may-like-header clearfix">
      <span class="may-like-left pull-left">{{modelName}}</span>
      <%--<a class="may-like-right pull-right"><i></i>换一批</a>--%>
    </div>
    <ul class="may-like-product clearfix">
      {{each goodList as good index}}
      {{if index < 5}}
      <li class="pull-left">
        <div class="product-wrap">
          <a href="/purchase/detail/{{good.id}}.html" class="may-like-img">
            {{if good.image && good.image.url}}
            <img src="{{good.image.url}}">
            {{else}}
            <img src="/static/images/product_list_aside_img.png">
            {{/if}}
          </a>
          <a href="/purchase/detail/{{good.id}}.html" class="may-like-txt">
            {{good.name}}
          </a>
          <span class="price"><sub>¥</sub>
            {{if shopId}}
              {{good.price}}
            {{else}}
              询价
            {{/if}}
          </span>
          <span class="sails-number">
            {{if good.saleNum}}
              销量：{{good.saleNum}}笔
            {{else}}
              销量：0笔
            {{/if}}
          </span>
        </div>
      </li>
      {{/if}}
      {{/each}}
    </ul>
  </div>
</script>
