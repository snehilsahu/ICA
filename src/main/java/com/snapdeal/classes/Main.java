package com.snapdeal.classes;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.snapdeal.dao.MysqlDAO;
import com.snapdeal.dbmanager.DBManager;

import com.snapdeal.entity.CriteriaJson;
import com.snapdeal.entity.SuborderDetails;

/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args ) throws ParseException
    {
    	Connection connection = DBManager.getLocalConnection();
    	
    	List<String> suborders = new MysqlDAO().getSuborders();
		String subo = "";
		System.out.println(suborders);
		for(String suborder:suborders){
			if (subo.length() == 0)
				subo = "'"+ suborder +"'";
			else
				subo = subo + ",'"+ suborder +"'";
		}
		
		List<SuborderDetails> sdList= new MysqlDAO().getSuborderDetailsBySuborder(subo);
		
		List<SuborderDetails> sdListwithRequest = new MysqlDAO().getRuleId(sdList);
		if (sdListwithRequest.size() > 0){
			for(SuborderDetails slr :sdListwithRequest){
					
					JSONParser parser = new JSONParser();
					
					//String ruleId = "15544,183832";
					List<CriteriaJson> bigQ = new MysqlDAO().checkBigQuery(slr.getRuleList(),connection);
					for (CriteriaJson criteriaJson : bigQ) {
						 Object obj = parser.parse(criteriaJson.getCriteria());
		
						 JSONObject jsonObject = (JSONObject) obj;
						 JSONArray criteria = (JSONArray) jsonObject.get("criteria");
						
						 @SuppressWarnings("rawtypes")
						Iterator i = criteria.iterator();
		
					     while (i.hasNext()) {
				            JSONObject slide = (JSONObject) i.next();
				            String name = (String)slide.get("name");
				            String operator = (String)slide.get("operator");
				            String value = (String)slide.get("value");
				            System.out.println(name+"-"+operator+"-"+value);
					        }
						
						
					}
				}
		}
    }
}
