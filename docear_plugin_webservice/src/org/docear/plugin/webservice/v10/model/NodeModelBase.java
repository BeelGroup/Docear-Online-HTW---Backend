package org.docear.plugin.webservice.v10.model;

import javax.xml.bind.annotation.XmlTransient;

import org.freeplane.features.icon.MindIcon;

@XmlTransient
abstract public class NodeModelBase {

	public final String id;
	public final String nodeText;
	public final Boolean isHtml;
	public final Boolean folded;
	public final String[] icons;
	public final ImageModel image;
	public final String link;
	
	/**
	 * necessary for JAX-B
	 */
	protected NodeModelBase() {
		id = null;
		nodeText = null;
		isHtml = false;
		folded = false;
		icons = null;
		image = null;
		link = null;
	}
	
	public NodeModelBase(org.freeplane.features.map.NodeModel freeplaneNode) {
		
		this.id = freeplaneNode.getID();
		this.nodeText = freeplaneNode.getText();
		this.isHtml = freeplaneNode.getXmlText() != null;
		this.folded = freeplaneNode.isFolded();
		this.icons = getIconArray(freeplaneNode);
		this.image = getImage(freeplaneNode);
		
		this.link = null; //TODO: Where is the link hidden? Might be an Extension. (JS)
	}
	
	private String[] getIconArray(org.freeplane.features.map.NodeModel freeplaneNode) {
		String[] iconNames = new String[freeplaneNode.getIcons().size()];
		int count = 0;
		for(MindIcon mi : freeplaneNode.getIcons()) {
			iconNames[count++] = mi.getName();
		}
		return iconNames;
	}
	
	private ImageModel getImage(org.freeplane.features.map.NodeModel freeplaneNode) {
		// TODO: implement; Where is the Image hidden? (JS) 
		return null;
	}
}
