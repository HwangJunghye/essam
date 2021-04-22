<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">

.clsTitle{
font-weight: bold;
font-size: 24px;
line-height: 28px;
}

.line{
width: 960px;
height: 0px;
border: 2px solid #3566A2;
}

#attendResult{
width: 490px;
height: 110px;
display: inline-block;
border: 2px double #3566A2;
text-align: center;
font-size: 18px;
line-height: 21px;
}

.attendText{

}

</style>


<meta charset="UTF-8">
<title>출석현황</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
</head>
<body>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp" %>
<section>

<p class="clsTitle">${attendInfo.clsName}</p>
<br/>
<hr class="line">
<br/>
<div>
전체수업: ${attendInfo.totalDay} 회 <br/>
출 석: ${attendInfo.attendDay} 회 <br/>
결 석: ${attendInfo.absenceDay} 회 <br/>
</div>
<br/>
<br/>
<br/>
<div id="attendResult">
${attendMsg}<br/>
${attendInfo.mbNickName}님의 출석률은 ${attendPercent}%입니다.<br/>
앞으로도 꾸준히 출석해주세요^^<br/>
</div>
	
</section>
<%@ include file="../common/footer.jsp" %>
</body>
</html>