package com.aeolian.checkbalance;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	Button btnSendSMS;
	EditText etCarrierNum;
	
	TelephonyManager tm;
	SmsManager sm;
	String MCC_MNC;
	String carrierNum;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnSendSMS = (Button) findViewById(R.id.btnSendSMS);
		etCarrierNum = (EditText) findViewById(R.id.tvCarrierNum);
		TextView tvHello = (TextView) findViewById(R.id.hello);
		
		tvHello.setText("尊敬的客户，您好!截至12月22日21时，您的话费余额是42.08元.本月可用的赠款额度0.50元,其中当前已用0.50元, 拨打10086选择2号键“手机充值”再选择“银行卡充值”，即可随时随地使用银行卡为全国移动号码交费，无需开通网银，交费还享99折！欢迎使用。");
		sm = SmsManager.getDefault();
		tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		MCC_MNC = tm.getNetworkOperator();
		
		if (MCC_MNC.equals("46000") || MCC_MNC.equals("46002") || MCC_MNC.equals("46007")) {
			carrierNum = "10086";
		} else if (MCC_MNC.equals("46001")) {
			carrierNum = "10010";
		} else if (MCC_MNC.equals("46000")) {
			carrierNum = "10000";
		} else {
			carrierNum = "15865604965";
		}
		
		btnSendSMS.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (MCC_MNC != null) {
					Toast.makeText(MainActivity.this, MCC_MNC, Toast.LENGTH_LONG).show();
				}
				Toast.makeText(MainActivity.this, MCC_MNC + " carrierNum: " + carrierNum, Toast.LENGTH_LONG).show();
				sm.sendTextMessage(carrierNum, null, "查询余额", null, null);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
