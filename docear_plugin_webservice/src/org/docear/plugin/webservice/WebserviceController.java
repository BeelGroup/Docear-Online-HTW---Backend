package org.docear.plugin.webservice;

import java.awt.Container;
import java.io.IOException;

import org.docear.plugin.webservice.v10.Webservice;
import org.docear.plugin.webservice.v10.model.EdgeModel;
import org.docear.plugin.webservice.v10.model.RootNodeModel;
import org.freeplane.core.util.LogUtils;
import org.freeplane.features.map.MapModel;
import org.freeplane.features.map.NodeModel;
import org.freeplane.features.mode.ModeController;
import org.freeplane.features.ui.INodeViewLifeCycleListener;
import org.osgi.framework.BundleContext;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.ClassNamesResourceConfig;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.net.httpserver.HttpServer;



public class WebserviceController {

	private static WebserviceController webserviceController;	
	private final ModeController modeController;
	
	public static WebserviceController getInstance() {
		return webserviceController;
	}

	WebserviceController(final ModeController modeController, BundleContext context) {
		super();
		webserviceController = this;
		this.modeController = modeController;
		LogUtils.info("starting Webservice Plugin...");
	    
		this.registerListeners();
		
		//change class loader
		final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(WebserviceController.class.getClassLoader());
		
		try {
			ResourceConfig rc = new ClassNamesResourceConfig(
					Webservice.class,
					MapModel.class,
					NodeModel.class,
					RootNodeModel.class,
					EdgeModel.class
					);
			
			HttpServer server = HttpServerFactory.create( "http://localhost:8080/rest",rc );
			server.start();
			
		} catch (IOException e) {} 
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
