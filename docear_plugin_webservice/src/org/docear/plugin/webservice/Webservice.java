package org.docear.plugin.webservice;

import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;


@Path( "map" )
public class Webservice {
	
	  @GET
	  @Path( "getMapName" )
	  @Produces( { MediaType.TEXT_XML, MediaType.APPLICATION_JSON } )
	  public MapModel getMapName()
	  {
	    return new MapModel();
	  }
	  
	  @XmlRootElement
	  static class MapModel
	  {
		    public String name;
		    public java.util.List<String> children;
		    
		    public MapModel() {
				this.name = "AAA";
				this.children = new ArrayList<String>();
				this.children.add("asda1");
				this.children.add("asda2");
				this.children.add("asda3");
			}
	  }

}
