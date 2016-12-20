
//    $.ajax('http://localhost:9411/api/v1/trace/b9ca34e0866a85ae', {
//        type: 'GET',
//        dataType: 'json'
//      }).then(data => {console.log(data)
//    });

$(document).ready(function() {
	getValues("b9ca34e0866a85ae");
});

function getValues(traceId) {
	console.log("test");
    var result = null;
  $.ajax('http://localhost:9411/api/v1/trace/' + traceId, {
  type: 'GET',
  async: 'false',
  dataType: 'json'
  	}).then(data => {
  		console.log(data);
  		result = data;
  		initD3(result);
});
}

function initD3(result) {
	console.log("initD3");
    console.log(result);
    var nodes = [];
    var links = [];
    for (var i in result) {
    	
    	if (result[i]["parentId"] != undefined) {
    		nodes.push({
    			"id" : result[i]["id"],
    			"group": parseInt(i)
    		});
    		links.push({
    			source : result[i]["parentId"],
    			target : result[i]["id"],
    			value: Math.random() * 100// would be redefine
    		});
    		links.push({
    			source : result[i]["parentId"],
    			target : result[i]["id"],
    			value: 1// would be redefine
    		})
    	} else {
    		nodes.push({
    			"id" : result[i]["id"],
    			"group": parseInt(i)
    		})
    	}
    	
    }
    console.log(nodes);
    console.log(links);

    
    var svg = d3.select("svg"),
    width = +svg.attr("width"),
    height = +svg.attr("height");

var color = d3.scaleOrdinal(d3.schemeCategory20);

var simulation = d3.forceSimulation()
    .force("link", d3.forceLink().id(function(d) { return d.id; }))
    .force("charge", d3.forceManyBody())
    .force("center", d3.forceCenter(width / 2, height / 2));

  var link = svg.append("g")
      .attr("class", "links")
    .selectAll("line")
    .data(links)
    .enter().append("line")
      .attr("stroke-width", function(d) { return Math.sqrt(d.value); });

      var node = svg.append("g")
          .attr("class", "nodes")
        .selectAll("circle")
        .data(nodes)
        .enter().append("circle")
          .attr("r", 5)
          .attr("fill", function(d) { return color(d.group); })
          .call(d3.drag()
              .on("start", dragstarted)
              .on("drag", dragged)
              .on("end", dragended));

      node.append("title")
          .text(function(d) { return d.id; });

      simulation
          .nodes(nodes)
          .on("tick", ticked);

      simulation.force("link")
          .links(links);
      function ticked() {
    	    link
    	        .attr("x1", function(d) { return d.source.x; })
    	        .attr("y1", function(d) { return d.source.y; })
    	        .attr("x2", function(d) { return d.target.x; })
    	        .attr("y2", function(d) { return d.target.y; });

    	    node
    	        .attr("cx", function(d) { return d.x; })
    	        .attr("cy", function(d) { return d.y; });
    	  }

    	function dragstarted(d) {
    	  if (!d3.event.active) simulation.alphaTarget(0.3).restart();
    	  d.fx = d.x;
    	  d.fy = d.y;
    	}

    	function dragged(d) {
    	  d.fx = d3.event.x;
    	  d.fy = d3.event.y;
    	}
    	function dragended(d) {
    		  if (!d3.event.active) simulation.alphaTarget(0);
    		  d.fx = null;
    		  d.fy = null;
    		}

}
  