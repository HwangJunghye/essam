<%@page import="com.essam.www.constant.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://kit.fontawesome.com/a076d05399.js"></script>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
</head>
<body>
<script>
</script>

		<table style="width:100%"><tr>	
		<c:forEach var="eclass" items="${myList}">
			<td align="center">
			<div class="shadow p-3 mb-5 bg-white rounded" style="width:200px;height:190px;">
				<table>
				<tr><td><a href="${ctxPath}/class/curriculum?clsNo=${eclass.clsNo}"><img src="${ctxPath}/getthumbnail?fileNo=${eclass.fileNo}&width=170&height=100" style="width:170px;height:100px;margin-bottom:10px;"></a></td></tr>
				<tr><td>${eclass.mbNickName}</td></tr>
				<tr><td><a href="${ctxPath}/class/curriculum?clsNo=${eclass.clsNo}" style="font-weight: bold;">${eclass.clsName}</a></td></tr>
				</table>
			</div>
			</td>
		</c:forEach>
		</tr></table>

</body>
</html>