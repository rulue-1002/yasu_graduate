package graduate;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StringUtil {

	/**
	 * judge ordered string is empty or null.
	 * @param str
	 * @return
	 */
	public static boolean isEmpty (String str) {
		return str == null || str.isEmpty();
	}

	/**
	 * get stacktrace string from throwable etc.
	 * @param e
	 * @return
	 */
	public static String getStackTraceStr(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		pw.flush();
		return sw.toString();

	}

}
