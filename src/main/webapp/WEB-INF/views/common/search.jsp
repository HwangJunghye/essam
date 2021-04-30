<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>검색 페이지</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<script src="${ctxPath}/resources/js/search.js"></script>
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<%@ include file="../common/nav.jsp"%>
	<section>
		<div id="contents">
			<div id="cls-list">
			</div>
		</div>
		<br/><br/><br/><br/>
	</section>
	<%@ include file="../common/footer.jsp"%>

	<script>
		$(function() {
			searchReady({ctxPath:'${ctxPath}', cate1No:'${cate1No}', cate2No:'${cate2No}', keyword:'${keyword}'});
		}); // ready End
	</script>
</body> 
</html>