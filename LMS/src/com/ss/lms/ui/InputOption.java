package com.ss.lms.ui;

//Smoothstack Essentials LMS project
//Henry Arjet - July Cloud Engineering
//A simple struct for use in BaseUI.takeInputOptions

public class InputOption {
	public InputOption(int index, String text) {
		this.index = index;
		this.text = text;
	}
	public int index; //starts at 1
	public String text; //everything after 'X) '
}
