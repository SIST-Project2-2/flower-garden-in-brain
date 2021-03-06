package dao;

import java.util.ArrayList;
import concert.Commons;
import vo.CommentVO;


public class CommentDAO extends DAO{
	int commentPerPage = 10; // 댓글 목록 한 페이지 당 보이는 댓글 수
	String listCommonStart = " select no, artist, id, content, report, recommend, wdate "
			+ " from (select rownum as rno, no, artist, id, content, report, recommend, wdate "
			+ " 	from (select no, artist, id, content, report, recommend, wdate "
			+ "			from comments "; // 댓글 리스트 출력 sql문 앞부분 공통
	String listCommonEnd = " no desc) where rownum <= ? * ? ) where rno > ? * (? - 1) "; // 댓글 리스트 출력 sql문 뒷부분 공통
	String countCommon = " select count(*) from comments "; // 댓글 수 출력 sql문 공통
	
	
	
	
	
	//추천 수 1증가
	public int like(int no) {
		String sql = "UPDATE COMMENTS SET RECOMMEND = RECOMMEND + 1 WHERE NO=?";
		getPreparedStatement(sql);
		try {
			pstmt.setInt(1, no);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	//댓글 신고하기
	public int report(int no) {
		String sql = "UPDATE COMMENTS SET REPORT = REPORT + 1 WHERE NO=?";
		getPreparedStatement(sql);
		try {
			pstmt.setInt(1, no);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	//해당 댓글 삭제
	public int delete(int no) {
		String sql = "DELETE FROM COMMENTS WHERE NO=?";
		getPreparedStatement(sql);
		try {
			pstmt.setInt(1, no);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	
	//댓글쓴 사람 id 값 가져오기 오버로딩
	public String getUserId(int no) {
		String sql = "SELECT ID FROM COMMENTS WHERE NO = ? ";
		getPreparedStatement(sql);
		
		try {
			pstmt.setInt(1, no);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//댓글쓴 사람 신고 값 가져오기
	public int getReport(int no) {
		String sql = "SELECT REPORT FROM COMMENTS WHERE NO = ? ";
		getPreparedStatement(sql);
		
		try {
			pstmt.setInt(1, no);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	//댓글쓴 사람 추천수 값 가져오기
	public int getRecommend(int no) {
		String sql = "SELECT RECOMMEND FROM COMMENTS WHERE NO = ? ";
		getPreparedStatement(sql);
		
		try {
			pstmt.setInt(1, no);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	

	
	//댓글 저장
	public int saveReply(CommentVO vo) {
		int result = -2;
		try {
			String sql = "INSERT INTO COMMENTS VALUES(COMMENTS_NO_SEQ.NEXTVAL,?,?,?,SYSDATE,0,0)";
			getPreparedStatement(sql);

			pstmt.setString(1, vo.getArtist());
			pstmt.setString(2, vo.getId());
			pstmt.setString(3, vo.getContent());
			

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		close();
		return result;
	}

	//댓글 전체리스트 출력
	public ArrayList<CommentVO> getList() {
		ArrayList<CommentVO> list = new ArrayList<CommentVO>();

		try {
			String sql = "SELECT ARTIST,ID,CONTENT,TO_CHAR(WDATE, 'YYYY/MM/DD'),REPORT,RECOMMEND FROM (SELECT * FROM COMMENTS ORDER BY WDATE DESC)";
			getPreparedStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				CommentVO vo = new CommentVO();
				vo.setArtist(rs.getString(1));
				vo.setId(rs.getString(2));
				vo.setContent(rs.getString(3));
				vo.setDate(rs.getString(4));

				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		close();
		return list;
	}
	
	//댓글 페이징
	public ArrayList<CommentVO> getListPage(int first, int last) {
		ArrayList<CommentVO> list = new ArrayList<CommentVO>();

		try {
			String sql = "SELECT*FROM(SELECT ROWNUM NUM, A.* FROM (SELECT NO,ARTIST,ID,CONTENT,TO_CHAR(WDATE, 'YYYY/MM/DD'),REPORT,RECOMMEND " + 
						" FROM (SELECT * FROM COMMENTS ORDER BY WDATE DESC)) A)WHERE NUM BETWEEN ? AND ?";
			getPreparedStatement(sql);

			pstmt.setInt(1, first);
			pstmt.setInt(2, last);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CommentVO vo = new CommentVO();
				
				rs.getInt(1);
				vo.setNo(rs.getInt(2));
				vo.setArtist(rs.getString(3));
				vo.setId(rs.getString(4));
				vo.setContent(rs.getString(5));
				vo.setDate(rs.getString(6));
				vo.setReport(rs.getInt(7));
				vo.setRecommend(rs.getInt(8));

				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		close();
		return list;
	}
	
	
	
	
	
	
	// 댓글 삭제
	public boolean deleteComment(int no) {
		int result = 0;
		
		try {
			String sql = " DELETE FROM COMMENTS "
					+ " WHERE NO = ? ";
			
			getPreparedStatement(sql);
			
			pstmt.setInt(1, no);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 1개 데이터가 삭제된 경우 true 반환, 아닌 경우 false 반환
		if(result == 1) {
			return true;
		}else {
			return false;
		}
	}
	
	// 목록 불러오기 실행 함수 - 기본
	private ArrayList<CommentVO> executeSelectList(String sql, int page) {
		ArrayList<CommentVO> list = new ArrayList<CommentVO>();
		CommentVO comment = null;
		
		try {
			getPreparedStatement(sql);
			
			pstmt.setInt(1, commentPerPage);
			pstmt.setInt(2, page);
			pstmt.setInt(3, commentPerPage);
			pstmt.setInt(4, page);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				comment = new CommentVO();
				
				comment.setNo(rs.getInt(1));
				comment.setArtist(rs.getString(2));
				comment.setId(rs.getString(3));
				comment.setContent(rs.getString(4));
				comment.setReport(rs.getInt(5));
				comment.setRecommend(rs.getInt(6));
				comment.setDate(rs.getString(7));
				
				list.add(comment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 목록 불러오기 실행 함수 - 아티스트별 또는 검색
	private ArrayList<CommentVO> executeSelectList(String sql, int page, String str) {
		ArrayList<CommentVO> list = new ArrayList<CommentVO>();
		CommentVO comment = null;
		
		try {
			getPreparedStatement(sql);
			
			pstmt.setString(1, str);
			pstmt.setInt(2, commentPerPage);
			pstmt.setInt(3, page);
			pstmt.setInt(4, commentPerPage);
			pstmt.setInt(5, page);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				comment = new CommentVO();
				
				comment.setNo(rs.getInt(1));
				comment.setArtist(rs.getString(2));
				comment.setId(rs.getString(3));
				comment.setContent(rs.getString(4));
				comment.setReport(rs.getInt(5));
				comment.setRecommend(rs.getInt(6));
				comment.setDate(rs.getString(7));
				
				list.add(comment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 목록 불러오기 실행 함수 - 아티스트별 검색
	private ArrayList<CommentVO> executeSelectList(String sql, int page, String artist, String search) {
		ArrayList<CommentVO> list = new ArrayList<CommentVO>();
		CommentVO comment = null;
		
		try {
			getPreparedStatement(sql);
			
			pstmt.setString(1, artist);
			pstmt.setString(2, Commons.s_string(search));
			pstmt.setInt(3, commentPerPage);
			pstmt.setInt(4, page);
			pstmt.setInt(5, commentPerPage);
			pstmt.setInt(6, page);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				comment = new CommentVO();
				
				comment.setNo(rs.getInt(1));
				comment.setArtist(rs.getString(2));
				comment.setId(rs.getString(3));
				comment.setContent(rs.getString(4));
				comment.setReport(rs.getInt(5));
				comment.setRecommend(rs.getInt(6));
				comment.setDate(rs.getString(7));
				
				list.add(comment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 댓글 목록 출력 - 기본
	public ArrayList<CommentVO> getCommentList(int page, int order) {
		String sql = listCommonStart + " order by ";
		
		// order가 0일 경우 최신순, 1일 경우 신고 내림차순, 2일 경우 신고 오름차순
		if(order == 1) {
			sql += " report desc, ";
		}
				
		sql +=  listCommonEnd;
		
		return executeSelectList(sql, page);
	}
	
	// 댓글 목록 출력 - 아티스트별
	public ArrayList<CommentVO> getCommentList(int page, int order, String artist) {
		String sql = listCommonStart + " where artist = ? order by ";
		
		// order가 0일 경우 최신순, 1일 경우 신고 내림차순, 2일 경우 신고 오름차순
		if(order == 1) {
			sql += " report desc, ";
		}
		
		sql += listCommonEnd;
		
		return executeSelectList(sql, page, artist);
	}
	
	// 댓글 목록 출력 - 검색
	public ArrayList<CommentVO> getCommentListSearch(int page, int order, String category, String search) {
		String sql = listCommonStart + " where " + category + " like(?) order by ";
		
		// order = 1일 경우 신고 순 정렬 추가
		if(order == 1) {
			sql += "report desc, ";
		}
		
		sql += listCommonEnd;
		
		return executeSelectList(sql, page, Commons.s_string(search));
	}
	
	// 댓글 목록 출력 - 아티스트별 검색
	public ArrayList<CommentVO> getCommentListSearch(int page, int order, String category, String artist, String search) {
		String sql = listCommonStart + " where artist = ? and " + category + " like(?) order by ";
		
		// order가 0일 경우 최신순, 1일 경우 신고 내림차순, 2일 경우 신고 오름차순
		if(order == 1) {
			sql += " report desc, ";
		}
		
		sql += listCommonEnd;
		
		return executeSelectList(sql, page, artist, Commons.s_string(search));
	}
	
	// 댓글 수 구하기 - 기본
	private int executeCount(String sql) {
		int count = 0;
		
		try {
			getPreparedStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	// 댓글 수 구하기 - 아티스트별 또는 검색
	private int executeCount(String sql, String str) {
		int count = 0;
		
		try {
			getPreparedStatement(sql);
			
			pstmt.setString(1, str);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	// 댓글 수 구하기 - 아티스트별 검색
	private int executeCount(String sql, String artist, String search) {
		int count = 0;
		
		try {
			getPreparedStatement(sql);
			
			pstmt.setString(1, artist);
			pstmt.setString(2, search);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	// 페이지 정보 출력 - 기본
	public int getCount(int nowPage) {
		String sql = countCommon;
		
		int count = executeCount(sql);
		
		return count;
	}
	
	// 페이지 정보 출력 - 기본
	public int getCount() {
		String sql = countCommon;
		
		int count = executeCount(sql);
		
		return count;
	}
	
	// 페이지 정보 출력 - 아티스트별
	public int getCount(int nowPage, String artist) {
		String sql = countCommon + " where artist = ? ";
		
		int count = executeCount(sql, artist);
		
		return count;
	}
	
	// 페이지 정보 출력 - 검색
	public int getCountSearch(int nowPage, String category, String search) {
		String sql = countCommon + " where " + category + " like(?) ";
		
		int count = executeCount(sql, Commons.s_string(search));
		
		return count;
	}
	
	// 페이지 정보 출력 - 아티스트별 검색
	public int getCountSearch(int nowPage, String category, String artist, String search) {
		String sql = countCommon + " where artist = ? and " + category + " like(?) ";
		
		int count = executeCount(sql, artist, Commons.s_string(search));
		
		return count;
	}
}
