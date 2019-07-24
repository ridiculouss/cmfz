<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-inverse">
    <div class="container-fluid"><strong class="navbar-text">持明法洲后台管理系统v1.0</strong>
        <ul class="nav navbar-nav navbar-right">
            <li>
                <a href="javascript:void (0);" class="dropdown-toggle"role="button">
                    欢迎：${sessionScope.adminSession.username}<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                </a>

            </li>
            <li>
                <a href="${pageContext.request.contextPath}/admin/logout" class="dropdown-toggle" role="button">
                    退出登录&nbsp;<span class="glyphicon glyphicon-log-in" aria-hidden="true"></span>
                </a>
            </li>
        </ul>
    </div>
</nav>
