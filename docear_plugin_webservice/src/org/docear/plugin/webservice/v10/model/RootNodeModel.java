package org.docear.plugin.webservice.v10.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.docear.plugin.webservice.v10.model.DefaultNodeModel;
import org.freeplane.features.icon.MindIcon;

@XmlRootElement
public class RootNodeModel extends NodeModelBase {
	
	
	public final List<DefaultNodeModel> leftChildren;
	public final List<DefaultNodeModel> rightChildren;

	//public NodeModel preferredChild;
	
	
	/**
	 * necessary for JAX-B
	 */
	@SuppressWarnings("unused")
	private RootNodeModel() {
		super();
		leftChildren = new ArrayList<DefaultNodeModel>();
		rightChildren = new ArrayList<DefaultNodeModel>();
	}
	
	/**
	 * automatically converts the whole tree
	 * @param freeplaneNode
	 */
	public RootNodeModel(org.freeplane.features.map.NodeModel freeplaneNode) {
		super(freeplaneNode);
		leftChildren = new ArrayList<DefaultNodeModel>();
		rightChildren = new ArrayList<DefaultNodeModel>();
		
		addChildren(freeplaneNode);
	}
	
	private void addChildren(org.freeplane.features.map.NodeModel freeplaneNode) {
		for(org.freeplane.features.map.NodeModel child : freeplaneNode.getChildren()) {
			if(child.isLeft()) {
				this.leftChildren.add(new org.docear.plugin.webservice.v10.model.DefaultNodeModel(child));
			} else {
				this.rightChildren.add(new org.docear.plugin.webservice.v10.model.DefaultNodeModel(child));
			}
		}
	}
}
