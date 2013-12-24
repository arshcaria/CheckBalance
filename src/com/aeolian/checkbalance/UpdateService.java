package com.aeolian.checkbalance;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class UpdateService extends Service {

	TelephonyManager tm;
	SmsManager sm;
	String MCC_MNC;
	String carrierNum;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent.getAction().equals(CBApplication.ACTION_WIDGET_REQUEST_UPDATE)) {
			prepareInfo();
			sendQuerySMS();
		}
		this.stopSelf();
		return super.onStartCommand(intent, flags, startId);
	}

	private void prepareInfo() {
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
	}

	private void sendQuerySMS() {
		if (MCC_MNC != null) {
			Toast.makeText(UpdateService.this, MCC_MNC, Toast.LENGTH_LONG).show();
		}
		Toast.makeText(UpdateService.this, MCC_MNC + " carrierNum: " + carrierNum, Toast.LENGTH_LONG).show();
		sm.sendTextMessage(carrierNum, null, "≤È—Ø”‡∂Ó", null, null);
	}
}
