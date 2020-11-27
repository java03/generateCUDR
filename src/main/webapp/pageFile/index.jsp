<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta charset="utf-8">
<title>Index page</title>
</head>
<body>
<table border="1" align="center" width="80%">
	<thead>
		<tr>
			<th>empNo</th><th>eName</th><th>eSal</th><th>eHiredate</th><th>操作</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${vos}" var="vo">
		<tr>
			<td>${vo.empNo}</td><td>${vo.eName}</td><td>${vo.eSal}</td><td><fmt:formatDate value="${vo.eHiredate}" pattern="yyyy-MM-dd"/></td><td><a href="${pageContext.request.contextPath}/modifyVOPage/${vo.empNo}">修改</a><a href="${pageContext.request.contextPath}/delEmpTabVO/${vo.empNo}">删除</a></td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<p align="center"><a href="${pageContext.request.contextPath}/addVOPage">添加</a></p>
</body>
</html>