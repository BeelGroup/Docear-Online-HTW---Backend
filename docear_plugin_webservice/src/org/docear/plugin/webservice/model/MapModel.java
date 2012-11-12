package org.docear.plugin.webservice.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MapModel {
	public String id;
	public NodeModel root;

	public MapModel() {
	}
}
