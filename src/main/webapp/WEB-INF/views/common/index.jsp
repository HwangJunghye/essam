<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>e-쌤 :::메인:::</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/class.css">
<link rel="icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
<link rel="shortcut icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
</head>
<body>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/main_cover.jsp" %>
<section>
<div id="contents">
	<div id="classInfo_area2"><br><br>
	<!-- 수강중인 클래스가 있으면 -->
	<c:if test="${!empty myList}">
		<table style="width:90%" align="center">
		<tr>
			<td align="left"><i class="fas fa-user-edit"></i><span style="font-weight: bold;"> 수강중인 클래스</span></td>
		</tr></table><br>
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
	</c:if>

	<table style="width:90%" align="center">
	<tr>
		<td align="left"><i class="fas fa-angle-double-right"></i><span style="font-weight: bold;"> New 클래스</span></td>
	</tr></table><br>
	<c:if test="${empty nList}">
		<table style="width:90%" align="center">
		<tr>
			<td align="left">등록된 New 클래스 정보가 없습니다. </td>
		</tr></table>
	</c:if>
	<c:if test="${!empty nList}">
		<table style="width:100%;"><tr>	
		<c:forEach var="eclass" items="${nList}">
			<td align="center">
			<div class="shadow p-3 mb-5 bg-white rounded" style="width:200px;height:190px;">
				<table>
				<tr><td><a href="${ctxPath}/classinfo?clsNo=${eclass.clsNo}"><img src="${ctxPath}/getthumbnail?fileNo=${eclass.fileNo}&width=170&height=100" style="width:170px;height:100px;margin-bottom:10px;"></a></td></tr>
				<tr><td>${eclass.mbNickName}</td></tr>
				<tr><td><a href="${ctxPath}/classinfo?clsNo=${eclass.clsNo}" style="font-weight: bold;">${eclass.clsName}</a></td></tr>
				</table>
			</div>
			</td>
		</c:forEach>
		</tr></table>
	</c:if>
	
	<table style="width:90%" align="center">
	<tr>
		<td align="left"><i class="fas fa-angle-double-right"></i><span style="font-weight: bold;"> Hot 클래스</span></td>
	</tr></table><br>
	<c:if test="${empty hList}">
		<table style="width:90%" align="center">
		<tr>
			<td align="left">등록된 Hot 클래스 정보가 없습니다.</td>
		</tr></table>
	</c:if>
	<c:if test="${!empty hList}">
		<table style="width:100%"><tr>	
		<c:forEach var="eclass" items="${hList}">
			<td align="center">
			<div class="shadow p-3 mb-5 bg-white rounded" style="width:200px;height:190px;">
				<table>
				<tr><td><a href="${ctxPath}/classinfo?clsNo=${eclass.clsNo}"><img src="${ctxPath}/getthumbnail?fileNo=${eclass.fileNo}&width=170&height=100" style="width:170px;height:100px;margin-bottom:10px;"></a></td></tr>
				<tr><td>${eclass.mbNickName}</td></tr>
				<tr><td><a href="${ctxPath}/classinfo?clsNo=${eclass.clsNo}" style="font-weight: bold;">${eclass.clsName}</a></td></tr>
				</table>
			</div>
			</td>
		</c:forEach>
		</tr></table>
	</c:if></p><br><br><br><br><br>
	</div>
</div>
</section>

<%@ include file="../common/footer.jsp" %>
</body>
</html>