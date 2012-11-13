package org.docear.plugin.webservice.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.docear.plugin.webservice.model.NodeModel;

@XmlRootElement
public class RootNode {
	
	public String id;
	public final List<NodeModel> leftChildren;
	public final List<NodeModel> rightChildren;
	public String text;
	public boolean isHtml;
	
	public RootNode() {
		leftChildren = new ArrayList<NodeModel>();
		rightChildren = new ArrayList<NodeModel>();
	}
	
	/**
	 * automatically converts the whole tree
	 * @param freeplaneNode
	 */
	public RootNode(org.freeplane.features.map.NodeModel freeplaneNode) {
		this();
		this.id = freeplaneNode.getID();
		this.text = freeplaneNode.getText();
		this.isHtml = freeplaneNode.getXmlText() != null;
		for(org.freeplane.features.map.NodeModel child : freeplaneNode.getChildren()) {
			if(child.isLeft()) {
				this.leftChildren.add(new org.docear.plugin.webservice.model.NodeModel(child));
			} else {
				this.rightChildren.add(new org.docear.plugin.webservice.model.NodeModel(child));
			}
		}
	}
	
	public void convertChildren() {
		
	}
	
}
