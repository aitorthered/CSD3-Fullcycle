package specs.example1;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.Before;
import org.junit.runner.RunWith;

import scrumcourse.fullcycle.service.ServiceUsuario;

/* Run this class as a JUnit test. */

@RunWith(ConcordionRunner.class)
public class LoginTest {
	ServiceUsuario su;

	@Before
	public void setUp() {
		su = new ServiceUsuario();
	}

	public String canLogin(String user, String password) {
		boolean returned = su.canLogin(user, password);
		if (returned) {
			return "SI";
		} else {
			return "NO";
		}
	}

	public void insertUser(String userId, String nombre, String correo,
			String password) {
		su.insertUser(userId, nombre, correo, password);
	}
}
