package org.docear.plugin.webservice;

import java.util.Collection;

import org.docear.plugin.core.DocearService;
import org.freeplane.features.mode.ModeController;
import org.freeplane.main.osgi.IControllerExtensionProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


//public class Activator implements BundleActivator {
//
//    private ServiceRegistration registration;
//
//    public void start(BundleContext bc) throws Exception {
//	    Dictionary<String, String> props = new Hashtable<String, String>();
//	    props.put("osgi.remote.interfaces", "*");
//	    props.put("osgi.remote.configuration.type", "pojo");
//	    props.put("osgi.remote.configuration.pojo.address", 
//                  "http://localhost:8000/DictionaryService");    
//	
//	    registration = bc.registerService(Webservice.class.getName(), 
//	                                      new Webservice(), props);
//	    System.out.println("testtest");
//	}
//
//    public void stop(BundleContext context) throws Exception {
//        registration.unregister();
//    }
//}


public class Activator extends DocearService implements BundleActivator {

	public void stop(BundleContext context) throws Exception {
		
	}

	public void startService(BundleContext context, ModeController modeController) {
			new WebserviceController(modeController,context);
	}

	protected Collection<IControllerExtensionProvider> getControllerExtensions() {
		return null;
	}

}
