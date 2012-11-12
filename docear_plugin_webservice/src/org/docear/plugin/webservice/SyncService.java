package org.docear.plugin.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.docear.plugin.webservice.model.MapModel;



@WebService
public interface SyncService {
	
	MapModel getMapObject(@WebParam(name="id") String id);
	
	String addNodeToRootNode(@WebParam(name="text") String text);
	String addNodeToSelectedNode(@WebParam(name="text") String text);
	Boolean removeNode(@WebParam(name="id") String id);

}
