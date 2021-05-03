<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<link rel="icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
<link rel="shortcut icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">

<style>
      p.a {
        cursor: pointer;
        font-weight: bold;
      }
</style>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
  $(document).ready( function() {
    $( 'p.a' ).click( function() {
      $( 'p.b' ).toggle( 'slow' );
    });
  });
</script>
</head>
<body>

<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp" %>
<section>
<div id="contents">
	<div id="aside">
		<div id="aside_area">
			<%@ include file="../common/aside.jsp"%>
		</div>
	</div>
	<div id="contents_area">
<!--------- 본문 시작 -------------->


<h1>curriculum_write.jsp</h1>
<p class="a">Click to toggle</p>
<p class="b">Lorem ipsum dolor.</p>
${msg}<br/><br/><br/>
<h1>Good Morning!!!</h1>
<!--------- 본문 끝 -------------->
	</div>
</div>
</section>
<%@ include file="../common/footer.jsp" %>


</body>
</html>