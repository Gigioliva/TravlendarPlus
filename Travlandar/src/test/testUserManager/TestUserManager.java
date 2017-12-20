package test.testUserManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import database.DataHandlerDBMS;
import userManager.SecurityAuthenticator;
import userManager.UserManager;

import static org.junit.Assert.assertEquals;
import org.mockito.Matchers;
import java.sql.ResultSet;
import java.sql.SQLException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DataHandlerDBMS.class, SecurityAuthenticator.class})
public class TestUserManager {

	@Mock
	private ResultSet resultSet;

	@Test
	public void testLogin() throws SQLException {
		Mockito.when(resultSet.getString("password")).thenReturn("password");
		Mockito.when(resultSet.next()).thenReturn(true);
		PowerMockito.mockStatic(DataHandlerDBMS.class);
		PowerMockito.when(DataHandlerDBMS.sendQuery(Matchers.anyString())).thenReturn(resultSet);
		PowerMockito.mockStatic(SecurityAuthenticator.class);
		PowerMockito.when(SecurityAuthenticator.addLogin("marco")).thenReturn("123");
		assertEquals("123", UserManager.login("marco", "password"));
	}

}