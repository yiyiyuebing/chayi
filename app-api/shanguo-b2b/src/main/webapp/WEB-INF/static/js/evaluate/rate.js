/**
 * Created by Administrator on 2017/6/16.
 */
var Rate = {
    init:function(){
        Rate.initCredenImg();
    },

    initCredenImg : function (){
        $(".evaluationvo").each(function(i, perEvalDiv) {
            var evalId = $(perEvalDiv).attr("data-evaluation-id");
            var picUrlList = [];
            $(".evaluationVo-" + evalId).each(function(j, perIpt) {
                var picUrlObj = {};
                if ($(perIpt).val()) {
                    picUrlObj.id = j;
                    picUrlObj.url = $(perIpt).val();
                    picUrlList.push(picUrlObj);
                }
            });

            if (picUrlList.length <= 0) {
                return false;
            }
            $.createWebUploader({
                viewListId: "#evaluation-photo-list-" + evalId,
                pickBtn: "#pick-evaluation-photo-" + evalId,
                replaceModel: false,
                fileNumLimit: 10,
                readOnly: true,
                imageList:picUrlList
            });

        });

    }
};
$(function(){
    Rate.init();
});