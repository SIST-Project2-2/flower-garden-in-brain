package dao;

import java.util.ArrayList;

import vo.ConcertVO;

public class ConcertDAO extends DAO {

	// Field

	// Constructor
	public ConcertDAO() {
		super();
	}

	// Method

	// 등록된 콘서트 개수 조회하기
	public int count_concerts() {
		int result = -2;
		try {
			String sql = "SELECT COUNT(*) FROM CONCERTS";
			getPreparedStatement(sql);

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 콘서트 특정 페이지 조회
	public ArrayList<ConcertVO> get_concert_list(int page_no, int list_size) {
		ArrayList<ConcertVO> concert_list = null;

		try {
			concert_list = new ArrayList<ConcertVO>();
			String sql = "SELECT * FROM ( SELECT ROWNUM AS RNO, C.* FROM CONCERTS C ORDER BY NO DESC) WHERE RNO> " + list_size + " * (? - 1) AND RNO<= " + list_size + " * ?";
			getPreparedStatement(sql);

			pstmt.setInt(1, page_no);
			pstmt.setInt(2, page_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ConcertVO concert = new ConcertVO();
				concert.setNo(rs.getInt(2));
				concert.setArtist(rs.getString(3));
				concert.setTitle(rs.getString(4));
				concert.setContent(rs.getString(5));
				concert.setCdate(rs.getString(6));
				concert.setLocation(rs.getString(7));
				concert_list.add(concert);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return concert_list;
	}

	// 콘서트 등록
}
