package service;

import java.util.List;

import javax.persistence.Persistence;

import dao.UserDao;
import model.User;

public class UserService {
	private UserDao userDao;

	public UserService() {
		try {
			userDao = new UserDao(Persistence.createEntityManagerFactory("Cinema"));
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void addUser(User newUser) {
		userDao.create(newUser);
	}

	public void updateUser(User updatedUser) {
		userDao.update(updatedUser);
	}

	public List<User> getAllUsers() {
		return userDao.findAll();
	}

	public User findUser(String user, String pass) throws Exception {
		List<User> users = userDao.find(user);
		if (users.size() == 0) {
			throw new Exception("User not found!");
		}
		User u = users.get(0);
		if (pass.compareTo(u.getUserPassword()) != 0) {
			throw new Exception("Wrong password");
		}
		return u;
	}
	
	public User findIfExist (String userName) {
		return userDao.findUser(userName);
	}
}
