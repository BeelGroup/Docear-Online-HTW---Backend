package org.docear.plugin.webservice;

import java.awt.Container;
import java.io.IOException;
import java.util.logging.Level;

import org.docear.plugin.webservice.v10.Webservice;
import org.freeplane.core.util.LogUtils;
import org.freeplane.features.mode.ModeController;
import org.freeplane.features.ui.INodeViewLifeCycleListener;
import org.osgi.framework.BundleContext;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.ClassNamesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.net.httpserver.HttpServer;



public class WebserviceController {

	private static WebserviceController webserviceController;	
	private final ModeController modeController;
	private HttpServer server = null;
	
	public static WebserviceController getInstance() {
		return webserviceController;
	}

	WebserviceController(final ModeController modeController, BundleContext context) {
		webserviceController = this;
		this.modeController = modeController;
		LogUtils.info("starting Webservice Plugin...");
		
		int port = 8080;
		try {
			port = Integer.parseInt(System.getenv("webservice_port"));
		} catch (Exception e) {}
	    
		this.registerListeners();
		
		//change class loader
		final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(WebserviceController.class.getClassLoader());
		
		try {
			ResourceConfig rc = new ClassNamesResourceConfig(
					Webservice.class
					);
			
			
			System.out.println("Webservice address: http://localhost:"+port+"/rest");
			server = HttpServerFactory.create( "http://localhost:"+port+"/rest",rc );
			server.start();
			
		} catch (IOException e) {
			LogUtils.getLogger().log(Level.SEVERE, "Webservice could not be started.",e);
		} 
		finally {
			//set back to original class loader
			Thread.currentThread().setContextClassLoader(contextClassLoader);
		}

	}

	/**
	 * registers all listeners to react on necessary events like created nodes
	 * Might belong into a new plugin, which sends changes to the server (And this IS the server)
	 */
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


	public ModeController getModeController() {
		return modeController;
	}

}
