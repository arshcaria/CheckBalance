package com.aeolian.checkbalance;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSParser {
	public void find(String str) {
		String s = "尊敬的客户，您好!截至12月22日21时，您的话费余额是42.08元.本月可用的赠款额度0.50元,其中当前已用0.50元, 拨打10086选择2号键“手机充值”再选择“银行卡充值”，即可随时随地使用银行卡为全国移动号码交费，无需开通网银，交费还享99折！欢迎使用。";
		Pattern p = Pattern.compile("时，您的话费余额是(.*)元");
		Matcher match = p.matcher(s);
	    while ( match.find() ) {
	    	match.group();
	    }
	}
}
