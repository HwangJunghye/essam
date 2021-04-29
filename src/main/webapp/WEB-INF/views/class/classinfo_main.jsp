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
<style>

#classInfo_area1 {
	flex: 7;
	background-color: #ffffff;
	text-align: center;
/*	border: 2px solid black;	 div 구분용 */
}
#classInfo_area2 {
	flex: 1;
	background-color: #ffffff;
	text-align: center;
/*	border: 2px solid black;	 div 구분용 */
}
</style>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp" %>
<section>
<div id="contents">
	<!---------- 본문 시작 ---------->
	
	<div id="classInfo_area1">
		<div class="container">
		<table>
		<tr>
			<td align="left" style="padding:20px 0;">
			<img src="${ctxPath}/getimage?fileNo=${classInfo.fileNo}">
			<p>
			카테고리 : ${classInfo.cate1Name} > ${classInfo.cate2Name}<br/>
			클래스명 : ${classInfo.clsName}<br/>
			정원 : <fmt:formatNumber value="${classInfo.clsLimit}" type="number"/> 명<br/>
			한줄 소개 : ${classInfo.clsIntro}<br/>
			상세 소개 : ${classInfo.clsDesc}<br/></p>
			</td>
		</tr>
		</table>
		</div>
	</div>
	<div id="classInfo_area2">
			<p><br><br>
			<table align="center" style="border:2px solid #e0e0e0;">
			<tr>
				<td>가격 : <fmt:formatNumber value="${classInfo.clsPrice}" type="number"/> 원 / 월</td>
			</tr>
			<tr>
				<td>신청현황 : <fmt:formatNumber value="${classInfo.clsRegiCnt}" type="number"/> / <fmt:formatNumber value="${classInfo.clsLimit}" type="number"/></td>
			</tr>
			<tr>
				<td style="background-color:#fca9a8;"><a href="${ctxPath}/classjoin?clsNo=${classInfo.clsNo}">수강신청</a></td>
			</tr></table>
			</p>
	</div>
	
	<!---------- 본문 끝 ---------->
</div>
</section>
<%@ include file="../common/footer.jsp" %>
</body>
</html>