package main;

import main.service.ShapleyShubikPowerIndexService;

public class WolframConverter {

	public static void main(String[] args) {

		System.out.println("------- wolfram converter start -------");
		ShapleyShubikPowerIndexService service = new ShapleyShubikPowerIndexService();
		service.calcSSPI();
		System.out.println("------- wolfram converter end -------");

	}

}
