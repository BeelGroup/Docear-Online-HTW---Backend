# Installation

1. Download the "Docear/Desktop"-Repository and the "Docear/HTW-Backend"-Repository into the same workspace
1. Make sure the "docear-osgi"-configuration uses the docear.plugin.webservice - bundle
1. run "docear-osgi"

# Usage

The Following REST-calls are currently implemented

* http://localhost:8080/rest/v1/getMapModel
	* returns the current Map-JSON-Model
* http://localhost:8080/rest/v1/addNodeToRootNode?[text]
	* adds a node to the root of current map.
	* if text is entered, it is added as node text
	* returns node id
* http://localhost:8080/rest/v1/addNodeToRootNode?[text]
	* adds a node to the root of current map.
	* if text is entered, it is added as node text
	* returns node id
* http://localhost:8080/rest/v1/removeNode/{id}
	* removes the specified node
	* returns true on success
* http://localhost:8080/rest/v1/sampleNode
	* execute and see :) (in Docear)