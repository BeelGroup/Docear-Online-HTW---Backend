package org.docear.plugin.webservice;

import java.net.URI;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import javax.xml.ws.WebServiceException;

import org.docear.plugin.webservice.model.MapModel;
import org.docear.plugin.webservice.model.RootNode;
import org.freeplane.features.map.MapChangeEvent;
import org.freeplane.features.map.NodeChangeEvent;
import org.freeplane.features.map.NodeModel;
import org.freeplane.features.mode.ModeController;

import com.sun.xml.internal.ws.Closeable;


@WebService (endpointInterface="org.docear.plugin.webservice.SyncService", serviceName="SyncService", portName="SyncPort")
public class SyncServer implements SyncService, Closeable {

	private Endpoint endpoint;
	private ModeController modeController;
	
	@Override
	public MapModel getMapObject(@WebParam(name = "id") String id) {
		MapModel mm = new MapModel();
		org.freeplane.features.map.MapModel freeplaneMm =  modeController.getController().getMap();
		mm.id = freeplaneMm.getTitle();
		NodeModel rootNodeFreeplane = modeController.getMapController().getRootNode();
		mm.root = new RootNode(rootNodeFreeplane);
		
		
		return mm;
	}
	
	public SyncServer(final String binding, final URI serviceURI, ModeController modeController) {
		super();

		this.endpoint = Endpoint.create(binding, this);
		this.endpoint.publish(serviceURI.toASCIIString());
		this.modeController = modeController;
	}



	@Override
	public void close() throws WebServiceException {
		endpoint.stop();
	}

	@Override
	public String addNodeToRootNode(@WebParam(name = "text") String text) {
		org.freeplane.features.map.MapModel mm = modeController.getMapController().getRootNode().getMap();
		NodeModel root = modeController.getMapController().getRootNode();
		NodeModel node = modeController.getMapController().newNode("Sample Text", mm);
		root.insert(node);
		modeController.getMapController().fireMapChanged(new MapChangeEvent(this, "node", "", ""));
		node.createID();
		return node.getID();
	}

	@Override
	public String addNodeToSelectedNode(@WebParam(name = "text") String text) {
		org.freeplane.features.map.MapModel mm = modeController.getMapController().getRootNode().getMap();
		NodeModel selectedNode = modeController.getMapController().getSelectedNodes().iterator().next();
		NodeModel node = modeController.getMapController().newNode("Sample Text", mm);
		selectedNode.insert(node);
		modeController.getMapController().fireMapChanged(new MapChangeEvent(this, "node", "", ""));
		node.createID();
		return node.getID();
	}

	@Override
	public Boolean removeNode(@WebParam(name = "id") String id) {
		NodeModel node = modeController.getMapController().getNodeFromID(id);
		node.removeFromParent();
		node.fireNodeChanged(new NodeChangeEvent(node, "parent", "", ""));
		return true;
	}
}
