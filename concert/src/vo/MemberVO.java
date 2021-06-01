package vo;

public class MemberVO {

	// Field
	private int no; // 회원번호
	private String id; // 아이디
	private String pw; // 비밀번호
	private String nickname; // 닉네임
	private String first_name; // 이름
	private String last_name; // 성
	private String birth_date; // 생일
	private String sex; // 성별
	private String address; // 주소
	private String phone; // 전화번호
	private String authority; // 권한
	private String withdrawal; // 탈퇴 요청 여부
	private String email; // 이메일 주소

	// Constructor
	public MemberVO(int no, String id, String pw, String nickname, String name, String birth_date, String sex, String address, String phone, String authority, String withdrawal, String email) {
		super();
		this.no = no;
		this.id = id;
		this.pw = pw;
		this.nickname = nickname;
		this.birth_date = birth_date;
		this.sex = sex;
		this.address = address;
		this.phone = phone;
		this.authority = authority;
		this.withdrawal = withdrawal;
		this.email = email;
	}

	// Method
	public MemberVO() {

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getWithdrawal() {
		return withdrawal;
	}

	public void setWithdrawal(String withdrawal) {
		this.withdrawal = withdrawal;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	// Constructor
	public MemberVO(String id, String pw, String nickname, String first_name, String last_name, String birth_date, String sex, String address, String phone, String email) {
		super();

		this.id = id;
		this.pw = pw;
		this.nickname = nickname;
		this.first_name = first_name;
		this.last_name = last_name;
		this.birth_date = birth_date;
		this.sex = sex;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

}
