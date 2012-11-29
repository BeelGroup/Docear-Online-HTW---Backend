package org.docear.plugin.webservice.v10.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.docear.plugin.webservice.WebserviceController;
import org.freeplane.features.map.MapController;
import org.freeplane.features.map.NodeModel;

@XmlRootElement
public class RootNodeModel extends NodeModelBase {
	
	@XmlElement
	public List<DefaultNodeModel> leftChildren;
	@XmlElement
	public List<DefaultNodeModel> rightChildren;

	//public NodeModel preferredChild;
	
	
	/**
	 * necessary for JAX-B
	 */
	@SuppressWarnings("unused")
	private RootNodeModel() {
		super();
	}
	
	/**
	 * automatically converts the whole tree
	 * @param freeplaneNode
	 */
	public RootNodeModel(org.freeplane.features.map.NodeModel freeplaneNode, boolean autoloadChildren) {
		super(freeplaneNode,autoloadChildren);
	}
	
	
	
	@Override
	public int loadChildren(boolean autoloadChildren) {
		leftChildren = new ArrayList<DefaultNodeModel>();
		rightChildren = new ArrayList<DefaultNodeModel>();
		
		MapController mapController = WebserviceController.getInstance().getModeController().getMapController(); 
		int totalCount = childrenIds.size();
		for(String nodeId : childrenIds) {
			NodeModel child = mapController.getNodeFromID(nodeId);
			if(child.isLeft()) {
				this.leftChildren.add(new org.docear.plugin.webservice.v10.model.DefaultNodeModel(child,false));
			} else {
				this.rightChildren.add(new org.docear.plugin.webservice.v10.model.DefaultNodeModel(child,false));
			}
		}
		
		if(autoloadChildren) {
			for(DefaultNodeModel child : this.leftChildren) {
				totalCount += child.loadChildren(true);
			}
			for(DefaultNodeModel child : this.rightChildren) {
				totalCount += child.loadChildren(true);
			}
		}
			
		childrenIds = null;
		return totalCount;
	}

	@Override
	public List<NodeModelBase> getAllChildren() {
		List<NodeModelBase> list = new ArrayList<NodeModelBase>(leftChildren);
		list.addAll(rightChildren);
		return list;
	}

	
}
