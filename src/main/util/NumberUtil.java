package main.util;

public class NumberUtil {

	/**
	 * 階乗の計算をして結果を返します。
	 * @param num
	 * @return
	 */
	public static Long calcFactorial (Long num) {
		Long factorial = 1L;
		for (int i=1; i<=num.intValue(); i++) {
			factorial = factorial * i;
		}
		return factorial;
	}
}
