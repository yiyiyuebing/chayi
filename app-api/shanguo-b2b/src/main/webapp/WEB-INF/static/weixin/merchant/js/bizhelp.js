function getToutiaoUrl(id, url, classify) {
    var rtUrl = '';
    if (classify === "sp") {
        rtUrl = "goods_detail.html?id=" + url  + "&userId=" + localStorage.getItem("userid") + "&storeId=" + localStorage.getItem("shopId");
    } else if (classify === "wz") {
        rtUrl = "article.html?share=0&type=weixinIndex&id=" + url + "&userId=" + localStorage.getItem("shopId") + "&storeId=" + localStorage.getItem("shopId");
    } else {
        rtUrl = "noticeDetail.html?toutiaoId=" + id;
    }
    return rtUrl;
}

function setPricePoint(price) {
    var priceArr = [];
    if (price.indexOf('.') > -1) {
        priceArr = price.split('.');
    } else {
        priceArr.push(price);
        priceArr.push('00');
    }
    return priceArr;
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

function dateDouble(num) {
    if(num<10&&num >=0){
        num = '0' + num;
    }
    return num
}