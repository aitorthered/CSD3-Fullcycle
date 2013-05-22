package scrumcourse.fullcycle.service;

import scrumcourse.fullcycle.dao.UserDao;
import scrumcourse.fullcycle.entity.User;
import scrumcourse.fullcycle.exception.DaoException;

public class ServiceUsuario {
	UserDao myUserDao;

	// public ServiceUsuario(UserDao _myUserDao) {
	// this.myUserDao = _myUserDao;
	// }

	public void setUserDao(UserDao _myUserDao) {
		this.myUserDao = _myUserDao;
	}

	public boolean canLogin(String user, String password) {
		throw new RuntimeException("NOT IMPLEMENTED YET ServiceLogin.canLogin");
	}

	public boolean insertUser(String userId, String nombre, String correo,
			String password) {
		User myUser = new User();
		if (nombre.equals("") || userId.equals("") || correo.equals("")) {
			return false;
		}
		myUser.setName(nombre);
		myUser.setEmail(correo);
		myUser.setUserId(userId);
		try {
			myUserDao.insert(myUser);
		} catch (DaoException daoe) {
			return false;
		}
		return true;
	}

}
