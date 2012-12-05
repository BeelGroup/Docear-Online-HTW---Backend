package org.docear.plugin.webservice;

import java.util.Collection;

import org.docear.plugin.core.DocearService;
import org.freeplane.features.mode.ModeController;
import org.freeplane.main.osgi.IControllerExtensionProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;




public class Activator extends DocearService implements BundleActivator {

	//private ServiceTracker httpServiceTracker;

	public void stop(BundleContext context) throws Exception {
//		httpServiceTracker.close();
//		httpServiceTracker = null;
	}

	public void startService(BundleContext context, ModeController modeController) {
//		httpServiceTracker = new HttpServiceTracker(context);
//		httpServiceTracker.open();

		new WebserviceController(modeController,context);
	}

	protected Collection<IControllerExtensionProvider> getControllerExtensions() {
		return null;
	}

//	private class HttpServiceTracker extends ServiceTracker {
//
//		public HttpServiceTracker(BundleContext context) {
//			super(context, HttpService.class.getName(), null);
//		}
//
//		public Object addingService(ServiceReference reference) {
//			HttpService httpService = (HttpService) context.getService(reference);
//			try {
//				Thread currentThread = Thread.currentThread();
//				ClassLoader cl = currentThread.getContextClassLoader();
//				Thread.currentThread().setContextClassLoader(HttpServiceTracker.class.getClassLoader());
//				                        
//				HttpContext httpContext = httpService.createDefaultHttpContext();   
//				Hashtable params = new Hashtable();
//				params.put("javax.ws.rs.Application",
//				"com.servicemesh.agility.internal.api.v1_0.AgilityApplication");
//				httpService.registerServlet("/api/v1.0", new ServletWrapper(new
//				com.sun.jersey.spi.container.servlet.ServletContainer()), params,
//				httpContext);
//
//				params = new Hashtable();
//				params.put("javax.ws.rs.Application",
//				"com.servicemesh.agility.internal.api.RootApplication");
//				httpService.registerServlet("/api", new ServletWrapper(new
//				com.sun.jersey.spi.container.servlet.ServletContainer()), params,
//				httpContext);
//				currentThread.setContextClassLoader(cl);
//				httpService.registerResources("/helloworld.html", "/helloworld.html", null); //$NON-NLS-1$ //$NON-NLS-2$
//				httpService.registerServlet("/helloworld", new HelloWorldServlet(), null, null); //$NON-NLS-1$
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return httpService;
//		}		
//
//		public void removedService(ServiceReference reference, Object service) {
//			HttpService httpService = (HttpService) service;
//			httpService.unregister("/helloworld.html"); //$NON-NLS-1$
//			httpService.unregister("/helloworld"); //$NON-NLS-1$
//			super.removedService(reference, service);
//		}
//	}

}
