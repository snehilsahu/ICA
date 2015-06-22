package com.snapdeal.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.snapdeal.entity.CriteriaJson;
import com.snapdeal.entity.SuborderDetails;



public class MysqlDAO {
	
	Connection connection = null;
	ResultSet resultSet = null;
	String queryString = "";
	Statement statement = null;


	public List<SuborderDetails> getSuborderDetailsBySuborder(String suborder)
	{
		
		List<SuborderDetails> suboDetails = null;
		
		try{
			statement = connection.createStatement();
			String query = "SELECT * from shipping.ica_couriers where subo in (" + suborder+")";
			suboDetails = new ArrayList<SuborderDetails>();
			ResultSet resultSet = statement.executeQuery(query);
		
			if(resultSet != null){
				while(resultSet.next()){
					SuborderDetails sd = new SuborderDetails();
					sd.setSupc(resultSet.getString("supc"));
					sd.setSuborderCode(resultSet.getString("subo"));
					sd.setVendorCode(resultSet.getString("VENDOR_CODE"));
					sd.setDestinationPincode(resultSet.getString("dest_pincode"));
					sd.setDels(resultSet.getString("DELS"));
					sd.setPulc(resultSet.getString("PULC"));
					sd.setPuls(resultSet.getString("PULS"));
					sd.setDelc(resultSet.getString("DELC"));
					sd.setPrice(resultSet.getString("PRICE"));
					sd.setWeight(resultSet.getString("WEIGHT"));
					sd.setFm(resultSet.getString("FM"));
					sd.setWarehouseCode(resultSet.getString("WAREHOUSE_CODE"));
					sd.setProductName(resultSet.getString("product_name"));
					sd.setBrand(resultSet.getString("BRAND"));
					sd.setCategory(resultSet.getString("CATEGORY"));
					sd.setCategoryUrl(resultSet.getString("CATEGORY_URL"));
					sd.setMultipart(resultSet.getString("MULTIPART"));
					sd.setDgType(resultSet.getString("DANGEROUSGOODSTYPE"));
					sd.setShippingMode(resultSet.getString("SHIPPING_MODE"));
					sd.setStdCourier(resultSet.getString("std_courier_t"));
					sd.setCodCourier(resultSet.getString("cod_courier_t"));
					sd.setReason(resultSet.getString("reason"));
					sd.setSuboCreated(resultSet.getString("subo_created"));
					sd.setCreated(resultSet.getString("created"));
					sd.setOfffset(resultSet.getString("offset"));
					suboDetails.add(sd);
				}
			}
			resultSet.close();
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}
		finally{
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return suboDetails;
	}

	public List<SuborderDetails> getRuleId(List<SuborderDetails> suboDetailsList )
	{
		
		List<SuborderDetails> sdListWithRequest = new ArrayList<SuborderDetails>();
		for (SuborderDetails sd : suboDetailsList){
			try{
				statement = connection.createStatement();
				String query = "select rule_id," +
				"case when (SOURCE_LOCATION like '%" + sd.getPulc() +"%' and (LOCATION like '%"+ sd.getDelc() +"%' or " +
				"DESTINATION_LOCATION like '%"+sd.getDelc()+"%')) then 	'C-C' " +
				"else case when (SOURCE_LOCATION = 'ALL' and (LOCATION like '%"+ sd.getDelc() +"%' or DESTINATION_LOCATION like '%"+ sd.getDelc() +"%')) " +
				"then 'A-C' else case when (SOURCE_LOCATION like '%"+ sd.getPulc() +"%' and " +
				"(LOCATION like '%"+sd.getDels()+"%' or DESTINATION_LOCATION like '%" + sd.getDels() +"%')) " +
				"then 'C-S' else case when (SOURCE_LOCATION = 'ALL' and (LOCATION like '%"+sd.getDels()+"%' or DESTINATION_LOCATION like '%"+sd.getDels()+"%')) " +
				"then 'A-S'	end	end end end " +
				"as 'type'," +
				"case when (SOURCE_LOCATION like '%" + sd.getPulc() +"%' and (LOCATION like '%"+ sd.getDelc() +"%' or " +
				"DESTINATION_LOCATION like '%"+sd.getDelc()+"%')) then 	'1' " +
				"else case when (SOURCE_LOCATION = 'ALL' and (LOCATION like '%"+ sd.getDelc() +"%' or DESTINATION_LOCATION like '%"+ sd.getDelc() +"%')) " +
				"then '2' else case when (SOURCE_LOCATION like '%"+ sd.getPulc() +"%' and " +
				"(LOCATION like '%"+sd.getDels() +"%' or DESTINATION_LOCATION like '%" + sd.getDels() +"%')) " +
				"then '3' else case when (SOURCE_LOCATION = 'ALL' and (LOCATION like '%"+sd.getDels()+"%' or DESTINATION_LOCATION like '%"+sd.getDels()+"%')) " +
				"then '4'	end	end end end " +
				"as 'priority' from score.sct2 " +
				"where (SOURCE_LOCATION like '%" + sd.getPulc() +"%' and (LOCATION like '%"+ sd.getDelc() +"%' or " +
				"DESTINATION_LOCATION like '%"+sd.getDelc()+"%'))  or " +
				"(SOURCE_LOCATION = 'ALL' and (LOCATION like '%"+ sd.getDelc() +"%' or DESTINATION_LOCATION like '%"+ sd.getDelc() +"%')) " +
				" or (SOURCE_LOCATION like '%"+ sd.getPulc() +"%' and (LOCATION like '%"+sd.getDels() +"%' or DESTINATION_LOCATION like '%" + sd.getDels() +"%')) " +
				" or (SOURCE_LOCATION = 'ALL' and (LOCATION like '%"+sd.getDels()+"%' or DESTINATION_LOCATION like '%"+sd.getDels()+"%')) " +
				" order by priority ";

				
				resultSet = statement.executeQuery(query);
				String type = null;
				int priority = 0;
				List<String> ruleList = new ArrayList<String>();
				if(resultSet != null){
					while(resultSet.next()){
						
						if (priority == resultSet.getInt("priority") || priority == 0){
							ruleList.add(resultSet.getString("rule_id"));
							
							priority = resultSet.getInt("priority");
							type = resultSet.getString("type");
						}
					}
				}
				
				System.out.println("ruleid"+ ruleList.toString().replace("[","").replace("]", ""));
				sd.setRuleList(ruleList.toString().replace("[","").replace("]", ""));
				sd.setRuleType(type);
				resultSet.close();
				sdListWithRequest.add(sd);

			}catch(SQLException se){
				se.printStackTrace();
			}catch(Exception e){
				//Handle errors for Class.forName
				e.printStackTrace();
			}
			finally{
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return sdListWithRequest;
	}



	public List<String> getSuborders(){
		
		
		List<String> subo = new ArrayList<String>();
		String shippingMode = "";
		try{
			statement = (Statement) connection.createStatement();
			String query = "SELECT suborder_code FROM shipping.tmp_suborder2 limit 10";
			
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			SuborderDetails sd = new SuborderDetails();
			if(resultSet != null){
				while(resultSet.next()){
					subo.add(resultSet.getString("suborder_code"));
					}
			}
			resultSet.close();
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}
		finally{
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("ship"+subo);
		return subo;
	}
	
	
public List<CriteriaJson> checkBigQuery(String ruleId,Connection connection){
		
		List<CriteriaJson> json = new ArrayList<CriteriaJson>();
		try{
			statement = connection.createStatement();
			String query = "SELECT rule_id,criteria from score.criteria_json where rule_id in ("+ruleId+")";
 		
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			CriteriaJson cj = new CriteriaJson();
			if(resultSet != null){
				while(resultSet.next()){
					cj.setId(resultSet.getString("rule_id"));
					cj.setCriteria(resultSet.getString("criteria"));
					json.add(cj);
					}
			}
			resultSet.close();
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}
		finally{
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//System.out.println("ship"+json);
		return json;
	}



	
	public List<SuborderDetails> getSuborderDetails()
	{
	
		List<SuborderDetails> details = null;
		String shippingMode = "";
		try{
			statement = connection.createStatement();
			queryString = "SELECT * FROM shipping.tmp_suborder2 limit 10";

			details = new ArrayList<SuborderDetails>();
			resultSet = statement.executeQuery(queryString);
			SuborderDetails sd = new SuborderDetails();
			if(resultSet != null){
				if(resultSet.next()){
					sd.setSupc(resultSet.getString("SUPC"));
					details.add(sd);
				}
			}
			resultSet.close();
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}
		finally{
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return details;
	}

	
	
	
	



}
