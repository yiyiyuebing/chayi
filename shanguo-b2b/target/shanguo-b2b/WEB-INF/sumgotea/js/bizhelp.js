function getToutiaoUrl(id, url, classify) {
    var rtUrl = '';
    if (classify === "sp") {
        rtUrl = "goods_detail.html?id=" + url;
    } else if (classify === "wz") {
        // rtUrl = "article.html?share=0&type=weixinIndex&id=" + url + "&userId=" + sessionStorage.getItem("userid") + "&storeId=" + sessionStorage.getItem("shopId");
        reUrl = '#'
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
    return year+"-"+month+"-"+date+" "+dateDouble(hour)+":"+dateDouble(minute)+":"+dateDouble(second); 
}
function tofromTime1(timestamp) {
    var year=timestamp.getFullYear(); 
    var month=timestamp.getMonth()+1; 
    var date=timestamp.getDate(); 
    return year+"-"+dateDouble(month)+"-"+dateDouble(date); 
}

function dateDouble(num) {
    if(num<10&&num >=0){
        num = '0' + num;
    }
    return num
}

function historyBack() {
    if (/(iPhone|iPad|iPod)/i.test(navigator.userAgent)) {             
            window.location.href = window.document.referrer;
    } else { window.history.go("-1"); }
}