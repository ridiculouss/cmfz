<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<div class="navbar navbar-default text-center" style="height: 50px">
    <span style="font-size: 30px">轮播图管理</span>
</div>

<script type="text/javascript">
    $(function () {

        $("#jqGrid").jqGrid({
            styleUI:"Bootstrap",
            url: '${pageContext.request.contextPath}/carousel/queryAll',
            editurl: '${pageContext.request.contextPath}/carousel/alter',
            datatype: "json",
            colNames:["编号","轮播图名称","轮播图图片","状态","创建时间"],
            colModel: [
                {name:"id",key: true},
                {name:"title",editable: true},
                {name:"image", edittype:"file",editable: true, formatter:function (cellvalue, options, rowObject) {
                        return "<img src='${pageContext.request.contextPath}/carouselPic/"+cellvalue+"' style='width: 40px'/>";
                    }},
                {name:"status",editable: true, edittype : "select" ,editoptions : {value :"on:正常;off:已冻结"},
                    formatter:function (cellvalue, options, rowObject) {
                        if (cellvalue == "on") return "正常";
                        if (cellvalue == "off") return "已冻结";
                    }},
                {name:"createTime", edittype:"date", editable: true}],
            pager: "#jqGridPager",
            viewrecords: true,
            autowidth:true,
            rownumbers: true,
            multiselect:true,
            height: 300,
            rowNum: 8,
            rowList: [5,10]
        }).jqGrid("navGrid","#jqGridPager", {search: false},
            {
                //修改的部分
                closeAfterEdit:true,
                afterSubmit:function (resp) {
                    if (resp.responseText != ""){
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/carousel/upload",
                            fileElementId: "image",
                            data:{"id":resp.responseText},
                            type:"post",
                            success:function () {
                                $("#jqGrid").trigger("reloadGrid");
                            }
                        });
                    }
                    return "[true]";
                }
            },{
                //添加的部分
                closeAfterAdd:true,
                afterSubmit:function (resp) {
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/carousel/upload",
                        fileElementId: "image",
                        data:{"id":resp.responseText},
                        type:"post",
                        success:function () {
                            $("#jqGrid").trigger("reloadGrid");
                        }
                    });
                    return "[true]";
                }
            }
        );
    });

</script>
<table id="jqGrid" style="text-align: center"></table>
<div id="jqGridPager"></div>