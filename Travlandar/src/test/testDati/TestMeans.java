package test.testDati;

import org.junit.Before;
import org.junit.Test;

import dati.Means;
import dati.TypeMeans;

public class TestMeans {

	private String name;
	private boolean status;
	private TypeMeans type;
	private Means means;

	@Before
	public void init() {
		name = new String("testName");
		status = false;
		type = TypeMeans.bicycling;
		means = new Means(name, status, type);
	}

	@Test
	public void testCreateMeans() {
		assert(means.getName().equals(name));
		assert(means.getType().equals(type));
		assert(means.isStatus()==status);
	}

	@Test
	public void testGetJSON() {
		assert(TestJSON.JSONSyntaxTest(means.getJson()));
	}
	
}
