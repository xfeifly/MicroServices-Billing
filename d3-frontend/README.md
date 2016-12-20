# d3_force_directed_graph_for_microservices
Spring-boot-applicaiton for visualizing the Span Data with D3.js

Usage:
1.Have to Choose the traceId first, could get from the zipkinUI or databases;
2.Modify the getValues() method with corresponding traceId in script.js;
3.Run the Service;

Note: 
1. sometimes have to clear the browser caches 
2. includes multiple APIs for practising. The endpoint for this specific project is "/processmap"