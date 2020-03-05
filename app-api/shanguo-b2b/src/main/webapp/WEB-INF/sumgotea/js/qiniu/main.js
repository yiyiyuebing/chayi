/*global Qiniu */
/*global plupload */
/*global FileProgress */
/*global hljs */
$(function() { 
	var uploaderImg=function(){
		var default_options={
	        runtimes: 'html5,flash,html4',
	        browse_button: '',
	        container: '',
	        tigger_container:'',
			chunk_size: '4mb',
			uptoken:localStorage.getItem('qiniu_token'),
			// uptoken_url: Util.common.baseUrl+"/weixin/qiniu/getToken.do",
	        domain: 'http://img.youchalian.com/',
	        multi_selection: !(mOxie.Env.OS.toLowerCase()==="ios"),
	        filters : {
	            max_file_size : '100000mb',
	            prevent_duplicates: false,
	            mime_types: [
	                   {title : "Image files", extensions : "jpg,jpeg,gif,png,bmp"}
	            ]
	        },
	        get_new_uptoken: false,
	        auto_start: true,
	        log_level: 5,
	        resize : {
	            width : 200,
	            height : 200,
	            quality : 72,
	            crop: true // crop to exact dimensions
	        },
	        init: {
	            'FilesAdded': function(up, files) {
	            	var op=up.getOption();
					var targetContainer=op.tigger_container;
					 plupload.each(files, function(file) {
						var progress = new FileProgress(file, targetContainer);
						progress.bindUploadCancel(up);     	
						up.refresh();
			                   
					});
	            },
	            'BeforeUpload': function(up, file) {
	            	var op=up.getOption();
					var targetContainer=op.tigger_container;
	                var progress = new FileProgress(file, targetContainer);
	                var chunk_size = plupload.parseSize(this.getOption('chunk_size'));
	                if (up.runtime === 'html5' && chunk_size) {
	                    progress.setChunkProgess(chunk_size);
	                }
	            },
	            'UploadComplete': function() {
	                console.log("ssss");
	            },
	            'FileUploaded': function(up, file, info) {
	                var op=up.getOption();
					var targetContainer=op.tigger_container;
	                var progress = new FileProgress(file, targetContainer);
					progress.setComplete(up, info, "-img");
	            },
	            'Error': function(up, err, errTip) {
	                var op=up.getOption();
					var targetContainer=op.tigger_container;
	                var progress = new FileProgress(err.file, targetContainer);
	                progress.setError();
	                progress.setStatus(errTip);
	            },
	           'Key': function(up, file) {
	              	var key = "";
	             	var timestamp=new Date().getTime();
	             	var imgType=file.name.substr(file.name.lastIndexOf(".")).toLowerCase();
	             	var randomStr = function(len){
				 		var targetStr="";
				 		var hexDigits = "0123456789abcdef";
				 		for(var i=0;i<len;i++){
				 			targetStr+=hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
				 		}
				 		return targetStr;
	             	}
	             	key=timestamp+randomStr(7)+imgType;
	             	return key;
	            }
	        }
		};
		var _init=function(options){
			return $.extend(true, default_options, options);
		}
		var _create=function(options){
			var uploader = Qiniu.uploader(_init(options));
			return uploader;
		}
	};
});
