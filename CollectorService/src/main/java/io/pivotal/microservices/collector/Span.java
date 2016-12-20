package io.pivotal.microservices.collector;

import java.util.List;

import org.bouncycastle.asn1.crmf.PKIPublicationInfo;
import org.omg.CORBA.PRIVATE_MEMBER;

public class Span {
	private String traceId; //the trace id that the span is in
	private String id; // the span id
	private String name; // span name
	private String parentId; //parent span id
	private String timestamp; // 
	private String duration;  
	private List<Annotation> annotations;
	private List<BinaryAnnotation> binaryAnnotations;
	public String getTraceId() {
		return traceId;
	}
	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public List<Annotation> getAnnotations() {
		return annotations;
	}
	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}
	public List<BinaryAnnotation> getBinaryAnnotations() {
		return binaryAnnotations;
	}
	public void setBinaryAnnotations(List<BinaryAnnotation> binaryAnnotations) {
		this.binaryAnnotations = binaryAnnotations;
	}



}
