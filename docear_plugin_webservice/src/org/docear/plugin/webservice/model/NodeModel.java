package org.docear.plugin.webservice.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NodeModel {
	public List<NodeModel> children;
	public String text;
	public boolean isHtml;
	public int vGap;
	public int schiftY;
	
	public NodeModel() {
		children = new ArrayList<NodeModel>();
	}
	
	public void addChild(NodeModel node) {
		children.add(node);
	}
}
