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


/**
 * 产品新增预订城市
 * @author huangcangbai
 *
 */
public class NewBookCity {

	static int PRODUCT_ID_INDEX = 1;
	
	static int PRODUCT_LINE_DEST_NAME_INDEX = 2;
	
	static int PRODUCT_CAT_ID_INDEX = 1;
	
	static String mysqlDriverClassName = "com.mysql.jdbc.Driver"; 
    
	static String RESULT_FILE_PATH = "C:\\workpro\\script\\src\\script\\product\\DBInsert.txt";
	 
	static String productDBName = "prd_nm"; 
	static String productDBPasswrod = "4021C63E1E"; 
	static String productDBUserName = "gethuangcangbai";  
	static String productDBProxyUrl = "jdbc:mysql://localhost:3307/" + productDBName;
	
	static String localDBPasswrod = "tuniu520";  
	static String localDBUserName = "prd_nmtest";  
	static String localDBUrl = "jdbc:mysql://10.10.30.141:3306/prd_nm?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
    
    static Connection productDBConn ;
    
    static Connection localDBConn;
    
    String queryProductSql = "SELECT p.id,pb.product_line_dest_name FROM product p "
    		+ "join product_base pb on  p.base_id = pb.id and pb.pro_type IN (8,9,10,11,12) "
            + "join product_book_city c on c.product_id=p.id and c.del_flag=0 and c.city_code=?";
     
	String queryProductSyncCityRelCountSql = "SELECT count(id) FROM product_book_city WHERE product_id = ? AND del_flag = 0 AND city_code = ?"; 
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
    
    static {
    	 try {
			Class.forName(mysqlDriverClassName);
			productDBConn = DriverManager.getConnection(productDBProxyUrl, productDBUserName,
	                 productDBPasswrod); 
	        localDBConn = DriverManager.getConnection(localDBUrl, localDBUserName,
	                 localDBPasswrod); 	
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    NewBookCity (int syncCityCode) {
    	try {
			queryProductStatement = productDBConn.prepareStatement(queryProductSql);
			queryProductStatement.setInt(1, syncCityCode);
			queryProductResultSet = queryProductStatement.executeQuery(); 
		} catch (SQLException e) {
			e.printStackTrace();
		} 
    }
    
    private boolean needInsertProductNewCityRel(int productId, int syncCityCode, int newCityCode) throws SQLException {
    	 boolean result = false;
    	
    	 int productSyncCityRelCount = 0; 
         int productNewCityRelCount = 0; 
         queryProductSyncCityRelCountStatement = productDBConn.prepareStatement(queryProductSyncCityRelCountSql);
         queryProductSyncCityRelCountStatement.setInt(1, productId);
         queryProductSyncCityRelCountStatement.setInt(2, syncCityCode);
         queryProductSyncCityRelCountResultSet = queryProductSyncCityRelCountStatement.executeQuery();
         if(queryProductSyncCityRelCountResultSet.next()){                   
             productSyncCityRelCount = queryProductSyncCityRelCountResultSet.getInt(1);
          }

         queryProductNewCityRelCountStatement = productDBConn.prepareStatement(queryProductNewCityRelCountSql);
         queryProductNewCityRelCountStatement.setInt(1, productId);
         queryProductNewCityRelCountStatement.setInt(2, newCityCode);
         queryProductNewCityRelCountResultSet = queryProductNewCityRelCountStatement.executeQuery();
         if(queryProductNewCityRelCountResultSet.next()){                   
             productNewCityRelCount = queryProductNewCityRelCountResultSet.getInt(1);
         }

         
    	 if(productSyncCityRelCount > 0 && productNewCityRelCount <=0){ 
    		result = true;
    	 }
    	 return result;
    }
    
	public void fetchProductNewCityRelationInsertSql(int syncCityCode, int newCityCode, String newCityName) { 
        try {
            BufferedWriter resultWriter = null; 
            try {
                resultWriter = new BufferedWriter(new FileWriter(new File(RESULT_FILE_PATH)));
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
                    	String city_name = newCityName;
                    	
                    	String cat_name = queryProductResultSet.getString(PRODUCT_LINE_DEST_NAME_INDEX);
                    	int cat_id = queryProductCatResultSet.getInt(PRODUCT_CAT_ID_INDEX);
                    	String product_virtual_id = String.valueOf(product_id) + String.valueOf(city_code);
                    	
                    	String postStr = String.format("(%s, %s, %s, %s, %s, %s);", 
                    			product_id,city_code,city_name,cat_name,cat_id,product_virtual_id);
                    	
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
	
    public static void main(String[] args) {
    	int syncCityCode = 1615;
    	int newCityCode = 1637;
    	String newCityName = "常熟";
    	NewBookCity newBookCity = new NewBookCity(syncCityCode);
    	newBookCity.fetchProductNewCityRelationInsertSql(syncCityCode, newCityCode, newCityName); 
    }
    
}
