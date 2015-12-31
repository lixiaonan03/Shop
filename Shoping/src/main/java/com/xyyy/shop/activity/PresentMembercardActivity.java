package com.xyyy.shop.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.toolUtil.RegularExpression;
/**
 * 赠送会员卡的界面
 */
public class PresentMembercardActivity extends BaseActivity {
	
	private ImageView top_back;
	private TextView top_text;
	private EditText edittext;
	private Button submit;
	private ImageView img;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_presentmembercard);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text=(TextView)findViewById(R.id.top_text);
		top_text.setText("赠送会员卡");
		top_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		img=(ImageView)findViewById(R.id.people);
		img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivityForResult(new Intent(
						Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI), 0);
			}
		});
		edittext=(EditText)findViewById(R.id.editTextCode);
		submit=(Button)findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				String phone=edittext.getText().toString().trim();
				String regular=RegularExpression.checkRegularExpression(
						phone,RegularExpression.MOBILE_PHONE,"请输入正确的手机号码"
						);
				if(regular.length()>0){
					Toast.makeText(PresentMembercardActivity.this,regular, Toast.LENGTH_SHORT).show();
					return;
				}else{
					//走接口
					
				}
			}
		});
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	if (resultCode == Activity.RESULT_OK) {
    		ContentResolver reContentResolverol = getContentResolver();
 			Uri contactData = data.getData();
 			@SuppressWarnings("deprecation")
			Cursor cursor = managedQuery(contactData, null, null, null, null);
 			cursor.moveToFirst();
 			String username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
            		 null, 
            		 ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, 
            		 null, 
            		 null);
             while (phone.moveToNext()) {
            	 String usernumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                // edittext.setText(usernumber+" ("+username+")");
            	 edittext.setText(usernumber);
             }

 		}
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPause(this);
	}
}
