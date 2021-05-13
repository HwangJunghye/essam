<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/aside.css">
<body>

	<div id="aside_menu">
		&nbsp;<i class="far fa-grin-beam"></i> <span style="font-weight: bold;">${sessionScope.loginData.mbNickName}</span> 님!<br>
		&nbsp;(${sessionScope.loginData.mbId})<hr>
		<!-- 로그인 상태 -->
		<c:if test="${!empty sessionScope.loginData}">
			<!-- 학생인 경우 -->
			<c:if test="${sessionScope.loginData.mbType==1}">				
				<!-- 1차 메뉴 -->
				<ul class="main_menu">
					<li class="main_item"><a href="${ctxPath}/mypage">계정 관리</a></li>
					<li class="main_item" id="main"><a href="${ctxPath}/myclass_s">마이 클래스</a></li>
				</ul>
				<!-- 2차 메뉴 -->
				<c:if test="${!empty param.clsNo || !empty clsNo || !empty boardData.clsNo}"> 
				<ul class="second_menu">
					<li class="second_item"><i class="fas fa-angle-right" style="color:#999;"></i> <a href="${ctxPath}/class/boardlist?clsNo=${param.clsNo}&clsBrdType=1&pageNum=1">공지사항/자료실</a></li>
					<li class="second_item"><i class="fas fa-angle-right" style="color:#999;"></i> <a href="${ctxPath}/class/curriculum?clsNo=${param.clsNo}&pageNum=1">커리큘럼</a></li>
					<li class="second_item"><i class="fas fa-angle-right" style="color:#999;"></i> <a href="${ctxPath}/class/boardlist?clsNo=${param.clsNo}&clsBrdType=2&pageNum=1">과제</a></li>
					<li class="second_item"><i class="fas fa-angle-right" style="color:#999;"></i> <a href="${ctxPath}/class/attend?clsNo=${param.clsNo}">출석 현황</a></li>
					<li class="second_item"><i class="fas fa-angle-right" style="color:#999;"></i> <a href="${ctxPath}/class/classinfo?clsNo=${param.clsNo}">클래스 소개</a></li>
				</ul>
				</c:if>
			</c:if>			
			<!-- 강사인 경우 -->
			<c:if test="${sessionScope.loginData.mbType==2}">
			<!-- 1차 메뉴 -->
				<ul class="main_menu">
					<li class="main_item"><a href="${ctxPath}/mypage">계정 관리</a></li>
					<li class="main_item"><a href="${ctxPath}/teacher_profile">프로필 관리</a></li>
					<li class="main_item"><a href="${ctxPath}/class/classinfo/write">클래스 개설</a></li>
					<li class="main_item" id="main"><a href="${ctxPath}/myclass_t">마이 클래스</a></li>
				</ul>
				<!-- 2차 메뉴 -->
				<c:if test="${!empty param.clsNo || !empty clsNo || !empty boardData.clsNo}"> 
				<ul class="second_menu">
					<li class="second_item"><i class="fas fa-angle-right" style="color:#999;"></i> <a href="${ctxPath}/class/classinfo?clsNo=${param.clsNo}">클래스 소개</a></li>
					<li class="second_item"><i class="fas fa-angle-right" style="color:#999;"></i> <a href="${ctxPath}/class/curriculum?clsNo=${param.clsNo}&pageNum=1">커리큘럼</a></li>
					<li class="second_item"><i class="fas fa-angle-right" style="color:#999;"></i> <a href="${ctxPath}/class/boardlist?clsNo=${param.clsNo}&clsBrdType=1&pageNum=1">공지사항/자료실</a></li>
					<li class="second_item"><i class="fas fa-angle-right" style="color:#999;"></i> <a href="${ctxPath}/class/boardlist?clsNo=${param.clsNo}&clsBrdType=2&pageNum=1">과제</a></li>
					<li class="second_item"><i class="fas fa-angle-right" style="color:#999;"></i> <a href="${ctxPath}/class/studentlist?clsNo=${param.clsNo}">학생</a></li>
				</ul>
				</c:if>
			</c:if>
			<!-- 관리자인 경우 -->
			<c:if test="${sessionScope.loginData.mbType==3}">
				<ul class="main_menu">
					<li class="main_item"><a href="${ctxPath}/mypage">계정 관리</a></li>
					<li class="main_item"><a href="${ctxPath}/admin">통계 관리</a></li>
				</ul>
			</c:if>
		</c:if>
		<br>
	</div>
	
</body>
</html>