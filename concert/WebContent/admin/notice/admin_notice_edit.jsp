<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- header -->
<jsp:include page="../../header.jsp"></jsp:include>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="container pt-3">
		<form>
			<div class="clearfix mb-3">
				<h3 class="d-inline-block float-left ">공지사항 수정</h3>
				<button class="btn btn-light float-right">임시저장</button>
				<button class="btn btn-light float-right">등록하기</button>
			</div>
			
			<%--컨텐츠 영역--%>
			<div class="h-100 ">
				<input type="text" class="w-100" placeholder="제목을 입력하세요">
				<div class="h-100">
					<div class="btn-group pt-3">
						<button class="btn btn-secondary btn btn-light float-righ">Text</button>
						<button class="btn btn-secondary btn btn-light float-righ">이미지 삽입</button>
					</div>
					<hr>
					<textarea class="w-100" placeholder="내용을 입력하세요" style="height: 500px;"></textarea>
				</div>
			</div>

		</form>
	</div>

</body>
</html>