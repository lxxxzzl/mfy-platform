package com.hcb.script;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class NewBookCity {

	static int PRODUCT_ID_INDEX = 1;
	
	static int PRODUCT_LINE_DEST_NAME_INDEX = 2;
	
	static int PRODUCT_CAT_ID_INDEX = 1;
	
	static String mysqlDriverClassName = "com.mysql.jdbc.Driver"; 
    
	static String RESULT_FILE_PATH = "C:\\workpro\\script\\src\\script\\product\\";
	 
	static String productDBName = "prd_nm"; 
	static String productDBPasswrod = "4021C63E1E"; 
	static String productDBUserName = "gethuangcangbai";  
	static String productDBProxyUrl = "jdbc:mysql://localhost:3307/" + productDBName;
	
	static String localDBPasswrod = "tuniu520";  
	static String localDBUserName = "prd_nmtest";  
	static String localDBUrl = "jdbc:mysql://10.10.30.141:3306/prd_nm?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
    
    Connection productDBConn ;
    
    Connection localDBConn;
    
    String queryProductSql = "SELECT p.id,pb.product_line_dest_name FROM product p "
    		+ "join product_base pb on  p.base_id = pb.id and pb.pro_type IN (8,9,10,11,12) "
            + "join product_book_city c on c.product_id=p.id and c.del_flag=0 and c.city_code=?";
     
	String queryProductNewCityRelCountSql = "SELECT count(id) FROM product_book_city WHERE product_id = ? AND del_flag = 0 AND city_code = ?";  
	
	String queryProductCatSql = "SELECT id from product_cat where city_code=%s and name= ? "; 

    PreparedStatement queryProductStatement = null; 
    PreparedStatement queryProductSyncCityRelCountStatement = null; 
    PreparedStatement queryProductNewCityRelCountStatement = null;
    PreparedStatement queryProductCatSqlStatement = null;  
    ResultSet queryProductResultSet = null; 
    ResultSet queryProductSyncCityRelCountResultSet =null; 
    ResultSet queryProductNewCityRelCountResultSet =null; 
    ResultSet queryProductCatResultSet =null; 
    
    NewBookCity(int syncCityCode) {
    	try {
    		Class.forName(mysqlDriverClassName);
			productDBConn = DriverManager.getConnection(productDBProxyUrl, productDBUserName,
	                 productDBPasswrod); 
	        localDBConn = DriverManager.getConnection(localDBUrl, localDBUserName,
	                 localDBPasswrod);
	        
			queryProductStatement = productDBConn.prepareStatement(queryProductSql);
			queryProductStatement.setInt(1, syncCityCode);
			queryProductResultSet = queryProductStatement.executeQuery(); 
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    
    private boolean needInsertProductNewCityRel(int productId, int syncCityCode, int newCityCode) throws SQLException {
    	 boolean result = false;
    	
         int productNewCityRelCount = 0; 
         queryProductNewCityRelCountStatement = productDBConn.prepareStatement(queryProductNewCityRelCountSql);
         queryProductNewCityRelCountStatement.setInt(1, productId);
         queryProductNewCityRelCountStatement.setInt(2, newCityCode);
         queryProductNewCityRelCountResultSet = queryProductNewCityRelCountStatement.executeQuery();
         if(queryProductNewCityRelCountResultSet.next()){                   
             productNewCityRelCount = queryProductNewCityRelCountResultSet.getInt(1);
         }

         
    	 if(productNewCityRelCount <=0){ 
    		result = true;
    	 }
    	 
    	 //TODO: delete
    	 //result = true;
    	 
    	 return result;
    }
    
	public void fetchProductNewCityRelationInsertSql(int syncCityCode, int newCityCode, String newCityName) { 
        try {
            BufferedWriter resultWriter = null; 
            try {
            	StringBuilder sb = new StringBuilder(RESULT_FILE_PATH);
            	sb.append(newCityName);
            	sb.append(".txt");
                resultWriter = new BufferedWriter(new FileWriter(new File(sb.toString())));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            
            while (queryProductResultSet.next()) { 
                StringBuilder resultItemBuffer = new StringBuilder("insert into product_book_city (product_id,city_code,city_name,cat_name,cat_id,product_virtual_id) values ");
                
                
                queryProductCatSqlStatement = localDBConn.prepareStatement(String.format(queryProductCatSql, newCityCode)); 
                
                queryProductCatSqlStatement.setString(1, queryProductResultSet.getString(PRODUCT_LINE_DEST_NAME_INDEX));
                queryProductCatResultSet = queryProductCatSqlStatement.executeQuery();

                if(queryProductCatResultSet.next()){   
                	boolean needInsertProductNewCityRel = 
                			this.needInsertProductNewCityRel(queryProductResultSet.getInt(PRODUCT_ID_INDEX), syncCityCode, newCityCode);
                	
                    if(needInsertProductNewCityRel){
                    	int product_id = queryProductResultSet.getInt(PRODUCT_ID_INDEX);
                    	int city_code = newCityCode;
                    	StringBuilder city_nameBuffer = new StringBuilder();
                    	city_nameBuffer.append("'");
                    	city_nameBuffer.append(newCityName);
                    	city_nameBuffer.append("'");
                    	
                    	StringBuilder cat_nameBuffer = new StringBuilder();
                    	cat_nameBuffer.append("'");
                    	cat_nameBuffer.append(queryProductResultSet.getString(PRODUCT_LINE_DEST_NAME_INDEX));
                    	cat_nameBuffer.append("'");
                    	
                    	int cat_id = queryProductCatResultSet.getInt(PRODUCT_CAT_ID_INDEX);
                    	StringBuilder product_virtual_id_buffer = new StringBuilder(String.valueOf(product_id));
                    	product_virtual_id_buffer.append(String.valueOf(city_code));
                    	
                    	String postStr = String.format("(%s, %s, %s, %s, %s, %s);", 
                    			product_id,city_code,city_nameBuffer.toString(),cat_nameBuffer.toString(),cat_id,product_virtual_id_buffer.toString());
                    	
                    	resultItemBuffer.append(postStr);
                        System.out.println(resultItemBuffer.toString());
                        resultWriter.write(resultItemBuffer.toString());
                        resultWriter.newLine();
                        resultWriter.flush();
                    }
                }
                
            }
            
            resultWriter.close();
            this.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void close() {
        // 关闭记录集
        if (queryProductResultSet != null) {
            try {
                queryProductResultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (queryProductSyncCityRelCountResultSet != null) {
            try {
                queryProductSyncCityRelCountResultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (queryProductNewCityRelCountResultSet != null) {
            try {
                queryProductNewCityRelCountResultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (queryProductCatResultSet != null) {
            try {
                queryProductCatResultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // 关闭声明
        if (queryProductStatement != null) {
            try {
                queryProductStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (queryProductSyncCityRelCountStatement != null) {
            try {
                queryProductSyncCityRelCountStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (queryProductNewCityRelCountStatement != null) {
            try {
                queryProductNewCityRelCountStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (queryProductCatSqlStatement != null) {
            try {
                queryProductCatSqlStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // 关闭链接对象
        if (productDBConn != null) {
            try {
                productDBConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (localDBConn != null) {
            try {
                localDBConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
	
	static class ProductCityRel {
		public int syncCityCode;
		public int newCityCode;
		public String newCityName;
		
		ProductCityRel(int syncCityCode, int newCityCode, String newCityName) {
			this.syncCityCode = syncCityCode;
			this.newCityCode = newCityCode;
			this.newCityName = newCityName;
		}
	}
	
	public static List<ProductCityRel> initProductCityRelLst() {
		List<ProductCityRel> relList = new ArrayList<ProductCityRel>();
		//relList.add(new ProductCityRel(1615, 1637, "常熟"));
		
		//syncCityCode, newCityCode, newCityName
		relList.add(new ProductCityRel(2402, 2421, "淄博"));
//		relList.add(new ProductCityRel(2402, 2406, "东营"));
//		
//		relList.add(new ProductCityRel(2413, 2418, "威海"));
//		relList.add(new ProductCityRel(2413, 2417, "潍坊"));
//		relList.add(new ProductCityRel(2413, 2415, "日照"));
//		
//		relList.add(new ProductCityRel(1602, 1617, "泰州"));
		
		
		
		
		return relList;
	}
	
    public static void main(String[] args) {
    	List<ProductCityRel> relList = initProductCityRelLst();
    	for (ProductCityRel productCityRel : relList) {
    		long startTime = System.currentTimeMillis();
    		NewBookCity newBookCity = new NewBookCity(productCityRel.syncCityCode);
        	newBookCity.fetchProductNewCityRelationInsertSql(productCityRel.syncCityCode, productCityRel.newCityCode, productCityRel.newCityName); 
        	System.out.println(productCityRel.newCityName + " takes " + (System.currentTimeMillis() - startTime) + "ms");
    	}
    }
    
}
