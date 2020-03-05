/**
 * Created by Administrator on 2017/8/5.
 */
var isLogin = {
    init:function(){
        var login = localStorage.getItem('token');
        var pathname = window.location.pathname;
        var search = window.location.search;
        if(!login || login == null || login == ''){
            window.location.href = 'login.html?url=' + pathname + search;
        }else{
            Util.common.removeloadingAnimate();
        }
    }
};



