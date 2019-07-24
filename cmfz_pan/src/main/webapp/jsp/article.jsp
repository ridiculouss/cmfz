<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/kindeditor-all.js"></script>
<script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>

<div class="navbar navbar-default text-center" style="height: 50px">
    <span style="font-size: 30px">文章管理</span>
</div>

<script type="text/javascript">
    $(function () {
        KindEditor.create('#editor_id', {
            width: '680px',
            uploadJson: '${pageContext.request.contextPath}/article/upload',
            fileManagerJson: '${pageContext.request.contextPath}/article/showAllImg',
            allowFileManager: true,
            filePostName: 'image',
            afterBlur: function () {
                this.sync();
            }
        });

        $("#jqGrid").jqGrid({
            styleUI: "Bootstrap",
            url: '${pageContext.request.contextPath}/guru/queryAll',
            editurl: '${pageContext.request.contextPath}/guru/alter',
            datatype: "json",
            colNames: ["编号", "上师名称", "上师图片", "状态", "性别", "操作"],
            colModel: [
                {name: "id", key: true},
                {name: "name", editable: true},
                {
                    name: "profile",
                    edittype: "file",
                    editable: true,
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img src='${pageContext.request.contextPath}/guruPic/" + cellvalue + "' style='width: 40px'/>";
                    }
                },
                {
                    name: "status", editable: true, formatter: function (cellvalue, options, rowObject) {
                        if (cellvalue == "on") return "正常";
                        if (cellvalue == "off") return "已冻结";
                    }
                },
                {name: "sex", editable: true},
                {
                    name: "status", width: 200, formatter: function (cellvalue, options, rowObject) {
                        var b1 = "<button onclick='alterStatus(this)' gid='" + rowObject.id + "' status='" + cellvalue + "' class='btn btn-primary'>更改状态</button>";
                        var b2 = "<button style='margin-left: 20px' type='button' class='btn btn-primary' gid='" + rowObject.id + "' onclick='showModel(this)'>添加文章</button>"
                        return b1 + b2;
                    }
                }
            ],
            pager: "#jqGridPager",
            viewrecords: true,
            autowidth: true,
            rownumbers: true,
            multiselect: true,
            height: 450,
            rowNum: 8,
            rowList: [5, 10],
            subGrid: true,
            subGridRowExpanded: function (subgrid_id, row_id) {
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id
                    + "' class='scroll' style='width: auto'></table><div id='"
                    + pager_id + "' class='scroll'></div>");
                jQuery("#" + subgrid_table_id).jqGrid(
                    {
                        styleUI: "Bootstrap",
                        url: "${pageContext.request.contextPath}/article/queryArticle?gid=" + row_id,
                        datatype: "json",
                        colNames: ['编号', '上师编号', '标题', '发表时间', '内容'],
                        colModel: [
                            {name: "id", key: true},
                            {name: "gid"},
                            {name: "title", editable: true},
                            {name: "publishTime", edittype: "date", editable: true},
                            {
                                name: "content",
                                editable: true,
                                edittype: "textarea",
                                formatter: function (cellvalue, options, rowObject) {
                                    return "<button onclick='showArticle(this)' abc='"+cellvalue+"'class='btn btn-primary'>预览</button>";
                                }
                            }
                        ],
                        editurl: '${pageContext.request.contextPath}/article/alter',
                        rownumbers: true,
                        rowNum: 5,
                        viewrecords: true,
                        pager: pager_id,
                        height: '100%'
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {search: false, edit: false, add: false}
                );
            }
        }).jqGrid("navGrid", "#jqGridPager", {search: false, edit: false, del: false}, {},
            {
                //添加的部分
                closeAfterAdd: true,
                afterSubmit: function (resp) {
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/guru/upload",
                        fileElementId: "profile",
                        data: {"id": resp.responseText},
                        type: "post",
                        success: function () {
                            $("#jqGrid").trigger("reloadGrid");
                        }
                    });
                    return "[true]";
                }
            }
        );
    });

    function alterStatus(astu) {
        var gid = astu.getAttribute("gid");
        var status = astu.getAttribute("status");
        $.get(
            "${pageContext.request.contextPath}/guru/alterStatus",
            {"id": gid, "status": status},
            function (data) {
                $("#jqGrid").trigger("reloadGrid");
            },
            "json"
        );
    }
    function showModel(model) {
        $("#imputgid").remove();
        $("#aform").append("<input id='imputgid' name='gid' value='"+model.getAttribute("gid")+"' type='hidden'/>");
        $('#myModal').modal('show');
    }
    function submitForm() {
        $('#myModal').modal('hide');
        $.post(
          "${pageContext.request.contextPath}/article/alter?oper=add",
            $("#aform").serialize(),
            function () {
                $("#aform")[0].reset();
                KindEditor.html("#editor_id","");
                $("#jqGrid").trigger("reloadGrid");
            }
        );
    }
    function showArticle(a) {
        $("#art").empty();
        $("#art").append(a.getAttribute("abc"));
        $("#myModal2").modal("show");
    }
</script>
<table id="jqGrid" style="text-align: center"></table>
<div id="jqGridPager"></div>


<!-- Modal -->
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width: 700px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">添加文章</h4>
            </div>
            <div class="modal-body">
                <form id="aform">
                    <div class="form-group">
                        <label>标题</label>
                        <input type="text" name="title" class="form-control">
                    </div>
                    <div class="form-group">
                        <label>发表时间</label>
                        <input type="date" name="publishTime" class="form-control">
                    </div>
                    <div class="form-group">
                        <label>内容</label>
                        <textarea id="editor_id" name="content" style="width:680px;height:300px;"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="submitForm()">提交</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width: 800px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel2">文章预览</h4>
            </div>
            <div class="modal-body" id="art">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default btn-primary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>