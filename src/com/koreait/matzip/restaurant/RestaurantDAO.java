package com.koreait.matzip.restaurant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.koreait.matzip.db.JdbcSelectInterface;
import com.koreait.matzip.db.JdbcTemplate;
import com.koreait.matzip.db.JdbcUpdateInterface;
import com.koreait.matzip.vo.RestaurantDomain;
import com.koreait.matzip.vo.RestaurantVO;

public class RestaurantDAO {
	
	public int insRestaurant(RestaurantVO param) {
		String sql = " INSERT INTO t_restaurant"
				+ " (nm, addr, lat, lng, cd_category, i_user)"
				+ " VALUES"
				+ " (?, ?, ?, ?, ?, ?) ";
		
		return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
			
			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setNString(1, param.getNm());
				ps.setNString(2, param.getAddr());
				ps.setDouble(3, param.getLat());
				ps.setDouble(4, param.getLng());
				ps.setInt(5, param.getCd_category());
				ps.setInt(6, param.getI_user());				
			}
		});
	}
	
	public List<RestaurantDomain> selRestList(){
		List<RestaurantDomain> list = new ArrayList();
		
		String sql = " SELECT i_rest, nm, lat, lng FROM t_restaurant ";
		
		JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {}		
			@Override
			public void executeQuery(ResultSet rs) throws SQLException {
				while(rs.next()) {
					RestaurantDomain vo = new RestaurantDomain();
					vo.setI_rest(rs.getInt("i_rest"));
					vo.setNm(rs.getNString("nm"));
					vo.setLat(rs.getDouble("lat"));
					vo.setLng(rs.getDouble("lng"));
					list.add(vo);
				}			
			}
		});
		
		return list;
	}
	// 디테일
	public RestaurantDomain selRest(RestaurantVO param) {
		RestaurantDomain vo = new RestaurantDomain();
		String sql = " SELECT nm, addr, i_user, A.hits as cntHits " 
				+ " , B.val AS cd_category_nm "
				+ " , IFNULL(C.cnt, 0) AS cntFavorite FROM t_restaurant A " 
				+ " INNER JOIN c_code_d B "
				+ " ON A.cd_category = B.cd " 
				+ " AND B.i_m = 1 " 
				+ " LEFT JOIN( " 
				+ " 	SELECT i_rest, COUNT(i_rest) AS cnt " 
				+ "		FROM t_user_favorite "
				+ " 	WHERE i_rest = ? " 
				+ " 	GROUP BY i_rest "
				+ " )C "
				+ " ON A.i_rest = C.i_rest " 
				+ " WHERE A.i_rest = ? ";
		JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getI_rest());
				ps.setInt(2, param.getI_rest());
			}
			
			@Override
			public void executeQuery(ResultSet rs) throws SQLException {
				if(rs.next()) {
					vo.setI_rest(param.getI_rest());
					vo.setAddr(rs.getNString("addr"));
					vo.setNm(rs.getNString("nm"));
					vo.setCd_category_nm(rs.getNString("cd_category_nm"));
					vo.setI_user(rs.getInt("i_user"));
					vo.setCntHits(rs.getInt("cntHits"));
					vo.setCntFavorite(rs.getInt("cntFavorite"));
				}
			}
		});
		return vo;
		
	}

}
