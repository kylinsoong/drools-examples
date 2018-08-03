package com.sample;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class NavServlet
 */
public class NavServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NavServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println(request.getParameter("submit").equals("Signal"));
		if (request.getParameter("submit").equals("Signal")){
			ProcessWorkflow pw = new ProcessWorkflow();
			pw.signalevent(Integer.parseInt(request.getSession().getAttribute("ksessionId").toString()),
					Long.parseLong(request.getSession().getAttribute("pid").toString()));
		}else{
		request.getRequestDispatcher("/orderEntry1.jsp").forward(request,response);
	}
	}
}
