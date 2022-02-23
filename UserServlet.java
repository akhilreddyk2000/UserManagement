package com.xadmin.usermanagement.web;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xadmin.usermanagement.dao.UserDao;
import com.xadmin.usermanagement.bean.User;


/**
 * ControllerServlet.java
 * This servlet acts as a page controller for the application, handling all
 * requests from the user.
 */


@WebServlet("/")
public class UserServlet extends HttpServlet {
	   private static final long serialVersionUID = 1L;
	   private UserDao userDao;
       
       public void init() {
	          userDao = new UserDao();	
	   }
    /**
	* @see HttpServlet#dopost(HttpServletRequest request, HttpServletResponse response)
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	/**
	* @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		
		try {
	           switch(action)
	           {
	           case "/new":
		               showNewForm(request, response);
		               break;
	           case "/insert":
		               insertUser(request, response);
	                   break;
		       case "/delete":
		               deleteUser(request, response);
		               break;
		       case "/edit":
		               showEditForm(request, response);
		               break;
		       case "/update":
		               updateUser(request, response);
		               break;
		       default:
			           listUser(request, response);
			           break;
	           }		
	    }catch (SQLException ex) {
		   throw new ServletException(ex);
	    }
}

	private void listUser(HttpServletRequest request, HttpServletResponse response)throws SQLException, IOException, ServletException
	{
	        List<User> listUser = userDao.selectAllUsers();
		    request.setAttribute("listUser", listUser);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
		    dispatcher.forward(request, response);
		    
	}
private void showNewForm(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
	{
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		dispatcher.forward(request, response);
	}
private void showEditForm(HttpServletRequest request, HttpServletResponse response)throws SQLException, ServletException, IOException
    {
	    int id = Integer.parseInt(request.getParameter("id"));
	    User existingUser = userDao.selectUser(id);
	    RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
	    request.setAttribute("user", existingUser);
	    dispatcher.forward(request, response);
	   
	 }

	// insert
	private void insertUser(HttpServletRequest request, HttpServletResponse response)throws SQLException, IOException 
	{
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		User newUser = new User(name, email, country);
		    userDao.insertUser(newUser);
		response.sendRedirect("list");
	}
	// update 
    private void updateUser(HttpServletRequest request, HttpServletResponse response)throws SQLException, IOException
	{
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String country = request.getParameter("country");

			User book = new User(id, name, email, country);
			userDao.updateUser(book);
			response.sendRedirect("list");
	}
	
	// delete
	private void deleteUser(HttpServletRequest request, HttpServletResponse response)throws SQLException, IOException
    {
		int id = Integer.parseInt(request.getParameter("id"));
		userDao.deleteUser(id);
		response.sendRedirect("list");
	}
	
	
}
