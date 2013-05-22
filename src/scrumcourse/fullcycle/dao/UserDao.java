package scrumcourse.fullcycle.dao;

import scrumcourse.fullcycle.entity.User;
import scrumcourse.fullcycle.exception.DaoException;

public interface UserDao extends Dao<User, String> {

	boolean isValidPassword(User user, String password);

	void setPassword(User user, String password);

	User findByEmail(String email) throws DaoException;
}
