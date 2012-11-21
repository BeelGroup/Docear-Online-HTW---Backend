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

## Get a MapObject
    GET /Map/{id}

### Input
mapId  
_Required_ **string**

## Add a Node
    POST /addNode  

### Input
<dl>
<dt>parentNodeId</dt>
<dd><em>Required</em> <strong>int</strong></dd>

<dt>nodeText</dt>
<dd><em>Optional</em> <strong>string</strong></dd>

<dt>isHtml</dt>
<dd><em>Optional</em> <strong>bool</strong></dd>
</dl>

## Change a Node
    POST /changeNode/{id}

### Input
<dl>

<dt>nodeText</dt>
<dd><em>Optional</em> <strong>string</strong></dd>

<dt>isHtml</dt>
<dd><em>Optional</em> <strong>bool</strong></dd>

<dt>hGap</dt>
<dd><em>Optional</em> <strong>int</strong></dd>

<dt>shiftY</dt>
<dd><em>Optional</em> <strong>int</strong></dd>

<dt>folded</dt>
<dd><em>Optional</em> <strong>bool</strong></dd>

<dt>icons</dt>
<dd><em>Optional</em> <strong>array of strings</strong></dd>

<dt>prefferedChild</dt>
<dd><em>Optional</em> <strong>string</strong></dd>

<dt>defaultStyle</dt>
<dd><em>Optional</em> <strong>string</strong></dd>

<dt>edgeStyle</dt>
<dd><em>Optional</em> <strong>`edge`</strong></dd>

<dt>image</dt>
<dd><em>Optional</em> <strong>`image`</strong></dd>

<dt>link</dt>
<dd><em>Optional</em> <strong>string</strong></dd>
</dl>

## Login user
    POST /login

<dl>
<dt>username</dt>
<dd><em>Required</em> <strong>string</strong></dd>

<dt>password</dt>
<dd><em>Required</em> <strong>string</strong></dd>
</dl>

## Get user Data
    GET /getUser/{userId}  

## Get workspace Data
    GET /getWorkspace/{workspaceId}

