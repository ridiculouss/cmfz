<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<div class="navbar navbar-default text-center" style="height: 50px">
    <span style="font-size: 30px">专辑管理</span>
</div>

<script type="text/javascript">
    $(function () {

        $("#jqGrid").jqGrid({
            styleUI:"Bootstrap",
            url: '${pageContext.request.contextPath}/album/queryAll',
            editurl: '${pageContext.request.contextPath}/album/alter',
            datatype: "json",
            colNames:["编号","专辑名称","专辑封面","章节数量","章节得分","专辑作者","播音员","专辑简介","出版时间"],
            colModel: [
                {name:"id",key: true},
                {name:"title",editable: true},
                {name:"cover", edittype:"file",editable: true, formatter:function (cellvalue, options, rowObject) {
                        return "<img src='${pageContext.request.contextPath}/albumPic/"+cellvalue+"' style='width: 40px'/>";
                    }},
                {name:"count",editable: true},
                {name:"score",editable: true},
                {name:"author",editable: true},
                {name:"broadcast",editable: true},
                {name:"brief",editable: true},
                {name:"publishTime", edittype:"date", editable: true}],
            pager: "#jqGridPager",
            viewrecords: true,
            autowidth:true,
            rownumbers: true,
            multiselect:true,
            height: 400,
            rowNum: 8,
            rowList: [5,10],
            subGrid : true,
            subGridRowExpanded : function(subgrid_id, row_id) {
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id
                    + "' class='scroll' style='width: auto'></table><div id='"
                    + pager_id + "' class='scroll'></div>");
                jQuery("#" + subgrid_table_id).jqGrid(
                    {
                        styleUI:"Bootstrap",
                        url : "${pageContext.request.contextPath}/chapter/queryChapter?aid=" + row_id,
                        datatype : "json",
                        colNames : [ '编号', '专辑编号', '音频名称', '音频大小','音频路径','上传时间','操作' ],
                        colModel : [
                            {name : "id",key : true},
                            {name : "aid"},
                            {name : "title",editable: true},
                            {name : "size",formatter:function (cellvalue) { return cellvalue + "MB"; }},
                            {name : "downPath",editable: true, edittype:"file"},
                            {name : "uploadTime", edittype:"date", editable: true},
                            {name : "downPath", formatter:function (cellvalue, options, rowObject) {
                                    return "<a type='button' href='${pageContext.request.contextPath}/chapter/download?path="+cellvalue+"' class='btn btn-primary'>下载</a>";
                                }}
                        ],
                        editurl: '${pageContext.request.contextPath}/chapter/alter?aid=' + row_id,
                        rownumbers: true,
                        rowNum : 5,
                        viewrecords: true,
                        pager : pager_id,
                        height : '100%'
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {search: false,edit: false},
                    {},
                    {//添加的部分
                        closeAfterAdd:true,
                        afterSubmit:function (resp) {
                            $.ajaxFileUpload({
                                url:"${pageContext.request.contextPath}/chapter/upload",
                                fileElementId: "downPath",
                                data:{"id":resp.responseText},
                                type:"post",
                                success:function () {
                                    $("#" + subgrid_table_id).trigger("reloadGrid");
                                }
                            });
                            return "[true]";
                        }}
                    );
            }
        }).jqGrid("navGrid","#jqGridPager", {search:false},{
                //修改的部分
                closeAfterEdit:true,
                afterSubmit:function (resp) {
                    if (resp.responseText != ""){
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/album/upload",
                            fileElementId: "cover",
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
                        url:"${pageContext.request.contextPath}/album/upload",
                        fileElementId: "cover",
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