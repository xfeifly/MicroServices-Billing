package io.pivotal.microservices.collector;

import org.bouncycastle.asn1.crmf.PKIPublicationInfo;
import org.hibernate.loader.custom.Return;

import com.sun.org.apache.bcel.internal.generic.ReturnaddressType;

/**
 * 
 * @author feixu
 * This class obtains SQL command by reading properties file
 * and provides SQL for other class in this package
 */
public class SQL {
	
    
    public String checkTableExistInDB() {
        return "SELECT count(1) as rowCount FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = SCHEMA() AND UPPER(TABLE_NAME) = UPPER(?)";     
    }
    
    public String deleteZipkin_Spans(){
    	return "DELETE FROM zipkin_spans";
    }
    
    public String deleteZipkin_Annotations(){
    	return "DELETE FROM zipkin_annotations";
    }
    
    public String deleteZipkin_Dependencies(){
    	return "DELETE FROM zipkin_dependencies";
    } 
   
    public String selectAllSpans(){
    	return "SELECT * FROM zipkin_spans";
    }
    
    public String selectAllAnnotations(){
    	return "SELECT * FROM zipkin_annotations";
    }
    
    public String selectAllDependencies(){
    	return "SELECT * FROM zipkin_dependencies";
    }
    
    public String findAllDistinctTraceIds(){
    	return "SELECT DISTINCT trace_id from zipkin_spans";
    }
    
    
    
    
    
    
    
    
    public String getAllAuthorsByTheAuthor(){
    	return "SELECT Author from Articles WHERE Author LIKE ?";
    }
    public String getArticlebByTheAuthor(){
    	return "SELECT * from Articles WHERE Author LIKE ?";
    }
    public String getArticleByFullTitle(){
    	return "SELECT * from Articles WHERE Title LIKE ?";
    }
    public String getArticleByKeyWord(){
    	return "SELECT * from Articles WHERE Title LIKE ?";
    }
    public String getArticleByTwoAuthors(){
    	return "SELECT * from Articles WHERE Author LIKE ? AND Author LIKE ?";
    }
    public String getArticleByJournalAndYear(){
    	return "SELECT * from Articles WHERE Journal = ? AND Year = ?";
    }
    
}
