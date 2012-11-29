package org.docear.plugin.webservice.v10.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.docear.plugin.webservice.WebserviceController;
import org.freeplane.features.map.MapController;
import org.freeplane.features.map.NodeModel;
import org.freeplane.features.nodelocation.LocationModel;

@XmlRootElement
public class DefaultNodeModel extends NodeModelBase{
	@XmlElement
	public List<NodeModelBase> children;

	public Integer hGap;
	public Integer shiftY;

	/**
	 * necessary for JAX-B
	 */
	public DefaultNodeModel() {
		super();
	}
	
	public DefaultNodeModel(org.freeplane.features.map.NodeModel freeplaneNode, boolean autoloadChildren) {
		super(freeplaneNode,autoloadChildren);

		LocationModel l = freeplaneNode.getExtension(LocationModel.class);
		if(l != null) {
			hGap = l.getHGap();
			shiftY = l.getShiftY();
		} else {
			hGap = 0;
			shiftY = 0;
		}
	}

	@Override
	public int loadChildren(boolean autoloadChildren) {
		children = new ArrayList<NodeModelBase>();
		
		MapController mapController = WebserviceController.getInstance().getModeController().getMapController();
		
		int totalCount = childrenIds.size();
		for(String nodeId : childrenIds) {
			NodeModel freeplaneChild = mapController.getNodeFromID(nodeId);
			children.add(new DefaultNodeModel(freeplaneChild,false));
		}
		
		if(autoloadChildren) {
			for(NodeModelBase child : this.children) {
				totalCount += child.loadChildren(true);
			}
		}
		
		childrenIds = null;
		return totalCount;
	}
	

	@Override
	public List<NodeModelBase> getAllChildren() {
		return children;
	}

}
