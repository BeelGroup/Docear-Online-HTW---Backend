package app;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;

import org.docear.plugin.webservice.SyncService;
import org.docear.plugin.webservice.model.MapModel;


public class Application {

	private static final String SERVICE_URL = "localhost:8000/SyncService";
	private SyncService proxy;
	private Stack<String> lastCreatedNodeIds;
	
	private JFrame frame;
	
	public Application() {
		createConnectionToWebservice();
		lastCreatedNodeIds = new Stack<String>();
		
		JPanel panel = new JPanel(new FlowLayout());
		
		JButton button = new JButton("get Map");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MapModel mm = proxy.getMapObject("blub");
				
				System.out.println(mm);
			}
		});
		panel.add(button);
		
		button = new JButton("add Node to Root");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = proxy.addNodeToRootNode("New Node");
				lastCreatedNodeIds.push(id);
				System.out.println(id);
			}
		});
		panel.add(button);
		
		button = new JButton("add Node to selected Node");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = proxy.addNodeToSelectedNode("New Node");
				lastCreatedNodeIds.push(id);
				System.out.println(id);
			}
		});
		panel.add(button);
		
		button = new JButton("remove last created Node");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(lastCreatedNodeIds.size() != 0)
					System.out.println(proxy.removeNode(lastCreatedNodeIds.pop()));
			}
		});
		panel.add(button);
		
		
		frame = new JFrame("Client App");
		frame.add(panel);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	private void createConnectionToWebservice() {
		if(proxy != null)
			return;
		
		Service proxyFactory = null;

		try {
			proxyFactory = Service.create(new URL("http://"+ SERVICE_URL + "?wsdl"), new QName("http://webservice.plugin.docear.org/", "SyncService"));
			proxy = proxyFactory.getPort(SyncService.class);

		} catch ( MalformedURLException e) {
			throw new AssertionError(e);
		} catch (WebServiceException e) {
			proxy = null;
		}
	}
	
	public static void main(String[] args) {

		new Application();
	}

}
