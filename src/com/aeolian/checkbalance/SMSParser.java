package com.aeolian.checkbalance;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSParser {
	public void find(String str) {
		String s = "�𾴵Ŀͻ�������!����12��22��21ʱ�����Ļ��������42.08Ԫ.���¿��õ�������0.50Ԫ,���е�ǰ����0.50Ԫ, ����10086ѡ��2�ż����ֻ���ֵ����ѡ�����п���ֵ����������ʱ���ʹ�����п�Ϊȫ���ƶ����뽻�ѣ����迪ͨ���������ѻ���99�ۣ���ӭʹ�á�";
		Pattern p = Pattern.compile("ʱ�����Ļ��������(.*)Ԫ");
		Matcher match = p.matcher(s);
	    while ( match.find() ) {
	    	match.group();
	    }
	}
}
