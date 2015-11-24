package com.android.volley.listener;

import com.android.volley.VolleyError;

/**
 * 类名:		HttpSuccessListener
 * 描述:		通信成功后的接口回调  其中成功的方法会直接把请求结果给返回来 之后自己解析
 */
public interface HttpBackBaseListener {

  public void onSuccess(String string);
  /**
   * 业务失败的回调方法
   * @param failstring
   */
  public void onFail(String failstring);
  /**
   * volley框架 请求失败调用的方法
   * @param error
   */
  public void onError(VolleyError error);
}
