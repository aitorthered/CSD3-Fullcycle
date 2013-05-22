package unit.scrumcourse.fullcycle.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import scrumcourse.fullcycle.dao.UserDao;
import scrumcourse.fullcycle.entity.User;
import scrumcourse.fullcycle.exception.DaoException;
import scrumcourse.fullcycle.service.ServiceUsuario;

public class TestServiceUsuario {

	ServiceUsuario su;

	@Before
	public void setServiceUsuario() {
		su = new ServiceUsuario();
	}

	@Test
	public void testLoginNotEmpty() {
		assertFalse(su.canLogin("", "password"));
	}

	@Test
	public void testLoginNotNully() {
		assertFalse(su.canLogin(null, "password"));
	}

	@Test
	public void testPasswordNotEmpty() {
		assertFalse(su.canLogin("login", ""));
	}

	@Test
	public void testPasswordNotNull() {
		assertFalse(su.canLogin("login", null));
	}

	@Test
	public void testWhenValidUserAndPasswordThenCallIsValidPasswordInUserDao() {
		UserDao mockedUserDao = mock(UserDao.class);
		when(mockedUserDao.findById(anyString())).thenReturn(new User());
		su.setUserDao(mockedUserDao);
		su.canLogin("login", "password");
		verify(mockedUserDao, times(1)).isValidPassword((User) any(),
				anyString());
	}

	@Test
	public void testWhenValidUserAndPasswordThenCallFindByIdInUserDao() {
		UserDao mockedUserDao = mock(UserDao.class);
		su.setUserDao(mockedUserDao);
		su.canLogin("login", "password");
		verify(mockedUserDao, times(1)).findById(anyString());
	}

	@Test
	public void testWhenValidUserAndPasswordThenCallFindByIdInUserDaoWithCorrectId() {
		UserDao mockedUserDao = mock(UserDao.class);
		su.setUserDao(mockedUserDao);
		su.canLogin("login", "password");
		verify(mockedUserDao, times(1)).findById(eq("login"));
	}

	@Test
	public void testWhenValidUserAndPasswordThenCallIsValidPasswordInUserDaoWithWrightArguments() {
		UserDao mockedUserDao = mock(UserDao.class);
		when(mockedUserDao.findById(anyString())).thenReturn(new User());
		su.setUserDao(mockedUserDao);
		su.canLogin("login", "password");
		verify(mockedUserDao, times(1)).isValidPassword((User) any(),
				eq("password"));
	}

	@Test
	public void testWhenValidUserAndValidPasswordThenReturnsTrue() {
		UserDao mockedUserDao = mock(UserDao.class);
		when(mockedUserDao.isValidPassword((User) any(), eq("password")))
				.thenReturn(true);
		when(mockedUserDao.findById(anyString())).thenReturn(new User());
		su.setUserDao(mockedUserDao);
		assertTrue(su.canLogin("login", "password"));
	}

	@Test
	public void testWhenValidUserAndInvalidPasswordThenReturnsFalse() {
		UserDao mockedUserDao = mock(UserDao.class);
		when(mockedUserDao.isValidPassword((User) any(), eq("password")))
				.thenReturn(false);

		su.setUserDao(mockedUserDao);
		assertFalse(su.canLogin("login", "password"));
	}

	@Test
	public void testWhenInvalidUserAndValidPasswordThenReturnFalse() {
		UserDao mockedUserDao = mock(UserDao.class);
		when(mockedUserDao.findById(anyString())).thenReturn(null);

		su.setUserDao(mockedUserDao);
		assertFalse(su.canLogin("login", "password"));
	}

	/**
	 * Requisitos de inser test: + Si inserto algún campo *required(UserId,
	 * nombre, mail, password) vacio, devuelve false - Si inserto un usuario no
	 * existente, que devuelva true - Si inserto un user que ya existe(userId),
	 * que devuelva false
	 */
	@Test
	public void testInserUserWithSomeFieldEmpty() {
		assertFalse(su.insertUser("", "Nombre", "mail@test.com", "password"));
		assertFalse(su.insertUser("userId", "", "mail@test.com", "password"));
		assertFalse(su.insertUser("userId", "Nombre", "", "password"));
		assertFalse(su.insertUser("userId", "Nombre", "mail@test.com", ""));
	}

	@Test
	public void testWhenNonExistingUserCallInsertDaoReturnsTrue() {
		UserDao mockedUserDao = mock(UserDao.class);
		su.setUserDao(mockedUserDao);
		assertTrue(su.insertUser("userId", "Nombre", "mail@test.com",
				"password"));
	}

	@Test
	public void testWhenNonExistingUserCallInsertDao() {
		UserDao mockedUserDao = mock(UserDao.class);
		su.setUserDao(mockedUserDao);
		assertTrue(su.insertUser("userId", "Nombre", "mail@test.com",
				"password"));
		verify(mockedUserDao, times(1)).insert((User) any());
	}

	@Test
	public void testInsertExistingUserWithDao() {
		UserDao mockedUserDao = mock(UserDao.class);
		doThrow(new DaoException()).when(mockedUserDao).insert((User) any());
		su.setUserDao(mockedUserDao);

		assertEquals(false,
				su.insertUser("userId", "Nombre", "mail@test.com", "password"));
		verify(mockedUserDao).insert((User) any());
	}

	@Test
	public void testWhenInsertUserSetPasswordIsCalled() {
		UserDao mockedUserDao = mock(UserDao.class);
		su.setUserDao(mockedUserDao);
		su.insertUser("userId", "Nombre", "mail@test.com", "password");
		verify(mockedUserDao, times(1)).setPassword((User) any(), anyString());
	}

	@Test
	public void testWhenInsertUserSetPasswordIsCalledWithCorrectPassword() {
		UserDao mockedUserDao = mock(UserDao.class);
		su.setUserDao(mockedUserDao);
		su.insertUser("userId", "Nombre", "mail@test.com", "password");
		verify(mockedUserDao, times(1)).setPassword((User) any(),
				eq("password"));
	}
}
