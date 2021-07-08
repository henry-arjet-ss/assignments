package com.ss.jb.three.two;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
/*
* Second assignment in JavaBasics 3 
* This class appends text to a file
* Specifically Infantry Columns (AKA Boots) by Rudyard Kipling
* Which is a very good poem if you ignore his other work 
* Henry Arjet 
* July 2021 Cloud Engineering
*/
public class appender {

	public static void main(String[] args) {
		
		//Don't--don't--don't--don't--look at what's in front of you.
		
		try(FileReader fr = new FileReader("resources/Boots.txt")){
			BufferedReader in = new BufferedReader(fr);//we break this up into two lines so we can later ask fr for length
			
			try(BufferedWriter out = new BufferedWriter(new FileWriter("resources/out.txt", true))){ //second argument for FileWriter sets append
				
				//(Boots--boots--boots--boots--movin' up an' down again);
				
				String line;
				while ((line = in.readLine()) != null) {
					out.write(line + '\n');
					
					//Men--men--men--men--men go mad with watchin' em,
				
				}
				out.write("\n--------------------------------------------------------------------\n\n");
			}
		}
		catch(Exception e) {
			System.out.println("Whoops!");
		}
	}
}
//		An' there's no discharge in the war!