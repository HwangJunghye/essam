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

		
			<table class="container">
				<tr>
					<td align="left" style="padding:20px 0;">
						<h6><i class="fab fa-edge-legacy"></i> 클래스 <i class="fas fa-angle-right"></i> <span style="font-weight: bold;background-color:#f4edd8;">${clsName}</span></h6>
						<hr class="hr_${mbColor}"></td>
				</tr>
			</table>
			<!-- curriInfo가 있으면 -->
			<c:if test="${!empty curriInfo}">
				<table>
					<tr>
						<th>회차</th>
						<th>수업명</th>
						<th>시작일시</th>
						<th>종료일시</th>
						<th>참여</th>
					</tr>
				
					<c:forEach var="cInfo" items="${curriInfo}">
						<tr class="listArea" onclick="location.href='${ctxPath}/class/curriculum/read?curNo=${cInfo.curNo}&clsNo=${cInfo.clsNo}'">
							<td></td>
							<td>${cInfo.curTitle}</td>
							<td>${cInfo.curStartDate}</td>
							<td>${cInfo.curEndDate}</td>
							<c:if test="${cInfo.curTypeNo==1}">
								<td><a href="${ctxPath}/class/videoplay?clsNo=${cInfo.clsNo}&curNo=${cInfo.curNo}">참여아이콘</a></td>
							</c:if>
							<c:if test="${cInfo.curTypeNo==2}">
								<td><a href="${ctxPath}/curriculum/zoomlink">참여아이콘</a></td>
							</c:if>
							
						</tr>
					</c:forEach>
				</table>
			<c:if test="${sessionScope.loginData.mbType==2}">
				<button type="button" class="btn_normal_t" onclick="location.href='${ctxPath}/class/curriculum/write?clsNo=${curriInfo[0].clsNo}'">등록</button>
			</c:if>
		</c:if>

		<!-- curriInfo가 없으면 -->
		<c:if test="${empty curriInfo}">
			<h3>${msg}</h3>
			<c:if test="${sessionScope.loginData.mbType==2}">
				<button type="button" class="btn_normal_t" onclick="location.href='${ctxPath}/class/curriculum/write?clsNo=${clsNo}'">등록</button>
			</c:if>
		</c:if>
		
<!--------- 본문 끝 -------------->
	</div>
</div>
</section>
<%@ include file="../common/footer.jsp" %>

</body>
</html>