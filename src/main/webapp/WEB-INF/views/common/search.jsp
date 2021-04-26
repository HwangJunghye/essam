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
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/search.css">
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<%@ include file="../common/nav.jsp"%>
	<section>
		<article id="cls-list">
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
						let $clsList = $('#cls-list');
						console.log(data);
						
						// for-반복문을 이용해 목록을 뻥튀기
						// 나중에 for-반복문 지워야 함
						for(let i = 0; i<20;i++){
						$.each(data.cList, function(index,item){
							let $clsItem = $('<div>',{class:'cls-item'}); // 클래스 정보 영역
							
							// 클래스 이미지 추가
							// 이미지를 200x200으로 설정
							let imgPath = '${ctxPath}/getthumbnail?fileNo=' + item.fileNo + '&width=200&height=150';
							let $imgDiv = $('<div>').appendTo($clsItem); 
							$('<img>',{class:'cls-image',width:'200',height:'150'}).attr('src',imgPath).appendTo($imgDiv); // 이미지 삽입
							
							// 클래스 닉네임 추가
							$('<div>',{class:'cls-nickname'}).append(item.mbNickName).appendTo($clsItem); 
							
							// 클래스 소개 추가
							$('<div>',{class:'cls-intro'}).append(item.clsIntro).appendTo($clsItem);

							// 클래스 항목을 눌렀을 때 클래스 페이지로 이동
							$clsItem.on('click',function(evt){
								location.href = '${ctxPath}/classinfo?clsNo=' + item.clsNo;
							});
							
							$clsItem.appendTo($clsList);
						}); // each End
						} // for-반복문 끝
						
					}); // ajax End
		} // getSearchList End
	</script>
</body> 
</html>