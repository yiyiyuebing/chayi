/**
 * Created by Administrator on 2017/6/10.
 */
(function(){   //命名空间
    var html = document.documentElement;
    var hWidth = html.getBoundingClientRect().width;//屏幕宽度
    html.style.fontSize = hWidth/15 + 'px';//  1rem的值  这个16可以修改
})()