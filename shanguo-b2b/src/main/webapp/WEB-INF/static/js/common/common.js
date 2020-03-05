/**
 * Created by dy on 2017/6/5.
 */
/**
 * Created by zzl on 2016/11/9.
 */
($.extend({
    /**
     * options.scopeId：选择器
     * options.data：待加载数据
     * @param options
     * 要求：需要给标签设置model属性，并设属性值（data中的key值）
     */
    loadData: function(options) {
        var _scopeId = options.scopeId;
        var _data = options.data && typeof options.data === "object" ? options.data : {};
        if (typeof _scopeId !== "string") {
            throw new Error("scopeId must be a string !");
        }
        var $scope = $(_scopeId);
        for (key in _data) {
            var key = key;
            var value = _data[key];
            $scope.find("[model='"+key+"'],[model='"+key+"[]']").each(function(){
                tagName = $(this)[0].tagName;
                if (tagName == 'INPUT') {
                    var type = $(this).attr('type');
                    if (type == 'radio') {
                        $(this).attr('checked', $(this).val() == value);
                    } else if (type=='checkbox') {
                        if (Object.prototype.toString.call(value) === '[object Array]') {
                            for(var i = 0; i < value.length; i++){
                                if($(this).val() == value[i]){
                                    $(this).attr('checked',true);
                                    break;
                                }
                            }
                        } else {
                            var  arr = value.split(',');
                            for(var i = 0; i < arr.length; i++) {
                                if ($(this).val() == arr[i]) {
                                    $(this).attr('checked', true);
                                    break;
                                }
                            }
                        }
                    } else {
                        $(this).val(value);
                    }
                } else if (tagName == 'SELECT' || tagName == 'TEXTAREA') {
                    $(this).val(value);
                } else if (tagName == 'IMG') {
                    $(this).attr("src", value);
                } else {
                    $(this).html(value);
                }
            });
        }
    },
    form: {
        load: function(options) {
            var _formId = options.formId;
            var _url = options.url;
            var _data = options.data && typeof options.data === "object" ? options.data : {};
            var _formData = options.formData && typeof options.formData === "object" ? options.formData : {};
            var _onLoadSuccess = options.onLoadSuccess && typeof options.onLoadSuccess === "function" ? options.onLoadSuccess : function(result){};
            var _onLoadError = options.onLoadError && typeof options.onLoadError === "function" ? options.onLoadError : function(){};
            if (typeof _formId !== "string") {
                throw new Error("formId must be a string !");
            }
            if (_url && typeof _url !== "string") {
                throw new Error("url must be a string !")
            }
            var $form = $(_formId);
            if (_url) {
                $.ajax({
                    url: _url,
                    type: 'post',
                    dataType: 'json',
                    progress: true,
                    data: _formData,
                    success: function(result) {
                        $.each(result, function(name, value) {
                            $e = $form.find('[name="' + name +'"]');
                            var eType = $e.attr('type');
                            if ($e.length == 1 && eType != "checkbox" && eType != "radio") {
                                $e.val(value);
                            };
                        });
                        _onLoadSuccess(result);
                        // validate the form
                        var _bv = $form.data('bootstrapValidator');
                        if (_bv) {
                            _bv.validate();
                        }
                    },
                    error: _onLoadError
                })
            }
            $.each(_data, function(name, value) {
                $form.find('[name="' + name +'"]').val(value);
            });
        },
        commit: function(options) {
            var _formId = options.formId;
            if (typeof _formId != "string") {
                throw new Error("arguments [formId] must be a string !");
            }
            var $form = $(_formId);
            if ($form.length <= 0) {
                throw new Error("can not find needed form area by id" + _formId + " !");
            }
            if ($form.length > 1) {
                throw new Error("ambiguous form id" + _formId + " !");
            }
            var _url = options.url;
            if (!_url || typeof _url != "string") {
                throw new Error("arguments [url] must be a string !");
            }
            var _extraData = options.extraData && typeof options.extraData === "object" ? options.extraData : {};
            var _success = options.success;
            var _error = options.error;
            if (typeof _success != "function") {
                throw new Error( _success + " is not a function !");
            }
            if (typeof _success != "function") {
                throw new Error( _success + " is not a function !");
            }
            var formDataArray = $form.serializeArray();
            // handle extra post data
            $.each(_extraData, function(name, value) {
                formDataArray.push({name: name, value: value});
            });
            var formData = {};
            $.each(formDataArray, function(i, obj) {
                if (formData[obj.name] && formData[obj.name].length > 0) {
                    formData[obj.name] = formData[obj.name] + "," + obj.value;
                } else {
                    formData[obj.name] = obj.value;
                }
            });
            var _formData = $.extend({}, formData);
            $.ajax({
                url: _url,
                type: 'post',
                dataType: 'json',
                data: _formData,
                success: _success,
                error: _error
            });
        },
        reset: function(options) {
            var _formId = options.formId;
            if (typeof _formId != "string") {
                throw new Error("arguments [formId] must be a string !");
            }
            var $form = $(_formId);
            if ($form.length <= 0) {
                throw new Error("can not find needed form area by id" + _formId + " !");
            }
            $.each($form.find('input'), function(i, e) {
                var $e = $(e);
                var type = $e.attr("type");
                if (type === "text") {
                    $e.val(null);
                } else if (type === "checkbox") {
                    $e.prop('checked', false);
                } else if (type === "radio") {
                    $e.prop("checked", false);
                } else {
                    $e.val(null);
                }
            });
            $form.find('textarea, select').val(null);
        },
        loadData: function(formData){
            var obj = eval("("+jsonStr+")");
            var key,value,tagName,type,arr;
            for(x in obj){
                key = x;
                value = obj[x];

                $("[name='"+key+"'],[name='"+key+"[]']").each(function(){
                    tagName = $(this)[0].tagName;
                    type = $(this).attr('type');
                    if(tagName=='INPUT'){
                        if(type=='radio'){
                            $(this).attr('checked',$(this).val()==value);
                        }else if(type=='checkbox'){
                            arr = value.split(',');
                            for(var i =0;i<arr.length;i++){
                                if($(this).val()==arr[i]){
                                    $(this).attr('checked',true);
                                    break;
                                }
                            }
                        }else{
                            $(this).val(value);
                        }
                    }else if(tagName=='SELECT' || tagName=='TEXTAREA'){
                        $(this).val(value);
                    }

                });
            }
        }
    },
    jsonToGetParamstr: function(param, key) {
        var paramStr = "";
        if (param instanceof String || param instanceof Number || param instanceof Boolean) {
            if (encodeURIComponent(param) && encodeURIComponent(param).length > 0) {
                paramStr += "&" + key + "=" + encodeURIComponent(param);
            }
        } else {
            $.each(param, function(i) {
                var k = key == null ? i : key + (param instanceof Array ? "[" + i + "]" : "." + i);
                if (this && (this instanceof String && this.length > 0) || this instanceof Number || this instanceof Boolean) {
                    paramStr += '&' + $.jsonToGetParamstr(this, k);
                }
            });
        }
        return paramStr.substr(1);
    },
    formatDate : function(fmt, date) {
        var o = {
            "M+" : date.getMonth()+1,                 //月份
            "d+" : date.getDate(),                    //日
            "H+" : date.getHours(),                   //小时
            "m+" : date.getMinutes(),                 //分
            "s+" : date.getSeconds(),                 //秒
            "q+" : Math.floor((date.getMonth()+3)/3), //季度
            "S"  : date.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt)) {
            fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
        }
        for(var k in o) {
            if(new RegExp("("+ k +")").test(fmt)){
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            }
        }
        return fmt;
    },
    setCookie: function(name,value) {
        var Days = 30;
        var exp = new Date();
        exp.setTime(exp.getTime() + Days*24*60*60*1000);
        document.cookie = name + "="+ value + ";expires=" + exp.toGMTString() + "; path=/";
    },
    getCookie: function(name) {
        var search = name + "="//查询检索的值
        var returnvalue = null;//返回值
        if (document.cookie.length > 0) {
            sd = document.cookie.indexOf(search);
            if (sd!= -1) {
                sd += search.length;
                end = document.cookie.indexOf(";", sd);
                if (end == -1)
                    end = document.cookie.length;
                //unescape() 函数可对通过 escape() 编码的字符串进行解码。
                returnvalue=unescape(document.cookie.substring(sd, end))
            }
        }
        return returnvalue;
    },
    delCookie: function (name) {
        var Days = 30;
        var exp = new Date();
        exp.setTime(exp.getTime() - Days*24*60*60*1000*10);
        var cval= $.getCookie(name);
        if(cval != null) {
            document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString() + "; path=/";
        }
    }, //使用示例 setCookie("name","hayden"); alert(getCookie("name")); //如果需要设定自定义过期时间 //那么把上面的setCookie　函数换成下面两个函数就ok; //程序代码 function setCookie(name,value,time) { var strsec = getsec(time); var exp = new Date(); exp.setTime(exp.getTime() + strsec*1); document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString(); } function getsec(str) { alert(str); var str1=str.substring(1,str.length)*1; var str2=str.substring(0,1); if (str2=="s") { return str1*1000; } else if (str2=="h") { return str1*60*60*1000; } else if (str2=="d") { return str1*24*60*60*1000; } }

    validatePhone: function(phone) {
        if (!(/^1[34578]\d{9}$/.test(phone))) {
            return false;
        }
        return true;
    },
    validateTel: function(tel) {
        if (!(/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(tel))) {
            return false;
        }
        return true;
    },
    validatePassword: function(password) {
        if (!(/^[a-zA-Z0-9\x21-\x7e]{6,20}$/.test(password))) {
            return false;
        }
        return true;
    },

    validateMoney: function(value) {
        if (!(/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test(value))) {
            return false;
        }
        return true;
    },

    validateBankNo: function(value) {
        if (!(/^([1-9]{1})(\d{14}|\d{18})$/.test(value))) {
            return false;
        }
        return true;
    },

    validateNull: function(value) {
        if ($.trim(value).length > 0) {
            return true;
        } else {
            return false;
        }
    },
    validateIdCard: function(value) {
        if (!(/(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/.test( value ))) {
            return false;
        }
        return true;
    },

    checkLogin: function() {
        if (!$("input[name=shopId]").val() || $("input[name=shopId]").val() == "null") {
            window.location.href = "/passport/login";
        } else {
            return true;
        }
        /*$.ajax({
            type: "POST",
            url: "/passport/checkLogin",
            success: function(result) {
                if (!result.success) {
                    var url = window.location.href;
                    $.setCookie("href", url);
                    window.location.href = "/passport/login";
                }
            }
        });*/

    }


}));