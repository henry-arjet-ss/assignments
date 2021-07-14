package com.ss.lms.service;

//simple struct for use in takeInputOptions


public class InputOption {
	public InputOption(int index, String text) {
		this.index = index;
		this.text = text;
	}
	public int index; //starts at 1
	public String text; //everything after 'X) '
}
