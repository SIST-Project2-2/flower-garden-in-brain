<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.ArtistDAO, vo.ArtistVO, java.util.*"%>
<%
	//
request.setCharacterEncoding("utf-8");
ArtistDAO dao = new ArtistDAO();
ArrayList<ArtistVO> list = dao.getList();
//String name = request.getParameter("name");
%>
<!-- header -->
<jsp:include page="../admin_header.jsp"></jsp:include>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 아티스트 목록</title>
</head>
<body class="admin_artist_list">
	<div class="container bootstrap snippets bootdey">
		<div class="row">
			<div class="post-list">
				<!-- 아티스트 추가 버튼  -->
				<div class="row">
					<div class="col-sm-12">
						<a class="btn btn btn-primary" href="http://localhost:9000/concert/admin/artist/admin_artist_add.jsp">아티스트 추가</a>
					</div>
				</div>
			</div>

			<%
				for (ArtistVO vo : list) {
			%>
			<div class="post-list">
				<div class="row">
					<div class="col-sm-2">
						<div class="picture">
							<img alt=<%=vo.getName()%> src="../../images/<%=vo.getSimg()%>">
						</div>
					</div>
					<div class="col-sm-6">
						<h4>
							<label class="label label-info"><%=vo.getName()%></label>
						</h4>

						<p class="description"><%=vo.getContent()%></p>
					</div>
					<div class="col-sm-4">
						<a class="btn btn btn-secondary" href="admin_artist_update.jsp?name=<%=vo.getName()%>">
							<i class="fa fa-share"></i> 수정
						</a>
						<a class="btn btn btn-danger" href="admin_artist_list_delete_process.jsp?name=<%=vo.getName()%>">
							<i class="fa fa-share"></i> 삭제
						</a>
					</div>
				</div>
			</div>

			<%
				}
			%>

		</div>
	</div>
</body>
</html>