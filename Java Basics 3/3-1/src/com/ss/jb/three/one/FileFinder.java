package com.ss.jb.three.one;

import java.io.File;

/*
* First assignment in JavaBasics 3 
* This class prints out all the files and directories (and one sublevel) in the "Resources" folder  
* Henry Arjet 
* July 2021 Cloud Engineering
*/

public class FileFinder {

	public static void main(String[] args) {
		File f = new File ("Resources");
		File[] files = f.listFiles();
		for (File fi : files){
			//handle directories
			if (fi.isDirectory()) {
				System.out.printf("%s - contains:%n",fi.getName()); //directory name
				File[] subFiles = fi.listFiles();
				for (File g : subFiles) {
					System.out.printf("	%s%n",g.getName()); //have subfile name indented
				}
			}
			else System.out.println(fi.getName());
		}
	}
}
