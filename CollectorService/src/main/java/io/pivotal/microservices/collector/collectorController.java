package io.pivotal.microservices.collector;


import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import org.springframework.http.*;
//import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bouncycastle.asn1.crmf.PKIPublicationInfo;
import org.hibernate.loader.custom.Return;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.omg.CORBA.PUBLIC_MEMBER;

import io.pivotal.microservices.exceptions.AccountNotFoundException;

@RestController
public class collectorController {
//	protected Logger logger = Logger.getLogger(eBusinessController.class
//			.getName());
	private static final Log logger = LogFactory.getLog(collectorController.class);
	
	private Random random = new Random();
	private DataBaseUtil dbUtil;
	private parseFromDB DBparser;
	
	public collectorController() {
		dbUtil = new DataBaseUtil();
	}
	
	//delet all data from local db
	@RequestMapping("/collector/deleteAllData")
	public void deleteAllDataFromDB(){
		logger.info("ready to delete all data from local db");
		try{
			Connection conn = dbUtil.getConnection();
			//delete spans;
			PreparedStatement stmt = conn.prepareStatement(new SQL().deleteZipkin_Spans());
			stmt.executeUpdate();
			//delete annotations;
			stmt = conn.prepareStatement(new SQL().deleteZipkin_Annotations());
			stmt.executeUpdate();
			//delete dependencies
			stmt = conn.prepareStatement(new SQL().deleteZipkin_Dependencies());
			stmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	//retrive al data from database
	@RequestMapping("/collector/retriveAllSpans")
	public void getAllSpansFromDB(){
		logger.info("In the collector controller! Ready to fetch all spans from zipkin");
		try {//go through the authors column
				Connection conn = dbUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(new SQL().selectAllSpans());
				ResultSet rs = stmt.executeQuery();
				int counter = 0;
				while(rs.next()) {
					String item = rs.getString(2);
					logger.info(counter + ": " + item);
	//            	for(String s: item){
	//    				logger.info(counter + ": " + s);
	//            	}
	            	counter++;
				} 
		} catch (Exception e) {
          e.printStackTrace();
		}
	}
	
	@RequestMapping("/collector/retriveAllAnnotations")
	public void getAllAnnotationsFromDB(){
		logger.info("In the collector controller! Ready to fetch all annotations from zipkin");

	}
	
	@RequestMapping("/collector/retriveAllDependencies")
	public void getAllDependencies(){
		logger.info("In the collector controller! Ready to fetch all dependencies from zipkin");
	}
	
	@RequestMapping(value="/collector/generateFirstTrace", method = RequestMethod.GET, produces = "application/json")
	public String findFirstTraceJSON(){
		JSONObject trace = new JSONObject();
		
		List<String> traceIds = findAllTraceIds();
		for(String str : traceIds){
			System.out.println(traceIds.get(0));			
		}
		
		String firstTraceId = traceIds.get(0);
		JSONArray traceJson = createTraceJSONFromTraceID(firstTraceId);
		//write JSON object to test.json file
		writeToJSONFile(traceJson);
		
		return traceJson.toString();
	}
	
	//write json object to output test.json file
	public void writeToJSONFile(JSONArray json){
		try {
			FileWriter file = new FileWriter("test.json");
			file.write(json.toString());
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//find all distinct trace-IDs
	public List<String> findAllTraceIds(){
		List<String> traceIds = new ArrayList<String>();
		try{
			Connection conn = dbUtil.getConnection();
			PreparedStatement stmt = conn.prepareStatement(new SQL().findAllDistinctTraceIds());
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				String traceId = rs.getString(1);
				traceIds.add(traceId);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return traceIds;
	}
	
	//////// create JSONarray of spans as a Trace//////
	public JSONArray createTraceJSONFromTraceID(String traceID){
		JSONArray traceJSON = new JSONArray();		
		DBparser = new parseFromDB();
		List<Span> spans = DBparser.getSpanTree(traceID);
		for(Span span : spans){
			traceJSON.add(createSpanJSON(span));
		}
		return traceJSON;	
	}
	
	// create and return a Annotation JSON object
	@SuppressWarnings("unchecked")
	public JSONObject creatAnnotationJSON(Annotation ann){
		JSONObject annJSON = new JSONObject();
		annJSON.put("timestamp", ann.getTimestamp());
		annJSON.put("value", ann.getValue());
		
		JSONObject annEndpoint = new JSONObject();
		annEndpoint.put("serviceName", ann.getEndpoint().getServiceName());
		annEndpoint.put("ipv4", ann.getEndpoint().getIpv4());
		annEndpoint.put("port", ann.getEndpoint().getPort());
		
		annJSON.put("endpoint", annEndpoint);
		
		return annJSON;

	}
	//create and return a BinaryAnnotation JSON object
	@SuppressWarnings("unchecked")
	public JSONObject createBinaryAnnotationJSON(BinaryAnnotation bAnn){
		JSONObject binaryAnnJSON = new JSONObject();
		binaryAnnJSON.put("key", bAnn.getKey());
		binaryAnnJSON.put("value", bAnn.getValue());
		
		JSONObject bAnnEndpoint = new JSONObject();
		bAnnEndpoint.put("serviceName", bAnn.getEndpoint().getServiceName());
		bAnnEndpoint.put("ipv4", bAnn.getEndpoint().getIpv4());
		bAnnEndpoint.put("port", bAnn.getEndpoint().getPort());
		
		binaryAnnJSON.put("endpoint", bAnnEndpoint);
		
		return binaryAnnJSON;
	}
	//return a span as JSON object
	@SuppressWarnings("unchecked")
	public JSONObject createSpanJSON(Span span){
		JSONObject spanJSON = new JSONObject();
		
		spanJSON.put("traceId", span.getTraceId());
		spanJSON.put("id", span.getId());
		spanJSON.put("name", span.getName());
		spanJSON.put("timestamp", span.getTimestamp());
		spanJSON.put("duration", span.getDuration());
		
		
		//add annotation array to the span JSON
		JSONArray annJSONArray = new JSONArray();
		for(Annotation annotation : span.getAnnotations()){
			annJSONArray.add(creatAnnotationJSON(annotation));
		}
		if(!span.getAnnotations().isEmpty()){
			spanJSON.put("annotations", annJSONArray);			
		}
		
		//add binaryAnnotation Array to the span JSON
		JSONArray binaryAnnJSONArray = new JSONArray();
		for(BinaryAnnotation binaryAnnotation : span.getBinaryAnnotations()){
			binaryAnnJSONArray.add(createBinaryAnnotationJSON(binaryAnnotation));
		}
		
		if(!span.getBinaryAnnotations().isEmpty()){
			spanJSON.put("binaryAnnotations", binaryAnnJSONArray);
		}
		
		return spanJSON;
	}
}
