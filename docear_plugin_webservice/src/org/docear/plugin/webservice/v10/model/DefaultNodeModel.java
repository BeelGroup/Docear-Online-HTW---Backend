package org.docear.plugin.webservice.v10.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.docear.plugin.webservice.WebserviceController;
import org.freeplane.features.map.MapController;
import org.freeplane.features.map.NodeModel;
import org.freeplane.features.nodelocation.LocationModel;

@XmlRootElement(name="node")
@XmlAccessorType(XmlAccessType.FIELD)
public class DefaultNodeModel extends NodeModelBase{
	@XmlElement(name="children")
	public List<DefaultNodeModel> children;

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
		children = new ArrayList<DefaultNodeModel>();
		
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
	public List<DefaultNodeModel> getAllChildren() {
		return children;
	}

}
