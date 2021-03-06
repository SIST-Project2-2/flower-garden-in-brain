package dao;

import java.lang.reflect.Field;
import java.util.ArrayList;

import vo.ConcertVO;

// 반환형이 int인 경우 성공하면 1, 성공 못하면 0, SQL 에러나면 -1, 자바에서 에러나면 -2
public class ConcertDAO extends DAO {

	// 월별 목록 조회
	public ArrayList<ConcertVO> getConcertListByDate(int year, int month) { // year: 년도, month: 월
		ArrayList<ConcertVO> list = new ArrayList<ConcertVO>();
		ConcertVO vo = null;
		int next_year = month != 12? year : year + 1;
		int next_month = month != 12? month + 1 : 1;
		
		try {
			String sql = " select no, title, to_char(cdate, 'MM/DD'), location, artist "
					+ " from concerts "
					+ " where cdate >= to_date(?, 'YYYYMM') and cdate < to_date(?, 'YYYYMM') "
					+ " order by cdate asc ";
			getPreparedStatement(sql);
			
			pstmt.setString(1, String.valueOf(year) + String.valueOf(month));
			pstmt.setString(2, String.valueOf(next_year) + String.valueOf(next_month));
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				vo = new ConcertVO();
				
				vo.setNo(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setCdate(rs.getString(3));
				vo.setLocation(rs.getString(4));
				vo.setArtist(rs.getString(5));
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 콘서트 조회 메소드
	public ConcertVO getConcertInfo(int no) {
		ConcertVO vo = null;
		try {
			String sql = "SELECT NO, ARTIST, TITLE, CONTENT, TO_CHAR(CDATE, 'YYYY-MM-DD'), LOCATION, PRICE FROM CONCERTS WHERE NO = ?";
			getPreparedStatement(sql);

			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo = new ConcertVO();
				vo.setNo(rs.getInt(1));
				vo.setArtist(rs.getString(2));
				vo.setTitle(rs.getString(3));
				vo.setContent(rs.getString(4));
				vo.setCdate(rs.getString(5));
				vo.setLocation(rs.getString(6));
				vo.setPrice(rs.getInt(7));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}

	public ConcertVO getConcertInfo(String no) {
		ConcertVO vo = null;
		try {
			String sql = "SELECT NO, ARTIST, TITLE, CONTENT, TO_CHAR(CDATE, 'YYYY-MM-DD'), LOCATION, PRICE FROM CONCERTS WHERE NO = ?";
			getPreparedStatement(sql);

			pstmt.setString(1, no);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo = new ConcertVO();
				vo.setNo(rs.getInt(1));
				vo.setArtist(rs.getString(2));
				vo.setTitle(rs.getString(3));
				vo.setContent(rs.getString(4));
				vo.setCdate(rs.getString(5));
				vo.setLocation(rs.getString(6));
				vo.setPrice(rs.getInt(7));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		close();
		return vo;
	}

	// 콘서트 삭제 메소드
	public int deleteConcert(ConcertVO concert) {
		int result = -2;
		try {
			String sql = "DELETE FROM CONCERTS WHERE NO = ?";
			getPreparedStatement(sql);

			pstmt.setInt(1, concert.getNo());

//			System.out.println("삭제대상: " + concert.getNo());
			// 성공하면 1, 성공 못하면 0, SQL 에러나면 -1, 자바에서 에러나면 -2
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		close();
		return result;
	}

	// 조건에 해당하는 sql 구문 자동생성 메소드
	public String getSql(int page_no, int page_size, ConcertVO search_target) {
		String sql = null;
		String sql_where = ""; // 검색 조건 설정하는 WHERE 구문
		boolean sql_whereHasCreated = false;

		try {
			if (search_target != null) { // 검색 조건이 존재하면 실행. 존재하지 않으면 null
				Field[] fields = search_target.getClass().getDeclaredFields(); // 해당 인스턴스에 선언된 필드 목록
				for (Field field : fields) {
					field.setAccessible(true); // private 설정된 필드도 접근 가능하도록 설정
					if (field.get(search_target) != null) { // 해당 필드가 null 이 아니면 실행
//						System.out.println(field.getName() + ": " + field.get(search_target));
						if ((field.getName().equals("no") || field.getName().equals("price")) && (int) field.get(search_target) == -1) { // no 필드는 int 타입이므로 없으면 null이 아닌 -1을 저장
							continue;
						}
						if (sql_where.equals("")) { // 해당 필드에 값이 존재하고 WHERE 구문이 존재하지 않으면 WHERE 생성
							sql_where += " WHERE ";
						}
						if (sql_whereHasCreated) { // WHERE 구문에 조건을 추가하는게 첫번째가 아니면 OR 추가
							sql_where += "OR ";
						}
						// 해당 필드가 존재하면 WHERE 구문에 "속성명 LIKE('%속성값%')" 을 추가함
						sql_where += "LOWER(" + field.getName() + ") LIKE(LOWER('%" + field.get(search_target) + "%')) ";
						sql_whereHasCreated = true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		sql = "SELECT * FROM (SELECT ROWNUM AS RNO, NO, ARTIST, TITLE, CONTENT, TO_CHAR(CDATE, 'YYYY-MM-DD'), LOCATION, PRICE FROM (SELECT * FROM CONCERTS " + sql_where + " ORDER BY CDATE DESC) C)";
		if (page_no != 0) {
			sql += " WHERE RNO > ? * (? - 1) AND RNO <= ? * ?";
		}
//		System.out.println("만들어진 SQL 구문: " + sql);
		return sql;
	}

	// 등록된 콘서트 개수 조회하기
	public int count_concerts() {
		int result = -2;
		try {
			String sql = "SELECT COUNT(*) FROM CONCERTS";
			getPreparedStatement(sql);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			} else {
				result = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	// 검색 결과에 해당하는 콘서트 개수 조회하기
	public int count_concerts(ConcertVO search_target) {
		int result = -2;
		try {
			String sql = "SELECT COUNT(*) FROM (" + getSql(0, 0, search_target) + ")";
			getPreparedStatement(sql);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			} else {
				result = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

//		System.out.println("총 게시글 수: " + result);
		return result;
	}

	// 콘서트 목록 페이지 수 조회
	public int count_concert_pages(int page_size, ConcertVO search_target) {
		return (int) Math.ceil(count_concerts(search_target) / (double) page_size);
	}

	// 콘서트 특정 페이지 조회
	public ArrayList<ConcertVO> get_concert_list(int page_no, int page_size) {
		ArrayList<ConcertVO> concert_list = new ArrayList<ConcertVO>();

		try {
			String sql = "SELECT * FROM (SELECT ROWNUM AS RNO, NO, ARTIST, TITLE, CONTENT, TO_CHAR(CDATE, 'YYYY-MM-DD'), LOCATION FROM (SELECT * FROM CONCERTS ORDER BY CDATE DESC) C) WHERE RNO > ? * (? - 1) AND RNO <= ? * ?";
			getPreparedStatement(sql);

			pstmt.setInt(1, page_size);
			pstmt.setInt(2, page_no);
			pstmt.setInt(3, page_size);
			pstmt.setInt(4, page_no);

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

	// 콘서트 검색 결과 중 특정 페이지 조회
	public ArrayList<ConcertVO> get_concert_search_list(int page_no, int page_size, ConcertVO search_target) {
		ArrayList<ConcertVO> concert_list = new ArrayList<ConcertVO>();

		try {
			String sql = getSql(page_no, page_size, search_target);
			getPreparedStatement(sql);

			pstmt.setInt(1, page_size);
			pstmt.setInt(2, page_no);
			pstmt.setInt(3, page_size);
			pstmt.setInt(4, page_no);

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

	// 콘서트 등록 번호 구하기
	private int getNextConcertNo() {
		int result = -2;
		try {
			String sql = "SELECT CONCERTS_NO_SEQ.NEXTVAL FROM DUAL";
			getPreparedStatement(sql);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 콘서트 등록
	public int insert_concert(ConcertVO concert) {
		int result = -2;

		int no = getNextConcertNo();

		try {
			String sql = "INSERT INTO CONCERTS(NO, ARTIST, TITLE, CONTENT, CDATE, LOCATION, PRICE) VALUES(?, ?, ?, ?, ?, ?, ?)";
			getPreparedStatement(sql);

			pstmt.setInt(1, no);
			pstmt.setString(2, concert.getArtist());
			pstmt.setString(3, concert.getTitle());
			pstmt.setString(4, concert.getContent());
			pstmt.setString(5, concert.getCdate());
			pstmt.setString(6, concert.getLocation());
			pstmt.setInt(7, concert.getPrice());

			// 성공하면 1, 성공 못하면 0, SQL 에러나면 -1, 자바에서 에러나면 -2
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		close();

		return result;
	}

	// 콘서트 수정
	public boolean edit_concert(String no, ConcertVO concert) {

		boolean result = false;

		try {
			String sql = "update concerts set artist=?, title=?, content=?, cdate=?, location=? where no=?";
			getPreparedStatement(sql);

			pstmt.setString(1, concert.getArtist());
			pstmt.setString(2, concert.getTitle());
			pstmt.setString(3, concert.getContent());
			pstmt.setString(4, concert.getCdate());
			pstmt.setString(5, concert.getLocation());
			pstmt.setString(6, no);
			// 성공하면 1, 성공 못하면 0, SQL 에러나면 -1, 자바에서 에러나면 -2

			int value = pstmt.executeUpdate();

			if (value != 0) {
				result = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		close();
		return result;
	}

}
