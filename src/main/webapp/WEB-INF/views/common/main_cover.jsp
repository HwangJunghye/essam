<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/main.css">
<style>
</style>
<body>

<nav>
	<div id="nav_title">
		<%-- <img src="${ctxPath}/resources/images/main_img1.jpg"> --%>    
		<div id="myCarousel" class="carousel slide" data-ride="carousel" data-interval="3000"><!-- 인터벌: 3초 -->
			<ul class="carousel-indicators">
				<li data-target="#myCarousel" data-slide-to="0" class="active"></li> 
				<li data-target="#myCarousel" data-slide-to="1"></li> 
				<li data-target="#myCarousel" data-slide-to="2"></li>
				<li data-target="#myCarousel" data-slide-to="3"></li>
			</ul>
			<div class="carousel-inner">
			<!-- 슬라이드 쇼 -->
				<div class="carousel-item active">
					<!--가로--> 
					<img id="btn1" class="d-block w-100" src="${ctxPath}/resources/images/main_img7.jpg?auto=compress&cs=tinysrgb&h=280&w=1400" alt="First slide">
					<div class="carousel-caption d-none d-md-block">
					<h1><span style="font-weight: bold;">Why e-쌤 ?!!</span></h1>
					<p>&nbsp;</p></div>
				</div>
				<div class="carousel-item">
					<img id="btn2" class="d-block w-100" src="${ctxPath}/resources/images/main_img2.jpg?auto=compress&cs=tinysrgb&h=280&w=1400" alt="Second slide">
					<div class="carousel-caption d-none d-md-block">
					<h3><span style="font-weight: bold;">언제나, 어디서나</span></h3>
					<p><h4>Private Lesson Only for you</h4></p></div>
				</div>
				<div class="carousel-item">
		            <img id="btn3" class="d-block w-100" src="${ctxPath}/resources/images/main_img11.jpg?auto=compress&cs=tinysrgb&h=280&w=1400" alt="Third slide">
					<div class="carousel-caption d-none d-md-block">
					<h3><span style="font-weight: bold;">나를 성장시키는 힘</span></h3>
					<p><h4>Make your Turning Point in your LIfe!</h4></p></div>
		        </div>
				<div class="carousel-item">
		            <img id="btn4" class="d-block w-100" src="${ctxPath}/resources/images/main_img4.jpg?auto=compress&cs=tinysrgb&h=280&w=1400" alt="fourth slide">
					<div class="carousel-caption d-none d-md-block">
					<h3><span style="font-weight: bold;">e-쌤과 함께라면 가능합니다.</span></h3>
					<p><h4>We can make it, with e-Ssam!</h4></p></div>
		        </div>
				<!-- / 슬라이드 쇼 끝 -->
				
				<!-- 왼쪽 오른쪽 화살표 버튼 
				<a class="carousel-control-prev" href="#demo" data-slide="prev"><span class="carousel-control-prev-icon" aria-hidden="true"></span>
				<a class="carousel-control-next" href="#demo" data-slide="next"><span class="carousel-control-next-icon" aria-hidden="true"></span>
				/ 화살표 버튼 끝 
				 -->
			</div>
	</div>
</nav>
<script>

</script>
</body>
</html>