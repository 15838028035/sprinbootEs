
//项目访问根目录
var basePath = "";

//公共基础类
var bs = {
    /**
     * 根据dataJson填充表单对象
     * @param {Jquery Form Object} formObject 需要设置属性的表单jquery对象
     * @param {Json} dataJson
     */
    loadFormData: function (formObject, dataJson) {
        for (var index in dataJson) {
            var elm = formObject.find("[name='" + index + "']");
            /**
             * radio 赋值
             */
            if ("radio" == elm.attr("type")) {
                for (var i = 0; i < elm.length; i++) {
                    var radio = elm[i];
                    if (radio.value == dataJson[index]) {
                        radio.checked = true;
                    }
                }
            } else {
                if (elm.hasClass('select')) {
                    elm.setValue(dataJson[index]);
                } else {
                    elm.val(dataJson[index]);
                }
            }
        }
    }//end bs.loadFormData
    //功能：将浮点数四舍五入，取小数点后1位
    , toDecimal1: function (x) {
        var f = parseFloat(x);
        if (isNaN(f)) {
            return;
        }
        f = Math.round(x * 10) / 10;
        return f;
    }//end bs.toDecimal1
    //功能：将以s为单位的时间改为mm:ss格式的时间
    , toTimeMin: function (t) {
        t = parseInt(t);
        var m = parseInt(t / 60);
        var s = t % 60;
        return m + ":" + s;
    }//end bs.toTimeMin
  
    // 初始化下拉框
    , selectDown: function ($obj, arr, value, width) {
        var content = arr;
        var w = '150px';
        if (width != null) {
            w = width
        }
        $obj.html("");
        for (var i = 0; i < content.length; i++) {
            var outName = content[i].name;
            var val = content[i].value;
            var $option = $('<option value="' + val + '" hassubinfo="true">' + outName + '</option>')
            $obj.append($option);
        }
        if ($obj.next(".chosen-container").length > 0) {
            $obj.chosen('destroy');
        }
        $obj.chosen({
            no_results_text: '没有数据!',
            width: w,
            disable_search_threshold: 5,
            placeholder_text_multiple: "全部"
        });

        if (value === undefined) {
            if (content.length > 0) {
                $obj.val(content[0].value)
            }
        } else {
            $obj.val(value);
        }
        if (arr.length > 0) {
            if (arr[0].value == "") {
                if (arr.length == 2) {
                    $obj.val(arr[1].value).prop("disabled", true).trigger("chosen:updated");
                } else {
                    $obj.val("").prop("disabled", false).trigger("chosen:updated");
                }
            } else {
                if (arr.length == 1) {
                    $obj.val(arr[0].value).prop("disabled", true).trigger("chosen:updated");
                } else {
                    $obj.val(arr[0].value).prop("disabled", false).trigger("chosen:updated");
                }
            }
        }
        $obj.trigger("chosen:updated");
    }
    // 初始化下拉框
    , selectDownNoDisabled: function ($obj, arr, value, width) {
        var content = arr;
        var w = '150px';
        if (width != null) {
            w = width
        }
        $obj.html("");
        for (var i = 0; i < content.length; i++) {
            var outName = content[i].name;
            var val = content[i].value;
            var disabledCode = content[i].disabled;
            var $option ="";
            if(disabledCode){
               $option = $('<option value="' + val + '" hassubinfo="true" disabled>' + outName + '</option>')
            }else{
                $option = $('<option value="' + val + '" hassubinfo="true">' + outName + '</option>')
            }
            $obj.append($option);
        }
        if ($obj.next(".chosen-container").length > 0) {
            $obj.chosen('destroy');
        }
        $obj.chosen({
            no_results_text: '没有数据!',
            width: w,
            disable_search_threshold: 5,
            placeholder_text_multiple: "全部"
        })

        if (value === undefined) {
            if (content.length > 0) {
                $obj.val(content[0].value)
            }
        } else {
            $obj.val(value);
        }
        $obj.trigger("chosen:updated");
    }
    , isNumber: function (value) {
        var patrn = /^(-)?\d+(\.\d+)?$/;
        if (patrn.exec(value) == null || value == "") {
            return false
        } else {
            return true
        }
    }
// 重置搜索条件
    , resetSearch: function (elem, arr) {
        var select = elem.find("select");
        select.each(function () {
            var opLength = $(this).find("option").length;
            if (opLength > 2) {
                var disabled = $(this).attr("disabled");
                if (!disabled) {
                    if (arr) {
                        for (var i = 0; i < arr.length; i++) {
                            if ($(this).attr("id") == arr[i]) {
                                return
                            }
                        }
                        $(this).val("").trigger("chosen:updated");
                    } else {
                        $(this).val("").trigger("chosen:updated");
                    }
                }
            }
        });
        var input = elem.find("input");
        input.each(function () {
            var disabled = $(this).attr("disabled");
            if (!disabled) {
                if (arr) {
                    for (var i = 0; i < arr.length; i++) {
                        if ($(this).attr("id") == arr[i]) {
                            return
                        }
                    }
                    $(this).val("");
                } else {
                    $(this).val("");
                }

            }
        });
    }
    // 根据表格数据返回null 或者 “”都显示“-”
    , filterData: function (value) {
        var str = "";
        if (value == null || value == "") {
            str = "-"
        } else {
            str = value
        }
        return str;
    }
    // 获取页面访问前缀
    , getPathName: function () {
        //页面链接：http://localhost:8080/topic/rest/page/login
        var href = window.location.href;
        //访问路径：/topic/rest/page/login
        var path = window.location.pathname;
        //访问路径在页面链接的开始位置
        var index = href.indexOf(path);
        //项目部署主机和端口
        var host = href.substr(0, index);
        //项目名称
        var project = path.substr(0, path.substr(1).indexOf("/") + 2);
        //项目访问根目录=项目部署主机和端口+项目名称
        var result = host + project;
        return result;
    }//end bs.getPathName()
    ,autoTableHeight: function(elem,winH) {
        var container = elem.parents('.fixed-table-container');
        var bodyElem = container.find(".fixed-table-body");
        var pageElem = container.find(".fixed-table-pagination");
        var pageHeight=pageElem.height();
        if (pageElem.css('display') == 'none') {
            pageHeight = 0
        }else {
            pageHeight = 59
        }

        container.css({height:(winH-40-pageHeight)});
        container.css({minHeight:450});
        // 是当内容少时，使用搜索功能高度保持不变
        elem.bootstrapTable('resetView',{height:"auto"});
    },
};//end bs

$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
$(function () {
    basePath = bs.getPathName();
});