<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<% //Author : 조참 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<style>
	#t_profile {
		 margin : 50px 100px
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
			등록된 프로필 정보가 없습니다.
			<a href="${ctxPath}/teacher_profile/write">등록</a>
		</c:if>
		<div id="t_profile">
			<c:if test="${teacherInfo.fileNo!=null || teacherInfo.teacherIntro!=null || teacherInfo.teacherDetail!=null}">
				<table align="left">
					<tr>
						<td>
							<img src="${ctxPath}/getthumbnail?fileNo=${teacherInfo.fileNo}&width=150&height=100"><br/>
						</td>
						<td>
							<tr>
								<h5 style="font-weight: bold;">${teacherInfo.mbNickName}</h5>
							</tr>
							<tr>
								<c:if test="${teacherInfo.teacherGrade==0}">
									<h5 style="font-weight: bold;">마스터</h5>
								</c:if>
							</tr>
							<tr>
								<h6 style="font-weight: bold;">${teacherInfo.teacherIntro}</h6><br/><br/>
							</tr>
							<tr>
								${teacherInfo.teacherDetail}<br/>
							</tr>
						</td>
					</tr>
					<tr>
						<a href="${ctxPath}/teacher_profile/write">수정</a>
						<a href="${ctxPath}/teacher_profile/delete">삭제</a>
					</tr>
				</table>
			</c:if>
		</div>
<!--------- 본문 끝 -------------->
   </div>
</div>
</section>
<%@ include file="../common/footer.jsp" %>
</body>
</html>