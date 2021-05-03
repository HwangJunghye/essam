<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<% //@Author 고연미 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>e-쌤</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/class.css">
</head>
<body>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp" %>
<section>
<div id="contents">
	<!---------- 본문 시작 ---------->
	
	<div id="classInfo_area1">
	<%@ include file="../class/inc_classinfo.jsp" %>
	</div>
	<div id="classInfo_area2">
			<div id="box_clsJoin"><br><br>
			
			<table style="border:2px solid #e0e0e0;" height=200>
			<tr>
				<td align="left" style="white-space:nowrap;">&nbsp;<br>&nbsp;가격 : <fmt:formatNumber value="${classInfo.clsPrice}" type="number"/> 원 / 월&nbsp;</td>
			</tr>
			<tr>
				<td align="left">&nbsp;신청현황 : <fmt:formatNumber value="${classInfo.clsRegiCnt}" type="number"/> / <fmt:formatNumber value="${classInfo.clsLimit}" type="number"/> 명&nbsp;<br>&nbsp;</td>
			</tr>
			<tr>
				<td align="center" style="background-color:#fca9a8;"><a href="${ctxPath}/classjoin?clsNo=${classInfo.clsNo}"><i class="far fa-arrow-alt-circle-right"></i> <span style="font-weight: bold;">수강신청</span></a></td>
			</tr></table>
			</div>
	</div>
	
	<!---------- 본문 끝 ---------->
</div>
</section>
<%@ include file="../common/footer.jsp" %>
</body>
</html>