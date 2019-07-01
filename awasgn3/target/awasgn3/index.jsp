<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>AW - Manan Temani</title>
	</head>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
	<body>
		<h1><center>Assignment 3</center></h1>
		<h3><center>Recommendations for Wiki/Oracle page</center></h3>
		
		<br/>
		
		<label><h3><b>Implementation:</b></h3></label>
   		<div>
  			<ul>
				<li>Project is divided into following categories: <br/>
				    i. Read and Parse the Excel <br/>
				    ii. Create a web crawler <br/>
				    iii. Index the crawled data <br/>
				    iv. Fetch the recommendation from indexed data based on the query
				</li>
				<li>The crawler navigates to the webpage, extracts the links and the text from those links upto level 1
				</li>
				<li>This data is then stored in the appropiate files (Based on the topics/headings/keywords extracted from the links and webpage (Wiki/Oracle))
				</li>
				<li>The data is then indexed using Appache Lucene which stores these extracted topics/headings/keywords into the index file along with the associated data files
				</li>
				<li>Using Porter Stemming algorithm, these extracted topics/headings/keywords are analyzed and stemmed to their root form
				</li>
				<li>Finally, the user query is passed through the analyzer and the based on the matched topics/headings/keywords, the recommendations are returned from the indexed data and displayed to the user
				</li>
			</ul>
   		</div>
   		
   		<br/><br/><br/>
		
		<label><h3><b>Crawler:</b></h3></label>
   		<div>
  			<form name="form-crawl" method="post" action="Crawl">
   				<button type="submit" value="Submit" method="post" action="Crawl">Click to Crawl</button>
				<p>
					<b>NOTE: JUST CRAWL ONCE TO AVOID DATA DUPLICATION</b><br/>
					NOTE: Crawl before running the recommendation system <br/>
					NOTE: Crawling may take 1-2 minutes depending on the connection speed 
				</p>
			</form>
   		</div>
   		
   		<br/><br/><br/>
		
		<form name="form" role="form" method="post" action="GetData">
  			<div>
  				<label><h3><b>Recommendations from Excel Query:</b></h3></label>
    			<div>
					<select name="excelQuery" onchange="document.form.submit();">
						<option disabled selected> Select Query </option>
						<option value=1>Query 1</option>
						<option value=2>Query 2</option>
						<option value=3>Query 3</option>
						<option value=4>Query 4</option>
						<option value=5>Query 5</option>
						<option value=6>Query 6</option>
						<option value=7>Query 7</option>
						<option value=8>Query 8</option>
						<option value=9>Query 9</option>
						<option value=10>Query 10</option>
					</select>
			    </div>
			    
			    <br/><br/><br/>
			    
    			<label><h3><b>Recommendations from Custom Query:</b></h3></label>
    			<div>
      				<input type="text" name="customQuery" id="customQuery" placeholder="Enter custom query">
    			</div>
    			<div>
      				<button type="submit" value="Submit">Get Recommendations</button>
    			</div> 				
			</div> 
		</form>
		
		<br/><br/><br/>
		
		<div>
			<% String query = (String)request.getAttribute("queryValue"); %>
			<div>
				<%
				List<String> results = (List<String>)request.getAttribute("url");
				if(results!=null){
				%>
				<div>
					<h3>Your Query:</h3><%=query%>
				</div>
				<br/><br/>
				<div>
					<h3><center><b>Top Recommendations for your query</b></center></h3>
				</div>
				<br/>
				<% Iterator<String> itr = results.iterator();
					int i=0;
					while (itr.hasNext() && i < 10) {%>
					<div>
						<h4><b>Recommendation<%=++i%></b></h4>
					</div>
					<div>
						<p><%=itr.next()%></p>
					</div>
				<%}
				}
				%>
			</div>
		</div>
	</body>
</html>