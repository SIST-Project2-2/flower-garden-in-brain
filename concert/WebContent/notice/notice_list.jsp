<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dao.NoticeDAO" %>
<%@ page import="vo.NoticeVO" %>
<%@ page import="concert.Commons" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("utf-8"); %>
<%
	PrintWriter script = response.getWriter();
	NoticeDAO dao = new NoticeDAO(); // db 연결 객체
	ArrayList<NoticeVO> list = null; // 공지사항 목록
	HashMap<String, Integer> pageInfo = null;
	HashMap<String, String[]> inputs = new HashMap<String, String[]>(request.getParameterMap()); // request 파라미터들을 저장
	String[] categories = {"전체", "제목", "내용"}; // 검색 카테고리 목록
	String[] options = {"전체", "장범준", "잔나비", "10cm", "현아", "IU"}; // 아티스트별 목록 보기 목록
	String url = request.getRequestURL().toString(); // 현 페이지 주소
	
	int noticePerPage = 8;
	int pageNumber = 1;
	int category = 0;
	String artist = "";
	String search = "";
	
	// 요청 변수 저장
	if(request.getParameter("pageNumber") != null) {
		pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
	}
	
	if(request.getParameter("category") != null) {
		category = Integer.parseInt(request.getParameter("category"));
	}
	
	if(request.getParameter("artist") != null && !"전체".equals(request.getParameter("artist"))) {
		artist = request.getParameter("artist");
	}
	
	if(request.getParameter("search") != null) {
		search = request.getParameter("search");
	}
	
	// 목록 불러오기
	if(!artist.equals("") && !search.equals("")) { // 아티스트별 + 검색
		list = dao.getNoticeListForUser(pageNumber, noticePerPage, category, artist, search);
		pageInfo = Commons.getPageInfo(dao.getCount(pageNumber, category, artist, search), pageNumber, noticePerPage);
	}else if(!artist.equals("")) { // 아티스트별
		list = dao.getNoticeListForUser(pageNumber, noticePerPage, artist);
		pageInfo = Commons.getPageInfo(dao.getCount(pageNumber, artist), pageNumber, noticePerPage);
	}else if(!search.equals("")) { // 검색
		list = dao.getNoticeListForUser(pageNumber, noticePerPage, category, search);
		pageInfo = Commons.getPageInfo(dao.getCount(pageNumber, category, search), pageNumber, noticePerPage);
	}else { // 기본
		list = dao.getNoticeListForUser(pageNumber, noticePerPage);
		pageInfo = Commons.getPageInfo(dao.getCount(pageNumber), pageNumber, noticePerPage);
	}
	
	dao.close(); // 데이터를 모두 불러온 뒤 dao 객체 닫기
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 목록</title>
<!-- header -->
<jsp:include page="../header.jsp"></jsp:include>
<script type="text/javascript">
	$(document).ready(function() {
		$("#artist").change(function() {
			location.href = "notice_list.jsp?artist=" + $(this).val();
		})
		
		// 검색 카테고리 클릭시 카테고리 바뀜
		$("#category_all").click(function() {
			$("#category_dropdown").html("전체");
			$("#category").val(0);
		})
		$("#category_title").click(function() {
			$("#category_dropdown").html("제목");
			$("#category").val(1);
		})
		$("#category_content").click(function() {
			$("#category_dropdown").html("내용")
			$("#category").val(2);
		})
		
		$("#btn_search").click(function() {
			notice_list_search_form.submit();
		})
	});
</script>
<style>
	.card {
		width: 250px; height: 420px;
	}
</style>
</head>
<body>
	<section class="container-md text-center" id="content_notice_list">
		<h1 class="font-weight-bold text-left">공지사항</h1>
		<!-- 공지사항 목록 검색 -->
		<form class="row justify-content-end" name="notice_list_search_form" id="notice_list_search_form" action="" method="get">
			<input type="hidden" name="category" id="category" value="<%= category %>">
			<div class="col-md-6 d-block">
				<div class="input-group input-group-sm">
					<div class="input-group-prepend">
						<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="category_dropdown">
    						<%= categories[category] %>
  						</button>
						<div class="dropdown-menu">
							<label class="dropdown-item" id="category_all" value="0">전체</label>
							<label class="dropdown-item" id="category_title" value="1">제목</label>
							<label class="dropdown-item" id="category_content" value="2">내용</label>
						</div>
					</div>
					<input type="text" class="form-control" placeholder="검색..." name="search" id="notice_list_search" value="<%= search %>">
					<div class="input-group-append">
						<button class="btn btn-light" type="button" id="btn_search">검색</button>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<small class="text-dark">가수</small>
				<select class="form-control-sm d-inline-block" name="artist" id="artist">
					<% for(String option : options) { // request로 요청받은 가수를 select한다. %>
					<option value="<%= option %>" <% if(option.equals(artist)) { %>selected <% } %>><%= option %></option>
					<% } %>
				</select>
			</div>
		</form>
		<!-- 공지사항 목록 -->
		<div class="container-md text-left">
			<!-- jsp 코드 -->
			<% if(list.size() == 0) { // 불러온 목록이 비어있을 때 %>
			<p class="text-center text-dark">데이터가 없습니다.</p>
			<% 
				}else {
					for(NoticeVO notice : list) {
			%>
			<a class="d-inline-block mt-3 mr-3" href="notice_info.jsp?no=<%= notice.getNo() %>"><div class="card d-inline-block">
				<img class="card-img-top" src="../images/장범준.jpg">
				<div class="card-body font-weight-bold">
					<%
						String tag = notice.getTag();
					%>
					<kbd><%= tag %></kbd>
					<h4 class="card-title text-left text-black text-truncate"><%= notice.getTitle() %></h4>
					<p class="card-text text-left text-dark text-truncate"><%= notice.getContent() %></p>
					<p class="card-text text-left text-dark d-inline-block"><%= notice.getDate() %></p>
					<small class="card-text text-left text-dark font-weight-bold"><%= "조회수: " + notice.getViews() %></small>
				</div>
			</div></a>
			<%
					}
				}
			%>
		</div>
		<!-- 페이지 이동 버튼 목록 -->
	  	<ul class="pagination justify-content-center mt-3">
		    <!-- 현 페이지가 1페이지일 경우, 이전 페이지 비활성화 -->
			<li class="page-item <% if(pageInfo.get("prev") != 1) { %> disabled <% } %>"><a class="page-link" href="<%= Commons.get_page(url, inputs, pageNumber - 1) %>">&lt;</a></li>
			<% 
		    int start = pageInfo.get("start");
		    int end = pageInfo.get("end");
		    
		    for(int i=start;i<=end;i++) {
			%>
			<li class="page-item <% if(i == pageNumber) { %>active <% } %>"><a class="page-link" href="<%= Commons.get_page(url, inputs, i) %>"><%= i %></a></li>
			<% } %>
		    <!-- 현 페이지가 마지막 페이지일 경우 다음 페이지 비홯성화 -->
			<li class="page-item <% if(pageInfo.get("next") != 1) { %> disabled <% } %>"><a class="page-link" href="<%= Commons.get_page(url, inputs, pageNumber + 1) %>">&gt;</a></li>
	  </ul>
	</section>
</body>
</html>