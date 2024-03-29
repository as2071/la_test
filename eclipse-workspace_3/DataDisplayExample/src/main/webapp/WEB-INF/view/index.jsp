<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Insert title here</title>
	<script type="text/javascript">
	
		var makeRequest = function(url) 
		{
		    return new Promise(function(resolve, reject)
	   		{
			      try 
			      {
			        var httpRequest = new XMLHttpRequest();
			        // Listen for API Response
			        httpRequest.onreadystatechange = function() 
			        {
			          if (httpRequest.readyState == XMLHttpRequest.DONE && httpRequest.status == 200) 
			        	  return resolve(httpRequest.responseText);
			        };
			        // Open connection
			        httpRequest.open("GET", url, true);
			        httpRequest.withCredentials = true;
			        httpRequest.setRequestHeader("Accept", "application/json");
			        // Perform an AJAX call
			        httpRequest.send();
			      } catch (ex) 
			      {
			        reject("Error during process of request");
			      }
			    });
		  };
	
		function loadOnStart()
		{
			// res contains all of the data represented as a string. This is a 
			// series of json arrays seperated by the string "|".
			makeRequest(window.location.href + '/data').then(function(res)
			{
				var rowsStr = res.split("|"); // Separate rows
				rowsStr = rowsStr.splice(0, rowsStr.length-1); // Remove final empty entry
				var rows = rowsStr.map(JSON.parse); // Parse rows to json
				var table = document.getElementById('output_table');
				var headerRow = table.insertRow(-1);
				var headers = ["first_name", "last_name", "product_name", "description", "price", "currency", "quantity", "purchase_date", "id"];
		        for (var i = 0; i < 9; i++) // Add headers
		        {
		            var headerCell = document.createElement("TH");
		            headerCell.innerHTML = headers[i];
		            headerRow.appendChild(headerCell);
		        }
				for (row of rows) // Build table data by appending rows
				{
					var newRow = table.insertRow(table.rows.length);
					for (var i = 0; i < row.length; i++)
					{
						cell = newRow.insertCell(i);
						cell.innerHTML = row[i];
					}
				}
		    });
		}
		window.onload = loadOnStart;
	</script>
</head>
<body>
	<h3>Data</h3>
	<table id="output_table"></table> <!-- Table to be used for displaying data -->
</body>
</html>