package com.snapdeal.classes;

import java.util.Set;

public class CourierType {

	public Set<String> getIcaCouriers(Set<String> laneCourier ,Set<String> scoreCourier,Integer icaCourierType ){
		Set<String> courier = null;
		
		switch (icaCourierType) {
		case 1:
			
			System.out.println("score"+scoreCourier);
			laneCourier.retainAll(scoreCourier);
			try{
			System.out.println(">>"+scoreCourier);
			}
			catch(Exception e)
			{
				System.out.println(e);
				break;
			}
			break;
		case 2:
			break;
		case 3:
			break;
		}
	
	 return courier;	
	}
	
}
