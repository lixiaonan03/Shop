package com.xyyy.shop.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.PreferencesUtil;
import com.xyyy.shop.toolUtil.StringUtils;

public class Welcome extends BaseActivity {
	private int newVerCode;
	private String newVerName;
	private String newApkUrl;
	private String desc;
	private int intcurrent;
	private ProgressBar progressBar_update;
	private TextView version_baifenbi;
	private TextView version_newcode;
	private TextView version_newstr;
	private Button update;
	private Button cancle;
	private DownSoft downSoft;
	private Handler handle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				// 更新
				showLoadDialog();
				break;
			case 2:
				// 不更新
				int guideInit = PreferencesUtil
						.getValue(PreferencesUtil.GUIDE_INIT);
				if (guideInit != 1) {
					// 跳转到引导页面
					Intent intent = new Intent(Welcome.this,
							GuideActivity.class);
					startActivity(intent);
					finish();
				} else {
					// 跳转到主界面的
					Intent intent = new Intent(Welcome.this, MainActivity.class);
					startActivity(intent);
					finish();
					/*
					 * * Timer timer = new Timer(); TimerTask task = new
					 * TimerTask() {
					 * 
					 * @Override public void run() {
					 * 
					 * } }; timer.schedule(task, 1000 * 1);
					 */

				}
				break;

			default:
				break;
			}
		}
	};
	private String newApkName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		// TODO 加载版本更新的内容
		intcurrent = getVersion();

		new Thread(new Runnable() { // 开启线程上传文件

					@Override
					public void run() {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						getServerVerCode();
					}
				}).start();
	}

	/**
	 * 获取服务器上apk版本号(其他信息)
	 */
	public void getServerVerCode() {

		try {
			String verjson = readContent(CommonVariable.IP
					+ "/mallService/soft/update.json");
			if (StringUtils.isNotBlank(verjson)) {
				JSONObject obj = new JSONObject(verjson);
				newVerCode = Integer.parseInt(obj.getString("verCode"));
				newVerName = obj.getString("verName");
				newApkName = obj.getString("apkname");
				newApkUrl = obj.getString("apkUrl");
				desc = obj.getString("desc");
				if (newVerCode > intcurrent) {
					// 需要版本更新
					handle.sendMessage(handle.obtainMessage(1));
				} else {
					handle.sendMessage(handle.obtainMessage(2));
				}
			} else {
				// 获取版本更新数据失败
				handle.sendMessage(handle.obtainMessage(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
			handle.sendMessage(handle.obtainMessage(2));
		}
	}

	public String readContent(String url) throws IOException {
		StringBuilder sb = new StringBuilder();
		URL getUrl = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) getUrl
				.openConnection();
		// 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
		connection.setConnectTimeout(3000);
		connection.connect();
		InputStream is = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				"UTF-8"), 8192);
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		reader.close();
		// 断开连接
		connection.disconnect();
		return sb.toString();
	}

	private void showLoadDialog() {
		final Dialog dialog = new Dialog(Welcome.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_update);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		// 进度条
		progressBar_update = (ProgressBar) dialog
				.findViewById(R.id.progressBar_update);
		// 进度条显示
		version_baifenbi = (TextView) dialog
				.findViewById(R.id.version_baifenbi);
		// 当前版本号
		version_newcode = (TextView) dialog.findViewById(R.id.version_newcode);
		version_newcode.setText("最新版本：" + newVerName);
		version_newstr = (TextView) dialog.findViewById(R.id.version_newstr);
		version_newstr.setText("更新内容：" + desc);
		update = (Button) dialog.findViewById(R.id.update);
		cancle = (Button) dialog.findViewById(R.id.cancle);

		update.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				// 更新
				int i = progressBar_update.getProgress();
				if (i == 0) {
					if (null == downSoft) {
						downSoft = new DownSoft();
						downSoft.execute();
					}
				} else {
					if (i == 100) {

					} else {
						Toast.makeText(Welcome.this, "正在下载,请稍后...", 0).show();
					}
				}
			}
		});
		cancle.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (downSoft != null) {
					downSoft.cancel(true);
				}
				dialog.dismiss();
				int guideInit = PreferencesUtil
						.getValue(PreferencesUtil.GUIDE_INIT);
				if (guideInit != 1) {
					// 跳转到引导页面
					Intent intent = new Intent(Welcome.this,
							GuideActivity.class);
					startActivity(intent);
					finish();
				} else {
					Intent intent = new Intent(Welcome.this, MainActivity.class);
					startActivity(intent);
					finish();
				}
			}
		});
		dialog.show();

	}

	public class DownSoft extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			if ("".equals(newApkUrl))
				return null;

			URL getUrl;
			try {
				getUrl = new URL(newApkUrl);
				HttpURLConnection connection = (HttpURLConnection) getUrl
						.openConnection();
				connection.connect();
				long length = connection.getContentLength();
				InputStream is = connection.getInputStream();
				FileOutputStream fileOutputStream = null;
				if (is != null) {
					File file = new File(
							Environment.getExternalStorageDirectory(),
							"shop.apk");
					if (file.exists()) {
						file.deleteOnExit();
					}
					fileOutputStream = new FileOutputStream(file);

					byte[] buf = new byte[1024];
					int ch = -1;
					int count = 0;
					while ((ch = is.read(buf)) != -1) {
						fileOutputStream.write(buf, 0, ch);
						count += ch;
						if (length > 0) {
							int d = (int) (count * 100 / length);
							publishProgress(d);
						}
					}
				}
				fileOutputStream.flush();
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(Void result) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setDataAndType(Uri.fromFile(new File(Environment
					.getExternalStorageDirectory(), "shop.apk")),
					"application/vnd.android.package-archive");
			startActivity(intent);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			progressBar_update.setProgress(values[0]);
			version_baifenbi.setText(values[0] + "%");
		}
	}

	/**
	 * 获取应用版本号
	 * 
	 * @return
	 */
	public int getVersion() {
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			int version = info.versionCode;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		StatService.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		/**
		 * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
		 * 不能与StatService.onPageStart一级onPageEnd函数交叉使用
		 */
		StatService.onPause(this);
	}
}
