package com.aeolian.checkbalance;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction()
				.equals("android.provider.Telephony.SMS_RECEIVED")) {
			StringBuffer sb = new StringBuffer();
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				Object[] pdus = (Object[]) bundle.get("pdus");
				SmsMessage[] msgs = new SmsMessage[pdus.length];
				for (int i = 0; i < pdus.length; i++) {
					msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				}
				for (SmsMessage msg : msgs) {
					sb.append("短信来自：" + msg.getDisplayOriginatingAddress()
							+ "\n");
					sb.append("短信内容：" + msg.getMessageBody());
				}
				if (sb.toString().contains("时，您的话费余额是")) {
					this.abortBroadcast();
					Toast.makeText(context, "此为话费余额短信", Toast.LENGTH_SHORT)
							.show();
					Pattern p = Pattern.compile("截至\\d*月\\d*日\\d*时，您的话费余额是(\\d*\\.\\d*)元");
					Matcher match = p.matcher(sb.toString());
					String r = "";
					
					while (match.find()) {
						r += match.group(1);
					}
					
					Toast.makeText(context, r, Toast.LENGTH_LONG).show();
					
					Intent i = new Intent();
					i.setAction(CBApplication.ACTION_UPDATE_WIDGET);
					i.putExtra("BALANCE", r);
					context.sendBroadcast(i);
				} else {
					Toast.makeText(context, "不是~~~", Toast.LENGTH_SHORT).show();
				}
				Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT)
						.show();

			}

		}
	}
}
