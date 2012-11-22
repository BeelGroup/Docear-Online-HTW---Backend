package org.docear.plugin.webservice.v10.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.freeplane.features.nodelocation.LocationModel;

@XmlRootElement
public class DefaultNodeModel extends NodeModelBase{
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
		int totalCount = freeplaneNode.getChildCount();
		for(org.freeplane.features.map.NodeModel freeplaneChild : freeplaneNode.getChildren()) {
			children.add(new DefaultNodeModel(freeplaneChild,false));
		}
		
		if(autoloadChildren) {
			for(NodeModelBase child : this.children) {
				totalCount += child.loadChildren(true);
			}
		}
			
		return totalCount;
	}

	@Override
	public List<NodeModelBase> getAllChildren() {
		return children;
	}

}
