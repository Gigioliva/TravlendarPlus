package test.testUserManager;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

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

@RunWith(PowerMockRunner.class)
@PrepareForTest(DataHandlerDBMS.class)
public class TestUserManager {
	
	
	@Mock
	private ResultSet resultSet;
	
	@Test
	public void testLogin() throws SQLException {
		PowerMockito.when(resultSet.getString("password")).thenReturn("password");
		PowerMockito.when(resultSet.next()).thenReturn(true);
		PowerMockito.mockStatic(DataHandlerDBMS.class);
		PowerMockito.when(DataHandlerDBMS.sendQuery("select password from user where username='marco'")).thenReturn(resultSet);
		//PowerMockito.mockStatic(SecurityAuthenticator.class);
		//PowerMockito.when(SecurityAuthenticator.addLogin("marco")).thenReturn("123");
		assertEquals("password", DataHandlerDBMS.sendQuery("select password from user where username='marco'").getString("password"));
		
	}
	
	
}
