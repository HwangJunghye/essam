<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<% //@Author 고연미 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>e-쌤</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/class.css">
</head>
<body>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp" %>
<section>
<div id="contents">
	<!---------- 본문 시작 ---------->
	
	<div id="classInfo_area1">
		<div class="container" style="background-color:#fffff;"><br>
			<ul class="nav nav-tabs" id="myTab" role="tablist">
			  <li class="nav-item" role="presentation">
			    <a class="nav-link active" href="#info_cls" >클래스소개</a>
			  </li>
			  <li class="nav-item" role="presentation">
			    <a class="nav-link" href="#info_th">강사소개</a>
			  </li>
			  <li class="nav-item" role="presentation">
			    <a class="nav-link" href="#info_curri">커리큘럼</a>
			  </li>
			</ul>
			<table id="info_cls">
			<tr>
				<td align="left" style="padding:20px 0;">
				<img src="${ctxPath}/getimage?fileNo=${classInfo.fileNo}">
				<p>
				<i class="far fa-caret-square-right"></i> 카테고리 : ${classInfo.cate1Name} > ${classInfo.cate2Name}<br/>
				<i class="far fa-caret-square-right"></i> 클래스명 : ${classInfo.clsName}<br/>
				<i class="far fa-caret-square-right"></i> 정원 : <fmt:formatNumber value="${classInfo.clsLimit}" type="number"/> 명<br/>
				<i class="far fa-caret-square-right"></i> 한줄 소개 : ${classInfo.clsIntro}<br/><br>
				<span style="white-space:pre-wrap;word-break:break-all;">${classInfo.clsDesc}</span><br/></p>
				</td>
			</tr>
			</table><br>
			<table align="right">
			<tr><td align="right"><a href="#nav_area"><i class="far fa-arrow-alt-circle-up"></i> Top</a></td></tr>
			</table><br>
			
			<div id="info_th">
			<i class="fas fa-user-graduate"></i> 강사 소개
			<hr class="hr_${mbColor}"><br>
			<c:if test="${teacherInfo.fileNo!=null || teacherInfo.teacherIntro!=null || teacherInfo.teacherDetail!=null}">
				<div id="wrap" style="width:960px; height:642px; margin:0 auto; overflow:hidden;">
					<div id="left_side_box" style="width:240px; height:642px; overflow:hidden; float:left;">
						<div id="profile_image_box" style="width:154px; height:154px; border-radius:70%; overflow:hidden; background:pink; position:relative; left:30px; top:96px;">
							<img id="profile" style="width:100%; height:100%; object-fit:cover;" src="${ctxPath}/getthumbnail?fileNo=${teacherInfo.fileNo}&width=150&height=150">
						</div>
					</div>
					<div id="right_side_box" style="width:720px; height:642px; float:left; overflow:hidden;">
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
						<div id="detail_box3" style="width:720px; height:304px; text-align:left; white-space:pre-wrap;word-break:break-all;">
							${teacherInfo.teacherDetail}
							<!-- <div id="contents" style="min-height:900px;"> -->
						</div>
					</div>
				</div>
			</c:if><br>
			<table align="right">
			<tr><td align="right"><a href="#nav_area"><i class="far fa-arrow-alt-circle-up"></i> Top</a></td></tr>
			</table><br>
			</div>
			<div id="info_curri">
			<i class="fas fa-user-graduate"></i> 커리큘럼
			<hr class="hr_${mbColor}"><br>
			...<br>
			<table align="right">
			<tr><td align="right"><a href="#nav_area"><i class="far fa-arrow-alt-circle-up"></i> Top</a></td></tr>
			</table><br><br><br><br><br>
			</div>
		</div>
	</div>
	<div id="classInfo_area2">
			<div id="box_clsJoin"><br><br>
			
			<table style="border:2px solid #e0e0e0;" height=200>
			<tr>
				<td align="left" style="white-space:nowrap;">&nbsp;<br>가격 : <fmt:formatNumber value="${classInfo.clsPrice}" type="number"/> 원 / 월</td>
			</tr>
			<tr>
				<td align="left">신청현황 : <fmt:formatNumber value="${classInfo.clsRegiCnt}" type="number"/> / <fmt:formatNumber value="${classInfo.clsLimit}" type="number"/> 명<br>&nbsp;</td>
			</tr>
			<tr>
				<td align="center" style="background-color:#fca9a8;"><a href="${ctxPath}/classjoin?clsNo=${classInfo.clsNo}"><i class="far fa-arrow-alt-circle-right"></i> <span style="font-weight: bold;">수강신청</span></a></td>
			</tr></table>
			</div>
	</div>
	
	<!---------- 본문 끝 ---------->
</div>
</section>
<%@ include file="../common/footer.jsp" %>
</body>
</html>