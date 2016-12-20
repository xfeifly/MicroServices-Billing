package io.pivotal.microservices.collector;

public class Annotation {
	private String timestamp;
	private String value;
	private Endpoint endpoint = new Endpoint();
	
		
	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Endpoint getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(Endpoint endpoint) {
		this.endpoint = endpoint;
	}

	class Endpoint{
		private String serviceName;
		private String ipv4;
		private String port;
		public String getServiceName() {
			return serviceName;
		}
		public void setServiceName(String serviceName) {
			this.serviceName = serviceName;
		}
		public String getIpv4() {
			return ipv4;
		}
		public void setIpv4(String ipv4) {
			this.ipv4 = ipv4;
		}
		public String getPort() {
			return port;
		}
		public void setPort(String port) {
			this.port = port;
		}
	}
}
