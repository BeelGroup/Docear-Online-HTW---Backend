package org.docear.plugin.webservice;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.docear.plugin.webservice.rest.Webservice;


public class TestServiceApplication extends Application {

	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(Webservice.class);
		return classes;
	}
	
	public Set<Object> getSingletons() {
		// nothing to do, no singletons
		return new HashSet<Object>();
	}
	
}