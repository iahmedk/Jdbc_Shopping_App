package com.ty.shopping.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ty.shopping.dto.UserDTO;
import com.ty.shopping.util.AES;
import static com.ty.shopping.util.ApplicationConstants.SECRETE_KEY;
import com.ty.shopping.util.SingletonConnection;

public class UserDAO {

	public int saveUser(UserDTO user) {

		Connection con = SingletonConnection.getConnection();
		String sql = "insert into user values(?,?,?,?,?)";

		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, user.getId());
			preparedStatement.setString(2, user.getName());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.setLong(5, user.getMobile());
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}

	public UserDTO getUserById(int id) {
		String sql = "Select *from user where id = ?";

		Connection con = SingletonConnection.getConnection();
		PreparedStatement preparedStatement;
		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				while (resultSet.next()) {
					UserDTO dto = new UserDTO();
					dto.setId(resultSet.getInt(1));
					dto.setName(resultSet.getString(2));
					dto.setEmail(resultSet.getString(3));
					dto.setMobile(resultSet.getLong(5));
					return dto;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public List<UserDTO> getAllUser() {

		String sql = "Select *from user";
		Connection con = SingletonConnection.getConnection();
		List<UserDTO> users = new ArrayList<UserDTO>();

		try {
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				UserDTO dto = new UserDTO();
				dto.setId(resultSet.getInt(1));
				dto.setName(resultSet.getString(2));
				dto.setEmail(resultSet.getString(3));
				dto.setMobile(resultSet.getLong(5));
				users.add(dto);
			}
			return users;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public int updateUser(int id, UserDTO user) {
		return 0;
	}

	public UserDTO validateUser(String email, String password) {

		String sql = "Select *from user where email = ? and password = ?";
		Connection con = SingletonConnection.getConnection();

		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, email);
			String enc = AES.encrypt(password, SECRETE_KEY);
			preparedStatement.setString(2, enc);
			ResultSet res = preparedStatement.executeQuery();

			if (res.next()) {
				UserDTO dto = new UserDTO();
				dto.setId(res.getInt(1));
				dto.setName(res.getString(2));
				dto.setEmail(res.getString(3));
				dto.setMobile(res.getLong(5));
				dto.setPassword(res.getString(4));
				return dto;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
