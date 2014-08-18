package com.hcb.web.wrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 解析post请求的消息体到request的parameter对象中
 * <br>post请求的消息体为json格式
 * @author huangcangbai
 *
 */
public class PostParserHttpServletRequestWrapper extends
		HttpServletRequestWrapper {

    private Map<String, String> parameters = new HashMap<String, String>();
    private ObjectMapper objectMapper = new ObjectMapper();
    
	public PostParserHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		try {
			//解析post请求的消息体到request的parameter对象中
            this.parseParameters();
        } catch (IOException e) {
            throw new RuntimeException("PostParserHttpServletRequestWrapper IOException", e);
        }   
	}

    /**
     * 解析post请求的消息体到request的parameter对象中
     * post请求的消息体为json格式
     * @throws IOException
     */
    protected void parseParameters() throws IOException {
        String method = this.getHttpServletRequest().getMethod();
        StringBuilder jsonstr = new StringBuilder();
        //只处理POST请求
        if (method.equalsIgnoreCase("POST")) {
            String line;
            BufferedReader reader = getReader();
            while ((line = reader.readLine()) != null) {
                jsonstr.append(line);
            }
        } else {
        	return;
        }
        
        if (jsonstr.length() > 0) {
            JsonNode node = objectMapper.readTree(jsonstr.toString());
            Iterator<String> fieldNames = node.getFieldNames();
            for (; fieldNames.hasNext();) {
                String key = fieldNames.next();
                String value = node.get(key).toString();
                if (value.length() > 2 && value.startsWith("\"")) {
                    parameters.put(key, value.substring(1, value.length() - 1));
                } else {
                    parameters.put(key, value);
                }
            }
        }
    }
    
    private HttpServletRequest getHttpServletRequest() {
    	return (HttpServletRequest)super.getRequest();
    }
    
    public String getParameter(String name) {
        return (String) parameters.get(name);
    }

    public String[] getParameterValues(String name) {
        String value = getParameter(name);
        if (value != null) {
            return new String[] { value };
        }
        return null;
    }

    public Map<String, String> getParameterMap() {
        return parameters;
    }


}
