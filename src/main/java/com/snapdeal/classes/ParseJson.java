package com.snapdeal.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.snapdeal.dao.MysqlDAO;
import com.snapdeal.entity.CourierDetails;
import com.snapdeal.entity.CriteriaJson;
import com.snapdeal.entity.SuborderDetails;

public class ParseJson {

	public String getData(List<SuborderDetails> sdListwithRequest) throws ParseException{
		
		Set<String> ruleCourier = new HashSet<String>();
		List<CourierDetails> cd = new ArrayList<CourierDetails>();
		Set <String> scoreCourier = null;
		
		if (sdListwithRequest.size() > 0){
			for(SuborderDetails slr :sdListwithRequest){
					if(slr.getShippingMethod()!=null && slr.getShippingMethod().equals("STD")){
//						scoreCourier.add(slr.getStdCourier());
						
						scoreCourier = new HashSet<String>(Arrays.asList(slr.getStdCourier()));
					}else{
						//scoreCourier.add(slr.getCodCourier());
						scoreCourier = new HashSet<String>(Arrays.asList(slr.getStdCourier()));
						System.out.println("checking"+scoreCourier.toString());

					}
					JSONParser parser = new JSONParser();
					List<CriteriaJson> bigQ = new MysqlDAO().checkBigQuery(slr.getRuleList());
					/** Getting overall rule courier from available ruleID **/
					for (int i=0;i<bigQ.size();i++) {
						
						 Object obj = parser.parse(bigQ.get(i).getCriteria());
						 JSONObject jsonObject = (JSONObject) obj;
						 JSONArray criteria = (JSONArray) jsonObject.get("criteria");
						 @SuppressWarnings("rawtypes")
						 Iterator j = criteria.iterator();
						
					     while (j.hasNext()) {
				            JSONObject slide = (JSONObject) j.next();
				            
				            String name = (String)slide.get("name");
				            String value = (String)slide.get("value");
				            name.trim();
				            value.trim();
				            if(name.equals("COURIER_CODE")){
				            	ruleCourier.add(value.trim());
				            	
				            	System.out.println("Iteration wise-:"+ruleCourier);
				            }
				            String operator = (String)slide.get("operator");
				            /*System.out.println(value);*/
					        }
					}
					System.out.println("rule"+ruleCourier);
					Set <String> pcwrCouriers = new CourierType().getIcaCouriers(ruleCourier, scoreCourier,1); 
					Set <String> pcworCouriers = new CourierType().getIcaCouriers(ruleCourier, scoreCourier,2);
					Set <String> npcwrCouriers = new CourierType().getIcaCouriers(ruleCourier, scoreCourier,3);
					System.out.println(ruleCourier);
			}
	}
		return "";
}
}