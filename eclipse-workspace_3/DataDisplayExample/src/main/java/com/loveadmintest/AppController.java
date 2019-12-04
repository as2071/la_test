package com.loveadmintest;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

@Controller
public class AppController
{
	
	 // Get html for the returned page name
	 @GetMapping({"/", "/index"})
	 public String loadIndex()
	 {
		 return "index";
	 }
	 
	 // Service for retrieving data from the database and returning as a stream
	 @RequestMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
	 @ResponseBody
	 public StreamingResponseBody getData()
	 {
	
		
		  String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;" +
		  "databaseName=loveadmin-test;" + "username=readonlyuser;" +
		  "password=readonlypass;"; 
		
	
		try 
		{
			final Connection connection = DriverManager.getConnection(url);
			Statement statement = connection.createStatement();
			
		 	try
		 	{
		 		// Retrieve all data from the view
		        String SQL = "select first_name, last_name, product_name, description, price, currency, quantity, purchase_date, id from user_product_purchases";
		        final ResultSet resultSet = statement.executeQuery(SQL);
		        
		        return new StreamingResponseBody() 
		        {
		            @Override
		            public void writeTo(OutputStream out) throws IOException 
		            {
		            	Gson gson = new Gson();
		            	try 
		            	{
							while (resultSet.next())
							{
								// A row of data is represented as a string array
								// Each row is converted into json and the bytes are written
								// to an output stream
								String[] row = new String[9];
								for (int i = 0; i < 9; i++)
								{
									row[i] = resultSet.getString(i+1);
								}
								// "|" is used as a separator for json rows
								out.write((gson.toJson(row)+"|").getBytes());
								out.flush();
							}
						} 
		            	catch (SQLException e) 
		            	{
							e.printStackTrace();
							closeConnection(connection);
						}
		            }
		        };       
		        
	        }
	        catch (SQLException e) 
		 	{
	            e.printStackTrace();
	            closeConnection(connection);
	        }
	 	
		}
		catch (SQLException e1) 
		{
			e1.printStackTrace();
		}
	
	 	return null;
	 }
	
    // Wraps the try-catch code for closing a database connection
	private void closeConnection(Connection connection)
	{
		if (connection == null) return;
		try 
		{
			connection.close();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}  
