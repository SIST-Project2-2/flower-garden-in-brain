<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>id</title>
<script defer src="join.js"></script>
<script>
	$(document).ready(function() {

		$("#ibtn").click(function() {
			$(".idHide").hide();
			$(".passHide").css("display", "block");
		});
		$("#pbtn").click(function() {
			$(".passHide").hide();
			$(".nameHide").css("display", "block");
		})
		$("#nbtn").click(function() {
			$(".nameHide").hide();
			$(".hpHide").css("display", "block");
		})
		$("#hbtn").click(function() {
			$(".hpHide").hide();
			$(".birthHide").css("display", "block");
		})
		$("#bbtn").click(function() {
			$(".birthHide").hide();
			$(".profileHide").css("display", "block");
		})
	});
</script>
<link rel="stylesheet" href="join.css">
</head>
<body>
	<!-- header -->
	<jsp:include page="../header.jsp"></jsp:include>

	<form name="joinForm" action="#" method="GET">
		<div class="idHide">
			<div class="joinID">
				<h1>가입하기</h1>
				<label for="file"></label>
				<progress id="file" value="20" max="100"> 15% </progress>
				<div>로그인에 사용할 아이디를 입력해주세요</div>
				<input type="text" name="id" id="id" class="form-control" placeholder="아이디 입력" required>
				<button type="submit" id="ibtn">다음</button>
			</div>
		</div>


		<div class="passHide" style="display: none;">
			<div class="joinID">
				<h1>가입하기</h1>
				<label for="file"></label>
				<progress id="file" value="40" max="100"> 30% </progress>
				<div>로그인에 사용할 비밀번호를 입력해주세요</div>
				<input type="password" name="pass" id="pass" placeholder="비밀번호 입력" required>
				<input type="password" name="cpass" id="cpass" onblur="passCheck()" placeholder="비밀번호 확인" required>
				<div id="msg"></div>
				<button type="submit" id="pbtn">다음</button>
			</div>
		</div>

		<div class="nameHide" style="display: none;">
			<div class="joinID">
				<h1>가입하기</h1>
				<label for="file"></label>
				<progress id="file" value="60" max="100"> 60% </progress>
				<div>
					<div>이름과 주소를 입력해주세요</div>
					<input type="text" name="name" id="name" required placeholder="이름 입력">
					<input type="text" name="addr" id="addr" required placeholder="주소입력">
					<input type="text" name="daddr" id="daddr" required placeholder="상세주소입력">
				</div>
				<button type="submit" id="nbtn">다음</button>
			</div>
		</div>

		<div class="hpHide" style="display: none;">
			<div class="joinID">
				<h1>가입하기</h1>
				<label for="file"></label>
				<progress id="file" value="80" max="100"> 80% </progress>
				<div>휴대폰번호를 입력해주세요</div>
				<div class="hp">
					<input type="text" name="hp1" id="hp1" maxlength="3" placeholder="번호입력" required>
					-
					<input type="text" name="hp2" id="hp2" maxlength="4" required>
					-
					<input type="text" name="hp3" id="hp3" maxlength="4" required>
				</div>
				<button type="submit" id="hbtn">다음</button>
			</div>
		</div>

		<div class="birthHide" style="display: none;">
			<div class="joinID">
				<h1>가입하기</h1>
				<label for="file"></label>
				<progress id="file" value="90" max="100"> 90% </progress>
				<div>생년월일을 입력해주세요</div>
				<input type="text" name="birth" placeholder="생년월일 입력(YYMMDD)" required>
				<button type="submit" id="bbtn">다음</button>
			</div>
		</div>

		<div class="profileHide" style="display: none;">
			<div class="joinID">
				<h1>가입하기</h1>
				<label for="file"></label>
				<progress id="profile" value="100" max="100"> 100% </progress>
				<div>프로필 사진을 등록해주세요</div>
				<input type="file" name="profile" id="profile">
				<button type="submit" onclick="home()">다음</button>
			</div>
		</div>
	</form>
</body>
</html>