package org.docear.plugin.webservice.v10.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.freeplane.features.nodelocation.LocationModel;

@XmlRootElement
public class DefaultNodeModel extends NodeModelBase{
	public final List<DefaultNodeModel> children;

	public int hGap;
	public int shiftY;

	/**
	 * necessary for JAX-B
	 */
	@SuppressWarnings("unused")
	private DefaultNodeModel() {
		super();
		children = new ArrayList<DefaultNodeModel>();
	}
	
	public DefaultNodeModel(org.freeplane.features.map.NodeModel freeplaneNode) {
		super(freeplaneNode);

		children = new ArrayList<DefaultNodeModel>();

		LocationModel l = freeplaneNode.getExtension(LocationModel.class);
		if(l != null) {
			hGap = l.getHGap();
			shiftY = l.getShiftY();
		} else {
			hGap = 0;
			shiftY = 0;
		}

		addChildren(freeplaneNode);
	}

	private void addChildren(org.freeplane.features.map.NodeModel freeplaneNode) {
		for(org.freeplane.features.map.NodeModel freeplaneChild : freeplaneNode.getChildren()) {
			children.add(new DefaultNodeModel(freeplaneChild));
		}
	}

}
