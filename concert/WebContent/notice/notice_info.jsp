<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- header -->
<jsp:include page="../header.jsp"></jsp:include>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src=""></script>
<!-- js 파일 경로 넣기! -->
</head>
<body>
	<section class="container-md text-center" id="content_notice_info">
		<h1 class="font-weight-bold text-left">공지사항 상세</h1>
		<!-- 공지사항 상세내용 -->
		<table class="table" id="notice_info_table">
			<!-- 제목 -->
			<thead>
				<tr>
					<th class="text-left">
						<div>
							<kbd>일정1</kbd>
							<kbd>일정2</kbd>
							<kbd>일정3</kbd>
						</div>
						<h2 class="text-weight-bold">공지입니다</h2>
						<small>2021.05.21</small>
					</th>
				</tr>
			</thead>
			<!-- 내용 -->
			<tbody>
				<tr>
					<td colspan="4" class="text-left">
						<div class="text-center">
							<img class="img-fluid" src="../images/장범준.jpg">
							<br>
						</div>
						공지입니다~~~
						<br>
						공지입니다~~~
						<br>
						공지입니다~~~
						<br>
						공지입니다~~~
						<br>
						공지입니다~~~
						<br>
					</td>
				</tr>
				<tr>
					<td></td>
				</tr>
			</tbody>
		</table>
		<!-- 
		<div class="row">
			<div class="text-right">
				<button type="button" class="btn btn-primary" id="notice_info_back">목록으로</button>
			</div>
		</div>
		-->
	</section>
</body>
</html>