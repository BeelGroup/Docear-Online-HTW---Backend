package tests;
import static org.junit.Assert.assertTrue;

import javax.jws.WebResult;
import javax.ws.rs.core.MediaType;

import org.docear.plugin.webservice.v10.model.MapModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;


public class RestApiTest {

	private Client client;
	
	
	@Before
	public void setUp() throws Exception {
		client = Client.create();
	}

	@After
	public void tearDown() throws Exception {
		client.destroy();
	}

	@Test
	public void addNodeToRootAndRemoveTest() {
		WebResource wr = client.resource("http://localhost:8080/rest/v1");
		String nodeId = wr.path("addNodeToRootNode").accept(MediaType.APPLICATION_JSON_TYPE).get(String.class);
		assertTrue(nodeId.startsWith("ID_"));
		
		Boolean deleted = Boolean.valueOf(wr.path("removeNode").path(nodeId).delete(String.class));
		assertTrue(deleted);
	}
	
	@Test
	public void getMapTest() {
		WebResource wr = client.resource("http://localhost:8080/rest/v1");
		
		WebResource getMapResource = wr.path("map").path("NOTWORKING").queryParam("nodeCount", "5"); 
		MapModel map = getMapResource.get(MapModel.class);
		assertTrue(map.root.nodeText.equals("foo2"));
	}

}
