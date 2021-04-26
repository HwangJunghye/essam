<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 페이지</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
</head>
<style>
.btn{
width: 64px;
height: 32px;
background: #3566A2;
box-shadow: 3px 3px 4px 1px rgba(0, 0, 0, 0.1);
border-radius: 10px;
color: #FFFFFF;
text-align: center;
border: none;
}
</style>
<body>

<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp" %>
<section>


조회기간: <input type="date" id="startDate"> ~ <input type="date" id="endDate"><br/>
조회대상:
<input type="radio" name="searchTarget" value="1" required/>클래스
<input type="radio" name="searchTarget" value="2" />강사
<input type="radio" name="searchTarget" value="3" />수강생
<br/>
필터1: 
<input type="radio" name="filter1" value="1" required/>전체
<input type="radio" name="filter1" value="2" />카테고리
<br/>
필터2:
<input type="radio" name="filter2" value="1" required/>총
<input type="radio" name="filter2" value="2" />신규
<br/>
<br/>
<input type="reset" class="btn" id="reset" value="초기화">
<input type="button" class="btn" id="showChart" value="조회">
<br/>
<br/>

<div id="chartArea">
차트 표시 영역
</div>

	
</section>
<%@ include file="../common/footer.jsp" %>

<script type="text/javascript">

//function showChart(){}

$(function(){
	$("#showChart").on("click", function(){
		let startDate = $("#startDate").val();
		let endDate = $("#endDate").val();
		let searchTarget = $("#").val();
		let filter1 = $("#").val();
		let filter2 = $("#").val();
		
		
	})//showchart onclick end
	
})//ready function end



</script>


</body>
</html>