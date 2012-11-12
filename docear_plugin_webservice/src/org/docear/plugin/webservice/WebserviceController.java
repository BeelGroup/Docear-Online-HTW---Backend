package org.docear.plugin.webservice;

import java.awt.Container;

import org.freeplane.core.util.LogUtils;
import org.freeplane.features.map.MapChangeEvent;
import org.freeplane.features.map.MapModel;
import org.freeplane.features.map.NodeChangeEvent;
import org.freeplane.features.map.NodeModel;
import org.freeplane.features.mode.ModeController;
import org.freeplane.features.ui.INodeViewLifeCycleListener;

public class WebserviceController {

	private static WebserviceController webserviceController;

	private final ModeController modeController;

	WebserviceController(final ModeController modeController) {
		super();
		webserviceController = this;
		this.modeController = modeController;
		LogUtils.info("starting Webservice Plugin...");

		this.registerListeners();

		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					Thread.sleep(10000);
					} catch(Throwable t) {
						
					}
				while(true) {
					MapModel mm = modeController.getMapController().getRootNode().getMap();
					NodeModel root = modeController.getMapController().getRootNode();
					modeController.getMapController().select(modeController.getMapController().getRootNode());
					NodeModel node = modeController.getMapController().newNode("Sample Text", mm);
					modeController.getMapController().insertNodeIntoWithoutUndo(node, root);
					modeController.getMapController().fireMapChanged(new MapChangeEvent(this, node, null, node));
//					AFreeplaneAction action = modeController.getAction("NewChildAction");
//					action.actionPerformed(null);

					node.setUserObject("3 Seconds to deletion");
					node.fireNodeChanged(new NodeChangeEvent(node, "userObject", "blub", "bla"));
					modeController.getMapController().fireMapChanged(new MapChangeEvent(this, node, null, node));
					try {Thread.sleep(1000);} catch(Throwable t) {}
					
					node.setUserObject("2 Seconds to deletion");
					node.fireNodeChanged(new NodeChangeEvent(node, "userObject", "blub", "bla"));
					try {Thread.sleep(1000);} catch(Throwable t) {}
					node.setUserObject("1 Seconds to deletion");
					node.fireNodeChanged(new NodeChangeEvent(node, "userObject", "blub", "bla"));
					try {Thread.sleep(1000);} catch(Throwable t) {}
					
					node.removeFromParent();
					modeController.getMapController().fireMapChanged(new MapChangeEvent(this, node, null, node));
					try {Thread.sleep(3000);} catch(Throwable t) {}
					
				}
			}
		}).start();

	}

	private void registerListeners() {
		modeController.addINodeViewLifeCycleListener(new INodeViewLifeCycleListener() {

			@Override
			public void onViewRemoved(Container nodeView) {

			}

			@Override
			public void onViewCreated(Container nodeView) {				
			}
		});
	}

	public WebserviceController getInstance() {
		return webserviceController;
	}

}
