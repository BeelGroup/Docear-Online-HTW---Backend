package org.docear.plugin.webservice.v10.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RootNodeModel extends NodeModelBase {
	
	
	public List<DefaultNodeModel> leftChildren;
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
		
		int totalCount = freeplaneNode.getChildCount();
		for(org.freeplane.features.map.NodeModel child : freeplaneNode.getChildren()) {
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
			
		return totalCount;
	}

	@Override
	public List<NodeModelBase> getAllChildren() {
		List<NodeModelBase> list = new ArrayList<NodeModelBase>(leftChildren);
		list.addAll(rightChildren);
		return list;
	}

	
}
