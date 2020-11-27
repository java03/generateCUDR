<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
	<head>
		<meta charset="utf-8">
		<title>Modify EmpTabVO page</title>
	</head>
	<body>
		<div align="center">
			<form method="post" action="${pageContext.request.contextPath}/modifyEmpTabVO">
				<label>empNo:</label><input type="text" name="empNo" readonly="readonly" value="${modVO.empNo}"><br>
				<label>eName:</label><input type="text" name="eName" value="${modVO.eName}"><br>
				<label>eSal:</label><input type="text" name="eSal" value="${modVO.eSal}"><br>
				<label>eHiredate:</label><input type="text" name="eHiredate" value="<fmt:formatDate value='${modVO.eHiredate}' pattern='yyyy-MM-dd'/>"><br>
				<input type="submit" value="添加">
			</form>
		</div>
	</body>
</html>