package com.trackorjargh.grafics;

public class NumberItemByGende {
	private String name;
	private int numItems;
	
	public NumberItemByGende() {
	}

	public NumberItemByGende(String name, int numItems) {
		this.name = name;
		this.numItems = numItems;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumItems() {
		return numItems;
	}

	public void setNumItems(int numItems) {
		this.numItems = numItems;
	}
}
