package test.testUserManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import database.DataHandlerDBMS;
import dati.TypeMeans;
import userManager.SecurityAuthenticator;
import userManager.UserManager;

import static org.junit.Assert.assertEquals;
import org.mockito.Matchers;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UserManager.class, DataHandlerDBMS.class, SecurityAuthenticator.class})		
	//classi interessate dal mock, non quella che vieme testata
public class TestUserManager {

	@Mock
	private ResultSet resultSet;
		// strutture ausiliarie

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

	@Test
	public void testSignUp() throws SQLException {
		Mockito.when(resultSet.getString("Num")).thenReturn("0");
		Mockito.when(resultSet.next()).thenReturn(true);
		PowerMockito.mockStatic(DataHandlerDBMS.class);
		PowerMockito.when(DataHandlerDBMS.sendQuery(Matchers.anyString())).thenReturn(resultSet);
		PowerMockito.when(DataHandlerDBMS.executeDML(Matchers.anyString())).thenReturn(true);
		HashMap<String, String> aux = new HashMap<String, String>();
		aux.put("username", "marco");
		assertEquals(true, UserManager.signUp(aux));
	}
	
	/*
	@Test
	public void testSetBreakPref() throws SQLException {
		PowerMockito.mockStatic(DataHandlerDBMS.class);
		PowerMockito.when(DataHandlerDBMS.executeDML(Matchers.anyString())).thenReturn(true);
		PowerMockito.mockStatic(UserManager.class);
		User user = new User("testUsername","name","surname","mail","phone",
				"drivinglicence","0000111122223333",100, new Time(36000000));
		user.addBreakPref(new Break("Lunch", new Time(36000000),new Time(39600000),new Time(3600000)));
		PowerMockito.when(UserManager.getUserInformation(Matchers.anyString())).thenReturn(user);
		Boolean bool = UserManager.setBreakPref("testUsername", true, 
				new Break("breakName", new Time(72000000),new Time(75600000),new Time(3600000)));
		//System.out.println(bool.toString());
		Assert.assertTrue(bool);
	}
	*/
	
	@Test
	public void testSetFieldUser() throws SQLException {
		PowerMockito.mockStatic(DataHandlerDBMS.class);
		PowerMockito.when(DataHandlerDBMS.executeDML(Matchers.anyString())).thenReturn(true);
		assertEquals(true, UserManager.setFieldUser("str1","str2","str3"));
	}
	
	@Test
	public void testSetFieldMeansPref() {
		PowerMockito.mockStatic(DataHandlerDBMS.class);
		PowerMockito.when(DataHandlerDBMS.executeDML(Matchers.anyString())).thenReturn(true);
		assertEquals(true, UserManager.setFieldMeansPref("name",true,TypeMeans.bicycling));
	}
	
}