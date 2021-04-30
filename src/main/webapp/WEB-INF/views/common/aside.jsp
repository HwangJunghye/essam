<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet" type="text/css"
	href="${ctxPath}/resources/css/nav.css">
<body>

	<div id="aside_menu">
		<!-- 로그인 상태 -->
		<c:if test="${!empty sessionScope.loginData}">
			<!-- 학생인 경우 -->
			<c:if test="${sessionScope.loginData.mbType==1}">
				
				<!-- 1차 메뉴 -->
				<ul class="main_menu">
					<li class="main_item"><a href="${ctxPath}/mypage">계정관리</a></li>
					<li class="main_item" id="main"><a href="${ctxPath}/myclass_s">마이 클래스</a>
						<!-- 2차 메뉴 -->
						<c:if test="${!empty param.clsNo}">
						<ul class="second_menu">
							<li class="second_item"><a href="${ctxPath}/class/boardlist?clsNo=${param.clsNo}&clsBrdType=1&pageNum=1">공지사항/자료실</a></li>
							<li class="second_item"><a href="${ctxPath}/class/curriculum?clsNo=${param.clsNo}">커리큘럼</a></li>
							<li class="second_item"><a href="${ctxPath}/class/boardlist?clsNo=${param.clsNo}&clsBrdType=2&pageNum=1">과제</a></li>
							<li class="second_item"><a href="${ctxPath}/class/attend?clsNo=${param.clsNo}">출석현황</a></li>
							<li class="second_item"><a href="${ctxPath}/class/classinfo?clsNo=${param.clsNo}">클래스소개</a></li>
						</ul>
						</c:if>
					</li>
				</ul>
			</c:if>
			
			<!-- 강사인 경우 -->
			<c:if test="${sessionScope.loginData.mbType==2}">
			<!-- 1차 메뉴 -->
				<ul class="main_menu">
					<li class="main_item"><a href="${ctxPath}/mypage">계정관리</a></li>
					<li class="main_item"><a href="${ctxPath}/teacher_profile">프로필 관리</a></li>
					<li class="main_item"><a href="${ctxPath}/class/classinfo/write">클래스 개설</a></li>
					<li class="main_item" id="main"><a href="${ctxPath}/myclass_t">마이 클래스</a>
						<!-- 2차 메뉴 -->
						<c:if test="${!empty param.clsNo}">
						<ul class="second_menu">
							<li class="second_item"><a href="${ctxPath}/class/classinfo?clsNo=${param.clsNo}">클래스 소개</a></li>
							<li class="second_item"><a href="${ctxPath}/class/curriculum?clsNo=${param.clsNo}">커리큘럼</a></li>
							<li class="second_item"><a href="${ctxPath}/class/boardlist?clsNo=${param.clsNo}&clsBrdType=1&pageNum=1">공지사항/자료실</a></li>
							<li class="second_item"><a href="${ctxPath}/class/boardlist?clsNo=${param.clsNo}&clsBrdType=2&pageNum=1">과제</a></li>
							<li class="second_item"><a href="${ctxPath}/class/studentlist?clsNo=${param.clsNo}">학생</a></li>
						</ul>
						</c:if>
					</li>
				</ul>
				<%-- <!-- 1차 메뉴 -->
				<ul class="main_menu">
					<li class="main_item"><a href="${ctxPath}/mypage">계정관리</a></li>
					<li class="main_item" id="main"><a>클래스 관리</a>
					
						<!-- 2차 메뉴 -->
						<ul class="second_menu">
							<li class="second_item"><a href="${ctxPath}/teacher_profile">프로필 관리</a></li>
							<li class="second_item"><a href="${ctxPath}/class/classinfo/write">클래스 개설</a></li>
							<li class="second_item" id="second"><a href="${ctxPath}/myclass_t">마이 클래스</a>
							
								<!-- 3차 메뉴 -->
								<ul class="third_menu">
									<li class="third_item"><a href="${ctxPath}/class/classinfo/write?clsNo=${param.clsNo}">클래스 수정</a></li>
									<li class="third_item"><a href="${ctxPath}/class/curriculum?clsNo=${param.clsNo}">커리큘럼</a></li>
									<li class="third_item"><a href="${ctxPath}/class/boardlist?clsNo=${param.clsNo}&clsBrdType=1&pageNum=1">공지사항/자료실</a></li>
									<li class="third_item"><a href="${ctxPath}/class/boardlist?clsNo=${param.clsNo}&clsBrdType=2&pageNum=1">과제</a></li>
									<li class="third_item"><a href="${ctxPath}/class/studentlist?clsNo=${param.clsNo}">학생</a></li>
								</ul>
							</li>
						</ul>
					</li>
				</ul> --%>
			</c:if>
			<!-- 관리자인 경우 -->
			<c:if test="${sessionScope.loginData.mbType==3}">
				<ul class="main_menu">
					<li class="main_item"><a href="${ctxPath}/admin">통계관리</a></li>
				</ul>
			</c:if>
			
		</c:if>
	</div>
<script>

	$("#main").hover(function(){
		
		$(".second_menu").css('visibility','visible');
		
	}, function(){
		$(".second_menu").css('visibility','hidden');
	});
	
$("#second").hover(function(){
		
		$(".third_menu").css('visibility','visible');
		
	}, function(){
		$(".third_menu").css('visibility','hidden');
	});
</script>
</body>
</html>