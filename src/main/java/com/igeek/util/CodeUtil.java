package com.igeek.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 判断验证码是否正确
 */
public class CodeUtil {
	public static boolean checkVerifyCode(HttpServletRequest request) {
		String verifyCodeExpected = (String) request.getSession().getAttribute(
				com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		String verifyCodeActual = HttpServletRequestUtil.getString(request,
				"verifyCodeActual");
        //System.out.println(verifyCodeExpected);
        //System.out.println(verifyCodeActual);
		if (verifyCodeActual == null
				|| !verifyCodeActual.equalsIgnoreCase(verifyCodeExpected)) {
			return false;
		}
		return true;
	}
}
