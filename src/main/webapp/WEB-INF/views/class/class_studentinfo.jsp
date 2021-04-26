<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctxPath" value="<%=request.getContextPath()%>" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="${ctxPath}/resources/css/basic.css">
</head>
<style>
table{
float: center;
margin: auto;
items-align:center;
width:80%;
border: none;
text-align: center;
line-height: 28px;
}

.line{
width: 960px;
height: 0px;
border: 2px solid #9E3C7E;
}

</style>
<body>
	<%@ include file="../common/header.jsp"%>
	<%@ include file="../common/nav.jsp"%>


	<section>



		<!-- sList가 있으면 -->
		<c:if test="${!empty sList}">
			<h3>${clsInfo.clsName}</h3>
			<br>
			<hr class="line">
			<br>
			
			<table>
				<tr>
					<th>닉네임</th>
					<th>이메일</th>
					<th>등록일</th>
					<th colspan="3">출결현황(출석일/총수업일)</th>
				</tr>
				<c:forEach var="sInfo" items="${sList}">
					<tr>					
						<td>${sInfo.mbNickName}</td>

						<td>${sInfo.mbId}</td>
						
						<td>${sInfo.regiStartDate}</td>
						<td>${sInfo.attendDay}</td>
						<td>/</td>
						<td>${sInfo.totalDay}</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		

			<!-- sList가 없으면 -->
			<c:if test="${empty sList}">
				<h3>수강생이 없습니다.</h3>
			</c:if>
	</section>


	<%@ include file="../common/footer.jsp"%>
</body>
</html>