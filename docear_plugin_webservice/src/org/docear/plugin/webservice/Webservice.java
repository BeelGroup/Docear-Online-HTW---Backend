package org.docear.plugin.webservice;

import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

@Path("/map")
public class Webservice {

	@GET
	@Path("getMapName")
	@Produces({ MediaType.APPLICATION_JSON })
	public MapModel getMapName() {
		return new MapModel();
	}

	@GET
	@Produces({ MediaType.TEXT_PLAIN  })
	@Path("search/mapId/{mapId}/query/{query}")
	public String search(@PathParam("mapId") Long mapId, @PathParam("query") String query) {
		return String.format("mapId = %s, searchString = %s", mapId, query);
	}
	
	@XmlRootElement
	static class MapModel {
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
