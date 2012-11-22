package org.docear.plugin.webservice.v10;

import java.util.LinkedList;

import org.docear.plugin.webservice.v10.model.NodeModelBase;

public final class WebserviceHelper {
	
	public static void loadNodesIntoModel(NodeModelBase node, int nodeCount) {
		LinkedList<NodeModelBase> nodeQueue = new LinkedList<NodeModelBase>();
		nodeQueue.add(node);
		while(nodeCount > 0 && !nodeQueue.isEmpty()) {
			NodeModelBase curNode = nodeQueue.pop();
			
			nodeCount -= curNode.loadChildren(false);
			for(NodeModelBase child : curNode.getAllChildren()) {
				nodeQueue.add(child);
			}
		}
	}
}
