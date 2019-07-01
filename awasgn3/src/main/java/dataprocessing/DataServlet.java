package dataprocessing;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DataServlet
 */
@WebServlet("/GetData")
public class DataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public DataServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Fetcher fetch = new Fetcher();
		
		try {
			if(!request.getParameter("customQuery").isEmpty()) {
				String query = request.getParameter("customQuery");
				List<String> result = fetch.fetchData(query);
				request.setAttribute("queryValue", query);
				request.setAttribute("url", result);
			}
			else if(request.getParameter("excelQuery") != null) {
				ReadExcel excel = new ReadExcel();
				int index = Integer.parseInt(request.getParameter("excelQuery"));
				String query = excel.parseExcel(index);
				List<String> result = fetch.fetchData(query);
				request.setAttribute("queryValue", query);
				request.setAttribute("url", result);
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
