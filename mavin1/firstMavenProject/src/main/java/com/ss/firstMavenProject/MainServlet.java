package com.ss.firstMavenProject;


import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.google.gson.*;

@WebServlet("/login")
public class MainServlet extends HttpServlet {
	
	private final String user = "user";
	private final String pass = "pass";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Gson gson = new Gson();
		
		LoginRequest lr = gson.fromJson(request.getReader(), LoginRequest.class);
		if (user.equals(lr.username) && pass.equals(lr.password)) {
			response.setStatus(200);
		}else response.setStatus(401);
		System.out.println(lr.username);
	}
	
	
	

}

final class LoginRequest{
	public String username;
	public String password;
}
