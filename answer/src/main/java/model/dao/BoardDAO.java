package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.util.JDBCUtil2;
import model.vo.BoardVO;

public class BoardDAO {
	Connection conn;
	PreparedStatement pstmt;
	final String sql_selectOne="SELECT * FROM BOARD WHERE BID=?";
	final String sql_selectAll="SELECT * FROM BOARD ORDER BY BID DESC";
	final String sql_insert="INSERT INTO BOARD (TITLE,CONTENT,WRITER) VALUES(?,?,?)";
		// INSERT INTO BOARD VALUES((서브쿼리),?,?,?)
	final String sql_update="UPDATE BOARD SET TITLE=?,CONTENT=? WHERE BID=?";
	final String sql_delete="DELETE FROM BOARD WHERE BID=?";
	public BoardVO selectOne(BoardVO vo) {
		System.out.println("BoardDAO의 로그: "+vo);
		conn=JDBCUtil2.connect();
		try {
			pstmt=conn.prepareStatement(sql_selectOne);
			pstmt.setInt(1, vo.getBid());
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				BoardVO data=new BoardVO();
				data.setBid(rs.getInt("BID"));
				data.setContent(rs.getString("CONTENT"));
				data.setTitle(rs.getString("TITLE"));
				data.setWriter(rs.getString("WRITER"));
				return data;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil2.disconnect(pstmt, conn);
		}		
		return null;
	}
	public ArrayList<BoardVO> selectAll(BoardVO vo){
		ArrayList<BoardVO> datas=new ArrayList<BoardVO>();
		conn=JDBCUtil2.connect();
		try {
			pstmt=conn.prepareStatement(sql_selectAll);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				BoardVO data=new BoardVO();
				data.setBid(rs.getInt("BID"));
				data.setContent(rs.getString("CONTENT"));
				data.setTitle(rs.getString("TITLE"));
				data.setWriter(rs.getString("WRITER"));
				datas.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil2.disconnect(pstmt, conn);
		}		
		return datas;
	}
	public boolean insert(BoardVO vo) {
		conn=JDBCUtil2.connect();
		try {
			pstmt=conn.prepareStatement(sql_insert);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getWriter());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil2.disconnect(pstmt, conn);
		}
		return true;
	}
	public boolean update(BoardVO vo) {
		conn=JDBCUtil2.connect();
		try {
			pstmt=conn.prepareStatement(sql_update);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3,vo.getBid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil2.disconnect(pstmt, conn);
		}
		return true;
	}
	public boolean delete(BoardVO vo) {
		conn=JDBCUtil2.connect();
		try {
			pstmt=conn.prepareStatement(sql_delete);
			pstmt.setInt(1,vo.getBid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil2.disconnect(pstmt, conn);
		}
		return true;
	}
}
