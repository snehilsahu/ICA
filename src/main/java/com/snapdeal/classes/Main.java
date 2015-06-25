package com.snapdeal.classes;

import java.sql.SQLException;
import java.util.List;

import org.json.simple.parser.ParseException;

import com.snapdeal.dao.MysqlDAO;
import com.snapdeal.entity.SuborderDetails;

/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args ) throws ParseException, SQLException
    {
    
    	/** Get SubOrder code from shipping**/
    	String suborders = new MysqlDAO().getSuborders();
		System.out.println(suborders);
		
		/** SubOrder details from ica courier**/
		List<SuborderDetails> sdList= new MysqlDAO().getSuborderDetailsBySuborder(suborders);
		
		/** Get rule id from sct2 **/
		List<SuborderDetails> sdListwithRequest = new MysqlDAO().getRuleId(sdList);
		
		/** Parsing criteria json **/
		String parseJson = new ParseJson().getData(sdListwithRequest);
		
				
		}
}
