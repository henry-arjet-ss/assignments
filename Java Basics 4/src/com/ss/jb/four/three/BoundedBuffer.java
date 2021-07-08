package com.ss.jb.four.three;
/*
* Third assignment in JavaBasics 4 
* Producer-consumer problem
* In this implementation, I take the numbers 1-100, square them, and then write them to the console
* The buffer allows the squaring and the writing to be split into two threads
* Henry Arjet 
* July 2021 Cloud Engineering
*/
public class BoundedBuffer {
	private static final int bufferSize = 7; //only an array of 7 values
	private static volatile int head = 0; //how many slots of the array are filled. at 0 no slots have been filled, at 7 the sixth slot has been filled
		//this property SHALL NOT be manipulated without first locking the buffer
	private static volatile int[] buffer = new int[bufferSize]; //the bounded buffer - used as a stack
	 
	private static void pushBuffer(int value) {//pushes an int onto the stack
		//not thread safe at all, nor index safe - relies on outside checks
		buffer[head] = value;
		head++;
	}
	private static int popBuffer() {//pops an int from the stack
		//not thread safe at all, nor index safe - relies on outside checks
		--head;
		return buffer[head];
	}
	
	private static volatile Boolean hasData = false; //keeps track of whether the stack has data and therefore whether it can be read by the consumer
	private static volatile Boolean hasSpace = true; //keeps track of whether the stack has space and therefore whether it can be written to by the producer
	private static volatile Boolean done = false; //tells consumer that it will get no more data
	
	public static void main(String[] args) {
		Runnable producer = new Runnable(){
			public void run() {
				for (int i = 1; i <= 100; i++ ) {//goes through the first 100 numbers, squares them, and writes them to the buffer
					int square = i * i;
					if (!hasSpace){//wait for space on the stack
						try {
							synchronized(hasSpace) {
								System.out.println("consumer waiting"); 
								hasSpace.wait();
							}
							
							
						} catch (InterruptedException e) {
							System.out.println("Whoops!");
						}
					}
					//If I include this sleep, the consumer pulls as soon as the producer pushes. If I dont,
						//the consumer doesn't awaken until the stack is full.
						//this makes me think that the 'notify' command takes a while to actually send
//					try { 
//						Thread.sleep(1);
//					} catch (InterruptedException e) {}
					
					//by this point we know there is space on the stack, so we may now write to it
					synchronized(buffer){
						pushBuffer(square);
						System.out.println("pushing");
						if (i == 100) done = true; //set it while buffer is still locked so we have no race condition
						synchronized(hasData) {//
							hasData.notify();
							hasData = true;
						}
						if (head == bufferSize) synchronized(hasSpace) {hasSpace = false;} //make sure we haven't run out of space 
					}
				}
				
			}
		};
		
		Runnable consumer = new Runnable(){
			public void run() {
				while (true) { //keeps trying to print until it hits the return condition (done and out of data)
					if (!hasData){//wait for data
						if(done) return; //if no data and no incoming data, we may end the thread
						try {
							synchronized(hasData) {
								System.out.println("consumer waiting");
								hasData.wait();

								System.out.println("consumer done waiting");
							}
							
						} catch (InterruptedException e) {
							System.out.println("Whoops!");
						}
					}
					//by this point we know there is data on the stack, so we may now read from it
					synchronized(buffer){
						System.out.println(popBuffer()); //print from buffer
						synchronized(hasSpace) { //lock it so we can notify and set hasSpace to true 
							hasSpace.notify(); //let the consumer know it now has data
							hasSpace = true;
						}  
						if (head == 0) synchronized(hasData) {hasData = false;} //make sure we haven't run out of data
					}
				}
			}
		};
		
		new Thread(producer).start();
		new Thread(consumer).start();
	}

}
