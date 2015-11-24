package com.xyyy.shop.model;

import java.io.Serializable;

/**
 *访问接口 返回的基础实体
 * @author lxn
 *
 * @param <T>
 */
public class ResponseMsg<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Header header = new Header();

	private T responseBody;
	
	
	public T getResponseBody() {
		return responseBody;
	}
	
	public void setResponseBody(T responseBody) {
		this.responseBody = responseBody;
	}

	public String getRetCode() {
		return header.retCode;
	}

	public void setRetCode(String retCode) {
		header.retCode = retCode;
	}

	public String getErrorDesc() {
		return header.errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		header.errorDesc = errorDesc;
	}
	
	 private final class Header{
		
		String retCode = "200";
		 
		String errorDesc = null;
		 
	}
	
	 @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Header [retCode=");
		builder.append(getRetCode());
		builder.append(", errorDesc=");
		builder.append(getErrorDesc());
		builder.append("]");
		builder.append("   Body [");
		if(responseBody!=null) {
			builder.append(responseBody.toString());
		}
		builder.append("]");
		return builder.toString();
	}


}
