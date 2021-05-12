/**
 * search.jsp에서 사용
 */

// 전역변수 설정
let searchFlag = true; // 페이징 여부
let pageNo = 0;
let pageSize = 20;
let ctxPath;
let cate1No;
let cate2No;
let keyword;

function searchReady(params) {
    // 외부 값 초기화
    ctxPath = params.ctxPath;
    cate1No = params.cate1No;
    cate2No = params.cate2No;
    keyword = params.keyword;

    // 처음 로딩시 검색 한번 실행
    getSearchList();

    // 스크롤 페이징 설정
    $(window).scroll(function () {
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
} // searchReady End

function getSearchList() {
    searchFlag = false;
    pageNo++;

    let param = {};
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
        url: 'getsearchlist',
        type: 'get',
        data: param,
        dataType: 'json'
    }).done(function (data) {
        let $clsList = $('#cls-list');
        console.log(data);

        $.each(data.cList, function (index, item) {
            let $clsItem = $('<div>', {
                class: 'cls-item'
            }); // 클래스 정보 영역	
            // 클래스 이미지 추가
            // 이미지를 200x200으로 설정
            let imgPath = ctxPath + '/getthumbnail?fileNo=' + item.fileNo + '&width=200&height=150';
            let $imgDiv = $('<div>').appendTo($clsItem);
            $('<img>', {
                class: 'cls-image',
                width: '200',
                height: '150'
            }).attr('src', imgPath).appendTo($imgDiv); // 이미지 삽입
            // 클래스 닉네임 추가
            $('<div>', {
                class: 'cls-nickname text_limit', style: 'width:180px;'
            }).append(item.mbNickName).appendTo($clsItem);
            // 클래스 소개 추가
            $('<div>', {
                class: 'cls-intro text_limit', style: 'width:180px;'
            }).append(item.clsName).appendTo($clsItem);
            // 클래스 항목을 눌렀을 때 클래스 페이지로 이동
            $clsItem.on('click', function (evt) {
                location.href = ctxPath + '/classinfo?clsNo=' + item.clsNo;
            }); // on End
            $clsItem.appendTo($clsList);
        }); // each End					
        

        if (data.searchSize == pageSize) { // 검색결과가 20보다 작다면 다음 페이지 정보는 존재하지 않음
            searchFlag = true;
        }
        console.log('searchFlag : ', searchFlag); // 나중에 주석처리
    }); // ajax End
} // getSearchList End