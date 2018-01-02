package servlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import schedule.ExternalRequestManager;


@RunWith(PowerMockRunner.class)
@PrepareForTest(ExternalRequestManager.class)
	public class TestGetWeather {

		@Mock
		private HttpServletRequest request;
		
		@Mock
		private HttpServletResponse response;
		
		@Test
		public void testDoPost() throws IOException, ServletException {
			
			HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
			HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
			Mockito.when(request.getMethod()).thenReturn("POST");
			String str = "{\"city\": \"Milan\"," + 
					"\"day\" : \"01-01-2000\"}";
			InputStream is = new ByteArrayInputStream(str.getBytes());
			BufferedReader buff = new BufferedReader(new InputStreamReader(is));
			Mockito.when(request.getReader()).thenReturn(buff);
			PowerMockito.mockStatic(ExternalRequestManager.class);
			PowerMockito.when(ExternalRequestManager.getWeatherTot(Matchers.anyString(),Matchers.anyString())).thenReturn("WheatherInformationJson");
			StringWriter sw = new StringWriter();
			PrintWriter pr = new PrintWriter(sw, false);
			//PrintWriter pr = new PrintWriter(System.out, false);
			Mockito.when(response.getWriter()).thenReturn(pr);
			new GetWeather().doPost(request, response);
			Mockito.verify(request, Mockito.times(1)).getReader();
			Mockito.verify(response, Mockito.times(1)).getWriter();
			assert(sw.toString().equals("WheatherInformationJson\n"));
			
		}

}
