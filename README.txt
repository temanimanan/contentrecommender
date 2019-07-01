MANAN TEMANI
ASU ID: 1211362201
ADAPTIVE WEB
ASSIGNMENT 3



Implementation:
1. Project is divided into following categories:
    i. Read and Parse the Excel
    ii. Create a web crawler
    iii. Index the crawled data
    iv. Fetch the recommendation from indexed data based on the query

2. The crawler navigates to the webpage, extracts the links and the text from those links upto level 1

3. This data is then stored in the appropiate files (Based on the topics/headings/keywords extracted from the links and webpage (Wiki/Oracle))

4. The data is then indexed using Appache Lucene which stores these extracted topics/headings/keywords into the index file along with the associated data files

5. Using Porter Stemming algorithm, these extracted topics/headings/keywords are analyzed and stemmed to their root form

6. Finally, the user query is passed through the analyzer and the based on the matched topics/headings/keywords, the recommendations are returned from the indexed data and displayed to the user



Instructions (Online):
NOTE: Do not CRAWL!!! Crawled Data is already available
1. Navigate to the webapp:
        http://awasgn3.herokuapp.com/

2. Select the Query number, to generate recommendation
Eg. query 5

3. Enter a custom query, to generate recommendation
Eg. java  OR [any query statement]



Instructions (Offline):
1. Deploy the WAR file into the tomcat server

2. Run the app into the webbrowser:
        http://localhost:8080/awasgn3
        (PORT can be different, Home: index.jsp)

3. Run the crawler to generate the data:
NOTE: JUST CRAWL ONCE TO AVOID DATA DUPLICATION
NOTE: Crawling may take 1-2 minutes depending on the connection speed
NOTE: Make sure the premission to the directories are given to save the crawled data

4. Select the Query number, to generate recommendation
Eg. query 5

5. Enter a custom query, to generate recommendation
Eg. java




References:
Oracle and Wiki Java Programming pages
Sample Code (Provided by the professor)
Youtube Videos
Google and StackOverFlow