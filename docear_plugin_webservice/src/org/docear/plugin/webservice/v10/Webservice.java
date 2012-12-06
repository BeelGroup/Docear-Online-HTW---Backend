package org.docear.plugin.webservice.v10;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.docear.plugin.webservice.WebserviceController;
import org.docear.plugin.webservice.v10.exceptions.MapNotFoundException;
import org.docear.plugin.webservice.v10.exceptions.NodeNotFoundException;
import org.docear.plugin.webservice.v10.model.DefaultNodeModel;
import org.docear.plugin.webservice.v10.model.MapModel;
import org.freeplane.features.map.MapChangeEvent;
import org.freeplane.features.map.MapController;
import org.freeplane.features.map.MapWriter;
import org.freeplane.features.map.NodeChangeEvent;
import org.freeplane.features.map.NodeModel;
import org.freeplane.features.mapio.MapIO;
import org.freeplane.features.mode.ModeController;
import org.freeplane.features.styles.MapViewLayout;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.net.httpserver.HttpServer;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public class Webservice {

	/**
	 * returns a map as a JSON-Object
	 * @param id ID of map
	 * @param nodeCount soft limit of node count. When limit is reached, it only loads the outstanding child nodes of the current node.
	 * @return a map model
	 */
	@GET
	@Path("map/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public MapModel getMapModel(
			@PathParam("id") String id, 
			@QueryParam("nodeCount") @DefaultValue("-1") int nodeCount) throws MapNotFoundException {
		boolean loadAllNodes = nodeCount == -1;
		ModeController modeController = getModeController();



		//find map
		URL pathURL = Webservice.class.getResource("/files/mindmaps/"+id+".mm");
		//		if(pathURL == null) {
		//			throw new MapNotFoundException("Map with id '"+id+"' not found.");
		//		}

		try {
			if(pathURL != null) {
				MapIO mio = modeController.getExtension(MapIO.class);
				mio.newMap(pathURL);
			}

			org.freeplane.features.map.MapModel freeplaneMap = modeController.getController().getMap();

			MapModel mm = new MapModel(freeplaneMap,loadAllNodes);

			if(!loadAllNodes) {
				WebserviceHelper.loadNodesIntoModel(mm.root, nodeCount);
			}

			return mm;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * returns the current map as a JSON-Object
	 * @param id ID of map
	 * @param nodeCount soft limit of node count. When limit is reached, it only loads the outstanding child nodes of the current node.
	 * @return a map model
	 */
	@GET
	@Path("map.json")
	@Produces(MediaType.APPLICATION_JSON)
	public MapModel getOpenMapAsJson( 
			@QueryParam("nodeCount") @DefaultValue("-1") int nodeCount) throws MapNotFoundException {
		boolean loadAllNodes = nodeCount == -1;
		ModeController modeController = getModeController();


		org.freeplane.features.map.MapModel freeplaneMap = modeController.getController().getMap();

		MapModel mm = new MapModel(freeplaneMap,loadAllNodes);
		if(!loadAllNodes) {
			WebserviceHelper.loadNodesIntoModel(mm.root, nodeCount);
		}

		return mm;
	}

	/**
	 * returns a map as a JSON-Object
	 * @param id ID of map
	 * @param nodeCount soft limit of node count. When limit is reached, it only loads the outstanding child nodes of the current node.
	 * @return a map model
	 * @throws IOException 
	 */
	@GET
	@Path("map.xml")
	@Produces(MediaType.APPLICATION_ATOM_XML)
	public String getOpenMapAsXml() throws MapNotFoundException, IOException {
		ModeController modeController = getModeController();
		org.freeplane.features.map.MapModel freeplaneMap = modeController.getController().getMap();

		StringWriter writer = new StringWriter();
		modeController.getMapController().getMapWriter().writeMapAsXml(freeplaneMap, writer, MapWriter.Mode.EXPORT, true, true);


		return writer.toString();
	}

	/**
	 * returns a map as a JSON-Object
	 * @param id ID of map
	 * @param nodeCount soft limit of node count. When limit is reached, it only loads the outstanding child nodes of the current node.
	 * @return a map model
	 * @throws IOException 
	 */
	@GET
	@Path("map/xml/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public String getMapModelXml(
			@PathParam("id") String id) throws MapNotFoundException, IOException {
		ModeController modeController = getModeController();

		//find map
		InputStream in = Webservice.class.getResourceAsStream("/files/mindmaps/"+id+".mm");
		//		if(in == null) {
		//			throw new MapNotFoundException("Map with id '"+id+"' not found.");
		//		}


		if(in == null) {
			in = Webservice.class.getResourceAsStream("/files/mindmaps/1.mm");
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		String result = "";
		String line;
		while((line = reader.readLine()) != null) {
			result += line+"\n";
		}

		reader.close();

		org.freeplane.features.map.MapModel freeplaneMap = modeController.getController().getMap();
		if(freeplaneMap == null)
			throw new MapNotFoundException("Map with id '"+id+"' not found.");


		return result;
	}


	@PUT
	@Path("openMindmap")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	public Response openMindmap(InputStream uploadedInputStream) {

		try {
			String folder = System.getProperty("java.io.tmpdir");
			System.out.println(folder);
			String filename = "1.mm";//fileDetail.getFileName();
			File file = new File(folder+filename);


			byte[] buffer = new byte[1024];
			FileOutputStream out = new FileOutputStream(file);

			int length;
			while((length = uploadedInputStream.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, length);
			}

			out.flush();
			out.close();

			ModeController modeController = getModeController();
			URL pathURL = file.toURI().toURL();


			if(pathURL != null) {
				MapIO mio = modeController.getExtension(MapIO.class);
				mio.newMap(pathURL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity("File uploaded succesfully").build();
	}

	/**
	 * returns a node as a JSON-Object
	 * @param id ID of node
	 * @param nodeCount soft limit of node count. When limit is reached, it only loads the outstanding child nodes of the current node.
	 * @return a node model
	 */
	@GET
	@Path("node/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public DefaultNodeModel getNode(
			@PathParam("id") String id, 
			@QueryParam("nodeCount") @DefaultValue("-1") int nodeCount) throws NodeNotFoundException {
		ModeController modeController = getModeController();
		boolean loadAllNodes = nodeCount == -1;

		NodeModel freeplaneNode = modeController.getMapController().getNodeFromID(id);
		if(freeplaneNode == null)
			throw new NodeNotFoundException("Node with id '"+id+"' not found.");

		DefaultNodeModel node = new DefaultNodeModel(freeplaneNode,loadAllNodes);

		if(!loadAllNodes) {
			WebserviceHelper.loadNodesIntoModel(node, nodeCount);
		}

		return node;
	}

	@POST
	@Path("node/{parentNodeId}")
	@Produces(MediaType.APPLICATION_JSON)
	public DefaultNodeModel addNode(@PathParam("parentNodeId") String parentNodeId,
			@QueryParam("nodeText") @DefaultValue("new Node") String nodeText, 
			@QueryParam("isHtml") @DefaultValue("false") Boolean isHtml) throws NodeNotFoundException {

		ModeController modeController = getModeController();
		MapController mapController = modeController.getMapController();
		//get map
		org.freeplane.features.map.MapModel mm = modeController.getController().getMap();

		//get parent Node
		NodeModel parentNode = mapController.getNodeFromID(parentNodeId);

		if(parentNode == null)
			throw new NodeNotFoundException("Node with id '"+parentNodeId+"' not found");

		//create new node
		NodeModel node = modeController.getMapController().newNode(nodeText, mm);

		//insert node
		mapController.insertNodeIntoWithoutUndo(node, parentNode);
		mapController.fireMapChanged(new MapChangeEvent(this, "node", "", ""));

		node.createID();
		return new DefaultNodeModel(node, false);	
	}

	@POST
	@Path("changeNode/{nodeId}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({ MediaType.APPLICATION_JSON })
	public DefaultNodeModel changeNode(DefaultNodeModel node) throws NodeNotFoundException {
		//get map
		ModeController modeController = getModeController();
		org.freeplane.features.map.MapModel mm = modeController.getController().getMap();
		//get node
		NodeModel freeplaneNode = mm.getNodeForID(node.id);
		if(freeplaneNode == null)
			throw new NodeNotFoundException("Node with id '"+node.id+"' not found");

		//TODO do stuff
		//Response.ok(new DefaultNodeModel(node)).
		return new DefaultNodeModel(freeplaneNode,false);	
	}


	@DELETE
	@Path("removeNode/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String removeNode(@PathParam("id")String id) throws NodeNotFoundException {
		ModeController modeController = getModeController();
		NodeModel node = modeController.getMapController().getNodeFromID(id);
		if(node == null)
			throw new NodeNotFoundException("Node with id '"+id+"' not found");

		node.removeFromParent();
		node.fireNodeChanged(new NodeChangeEvent(node, "parent", "", ""));
		return new Boolean(true).toString();
	}

	@GET
	@Produces({ MediaType.TEXT_PLAIN  })
	@Path("search/mapId/{mapId}/query/{query}")
	public String search(@PathParam("mapId") Long mapId, @PathParam("query") String query) {
		return String.format("mapId = %s, searchString = %s", mapId, query);
	}

	@GET
	@Path("addNodeToRootNode")
	@Produces({ MediaType.APPLICATION_JSON })
	public String addNodeToRootNode( @DefaultValue("Sample Text") @QueryParam("text")String text) {
		ModeController modeController = getModeController();
		org.freeplane.features.map.MapModel mm = getOpenMap();
		NodeModel root = modeController.getMapController().getRootNode();
		NodeModel node = modeController.getMapController().newNode(text, mm);
		root.insert(node);
		modeController.getMapController().fireMapChanged(new MapChangeEvent(this, "node", "", ""));
		node.createID();
		return node.getID();
	}	


	@GET
	@Path("addNodeToSelectedNode/query")
	@Produces({ MediaType.APPLICATION_JSON })
	public String addNodeToSelectedNodeQuery(@QueryParam("text")String text) {
		ModeController modeController = getModeController();
		org.freeplane.features.map.MapModel mm = getOpenMap();
		NodeModel selectedNode = modeController.getMapController().getSelectedNodes().iterator().next();
		if (text == null) text = "New Node.";
		NodeModel node = modeController.getMapController().newNode(text, mm);
		selectedNode.insert(node);
		modeController.getMapController().fireMapChanged(new MapChangeEvent(this, "node", "", ""));
		node.createID();
		return node.getID();
	}


	// testing methods
	@GET
	@Path("addNodeToRootNode/query")
	@Produces({ MediaType.APPLICATION_JSON })
	public String addNodeToRootNodeQuery(@QueryParam("text")String text) {
		ModeController modeController = getModeController();
		org.freeplane.features.map.MapModel mm = getOpenMap();
		NodeModel root = modeController.getMapController().getRootNode();
		if (text == null) text = "New Node.";
		NodeModel node = modeController.getMapController().newNode(text, mm);
		root.insert(node);
		modeController.getMapController().fireMapChanged(new MapChangeEvent(this, "node", "", ""));
		node.createID();
		return node.getID();
	}

	@GET
	@Path("addNodeToSelectedNode")
	@Produces({ MediaType.APPLICATION_JSON })
	public String addNodeToSelectedNode(@DefaultValue("Sample Text") @QueryParam("text")String text) {
		ModeController modeController = getModeController();
		org.freeplane.features.map.MapModel mm = getOpenMap();
		NodeModel selectedNode = modeController.getMapController().getSelectedNodes().iterator().next();
		NodeModel node = modeController.getMapController().newNode(text, mm);
		selectedNode.insert(node);
		modeController.getMapController().fireMapChanged(new MapChangeEvent(this, "node", "", ""));
		node.createID();
		return node.getID();
	}

	@GET
	@Path("sampleNode")
	@Produces({ MediaType.APPLICATION_JSON })
	public Boolean sampleNode() {
		ModeController modeController = getModeController();



		org.freeplane.features.map.MapModel mm = getOpenMap();
		NodeModel root = modeController.getMapController().getRootNode();
		modeController.getMapController().select(modeController.getMapController().getRootNode());
		NodeModel node = modeController.getMapController().newNode("Sample Text", mm);
		modeController.getMapController().insertNodeIntoWithoutUndo(node, root);
		modeController.getMapController().fireMapChanged(new MapChangeEvent(this, node, null, node));
		//					AFreeplaneAction action = modeController.getAction("NewChildAction");
		//					action.actionPerformed(null);

		node.setUserObject("3 Seconds to deletion");
		node.fireNodeChanged(new NodeChangeEvent(node, "userObject", "blub", "bla"));
		modeController.getMapController().fireMapChanged(new MapChangeEvent(this, node, null, node));
		try {Thread.sleep(1000);} catch(Throwable t) {}

		node.setUserObject("2 Seconds to deletion");
		node.fireNodeChanged(new NodeChangeEvent(node, "userObject", "blub", "bla"));
		try {Thread.sleep(1000);} catch(Throwable t) {}
		node.setUserObject("1 Seconds to deletion");
		node.fireNodeChanged(new NodeChangeEvent(node, "userObject", "blub", "bla"));
		try {Thread.sleep(1000);} catch(Throwable t) {}

		node.removeFromParent();
		modeController.getMapController().fireMapChanged(new MapChangeEvent(this, node, null, node));
		try {Thread.sleep(3000);} catch(Throwable t) {}		
		return Boolean.TRUE;
	}

	private ModeController getModeController() {
		return WebserviceController.getInstance().getModeController();
	}

	private org.freeplane.features.map.MapModel getOpenMap() {
		ModeController modeController = getModeController();
		return modeController.getMapController().getRootNode().getMap();
	}


}
