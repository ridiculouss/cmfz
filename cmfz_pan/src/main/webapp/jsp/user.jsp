<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<div class="navbar navbar-default text-center" style="height: 50px">
    <span style="font-size: 30px">用户管理</span>
</div>

<script type="text/javascript">
    $(function () {

        $("#jqGrid").jqGrid({
            styleUI:"Bootstrap",
            url: '${pageContext.request.contextPath}/user/queryAll',
            editurl: '${pageContext.request.contextPath}/user/alter',
            datatype: "json",
            colNames:["编号","手机","密码","盐","法名","省","市","性别","个人签名","头像","状态","注册时间","操作",""],
            colModel: [
                {name:"id",key: true},
                {name:"phone",editable: true},
                {name:"password",editable: true},
                {name:"salt",editable:true,hidden:true},
                {name:"dharmaName",editable: true},
                {name:"province",editable: true},
                {name:"city",editable: true},
                {name:"gender",editable: true},
                {name:"personalSign",editable: true},
                {name:"profile", edittype:"file",editable: true, formatter:function (cellvalue, options, rowObject) {
                        return "<img src='${pageContext.request.contextPath}/userPic/"+cellvalue+"' style='width: 40px'/>";
                    }},
                {name:"status",editable: true, edittype : "select" ,editoptions : {value :"on:正常;off:已冻结"},
                    formatter:function (cellvalue, options, rowObject) {
                        if (cellvalue == "on") return "正常";
                        if (cellvalue == "off") return "已冻结";
                    }},
                {name:"registTime", edittype:"date", editable: true},
                {name:"status", formatter:function (cellvalue, options, rowObject) {
                        return "<button onclick='alterStatus()' id='astu' rid='"+rowObject.id+"' status='"+cellvalue+"' class='btn btn-primary'>更改状态</button>";
                    }},
                {name:"opassword",editable:true,hidden: true,formatter: function (cellvalue, options, rowObject) {
                            return rowObject.password;
                        }}
            ],
            pager: "#jqGridPager",
            viewrecords: true,
            autowidth:true,
            rownumbers: true,
            multiselect:true,
            height: 420,
            rowNum: 5,
            rowList: [5,8]
        }).jqGrid("navGrid","#jqGridPager", {search: false},
            {
                //修改的部分
                closeAfterEdit:true,
                afterSubmit:function (resp) {
                    if (resp.responseText != ""){
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/user/upload",
                            fileElementId: "profile",
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
                        url:"${pageContext.request.contextPath}/user/upload",
                        fileElementId: "profile",
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
    function alterStatus() {
        var astu = document.getElementById("astu");
        var rid = astu.getAttribute("rid");
        var status = astu.getAttribute("status");
        $.get(
            "${pageContext.request.contextPath}/user/alterStatus",
            {"id":rid,"status":status},
            function (data) {
                $("#jqGrid").trigger("reloadGrid");
            },
            "json"
        );
    }
    function getPassword() {
        return document.getElementById("opwd").getAttribute("pwd");
    }
    function importExcel() {
        $("#myModal1").modal("hide");
        $.ajax({
            type:"post",
            url:"${pageContext.request.contextPath}/user/importExcel",
            data:new FormData($("#inForm")[0]),
            cache:false,
            processData:false,
            contentType:false,
            success:function (data) {
                $("#jqGrid").trigger("reloadGrid");
            }
        });
    }
    function exportExcel() {
        $("#myModal2").modal("hide");
        var filename = $("#filename").val();
        location.href = "${pageContext.request.contextPath}/user/exportExcel?filename="+filename;
    }
</script>
<table id="jqGrid" style="text-align: center"></table>
<div id="jqGridPager"></div>
<div style="margin-top: 20px;float: right">
    <button type="button" class="btn btn-info" data-toggle="modal" data-target="#myModal1" style="margin-right: 30px">Excel导入信息</button>
    <button type="button" class="btn btn-info" data-toggle="modal" data-target="#myModal2">信息导出至Excel</button>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel1">信息导入</h4>
            </div>
            <div class="modal-body">
                <form id="inForm">
                    <div class="form-group">
                        <label style="font-size: 18px;margin-bottom: 15px">选取文件:</label>
                        <input name="file" type="file"/>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="importExcel()">提交</button>
            </div>
        </div>
    </div>
</div>
<!-- Modal -->
<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel2">信息导出</h4>
            </div>
            <div class="modal-body">
                <form id="exForm">
                    <div class="form-group">
                        <label style="font-size: 18px;margin-bottom: 15px">设置文件名:</label>
                        <input id="filename" type="text" class="form-control"/>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="exportExcel()">提交</button>
            </div>
        </div>
    </div>
</div>