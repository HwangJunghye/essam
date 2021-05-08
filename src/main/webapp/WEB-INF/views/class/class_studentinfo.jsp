<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctxPath" value="<%=request.getContextPath()%>" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<link rel="icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
<link rel="shortcut icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
</head>
<style>
.tbl_stu {
	float: center;
	margin: auto;
	items-align: center;
	width: 80%;
	border: none;
	text-align: center;
	line-height: 28px;
}
.line {
	width: 960px;
	height: 0px;
	border: 2px solid #9E3C7E;
}
.listArea{
height: 20px;
}

.attendDay{
text-align:right;
}
.totalDay{
text-align:left;

}

</style>
<body>
	<%@ include file="../common/header.jsp"%>
	<%@ include file="../common/nav.jsp"%>
<section>

<div id="contents">
	<div id="aside">
		<div id="aside_area">
			<%@ include file="../common/aside.jsp"%>
		</div>
	</div>
	<div id="contents_area">
<!--------- 본문 시작 -------------->

		<!-- sList가 있으면 -->
		<c:if test="${!empty sList}">
			<!-- 클래스명 출력 -->	
			<table class="container">
				<tr>
					<td align="left" style="padding:20px 0;">
						<h6><i class="fab fa-edge-legacy"></i> 클래스 <i class="fas fa-angle-right"></i> <span style="font-weight: bold;background-color:#f4edd8;">${clsInfo.clsName}</span></h6>
						<hr class="hr_${mbColor}"></td>
				</tr>
			</table>
	
			<!-- 웹소켓을 사용해서 강사가 클래스 학생들에게 메세지 보내기 -->
			
			<div id="messageArea"></div>
			<table class="container" style="background:#efefef;border:2px solid #ddd;">
			<tr>
				<td style="padding:10px;"><h5>로그인한 학생들에게 푸쉬알림 메세지 보내기</h5>
					<form>
						<input type="text" size="100" id="sendMsg">
						<button type="button" id="btnSendMsg" class='btn_normal_t'>전송 <i class="far fa-paper-plane"></i></button>
					</form></td>
			</tr></table><br>
			
			<!-- 학생 목록 출력 -->			
			<table class="tbl_stu">
				<tr>
					<th>상태</th>
					<th>닉네임</th>
					<th>이메일</th>
					<th>등록일</th>
					<th colspan="3">출결현황(출석일/총수업일)</th>
				</tr>
			
				<c:forEach var="sInfo" items="${sList}">
					<tr class="listArea" onclick="location.href='${ctxPath}/class/studentinfo?mbId=${sInfo.mbId}&clsNo=${sInfo.clsNo}'">
						<td><c:if test="${sInfo.socketId != null}"><i class="fas fa-wifi" style="color:#007BFF"></i></c:if></td>
						<td>${sInfo.mbNickName}</td>
						<td>${sInfo.mbId}</td>
						<td>${sInfo.regiStartDate}</td>
						<td class="attendDay">${sInfo.attendDay}</td>
						<td>/</td>
						<td class="totalDay">${sInfo.totalDay}</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>

		<!-- sList가 없으면 -->
		<c:if test="${empty sList}">
			<h3>수강생이 없습니다.</h3>
		</c:if>

<!--------- 본문 끝 -------------->
	</div>
</div>
	</section>


	<%@ include file="../common/footer.jsp"%>
</body>
</html>