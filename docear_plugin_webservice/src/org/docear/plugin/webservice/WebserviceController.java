package org.docear.plugin.webservice;

import java.awt.Container;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.ws.soap.SOAPBinding;

import org.freeplane.core.util.LogUtils;
import org.freeplane.features.map.MapChangeEvent;
import org.freeplane.features.map.MapModel;
import org.freeplane.features.map.NodeChangeEvent;
import org.freeplane.features.map.NodeModel;
import org.freeplane.features.mode.ModeController;
import org.freeplane.features.ui.INodeViewLifeCycleListener;
import org.osgi.framework.BundleContext;



public class WebserviceController {

	private static WebserviceController webserviceController;	

	private final ModeController modeController;

	WebserviceController(final ModeController modeController, BundleContext context) {
		super();
		webserviceController = this;
		this.modeController = modeController;
		LogUtils.info("starting Webservice Plugin...");
	    
		try {
			final URI serviceURI = new URI("http", null,"localhost", 8000, "/" + "SyncService", null, null);
			SyncServer server = new SyncServer(SOAPBinding.SOAP11HTTP_BINDING, serviceURI, modeController);
			
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		try {
			//final ServerSocket ss = new ServerSocket(8080);
			
//			HttpServer server = HttpServerFactory.create( "http://localhost:8080/rest" );
//			server.start();
			

			this.registerListeners();

			new Thread(new Runnable() {

				@Override
				public void run() {
					

					while(true) {
						try {
							Webservice svc = null;
							Socket s = null;//ss.accept();
							
							BufferedReader br = new BufferedReader(new InputStreamReader( s.getInputStream()));
							//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
							
							String firstLine = br.readLine();

//							if(firstLine.contains("getMapName")) {
//								try {
//									JAXBContext jc =  JAXBContext.newInstance(org.docear.plugin.webservice.Webservice.MapModel.class);
//									org.docear.plugin.webservice.Webservice.MapModel model =  svc.getMapName();
//									Marshaller m = jc.createMarshaller();
//									m.marshal(model, s.getOutputStream());
//									s.close();
//
//								} catch (JAXBException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								} 
//
//							}
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

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});

//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

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
