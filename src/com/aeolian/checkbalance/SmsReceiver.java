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
					sb.append("�������ԣ�" + msg.getDisplayOriginatingAddress()
							+ "\n");
					sb.append("�������ݣ�" + msg.getMessageBody());
				}
				if (sb.toString().contains("ʱ�����Ļ��������")) {
					this.abortBroadcast();
					Toast.makeText(context, "��Ϊ����������", Toast.LENGTH_SHORT)
							.show();
					Pattern p = Pattern.compile("����\\d*��\\d*��\\d*ʱ�����Ļ��������(\\d*\\.\\d*)Ԫ");
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
					Toast.makeText(context, "����~~~", Toast.LENGTH_SHORT).show();
				}
				Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT)
						.show();

			}

		}
	}
}
