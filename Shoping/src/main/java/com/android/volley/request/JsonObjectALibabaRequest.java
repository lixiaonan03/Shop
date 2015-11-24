package com.android.volley.request;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

public  class JsonObjectALibabaRequest extends Request<JSONObject> {
	private Listener<JSONObject>         listener;
	private String jsonRequest;
	public JsonObjectALibabaRequest(int method, String url,
			ErrorListener listener) {
		super(method, url, listener);
		// TODO Auto-generated constructor stub
	}
	 public JsonObjectALibabaRequest(int method, String url,String jsonRequest,Listener<JSONObject> listener,
	            ErrorListener errorListener) {
	        super(method, url,errorListener);
	        this.jsonRequest=jsonRequest;
	        this.listener = listener;
	    }

	@Override
	protected Response<JSONObject> parseNetworkResponse(
			NetworkResponse paramNetworkResponse) {
		
			     String jsonString;
				try {
					jsonString = new String(paramNetworkResponse.data, HttpHeaderParser.parseCharset(paramNetworkResponse.headers));
					return Response.success(JSON.parseObject(jsonString), 
					         HttpHeaderParser.parseCacheHeaders(paramNetworkResponse));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
	}

	@Override
	protected void deliverResponse(JSONObject paramT) {
		// TODO Auto-generated method stub
		listener.onResponse(paramT);
	}
	@Override
    public byte[] getBody() throws AuthFailureError  {
        try {
			return jsonRequest == null ? super.getBody() : jsonRequest.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			 VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", new Object[] { 
					        this.jsonRequest, "utf-8" }); 
		}
		return super.getBody();
    }

}
