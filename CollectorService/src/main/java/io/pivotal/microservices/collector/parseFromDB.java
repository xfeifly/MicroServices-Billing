package io.pivotal.microservices.collector;

//annotations
//+---------------+--------------------+----------------------+-----------------------+---------------------------+--------+------------------+---------------+---------------+---------------+-----------------------+
//| trace_id_high | trace_id           | span_id              | a_key                 | a_value                   | a_type | a_timestamp      | endpoint_ipv4 | endpoint_ipv6 | endpoint_port | endpoint_service_name |
//+---------------+--------------------+----------------------+-----------------------+---------------------------+--------+------------------+---------------+---------------+---------------+-----------------------+
//|             0 | 276904906926531694 |   674394126391518659 | lc                    | unknown                   |      6 | 1481578022896000 |    2130706433 | NULL          |          3380 | testsleuthzipkin      |
//|             0 | 276904906926531694 |   674394126391518659 | mvc.controller.class  | SampleController          |      6 | 1481578022896000 |    2130706433 | NULL          |          3380 | testsleuthzipkin      |
//|             0 | 276904906926531694 |   674394126391518659 | mvc.controller.method | hi2                       |      6 | 1481578022896000 |    2130706433 | NULL          |          3380 | testsleuthzipkin      |
//|             0 | 276904906926531694 |   674394126391518659 | random-sleep-millis   | 127                       |      6 | 1481578022896000 |    2130706433 | NULL          |          3380 | testsleuthzipkin      |
//|             0 | 276904906926531694 | -7826938718312336265 | cs                    | NULL                      |     -1 | 1481578022883000 |    2130706433 | NULL          |          3380 | testsleuthzipkin      |
//|             0 | 276904906926531694 | -7826938718312336265 | cr                    | NULL                      |     -1 | 1481578023034000 |    2130706433 | NULL          |          3380 | testsleuthzipkin      |
//|             0 | 276904906926531694 | -7826938718312336265 | http.host             | localhost                 |      6 | 1481578022882000 |    2130706433 | NULL          |          3380 | testsleuthzipkin      |
//|             0 | 276904906926531694 | -7826938718312336265 | http.method           | GET                       |      6 | 1481578022882000 |    2130706433 | NULL          |          3380 | testsleuthzipkin      |
//|             0 | 276904906926531694 | -7826938718312336265 | http.path             | /hi2                      |      6 | 1481578022882000 |    2130706433 | NULL          |          3380 | testsleuthzipkin      |
//|             0 | 276904906926531694 | -7826938718312336265 | http.url              | http://localhost:3380/hi2 |      6 | 1481578022882000 |    2130706433 | NULL          |          3380 | testsleuthzipkin      |
//|             0 | 276904906926531694 | -7826938718312336265 | sa                    |                          |      0 | 1481578022882000 |    2130706433 | NULL          |          3380 | testsleuthzipkin      |
//|             0 | 276904906926531694 | -7826938718312336265 | sr                    | NULL                      |     -1 | 1481578022894000 |    2130706433 | NULL          |          3380 | testsleuthzipkin      |
//|             0 | 276904906926531694 | -7826938718312336265 | ss                    | NULL                      |     -1 | 1481578023036000 |    2130706433 | NULL          |          3380 | testsleuthzipkin      |
//|             0 | 276904906926531694 |  7758751760852786882 | lc                    | unknown                   |      6 | 1481578022277000 |    2130706433 | NULL          |          3380 | testsleuthzipkin      |
//|             0 | 276904906926531694 |  7758751760852786882 | mvc.controller.class  | SampleController          |      6 | 1481578022277000 |    2130706433 | NULL          |          3380 | testsleuthzipkin      |
//|             0 | 276904906926531694 |  7758751760852786882 | mvc.controller.method | hi                        |      6 | 1481578022277000 |    2130706433 | NULL          |          3380 | testsleuthzipkin      |
//|             0 | 276904906926531694 |   276904906926531694 | sr                    | NULL                      |     -1 | 1481578022261000 |    2130706433 | NULL          |          3380 | testsleuthzipkin      |
//|             0 | 276904906926531694 |   276904906926531694 | ss                    | NULL                      |     -1 | 1481578023038000 |    2130706433 | NULL          |          3380 | testsleuthzipkin      |
//+---------------+--------------------+----------------------+-----------------------+---------------------------+--------+------------------+---------------+---------------+---------------+-----------------------+


//spans
//+---------------+--------------------+----------------------+-----------+----------------------+-------+------------------+----------+
//| trace_id_high | trace_id           | id                   | name      | parent_id            | debug | start_ts         | duration |
//+---------------+--------------------+----------------------+-----------+----------------------+-------+------------------+----------+
//|             0 | 276904906926531694 | -7826938718312336265 | http:/hi2 |  7758751760852786882 | NULL  | 1481578022882000 |   151000 |
//|             0 | 276904906926531694 |   276904906926531694 | http:/    |                 NULL | NULL  | 1481578022260000 |   778774 |
//|             0 | 276904906926531694 |   674394126391518659 | hi2       | -7826938718312336265 | NULL  | 1481578022896000 |   135590 |
//|             0 | 276904906926531694 |  7758751760852786882 | hi        |   276904906926531694 | NULL  | 1481578022277000 |   760186 |
//+---------------+--------------------+----------------------+-----------+----------------------+-------+------------------+----------+

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class parseFromDB {
	
	DataBaseUtil dbUtil = new DataBaseUtil();
	//TODO: return a list of span ID according to the input traceID
	
	public List<Span> getSpanTree(String TraceId){
		List<Span> spanTree = new ArrayList<Span>();
		int numberOfLines = 0;
		//Get the number of spans with the same traceId
		try{
			Connection conn = dbUtil.getConnection();
			PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(1) FROM zipkin_spans WHERE trace_id = " + TraceId);	
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				numberOfLines = rs.getInt(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println("Number of spans with this traceId: " + numberOfLines);
		
		String PARENT_ID = null;
		while(numberOfLines > 0){
			if(spanTree.size() == 0){
				try{
					Connection conn = dbUtil.getConnection();
					PreparedStatement stmt = conn.prepareStatement("SELECT * FROM zipkin_spans WHERE trace_id = " + TraceId + " AND parent_id is NULL");
					ResultSet rs = stmt.executeQuery();
					while(rs.next()){
						String traceId = rs.getString(2);
						String spanId = rs.getString(3);
						String name = rs.getString(4);
					    String curparentId = rs.getString(5);
						String timestamp = rs.getString(7);
						String duration = rs.getString(8);
						PARENT_ID = spanId;
						Span cur = creatSpanById(traceId, spanId, curparentId, name, timestamp, duration);
						spanTree.add(cur);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				try{
					Connection conn = dbUtil.getConnection();
					PreparedStatement stmt = conn.prepareStatement("SELECT * FROM zipkin_spans WHERE trace_id = " + TraceId + " AND parent_id = " + PARENT_ID);
					ResultSet rs = stmt.executeQuery();
					while(rs.next()){
						String traceId = rs.getString(2);
						String spanId = rs.getString(3);
						String name = rs.getString(4);
					    String curparentId = rs.getString(5);
						String timestamp = rs.getString(7);
						String duration = rs.getString(8);
						PARENT_ID = spanId;
						Span cur = creatSpanById(traceId, spanId, curparentId, name, timestamp, duration);
						spanTree.add(cur);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
			numberOfLines--;
		}
		return spanTree;
		
	}
	
	
	//TODO: create and return the span according to spanID
	public Span creatSpanById(String traceId, String SpanId, String curparentId, String name, String timestamp, String duration){
		Span itemSpan = new Span();
		itemSpan.setTraceId(traceId);
		itemSpan.setId(SpanId);
		itemSpan.setDuration(duration);
		itemSpan.setName(name);
		itemSpan.setParentId(curparentId);
		itemSpan.setTimestamp(timestamp);
		List<Annotation> annotations = createAnnotationListBySpanId(itemSpan.getTraceId(), itemSpan.getId());
		List<BinaryAnnotation> binaryAnnotations = createBinaryAnnotationListBySpanId(itemSpan.getTraceId(), itemSpan.getId());
		itemSpan.setAnnotations(annotations);
		itemSpan.setBinaryAnnotations(binaryAnnotations);
		System.out.println("curSpan: " + itemSpan.getId() + "duration: " + itemSpan.getDuration());
		
		return itemSpan;
	}
	
	public List<Annotation> createAnnotationListBySpanId(String traceId, String spanId){
		List<Annotation> annotations = new ArrayList<Annotation>();
		try{
			Connection conn = dbUtil.getConnection();
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM zipkin_annotations WHERE trace_id = " + traceId + " AND span_id =" + spanId + " AND a_type = -1 ORDER BY a_timestamp");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				Annotation item = new Annotation();
				String timestamp = rs.getString(7);
				item.setTimestamp(timestamp);
				String value = rs.getString(4);
				item.setValue(value);
				String serviceName = rs.getString(11);
				item.getEndpoint().setServiceName(serviceName);
				String ipv4 = "127.0.0.1";
				item.getEndpoint().setIpv4(ipv4);
				String port = rs.getString(10);
				item.getEndpoint().setPort(port);
				annotations.add(item);
				System.out.println("Current annotation timestamp: " + item.getTimestamp());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return annotations;
	}
	
	public List<BinaryAnnotation> createBinaryAnnotationListBySpanId(String traceId, String spanId){
		List<BinaryAnnotation> binaryAnnotations = new ArrayList<BinaryAnnotation>();
		try{
			Connection conn = dbUtil.getConnection();
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM zipkin_annotations WHERE trace_id = " + traceId + " AND span_id =" + spanId + " AND a_type = 6");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				BinaryAnnotation item = new BinaryAnnotation();
				String key = rs.getString(4);
				item.setKey(key);
				String value = rs.getString(5);
				item.setValue(value);;
				String serviceName = rs.getString(11);
				item.getEndpoint().setServiceName(serviceName);
				String ipv4 = "127.0.0.1";
				item.getEndpoint().setIpv4(ipv4);
				String port = rs.getString(10);
				item.getEndpoint().setPort(port);
				binaryAnnotations.add(item);
				System.out.println("Current BinaryAnnotation: " + item.getValue());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			Connection conn = dbUtil.getConnection();
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM zipkin_annotations WHERE trace_id = " + traceId + " AND span_id =" + spanId + " AND a_type = 0");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				BinaryAnnotation item = new BinaryAnnotation();
				String key = rs.getString(4);
				item.setKey(key);
				item.setValue("true");
				String serviceName = rs.getString(11);
				item.getEndpoint().setServiceName(serviceName);
				String ipv4 = "127.0.0.1";
				item.getEndpoint().setIpv4(ipv4);
				String port = rs.getString(10);
				item.getEndpoint().setPort(port);
				binaryAnnotations.add(item);
				System.out.println("Current BinaryAnnotation: " + item.getValue());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return binaryAnnotations;
	}
	
	

}
