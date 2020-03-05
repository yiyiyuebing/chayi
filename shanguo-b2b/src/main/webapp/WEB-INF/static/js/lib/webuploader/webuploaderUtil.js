/**
 * Created by dy on 2017/6/11.
 */

($.extend({
    createWebUploader: function(options) {
        var autoUpload = false;
        var _$upload_btn = options.uploadBtn ? $(options.uploadBtn) : "", autoUpload = true;
        //if (_$upload_btn.length <= 0) {
        //    throw new Error("上传按钮为空!");
        //}
        var _$list = options.viewListId ? $(options.viewListId) : "";
        if (_$list.length <= 0) {
            throw new Error("预览容器为空!");
        }

        var _isShow = options.isShow ? options.isShow : false;
        if (!_isShow) {
            _$list.hide();
        }

        var _$pick_btn = options.pickBtn ? options.pickBtn : "";
        if (_$pick_btn.length <= 0) {
            throw new Error("选择图片按钮为空!");
        }

        var _server_url = options.serverUrl ? options.serverUrl : "/uploader/updloaderImg";
        if (_server_url.length <= 0) {
            throw new Error("上传图片服务器地址错误!");
        }


        var fileSingleSizeLimit = options.fileSingleSizeLimit ? options.fileSingleSizeLimit : 2*1024*1024;

        var fileNumLimit = options.fileNumLimit ? options.fileNumLimit : 100; //限制上传图片数量
        var thumbnailWidth = options.thumbnailWidth ? options.thumbnailWidth : 100; //预览图片宽度
        var thumbnailHeight = options.thumbnailHeight ? options.thumbnailHeight : 100;  //预览图片高度

        var replaceModel = options.replaceModel ? options.replaceModel : false; //是否替换图片原则

        var readOnly = options.readOnly ? "none!important;" : "block;"; //只读模式

        var imageList = options.imageList? options.imageList : []; //图片

        var showFileName = options.showFileName ? options.showFileName : false; //是否显示文件名称

        var _addImgSuccess = options.addImgSuccess && typeof options.addImgSuccess === "function" ? options.addImgSuccess : function(result){};

        var _formData = options.formData && typeof options.formData == "object" ? options.formData : {};

        var _uploadSuccess = options.uploadSuccess && typeof options.uploadSuccess === "function" ? options.uploadSuccess : function(result){};

        var _delClick = options.onDelete && typeof options.onDelete === "function" ? options.onDelete : function(result){};

        var thisObj = this;
        var uploader = WebUploader.create({
            // 选完文件后，是否自动上传。
            auto: autoUpload,
            // swf文件路径
            swf: 'Uploader.swf',
            // 文件接收服务端。
            server: _server_url,
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: {
                id:_$pick_btn,
                multiple: false
            },
            // 只允许选择图片文件。
            fileSingleSizeLimit: fileSingleSizeLimit,
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,png',
                mimeTypes: 'image/gif,image/jpg,image/jpeg,image/png'   //修改这行
            },
            fileNumLimit: fileNumLimit,
            method:'POST',
            formData: _formData
        });



        uploader.on("error", function (type){
            if (type == "Q_TYPE_DENIED") {
                layer.msg("请上传JPG、PNG格式文件");
            } else if (type == "F_EXCEED_SIZE") {
                layer.msg("文件大小不能超过"+ (fileSingleSizeLimit/1024/1024) +"M");
            } else if (type == "Q_EXCEED_NUM_LIMIT") {
                layer.msg("文件数量不能超过" + fileNumLimit + "张");
            } else if (type == "F_DUPLICATE") {
                layer.msg("该图片已经上传，请重新选择");
            } else {
                console.log("上传出错！请检查后重新上传");
                layer.msg("上传出错！请检查后重新上传！错误代码"+type);
            }
        });


        // 当有文件添加进来的时候
        uploader.on( 'fileQueued', function( file ) {  // webuploader事件.当选择文件后，文件被加载到文件队列中，触发该事件。等效于 uploader.onFileueued = function(file){...} ，类似js的事件定义。

            _$list.show();
            //if (replaceModel) { //如果为替换模式，则删除已经添加过的图片
            //    _$list.empty();
            //    var allFiles = uploader.getFiles();
            //    $.each(allFiles, function(i, perfile) {
            //        if (file.id != perfile.id) {
            //            uploader.removeFile( perfile );
            //        }
            //    });
            //}

            var $li = $(
                    '<div id="' + file.id + '" class="file-item thumbnail" style="float:left; padding: 5px;">' +
                    '<img>' +
                    '<div class="info">' + file.name + '</div>' +
                    '<i data-file-id="' + file.id + '" style="margin-top: -'+ thumbnailHeight +'px; margin-left:'+ (thumbnailWidth-14) +'px;"></i>' +
                    '</div>'
                ),
                $img = $li.find('img');

            // $list为容器jQuery实例
            _$list.append( $li );

            if (!showFileName) {
                _$list.find(".file-item").find(".info").hide();
            } else {
                _$list.find(".file-item").find(".info").show();
            }

            if (!_isShow) {
                if(_$list.find(".file-item").length >= fileNumLimit) {
                    $(_$pick_btn).addClass("webuploaderHide");
                }
            }

            // 创建缩略图
            // 如果为非图片文件，可以不用调用此方法。
            // thumbnailWidth x thumbnailHeight 为 100 x 100
            uploader.makeThumb( file, function( error, src ) {   //webuploader方法
                if ( error ) {
                    $img.replaceWith('<span>不能预览</span>');
                    return;
                }
                $img.attr( 'src', src );
            }, thumbnailWidth, thumbnailHeight );

            _addImgSuccess(options); //添加图片预览成功后回调
        });

        // 文件上传过程中创建进度条实时显示。
        uploader.on( 'uploadProgress', function( file, percentage ) {
            var $li = $( '#' + file.id ),
                $percent = $li.find('.progress span');
            // 避免重复创建
            if ( !$percent.length ) {
                $percent = $('<p class="progress"><span></span></p>')
                    .appendTo( $li )
                    .find('span');
            }
            $percent.css( 'width', percentage * 100 + '%' );
        });

        // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        uploader.on( 'uploadSuccess', function( file , response) {
            if (response.success) {
                var _$input = $('<input type="hidden" value="'+ response.data.url +'"/>');
                _$list.find( '#'+file.id ).append(_$input);
                _$list.find( '#'+file.id ).addClass('upload-state-done');
                layer.msg("上传成功");
                thisObj._check(_$list, _$pick_btn, fileNumLimit);
                _uploadSuccess(response);
            } else {
                layer.msg("上传失败");
            }
        });
        // 文件上传失败，显示上传出错。
        uploader.on( 'uploadError', function( file ) {
            var $li = $( '#'+file.id ),
                $error = $li.find('div.error');
            // 避免重复创建
            if ( !$error.length ) {
                $error = $('<div class="error"></div>').appendTo( $li );
            }
            layer.msg("上传失败");
            $error.text('上传失败');
        });
        // 完成上传完了，成功或者失败，先删除进度条。
        uploader.on( 'uploadComplete', function( file ) {
            $( '#'+file.id ).find('.progress').remove();
        });

        if (!autoUpload) {
            _$upload_btn.on( 'click', function() {
                console.log("上传...");
                uploader.upload();
                console.log("上传成功");
            });
        }

        if (imageList.length > 0) {
            _$list.show();
            if (!_isShow) {
                $(_$pick_btn).addClass("webuploaderHide");
            }
            $.each(imageList, function(i, perImage) {
                var $li = $(
                        '<div id="file_' + i + '" class="file-item thumbnail" style="float:left; padding: 5px; width:'+ thumbnailWidth +'px; height: '+ thumbnailHeight +'px;">' +
                        '<img src="'+ perImage.url +'" width="100" height="100">' +
                        '<div class="info">' + perImage.name + '</div>' +
                        '<i data-file-id="file_' + i + '" data-source="server" style="margin-top: -'+ thumbnailHeight +'px; margin-left:'+ (thumbnailWidth-14) +'px; display:'+ readOnly +'"></i>' +
                        '<input type="hidden" name="' + perImage.name + '" value="'+ perImage.url +'"/>' +
                        '</div>'
                    ),
                    $img = $li.find('img');

                // $list为容器jQuery实例
                _$list.append( $li );

                if (!showFileName) {
                    _$list.find(".file-item").find(".info").hide();
                } else {
                    _$list.find(".file-item").find(".info").show();
                }
            });
            _addImgSuccess(options);
            thisObj._check(_$list, _$pick_btn, fileNumLimit);
        }


        _$list.on( 'click', "i", function() {
            console.log(uploader.getFiles());
            var clickObj = this;

            _$list.find(".file-item").each(function(i, perView) {
                if($(perView).attr("id") == $(clickObj).attr("data-file-id")) {
                    $(perView).remove();
                    if ($(clickObj).attr("data-source") != "server") {
                        uploader.removeFile($(perView).attr("id"), true);
                    }
                }
            });
            _delClick();
            if (!_isShow) {
                if(_$list.find(".file-item").length == 0) {
                    _$list.hide();
                }
            }
            thisObj._check(_$list, _$pick_btn, fileNumLimit);
            _addImgSuccess(options);
            $(_$pick_btn).removeClass("webuploaderHide");
        });

        return uploader;
    },

    _check: function(viewList, uploadBtn, limitNum) {
        var count = viewList.children().length;
        if (count < limitNum) {
            $(uploadBtn).css({"pointer-events": ""});
        } else {
            $(uploadBtn).css({"pointer-events": "none"});
        }
    },
    /**
     * 获取图片
     * @param _$list 图片存放容器ID
     * @returns {Array}
     */
    getUploadImageList: function(_$list) {
        _$list = $(_$list);
        var imageList = [];
        _$list.find(".file-item").find("input").each(function(i, perInput) {
            var imageObj = {};
            if($(perInput).val()) {
                imageObj.url = $(perInput).val();
                if ($(perInput).attr("name")) {
                    imageObj.name = $(perInput).attr("name");
                }
            }
            imageList.push(imageObj);
        });
        return imageList;
    }

}));
