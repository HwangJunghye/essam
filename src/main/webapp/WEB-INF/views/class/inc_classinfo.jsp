<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% //@Author 고연미 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>클래스 소개</title>
</head>
<body>

		<div class="container" style="background-color:#fffff;">
			<img src="${ctxPath}/getimage?fileNo=${classInfo.fileNo}" style="margin-bottom:30px;"><br>
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
			<!-- 클래스 소개 -->
			<table id="info_cls">
			<tr> 
				<td align="left" style="padding:20px 0;">
				<i class="far fa-caret-square-right"></i> 카테고리 : ${classInfo.cate1Name} > ${classInfo.cate2Name}<br/>
				<i class="far fa-caret-square-right"></i> 클래스명 : ${classInfo.clsName}<br/>
				<i class="far fa-caret-square-right"></i> 정원 : <fmt:formatNumber value="${classInfo.clsLimit}" type="number"/> 명<br/>
				<i class="far fa-caret-square-right"></i> 한줄 소개 : ${classInfo.clsIntro}<br/><br>
				<span style="white-space:pre-wrap;word-break:break-all;">${classInfo.clsDesc}</span><br/>
				</td>
			</tr>
			</table><br>
			<!-- 강사 소개 -->
			<div id="info_th">
				<table width="100%" align="center">
				<tr><td align="left"><i class="fas fa-user-graduate"></i> 강사 소개</td>
					<td align="right"><a href="#nav_area"><i class="far fa-arrow-alt-circle-up"></i> Top</a></td>
				</tr>
				<tr><td colspan=2><hr class="hr_${mbColor}"></td></tr>
				</table>
				<c:if test="${teacherInfo.fileNo!=null || teacherInfo.teacherIntro!=null || teacherInfo.teacherDetail!=null}">
					<table>
					<tr>
						<td rowspan=3 valign="top" width="220" style="padding:10px;">
							<div id="profile_image_box" style="width:154px; height:154px; border-radius:70%; overflow:hidden; background:pink; position:relative; left:30px;">
								<img id="profile" style="width:100%; height:100%; object-fit:cover;" src="${ctxPath}/getthumbnail?fileNo=${teacherInfo.fileNo}&width=150&height=150">
							</div></td>
						<td align="left" style="padding:10px;"><h5 style="font-weight: bold; font-size:25px;">${teacherInfo.mbNickName}</h5></td>
						<td style="padding:10px;"><h5 style="font-weight: bold; font-size:17px;">마스터</h5></td>
					</tr>
					<tr>
						<td colspan=2 align="left" style="padding:10px;"><h6 style="font-weight: bold;">${teacherInfo.teacherIntro}</h6></td>
					</tr>
					<tr>
						<td	colspan=2 align="left" style="padding:10px;white-space:pre-wrap;word-break:break-all;">${teacherInfo.teacherDetail}</td>
					</tr>
					</table>
				</c:if><br><br>
			</div>
			<!-- 커리큘럼 -->
			<div id="info_curri">
				<table width="100%" align="center">
				<tr><td align="left"><i class="fas fa-user-graduate"></i> 커리큘럼</td>
					<td align="right"><a href="#nav_area"><i class="far fa-arrow-alt-circle-up"></i> Top</a></td>
				</tr>
				<tr><td colspan=2><hr class="hr_${mbColor}"></td></tr>
				</table>
				<table>
				<% int listNum = 1; %>
				<c:forEach var="cInfo" items="${curriList}" varStatus="status">
					<tr>
						<td align="left"><li style="list-style-type: circle;"><%=listNum %>회차 :&nbsp;&nbsp;</td>
						<td align="left" style="font-weight:bold;"> ${cInfo.curTitle}</td>
					</tr>
					<% listNum++; %>
				</c:forEach>
				</table>
				<br>
				<table align="right">
				<tr><td align="right"><a href="#nav_area"><i class="far fa-arrow-alt-circle-up"></i> Top</a></td></tr>
				</table><br><br><br><br><br>
			</div>
		</div>
		
</body>
</html>