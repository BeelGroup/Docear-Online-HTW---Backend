# Installation

1. Download the "Docear/Desktop"-Repository and the "Docear/HTW-Backend"-Repository into the same workspace
1. Make sure the "docear-osgi"-configuration uses the docear.plugin.webservice - bundle
1. run "docear-osgi"

# Usage

The Following REST-calls are currently implemented

* http://localhost:8080/rest/v1/getMapModel
	* returns the current Map-JSON-Model
* http://localhost:8080/rest/v1/addNodeToRootNode
	* adds a node to the root of current map.
	* node text is "Sample Text"
	* returns node id
* http://localhost:8080/rest/v1/addNodeToRootNode/query?text={nodeText}
	* adds a node to the root of current map.
	* text is optional. it is added as node text.
	* returns node id
* http://localhost:8080/rest/v1/removeNode/{id}
	* removes the specified node
	* returns true on success
* http://localhost:8080/rest/v1/sampleNode
	* execute and see :) (in Docear)

# Suggested API

## Add a Node
        POST /addNode  

### Input
hGap  
_Optional_ **int**  
shiftY  
_Optional_ **int**  
folded  
_Optional_ **bool**  
icons  
_Optional_ **array of strings**  
prefferedChild  
_Optional_ **string**  
nodeText  
_Required_ **string**  
isHtml  
_Optional_ **int**  
defaultStyle  
_Optional_ **string**  
edgeStyle  
_Optional_ **edge**  
image  
_Optional_ **image**  
link  
_Optional_ **string**  