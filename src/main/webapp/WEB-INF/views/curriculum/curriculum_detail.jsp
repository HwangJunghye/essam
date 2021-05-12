<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<link rel="icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
<link rel="shortcut icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
<style>
.contents{
	width: 600px;
	height: 200px;
	margin-left: auto;
    margin-right: auto;
}


</style>
</head>
<body>

<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp" %>
<section>
<div id="contents">
	<div id="aside">
		<div id="aside_area">
			<%@ include file="../common/aside.jsp"%>
		</div>
	</div>
	<div id="contents_area">
<!--------- 본문 시작 -------------->
	<c:if test="${!empty curriInfo}">
		<table class="container">
		<tr>
			<td align="left" style="padding:20px 0;">
				<h6><i class="fab fa-edge-legacy"></i> 클래스 <i class="fas fa-angle-right"></i> <span style="font-weight: bold;background-color:#f4edd8;">${clsName}</span></h6>
				<hr class="hr_${mbColor}"></td>
		</tr>
		</table>

		<table class="contents" border="1">
			<tr>
				<td class="item">제&emsp;목:</td>
				<td class="item_contents">&emsp;${curriInfo.curTitle}</td>
			</tr>
			<tr>
				<td class="item">시작일시:</td>
				<td class="item_contents">&emsp;${curriInfo.curStartDate}</td>
			</tr>
			<tr>
				<td class="item">종료일시:</td>
				<td class="item_contents">&emsp;${curriInfo.curEndDate}</td>
			</tr>
			<tr>
				<td class="item">설&emsp;명:</td>
				<td class="item_contents">&emsp;${curriInfo.curDisc}</td>
			</tr>
		</table>
		<br/>
		<br/>
		<br/>
			<button type="button" class="btn_normal_t" onclick="location.href='${ctxPath}/class/curriculum?clsNo=${curriInfo.clsNo}&pageNum=${pageNum}'">목록</button>
		<c:if test="${sessionScope.loginData.mbType==2}">
			<button type="button" class="btn_normal_t" onclick="location.href='${ctxPath}/class/curriculum/update?clsNo=${curriInfo.clsNo}&curNo=${curriInfo.curNo}&pageNum=${pageNum}'">수정</button>
			<button type="button" class="btn_normal_t" onclick="location.href='${ctxPath}/class/curriculum/delete?clsNo=${curriInfo.clsNo}&curNo=${curriInfo.curNo}&pageNum=${pageNum}'">삭제</button>
		</c:if>
	</c:if>
		
		
		
	<c:if test="${empty curriInfo}">
		<h3>${msg}</h3>
		<br/>
		<br/>
		<br/>
		<input type="button" value="커리큘럼목록" onclick="location.href='${ctxPath}/class/curriculum?pageNum=${pageNum}'">
		<c:if test="${mbType==2}">
			<button type="button" class="btn_normal_t" onclick="location.href='${ctxPath}/class/curriculum/write?pageNum=${pageNum}'">등록</button>
		</c:if>
	</c:if>


<!--------- 본문 끝 -------------->
	</div>
</div>
</section>
<%@ include file="../common/footer.jsp" %>

</body>
</html>