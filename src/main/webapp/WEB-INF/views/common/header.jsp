<%@page import="com.essam.www.constant.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 
ctxPath : contextPath는 절대경로
${ctxPath} + 매핑URL로 경로지정을 권장
예) 인덱스 페이지 -> ${ctxPath}/
--%>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<c:if test="${!empty sessionScope.loginData}">
	<c:if test="${sessionScope.loginData.mbType==2}">
		<c:set var="mbColor" value="t_color"/>
	</c:if>
	<c:if test="${sessionScope.loginData.mbType!=2}">
		<c:set var="mbColor" value="s_color"/>
	</c:if>
</c:if>
<c:if test="${empty sessionScope.loginData}">
	<c:set var="mbColor" value="s_color"/>
</c:if>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://kit.fontawesome.com/a076d05399.js"></script>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/header.css">
<script src="${ctxPath}/resources/js/header.js"></script>
<script>
	$(function(){
		headerReady({fMsg:'${fMsg}', ctxPath:'${ctxPath}',modal:"${modal}"});
	}); // ready End
</script>
</head>
<body>
<header>
	<div id="nav_area">
		<div id="nav_area1"><a href="${ctxPath}/"><img alt="essamLogo" src="${ctxPath}/resources/images/essamlogo.png" style="height:65px;"></a></div>
		<div id="nav_area2">
			<!-- 드롭다운 메뉴 -->
			<div class="dropdown-wrap">
				<c:forEach items="<%=Constant.cate1Name%>" begin="1" varStatus="state" var="item">
				<div class="dropdown-menu-wrap">
					<a href="${ctxPath}/search?cate1No=${state.index}"><div class="dropdown-main">${item}</div></a>
					<div class="dropdown-sub-wrap">
						<c:forEach items="<%=Constant.cate2Name%>" begin="1" varStatus="state2" var="item2">
						<a href="${ctxPath}/search?cate1No=${state.index}&cate2No=${state2.index}"><div class="dropdown-sub">&nbsp;${item2}&nbsp;</div></a>
						</c:forEach>
					</div>
				</div>
				</c:forEach>
        	</div>
		</div>
		<div id="nav_area3">
			<form action="${ctxPath}/search" method="get">    
    			<table>
				<tr>
					<td align="right"><input type="search" name="keyword" id="input_txt_src" placeholder="검색어" /></td>
					<td align="left"><button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-search"></i></button>
				</tr>
				</table>
			</form>
		</div>
		<div id="nav_area4"><p class="text-center" style="margin-top:28px"><h6><span style="font-weight: bold;">
			<c:if test="${empty sessionScope.loginData}">
				<!-- 로그아웃 상태 -->
				<a href="#" data-toggle="modal" data-target="#moaModal">로그인</a>&nbsp;&nbsp;
				<a href="${ctxPath}/join">회원가입</a>
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
				&nbsp;&nbsp;<a id="mypage" href="${ctxPath}/mypage">마이페이지</a>
				&nbsp;&nbsp;<a id="logout" href="#">로그아웃</a>
			</c:if></span></h6></p>
		</div>
	</div>
</header>

<!-- Moa Modal-->
  <div class="modal fade" id="moaModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel"><i class="fas fa-user-tie"></i>&nbsp;로그인</h5>
          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">x</span>
          </button>
        </div>
        <div class="modal-body">
	        <!-- 로그인 form 삽입 -->
	        <form action="${ctxPath}/access" method="post">
				아이디&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="mbId" id="mbId" required/><br>
				<br>
				비밀번호 <input type="text" name="mbPwd" required/><br>
				<br>
				<input type="submit" value="로그인"/><br>
				<a herf="#">비밀번호를 잊으셨나요?</a><br>
				<br>
				<h7>아직 e-쌤 회원이 아니신가요?</h7><br>
				<a href="${ctxPath}/join"><i class="far fa-arrow-alt-circle-right"></i>회원가입하기</a>
			</form>
        </div>
        <div class="modal-footer">
          <button class="btn btn-primary" type="button" data-dismiss="modal">Cancel</button>
        </div>
      </div>
    </div>
  </div>


</body>
</html>