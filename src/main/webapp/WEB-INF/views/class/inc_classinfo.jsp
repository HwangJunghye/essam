<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
						<td rowspan=3 valign="top" width="200"><img id="profile" src="${ctxPath}/getthumbnail?fileNo=${teacherInfo.fileNo}&width=150&height=150"></td>
						<td align="left"><h5 style="font-weight: bold; font-size:25px;">${teacherInfo.mbNickName}</h5></td>
						<td><h5 style="font-weight: bold; font-size:17px;">마스터</h5></td>
					</tr>
					<tr>
						<td colspan=2 align="left"><h6 style="font-weight: bold;">${teacherInfo.teacherIntro}</h6></td>
					</tr>
					<tr>
						<td	colspan=2 align="left" style="white-space:pre-wrap;word-break:break-all;">${teacherInfo.teacherDetail}</td>
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
				</table><br>
				...<br>
				<table align="right">
				<tr><td align="right"><a href="#nav_area"><i class="far fa-arrow-alt-circle-up"></i> Top</a></td></tr>
				</table><br><br><br><br><br>
			</div>
		</div>
		
</body>
</html>