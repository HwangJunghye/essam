<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<section>
<h1>클래스 소개</h1>
카테고리 : ${classInfo.cate1Name} > ${classInfo.cate2Name}<br/>
클래스명 : ${classInfo.clsName}<br/>
정원 : ${classInfo.clsLimit}<br/>
한줄 소개 : ${classInfo.clsIntro}<br/>
상세 소개 : ${classInfo.clsDesc}<br/>
<p>
가격 : ${classInfo.clsPrice}원/월<br/>
신청현황 : ${classInfo.clsRegiCnt} / ${classInfo.clsLimit}<br/>
<a href="${ctxPath}/classjoin">수강신청</a>
</p>
</section>
	<%@ include file="../common/footer.jsp" %>
</body>
</html>