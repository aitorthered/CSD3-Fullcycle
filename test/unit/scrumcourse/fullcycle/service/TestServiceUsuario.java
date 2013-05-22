package unit.scrumcourse.fullcycle.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import scrumcourse.fullcycle.dao.UserDao;
import scrumcourse.fullcycle.entity.User;
import scrumcourse.fullcycle.exception.DaoException;
import scrumcourse.fullcycle.service.ServiceUsuario;

public class TestServiceUsuario {

	// @Test
	// public void testCanLogin() {
	// fail("Not yet implemented");
	// }

	/**
	 * Requisitos de inser test: + Si inserto algún campo *required(UserId,
	 * nombre, mail, password) vacio, devuelve false - Si inserto un usuario no
	 * existente, que devuelva true - Si inserto un user que ya existe(userId),
	 * que devuelva false
	 */
	@Test
	public void testInserUserWithSomeFieldEmpty() {
		ServiceUsuario su = new ServiceUsuario();
		assertFalse(su.insertUser("", "Nombre", "mail@test.com", "password"));
		assertFalse(su.insertUser("userId", "", "mail@test.com", "password"));
		assertFalse(su.insertUser("userId", "Nombre", "", "password"));
	}

	@Test
	public void testWhenNonExistingUserCallInsertDaoReturnsTrue() {
		UserDao mockedUserDao = mock(UserDao.class);
		ServiceUsuario su = new ServiceUsuario();
		su.setUserDao(mockedUserDao);
		assertTrue(su.insertUser("userId", "Nombre", "mail@test.com",
				"password"));
	}

	@Test
	public void testWhenNonExistingUserCallInsertDao() {
		UserDao mockedUserDao = mock(UserDao.class);

		ServiceUsuario su = new ServiceUsuario();
		su.setUserDao(mockedUserDao);
		assertTrue(su.insertUser("userId", "Nombre", "mail@test.com",
				"password"));
		verify(mockedUserDao, times(1)).insert((User) any());
	}

	@Test
	public void testInsertExistingUserWithDao() {
		UserDao mockedUserDao = mock(UserDao.class);
		doThrow(new DaoException()).when(mockedUserDao).insert((User) any());
		ServiceUsuario su = new ServiceUsuario();
		su.setUserDao(mockedUserDao);

		assertEquals(false,
				su.insertUser("userId", "Nombre", "mail@test.com", "password"));
		verify(mockedUserDao).insert((User) any());
	}

}
