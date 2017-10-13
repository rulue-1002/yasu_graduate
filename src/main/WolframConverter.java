package graduate;

import java.math.BigDecimal;

public class WolframConverter {

	public static void main(String[] args) {

		System.out.println("------- wolfram converter start -------");
		ShapleyShubikPowerIndexService service = new ShapleyShubikPowerIndexService();
		BigDecimal result = service.calcSSPI();
		System.out.println(result);
		System.out.println("------- wolfram converter end -------");

	}

}
