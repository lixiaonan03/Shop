package com.xyyy.shop.model;

import java.io.Serializable;
import java.util.List;

public class QueryRespBean<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private PageParameter pageParameter = null; //分页参数
    
    private List<T> result = null; //返回结果集合
    
	public QueryRespBean() {
		super();
	}
	
	public QueryRespBean(PageParameter pageParameter, List<T> result) {
		super();
		this.pageParameter = pageParameter;
		this.result = result;
	}

	
	public PageParameter getPageParameter() {
		if(pageParameter==null){
			pageParameter = new PageParameter();
		}
		return pageParameter;
	}
	public void setPageParameter(PageParameter pageParameter) {
		this.pageParameter = pageParameter;
	}
	

	public List<T> getResult() {
		return result;
	}
	public void setResult(List<T> result) {
		this.result = result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueryRespBean [pageParameter=");
		builder.append(pageParameter);
		builder.append(", result=");
		builder.append(result);
		builder.append("]");
		return builder.toString();
	}


	
}
