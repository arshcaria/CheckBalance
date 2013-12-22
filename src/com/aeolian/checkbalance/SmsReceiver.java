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
		// 判断是系统短信；
		if (intent.getAction()
				.equals("android.provider.Telephony.SMS_RECEIVED")) {
			// 不再往下传播；
			this.abortBroadcast();
			StringBuffer sb = new StringBuffer();
			String sender = null;
			String content = null;
			String sendtime = null;
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				// 通过pdus获得接收到的所有短信消息，获取短信内容；
				Object[] pdus = (Object[]) bundle.get("pdus");
				// 构建短信对象数组；
				SmsMessage[] msgs = new SmsMessage[pdus.length];
				for (int i = 0; i < pdus.length; i++) {
					// 获取单条短信内容，以pdu格式存,并生成短信对象；
					msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				}
				for (SmsMessage msg : msgs) {
					sb.append("短信来自：" + msg.getDisplayOriginatingAddress()
							+ "\n");
					sb.append("短信内容：" + msg.getMessageBody());

					sender = msg.getDisplayOriginatingAddress();// 获取短信的发送者
					content = msg.getMessageBody();// 获取短信的内容

					// Date date = new Date(mge.getTimestampMillis());
					// SimpleDateFormat format = new SimpleDateFormat(
					// "yyyy-MM-dd HH:mm:ss");
					// sendtime = format.format(date);// 获取短信发送时间；
					// SmsManager manager = SmsManager.getDefault();
					// manager.sendTextMessage("5556",
					// null,"发送人:"+sender+"-----发送时间:"+sendtime+"----内容:"+content,
					// null, null);//把拦截到的短信发送到指定的手机，此处为5556;
					// if ("5556".equals(sender)){
					// //屏蔽手机号为5556的短信，这里还可以时行一些处理，如把该信息发送到第三人的手机等等。
					// abortBroadcast();
					// }
				}
				if (sb.toString().contains("时，您的话费余额是")) {
					Toast.makeText(context, "此为话费余额短信", Toast.LENGTH_SHORT)
							.show();
					CBApplication.msgContent = sb.toString();
				} else {
					Toast.makeText(context, "不是~~~", Toast.LENGTH_SHORT).show();
				}
				Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT)
						.show();

				String s = "尊敬的客户，您好!截至12月22日21时，您的话费余额是42.08元.本月可用的赠款额度0.50元,其中当前已用0.50元, 拨打10086选择2号键“手机充值”再选择“银行卡充值”，即可随时随地使用银行卡为全国移动号码交费，无需开通网银，交费还享99折！欢迎使用。";
				Pattern p = Pattern.compile("时，您的话费余额是(\\d*\\.\\d*)元");
				Matcher match = p.matcher(s);
				String r = "";
				while (match.find()) {
					r += match.group(1);
				}
				
				Toast.makeText(context, r, Toast.LENGTH_LONG).show();

			}

		}
	}
}
