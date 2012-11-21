package org.docear.plugin.webservice.v10;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.docear.plugin.webservice.WebserviceController;
import org.docear.plugin.webservice.v10.model.DefaultNodeModel;
import org.docear.plugin.webservice.v10.model.MapModel;
import org.freeplane.features.map.MapChangeEvent;
import org.freeplane.features.map.NodeChangeEvent;
import org.freeplane.features.map.NodeModel;
import org.freeplane.features.mode.ModeController;

@Path("/v1")
public class Webservice {

	
	@GET
	@Path("getMapModel/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public MapModel getMapModel(@PathParam("id") String id) {
		ModeController modeController = WebserviceController.getInstance().getModeController();
		MapModel mm = new MapModel(modeController.getController().getMap());

		return mm;
	}
	
	@GET
	@Path("test")
	@Produces({ MediaType.APPLICATION_JSON })
	public DefaultNodeModel test() {
		return new DefaultNodeModel();
	}
	
	@PUT
	@Path("addNode/{parentNodeId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public DefaultNodeModel addNode(@PathParam("parentNodeId") String parentNodeId,
									@QueryParam("nodeText") @DefaultValue("new Node") String nodeText, 
									@QueryParam("isHtml") @DefaultValue("false") Boolean isHtml) throws NodeNotFoundException {
		//get map
		ModeController modeController = WebserviceController.getInstance().getModeController();
		org.freeplane.features.map.MapModel mm = modeController.getController().getMap();
		//get parent Node
		NodeModel parentNode = mm.getNodeForID(parentNodeId);
		if(parentNode == null)
			throw new NodeNotFoundException("Node with id '"+parentNodeId+"' not found");
		//create new node
		NodeModel node = modeController.getMapController().newNode(nodeText, mm);
		modeController.getMapController().insertNodeIntoWithoutUndo(node, parentNode);
		modeController.getMapController().fireMapChanged(new MapChangeEvent(this, "node", "", ""));
		
		node.createID();
		return new DefaultNodeModel(node);	
	}
	
	@POST
	@Path("changeNode/{nodeId}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({ MediaType.APPLICATION_JSON })
	public DefaultNodeModel changeNode(DefaultNodeModel node) throws NodeNotFoundException {
		//get map
		ModeController modeController = WebserviceController.getInstance().getModeController();
		org.freeplane.features.map.MapModel mm = modeController.getController().getMap();
		//get node
		NodeModel freeplaneNode = mm.getNodeForID(node.id);
		if(node == null)
			throw new NodeNotFoundException("Node with id '"+node.id+"' not found");
		
		//TODO do stuff
		//Response.ok(new DefaultNodeModel(node)).
		return new DefaultNodeModel(freeplaneNode);	
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
		ModeController modeController = WebserviceController.getInstance().getModeController();
		org.freeplane.features.map.MapModel mm = modeController.getMapController().getRootNode().getMap();
		NodeModel root = modeController.getMapController().getRootNode();
		NodeModel node = modeController.getMapController().newNode(text, mm);
		root.insert(node);
		modeController.getMapController().fireMapChanged(new MapChangeEvent(this, "node", "", ""));
		node.createID();
		return node.getID();
	}
	
	@GET
	@Path("addNodeToRootNode/query")
	@Produces({ MediaType.APPLICATION_JSON })
	public String addNodeToRootNodeQuery(@QueryParam("text")String text) {
		ModeController modeController = WebserviceController.getInstance().getModeController();
		org.freeplane.features.map.MapModel mm = modeController.getMapController().getRootNode().getMap();
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
		ModeController modeController = WebserviceController.getInstance().getModeController();
		org.freeplane.features.map.MapModel mm = modeController.getMapController().getRootNode().getMap();
		NodeModel selectedNode = modeController.getMapController().getSelectedNodes().iterator().next();
		NodeModel node = modeController.getMapController().newNode(text, mm);
		selectedNode.insert(node);
		modeController.getMapController().fireMapChanged(new MapChangeEvent(this, "node", "", ""));
		node.createID();
		return node.getID();
	}
	
	@GET
	@Path("addNodeToSelectedNode/query")
	@Produces({ MediaType.APPLICATION_JSON })
	public String addNodeToSelectedNodeQuery(@QueryParam("text")String text) {
		ModeController modeController = WebserviceController.getInstance().getModeController();
		org.freeplane.features.map.MapModel mm = modeController.getMapController().getRootNode().getMap();
		NodeModel selectedNode = modeController.getMapController().getSelectedNodes().iterator().next();
		if (text == null) text = "New Node.";
		NodeModel node = modeController.getMapController().newNode(text, mm);
		selectedNode.insert(node);
		modeController.getMapController().fireMapChanged(new MapChangeEvent(this, "node", "", ""));
		node.createID();
		return node.getID();
	}

	@GET
	@Path("removeNode/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Boolean removeNode(@PathParam("id")String id) {
		ModeController modeController = WebserviceController.getInstance().getModeController();
		NodeModel node = modeController.getMapController().getNodeFromID(id);
		node.removeFromParent();
		node.fireNodeChanged(new NodeChangeEvent(node, "parent", "", ""));
		return true;
	}
	
	@GET
	@Path("sampleNode")
	@Produces({ MediaType.APPLICATION_JSON })
	public Boolean sampleNode() {
		ModeController modeController = WebserviceController.getInstance().getModeController();
		
		

		org.freeplane.features.map.MapModel mm = modeController.getMapController().getRootNode().getMap();
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

}
