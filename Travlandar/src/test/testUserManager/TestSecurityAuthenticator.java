package test.testUserManager;

import org.junit.Test;

import userManager.SecurityAuthenticator;

public class TestSecurityAuthenticator {
	
	String tokenLogin;
	String tokenUsername;
	String tokenLogout;
	String nameLogin = new String("test1");
	String nameUsername = new String("test2");
	String nameLogout = new String("test3");
	Boolean boolLogout;
	
	@Test
	public void testLogin() {
		tokenLogin = SecurityAuthenticator.addLogin(nameLogin);
		assert(tokenLogin.length() == 20);
		assert(SecurityAuthenticator.getUsername(tokenLogin).equals(nameLogin));

	}
	
	@Test
	public void testGetUsername() {
		tokenUsername = SecurityAuthenticator.addLogin(nameUsername);
		assert(SecurityAuthenticator.getUsername(tokenUsername).equals(nameUsername));
	}
	
	@Test
	public void testLogout() {
		tokenLogout = SecurityAuthenticator.addLogin(nameLogout);
		boolLogout = SecurityAuthenticator.Logout(tokenLogout);
		assert(boolLogout.equals(true));
		boolLogout = SecurityAuthenticator.Logout(tokenLogout);
		assert(boolLogout.equals(false));

	}
	
}
