<%@page import="com.essam.www.constant.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 
ctxPath : contextPath는 절대경로
${ctxPath} + 매핑URL로 경로지정을 권장
예) 인덱스 페이지 -> ${ctxPath}/
--%>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"
	integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
	crossorigin="anonymous"></script>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/header.css">
<style>

</style>
<script>
	$(function(){
		// alert창 출력
		let fMsg = '${fMsg}';
		if(fMsg !=''){
			alert(fMsg);
		}
		
		// 로그아웃 버튼 클릭시 로그아웃(post) 실행
		$('#logout').on('click',function(){
			// form 태그 생성
			let $logoutForm = $('<form>');
			$logoutForm.attr('action','${ctxPath}/logout');
			$logoutForm.attr('method','post');
			// body에 추가
			$logoutForm.appendTo('body');
			// 전송(로그아웃 실행)
			$logoutForm.submit();
		}); // on End
	}); // ready End
</script>
</head>
<body>
<header>
	<div id="nav">
		<div id="nav_area1"><h3>header1.jsp - <a href="${ctxPath}/">home</a></h3></div>
		<div id="nav_area2">
			<span id="#nav_cate1">
				<c:forEach items="<%=Constant.cate1Name%>" begin="1" varStatus="state" var="item">
					<a href="${ctxPath}/search?cate1No=${state.index}">${item}</a>
				</c:forEach>			
			</span>
		</div>
		<div id="nav_area3">
			<form action="${ctxPath}/search" method="get">
				<input type="search" name="keyword" /><input type="submit" value="검색"/>
			</form>
		</div>
		<div id="nav_area4">
			<c:if test="${empty sessionScope.loginData}">
				<!-- 로그아웃 상태 -->
				<a href="${ctxPath}/login">로그인</a>   <a href="${ctxPath}/join">회원가입</a>
			</c:if>
			<c:if test="${!empty sessionScope.loginData}">
				<!-- 로그인 상태 -->
				<c:if test="${sessionScope.loginData.mbType==1}">
					<!-- 학생인 경우 -->
					학생로그인
				</c:if>
				<c:if test="${sessionScope.loginData.mbType==2}">
					<!-- 강사인 경우 -->
					강사로그인
				</c:if>
				<a id="logout" href="#">로그아웃</a>
			</c:if>
		</div>
	</div>
</header>
</body>
</html>