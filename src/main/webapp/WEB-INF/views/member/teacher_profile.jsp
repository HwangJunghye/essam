<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<% //Author : 조참 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>e-쌤</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<link rel="icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
<link rel="shortcut icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
<style>
#t_profile {
	 margin : 50px 100px
}
input[type="button"]{
	width: 70px;
	height: 36px;
	box-shadow: 3px 3px 4px 1px rgba(0, 0, 0, 0.1);
	border-radius: 10px;
	color: #FFFFFF;
	text-align: center;
	border: none;
	cursor: pointer;
}

#msgbox{
width: 530px;
height: 120px;
margin:200px;
padding:auto;
float:center;
border: outset 3px #3566A2;
}

#msg{
width:500px;
height:100px;
font-style: bold;
font-weight: normal;
font-size: 24px;
margin:20px;
padding:auto;
}

</style>
</head>
<body>
<%@ include file="../common/header.jsp" %>
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
		<!-- <h1>teacher_profile.jsp</h1> -->
		<c:if test="${teacherInfo.fileNo==null && teacherInfo.teacherIntro==null && teacherInfo.teacherDetail==null}">
			<br><br><div id="msgbox"><div id="msg">등록된 프로필 정보가 없습니다.</div></div><br><br><br><br>
			<input type="button" class="${mbColor}" onclick="location.href='${ctxPath}/teacher_profile/write'" value="등록">
		</c:if>
		<c:if test="${teacherInfo.fileNo!=null || teacherInfo.teacherIntro!=null || teacherInfo.teacherDetail!=null}">
			<div id="wrap" style="width:960px; height:642px; margin:0 auto; overflow:hidden;">
				<div id="left_side_box" style="width:240px; height:642px; overflow:hidden; float:left;">
					<div id="profile_image_box" style="width:154px; height:154px; border-radius:70%; overflow:hidden; background:pink; position:relative; left:30px; top:96px;">
						<img id="profile" style="width:100%; height:100%; object-fit:cover;" src="${ctxPath}/getthumbnail?fileNo=${teacherInfo.fileNo}&width=150&height=150">
					</div>
				</div>
				<div id="right_side_box" style="width:720px; height:500px; float:left; overflow:hidden;">
					<div id="nickname_box1" style="width:720px; height:168px; overflow:auto;">
						<div id="nickname" style="width:104px; height:50px; text-align:left; float:left; position:relative; left:0px; top:118px;">
							<h5 style="font-weight: bold; font-size:25px;">${teacherInfo.mbNickName}</h5>
						</div>
						<div id="teachergrade" style="width:140px; height:50px; text-align:left; padding:5px 0px 0px; float:left; position:relative; left:50px; top:118px;">
							<h5 style="font-weight: bold; font-size:17px;">마스터</h5>
						</div>
					</div>
					<div id="intro_box2" style="width:720px; height:60px; text-align:left; padding:10px 0px 0px;">
						<h6 style="font-weight: bold;">${teacherInfo.teacherIntro}</h6>
					</div>
					<div id="detail_box3" style="width:720px; height:200px; text-align:left;">
						${teacherInfo.teacherDetail}<br><br><br><br>
						<!-- <div id="contents" style="min-height:900px;"> -->
					</div>
					<div style="width:720px; height:100px;">
						<table>
							<tr>
								<td style="align:right;">
									<input type="button" class="${mbColor}" onclick="location.href='${ctxPath}/teacher_profile/write'" value="수정">&emsp;
									<input type="button" class="${mbColor}" onclick="location.href='${ctxPath}/teacher_profile/delete'" value="삭제">
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</c:if><br><br><br><br>
<!--------- 본문 끝 -------------->
   </div>
</div>
</section>
<%@ include file="../common/footer.jsp" %>
</body>
</html>