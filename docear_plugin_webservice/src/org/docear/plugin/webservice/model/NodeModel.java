package org.docear.plugin.webservice.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.freeplane.features.nodelocation.LocationModel;

@XmlRootElement
public class NodeModel {
	public final List<NodeModel> children;
	public String text;
	public boolean isHtml;
	public int hGap;
	public int schiftY;
	
	public NodeModel() {
		children = new ArrayList<NodeModel>();
	}
	
	public NodeModel(org.freeplane.features.map.NodeModel freeplaneNode) {
		this();
		text = freeplaneNode.getText();
		isHtml = freeplaneNode.getXmlText() != null;
		LocationModel l = freeplaneNode.getExtension(LocationModel.class);
		hGap = l.getHGap();
		schiftY = l.getShiftY();
		
		for(org.freeplane.features.map.NodeModel freeplaneChild : freeplaneNode.getChildren()) {
			children.add(new NodeModel(freeplaneChild));
		}
	}
	
	public void addChild(NodeModel node) {
		children.add(node);
	}
}
