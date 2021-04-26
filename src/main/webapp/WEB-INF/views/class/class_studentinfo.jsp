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
</head>
<body>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp" %>


<section>

	<h1>${sList.clsName}</h1>


	
	<!-- sList가 있으면 -->
		<c:if test="${!empty sList}">
	
		
		
			<c:forEach var="sInfo" items="${sList}">
				<div>
				닉네임 이메일 연락처 등록일 출결현황(출석일/총수업일)
					<p>${sInfo.mbNickName} ${sInfo.mbId} ${sInfo.regiStartDate} ${sInfo.attendDay} / ${sInfo.totalDay}</p>
				</div>
			</c:forEach>
		</c:if>


		<!-- sList가 없으면 -->
		<c:if test="${empty sList}">
			<h3>수강생이 없습니다.</h3>
		</c:if>
	
</section>


<%@ include file="../common/footer.jsp" %>
</body>
</html>