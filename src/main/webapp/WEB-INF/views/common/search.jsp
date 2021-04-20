<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>검색 페이지</title>
<style type="text/css">
#searchnav {
	height: 75px;
	background-color: #3566A2;
	line-height: 75px;
	font-family: Comfortaa;
	font-style: normal;
	font-weight: normal;
	font-size: 40px;
	color: #FFFFFF;
}
</style>
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<div id="searchnav">${searchInfo!=null?searchInfo:"클래스 검색"}</div>
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
		const cate1No = '${param.cate1No}';
		const cate2No = '${param.cate2No}';
		const keyword = '${param.keyword}';
		$(function() {
			getSearchList(); // 처음에 초기화

			$(window).scroll(function() {
				let $window = $(this);
				let scrollTop = $window.scrollTop();
				let windowHeight = $window.height();
				let documentHeight = $(document).height();

				if (scrollTop + windowHeight + 30 > documentHeight) {
					getSearchList();
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
						if (data.pageSize < 10) {
							// searchFlag = false;
						}
						let word = '카테고리1 : ' + cate1No + '<br>' + '카테고리2 : '
								+ cate2No + '<br>' + '키워드 : ' + keyword
								+ '<br>' + '페이지 : ' + pageNo + '<br>'
								+ '결과수 : ' + data.pageSize + '<br>';

						$('#searchresult').append(
								$('<div>').addClass('classinfo').append(word));
						console.log(data.cList);
					}); // ajax End
		} // getSearchList End
	</script>
</body>
</html>