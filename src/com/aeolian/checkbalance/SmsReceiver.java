package com.aeolian.checkbalance;

import java.text.SimpleDateFormat;
import java.util.Date;
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
		// �ж���ϵͳ���ţ�
		if (intent.getAction()
				.equals("android.provider.Telephony.SMS_RECEIVED")) {
			StringBuffer sb = new StringBuffer();
			String sender = null;
			String content = null;
			String sendtime = null;
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				// ͨ��pdus��ý��յ������ж�����Ϣ����ȡ�������ݣ�
				Object[] pdus = (Object[]) bundle.get("pdus");
				// �������Ŷ������飻
				SmsMessage[] msgs = new SmsMessage[pdus.length];
				for (int i = 0; i < pdus.length; i++) {
					// ��ȡ�����������ݣ���pdu��ʽ��,�����ɶ��Ŷ���
					msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				}
				for (SmsMessage msg : msgs) {
					sb.append("�������ԣ�" + msg.getDisplayOriginatingAddress()
							+ "\n");
					sb.append("�������ݣ�" + msg.getMessageBody());

					sender = msg.getDisplayOriginatingAddress();// ��ȡ���ŵķ�����
					content = msg.getMessageBody();// ��ȡ���ŵ�����

					// Date date = new Date(mge.getTimestampMillis());
					// SimpleDateFormat format = new SimpleDateFormat(
					// "yyyy-MM-dd HH:mm:ss");
					// sendtime = format.format(date);// ��ȡ���ŷ���ʱ�䣻
					// SmsManager manager = SmsManager.getDefault();
					// manager.sendTextMessage("5556",
					// null,"������:"+sender+"-----����ʱ��:"+sendtime+"----����:"+content,
					// null, null);//�����ص��Ķ��ŷ��͵�ָ�����ֻ����˴�Ϊ5556;
					// if ("5556".equals(sender)){
					// //�����ֻ���Ϊ5556�Ķ��ţ����ﻹ����ʱ��һЩ������Ѹ���Ϣ���͵������˵��ֻ��ȵȡ�
					// abortBroadcast();
					// }
				}
				if (sb.toString().contains("ʱ�����Ļ��������")) {
					this.abortBroadcast();
					Toast.makeText(context, "��Ϊ����������", Toast.LENGTH_SHORT)
							.show();
					CBApplication.msgContent = sb.toString();
					Pattern p = Pattern.compile("����\\d*��\\d*��\\d*ʱ�����Ļ��������(\\d*\\.\\d*)Ԫ");
					Matcher match = p.matcher(sb.toString());
					String r = "";
					
					while (match.find()) {
						r += match.group(1);
					}
					
					Toast.makeText(context, r, Toast.LENGTH_LONG).show();
					
					Intent i = new Intent();
					i.setAction("ACTION_UPDATE_WIDGET");
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
