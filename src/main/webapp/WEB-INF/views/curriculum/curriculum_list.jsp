<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>e-쌤</title>
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
		<div class="container">
			<table class='table table-hover' style="background-color:white;">
			<% 
			int pageNum = Integer.parseInt(request.getParameter("pageNum"));
			int listNum = 1;
			if(pageNum > 1)
			      listNum = pageNum*10 - 9;
			 %>
			 <!-- curriInfo가 있으면 -->
			 <c:if test="${!empty curriInfo}">
			 	<thead>
					<tr>
						<th>회차</th>
						<th>수업명</th>
						<th>시작일시</th>
						<th>종료일시</th>
						<th>참여</th>
					</tr>
				</thead>
				<tbody class='tbl'>
					<c:forEach var="cInfo" items="${curriInfo}" varStatus="status">
						<tr class="listArea">
							<td><%=listNum %></td>
							<td><a href="${ctxPath}/class/curriculum/read?clsNo=${cInfo.clsNo}&curNo=${cInfo.curNo}&pageNum=${pageNum}">${cInfo.curTitle}</a></td>
							<td>${cInfo.curStartDate}</td>
							<td>${cInfo.curEndDate}</td>
							<c:if test="${cInfo.curTypeNo==1}">
								<td><a href="${ctxPath}/class/videoplay?clsNo=${cInfo.clsNo}&curNo=${cInfo.curNo}&pageNum=${pageNum}">동영상</a></td>
							</c:if>
							<c:if test="${cInfo.curTypeNo==2}">
								<td><a href="${ctxPath}/class/zoomlink?clsNo=${cInfo.clsNo}&curNo=${cInfo.curNo}&pageNum=${pageNum}" target="_blank">실시간</a></td>
							</c:if>	
						</tr>
						<%listNum++; %>
					</c:forEach>
				</tbody>
				
			</c:if>
			
			<!-- curriInfo가 없으면 -->
			<c:if test="${empty curriInfo}">
				<thead>
					<tr class='prod'>
						${msg}
					</tr>
				</thead><br/><br/><br/>
				<c:if test="${sessionScope.loginData.mbType==2}">
					<button type="button" class="btn_normal_t" onclick="location.href='${ctxPath}/class/curriculum/write?clsNo=${clsNo}'">등록</button>
				</c:if>
			</c:if>
			</table>
			<!-- 페이징 -->
		${paging}
		</div>
		<div class="container" style="width:720px; float:right; text-align:right; margin-right:30px;">
			<c:if test="${sessionScope.loginData.mbType==2}">
				<button type="button" class="btn_normal_t" onclick="location.href='${ctxPath}/class/curriculum/write?clsNo=${curriInfo[0].clsNo}&pageNum=${pageNum}'">등록</button>
			</c:if>
		</div>
<!--------- 본문 끝 -------------->
	</div>
</div>
</section>
<%@ include file="../common/footer.jsp" %>

</body>
</html>