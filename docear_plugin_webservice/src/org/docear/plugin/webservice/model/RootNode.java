package org.docear.plugin.webservice.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import sun.reflect.generics.visitor.Reifier;

@XmlRootElement
public class RootNode {
	public List<NodeModel> leftChildren;
	public List<NodeModel> rightChildren;
	public String text;
	public boolean isHtml;
	
	public RootNode() {
		leftChildren = new ArrayList<NodeModel>();
		rightChildren = new ArrayList<NodeModel>();
	}
	
}
