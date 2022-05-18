package com.ty.shopping.controller;

import com.ty.shopping.dao.UserDAO;
import com.ty.shopping.dto.UserDTO;
import com.ty.shopping.util.AES;
import static com.ty.shopping.util.ApplicationConstants.SECRETE_KEY;

import java.util.List;

public class UserApp {

	public static void main(String[] args) {
		// addUser();
		// validateUser();
		// searchUser();
		//dispUsers();
	}

	public static void addUser() {
		UserDTO dto = new UserDTO();
		String enc = AES.encrypt("abbu@123", SECRETE_KEY);

		dto.setId(30);
		dto.setName("Aaira");
		dto.setEmail("aaira2019@gmail.com");
		dto.setMobile(9986988436l);
		dto.setPassword(enc);

		UserDAO dao = new UserDAO();
		if (dao.saveUser(dto) > 0)
			System.out.println("Inserted Successfully !!");
	}

	public static void validateUser() {

		UserDAO dao = new UserDAO();
		UserDTO found = dao.validateUser("aaira2019@gmail.com", "abbu@123");
		if (found != null) {
			System.out.println(found);
		} else {
			System.out.println("email or password is wrong !!");
		}
	}

	public static void searchUser() {

		UserDAO dao = new UserDAO();
		UserDTO found = dao.getUserById(10);

		if (found != null) {
			System.out.println(found);
		} else {
			System.out.println("Selected Id user is not found !!");
		}
	}

	public static void dispUsers() {

		UserDAO dao = new UserDAO();
		List<UserDTO> users = dao.getAllUser();

		for (UserDTO dt : users) {
			System.out.println(dt);
		}
	}
}
