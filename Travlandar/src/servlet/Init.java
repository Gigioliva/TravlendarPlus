package servlet;

import database.DataHandlerDBMS;
import userManager.SecurityAuthenticator;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class Init implements ServletContextListener, HttpSessionListener, ServletRequestListener {
	
	/** eseguito quando si avvia il server */
	public Init() {
		new DataHandlerDBMS();
		new SecurityAuthenticator();
	}

	/** eseguito quando si avvia il server */
	public void contextInitialized(ServletContextEvent arg0) {
		// System.out.println("Listner contextInitialized");
	}

	/** eseguito quando si chiude/riavvia il server */
	public void contextDestroyed(ServletContextEvent arg0) {
		// System.out.println("Listner contextDestroyed");
	}

	/** eseguito quando perviene la richiesta al server */
	public void requestInitialized(ServletRequestEvent arg0) {
		// System.out.println("Listner requestInitialized");
	}

	/** eseguito quando termina la richiesta al server */
	public void requestDestroyed(ServletRequestEvent arg0) {
		// System.out.println("Listner requestDestroyed");
	}

	/** eseguito quando si crea la sessione */
	public void sessionCreated(HttpSessionEvent arg0) {
		// System.out.println("Listner sessionCreated");
	}

	/** eseguito quando si termina la sessione */
	public void sessionDestroyed(HttpSessionEvent arg0) {
		// System.out.println("Listner sessionDestroyed");
	}
}