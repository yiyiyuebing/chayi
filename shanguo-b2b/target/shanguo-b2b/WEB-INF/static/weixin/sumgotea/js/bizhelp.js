function getToutiaoUrl(id, url, classify) {
    var rtUrl = '';
    if (classify === "sp") {
        rtUrl = Util.common.baseUrl + "weixinfront/static/goods_detail.html?id=" + url  + "&userId=" + sessionStorage.getItem("userid") + "&storeId=" + sessionStorage.getItem("shopId");
    } else if (classify === "wz") {
        rtUrl = Util.common.baseUrl + "weixinfront/static/article.html?share=0&type=weixinIndex&id=" + url + "&userId=" + sessionStorage.getItem("userid") + "&storeId=" + sessionStorage.getItem("shopId");
    } else {
        rtUrl = "noticeDetail.html?toutiaoId=" + id;
    }
    return rtUrl;
}

function getPhone() {
    var u = navigator.userAgent;
    var phone = '';
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端 
    if (isAndroid) {
        phone = 'Android';
    } else {
        phone = 'IOS';
    }

    return phone;
}

function tofromTime(timestamp) {
    var year=timestamp.getFullYear(); 
    var month=timestamp.getMonth()+1; 
    var date=timestamp.getDate(); 
    var hour=timestamp.getHours(); 
    var minute=timestamp.getMinutes(); 
    var second=timestamp.getSeconds(); 
    return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second; 
}