package org.docear.plugin.webservice;

import java.util.Collection;

import org.docear.plugin.core.DocearService;
import org.freeplane.features.mode.ModeController;
import org.freeplane.main.osgi.IControllerExtensionProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;




public class Activator extends DocearService implements BundleActivator {

	//private ServiceTracker httpServiceTracker;


	public void startService(BundleContext context, ModeController modeController) {
		new WebserviceController(modeController,context);
	}

	protected Collection<IControllerExtensionProvider> getControllerExtensions() {
		return null;
	}

//	@Override
//	public void start(BundleContext context) throws Exception {
//		final ModeController modeController = Controller.getCurrentModeController();
//		new WebserviceController(modeController,context);
//	}

	@Override
	public void stop(BundleContext arg0) throws Exception {		
	}
}
