<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
    <div class="panel panel-default">
        <div class="panel-heading" role="tab" data-toggle="collapse" href="#collapseOne" data-parent="#accordion">
            <h4 class="panel-title">
                <a role="button" href="javascript:void(0);" aria-expanded="false" aria-controls="collapseOne">
                    轮播图
                </a>
            </h4>
        </div>
        <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
            <div class="panel-body">
                <a href="javascript:$('#rightJsp').load('${pageContext.request.contextPath}/jsp/carousel.jsp');" class="btn-block query">轮播图管理</a>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading" role="tab" id="headingTwo"  data-toggle="collapse" href="#collapseTwo" data-parent="#accordion">
            <h4 class="panel-title">
                <a class="collapsed" role="button" href="#" aria-expanded="false" aria-controls="collapseTwo">
                    专辑
                </a>
            </h4>
        </div>
        <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
            <div class="panel-body">
                <a href="javascript:$('#rightJsp').load('${pageContext.request.contextPath}/jsp/album.jsp');" class="btn-block">专辑和章节管理</a>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading" role="tab" id="headingThree"  data-toggle="collapse" href="#collapseThree" data-parent="#accordion">
            <h4 class="panel-title">
                <a class="collapsed" role="button" href="#" aria-expanded="false" aria-controls="collapseThree">
                    文章
                </a>
            </h4>
        </div>
        <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
            <div class="panel-body">
                <a href="javascript:$('#rightJsp').load('${pageContext.request.contextPath}/jsp/article.jsp');" class="btn-block">文章管理</a>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading" role="tab" id="headingFour"  data-toggle="collapse" href="#collapseFour" data-parent="#accordion">
            <h4 class="panel-title">
                <a class="collapsed" role="button" href="#" aria-expanded="true" aria-controls="collapseFour">
                    用户
                </a>
            </h4>
        </div>
        <div id="collapseFour" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingFour">
            <div class="panel-body">
                <a href="javascript:$('#rightJsp').load('${pageContext.request.contextPath}/jsp/user.jsp');" class="btn-block">用户管理</a>
                <a href="#" class="btn-block">每月注册用户</a>
                <a href="#" class="btn-block">全国用户分布图</a>
            </div>
        </div>
    </div>
</div>