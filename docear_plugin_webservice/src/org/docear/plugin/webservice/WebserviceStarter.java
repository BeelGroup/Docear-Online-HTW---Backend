package org.docear.plugin.webservice;

import java.io.IOException;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;
	
public class WebserviceStarter {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws IllegalArgumentException, IOException {
		HttpServer server = HttpServerFactory.create( "http://localhost:8080/rest" );
		server.start();
	}

}
