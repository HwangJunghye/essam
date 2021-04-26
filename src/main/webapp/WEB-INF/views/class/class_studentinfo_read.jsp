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
<style>
.line{
width: 960px;
height: 0px;
border: 2px solid #9E3C7E;
}
.btn{
width: 64px;
height: 32px;
background: #9E3C7E;
box-shadow: 3px 3px 4px 1px rgba(0, 0, 0, 0.1);
border-radius: 10px;
color: #FFFFFF;
text-align: center;
border: none;
cursor: pointer;
}
</style>
<body>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp" %>
<section>
<h1>${sInfo.clsName}</h1><br/>
<hr class="line"><br/>
<br/>
기본정보<br/>
이름 : ${mInfo.mbName}<br/>
닉네임:${sInfo.mbNickName} <br/>
이메일:${sInfo.mbId}<br/>
등록일:${sInfo.regiStartDate}<br/>
주소:${mInfo.mbAddr}<br/>


출석현황<br/>
출석일수/총수업일수: ${sInfo.attendDay}  / ${sInfo.totalDay} <br/>
출석률: ${attendPercent}%<br/>
<br/>
<input type="button" class="btn" value="학생목록" onclick="history.back(-1);">



	
	
	
	
	
</section>
<%@ include file="../common/footer.jsp" %>
</body>
</html>