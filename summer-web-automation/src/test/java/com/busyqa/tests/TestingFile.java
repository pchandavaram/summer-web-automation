package com.busyqa.tests;

import java.util.Random;

public class TestingFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		Random rn = new Random();

		for(int i =0; i < 100; i++)
		{
		    int answer = rn.nextInt(10) + 1;
		    System.out.println(answer);
		}

	}

}
