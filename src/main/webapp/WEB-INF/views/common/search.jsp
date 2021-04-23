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
<style type="text/css">

</style>
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<%@ include file="../common/nav.jsp"%>
	<section>
		<article id="searchresult">
			<!-- 검색 결과 표시 -->
		</article>
	</section>
	<%@ include file="../common/footer.jsp"%>

	<script>
		// 스크롤 페이징 생성
		let searchFlag = true; // 페이징 여부
		let pageNo = 0;
		const cate1No = '${requestScope.cate1No}';
		const cate2No = '${requestScope.cate2No}';
		const keyword = '${requestScope.keyword}';
		$(function() {
			getSearchList(); // 처음에 초기화

			$(window).scroll(function() {
				let $window = $(this);
				let scrollTop = $window.scrollTop();
				let windowHeight = $window.height();
				let documentHeight = $(document).height();

				if (searchFlag) {
					if (scrollTop + windowHeight + 30 > documentHeight) {
						getSearchList();
					}
				}
			}); // scroll End
		}); // ready End

		function getSearchList() {
			let param = {};
			pageNo++;
			param['pageNo'] = pageNo;

			if (cate1No != '') {
				param['cate1No'] = cate1No;
			}
			if (cate2No != '') {
				param['cate2No'] = cate2No;
			}
			if (keyword != '') {
				param['keyword'] = keyword;
			}

			$.ajax({
				url : 'getsearchlist',
				type : 'get',
				data : param,
				dataType : 'json'
			}).done(
					function(data) {
						if (data.pageSize < 20) { // 검색결과가 20보다 작다면 다음 페이지 정보는 존재하지 않음
							searchFlag = false;
							console.log(searchFlag);
						}
						let $resultArea = $('#searchresult');
						console.log(data);
						
						$.each(data.cList, function(index,item){
							let $span = $('<span>');
							let imgPath = '${ctxPath}/getthumbnail?fileNo=' + item.fileNo + '&width=200&height=200';
							$('<img>').attr('src',imgPath).appendTo($span);
							$span.append(item.mbNickName + '<br>');
							$span.append(item.clsIntro);
							$span.on('click',function(evt){
								location.href = '${ctxPath}/classinfo?clsNo=' + item.clsNo;
							});
							$span.appendTo($resultArea);
						});
						
						
					}); // ajax End
		} // getSearchList End
	</script>
</body>
</html>