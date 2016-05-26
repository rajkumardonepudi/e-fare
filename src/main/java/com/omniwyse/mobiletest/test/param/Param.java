package com.omniwyse.mobiletest.test.param;

import org.apache.poi.ss.usermodel.Cell;

public class Param {
	private String value;

	public Param(Cell cell) {
		value = String.valueOf(cell);
	}
	
	public Param() {
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
