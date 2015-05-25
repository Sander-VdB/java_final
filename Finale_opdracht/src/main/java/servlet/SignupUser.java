package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import dao.DAOFactory;
import dao.UserDAO;

@WebServlet("/signup")
public class SignupUser extends HttpServlet {
	private UserDAO userdao = DAOFactory.getDAOFactory(DAOFactory.MYSQL).getUserDAO();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		PrintWriter out = response.getWriter();
		String username = request.getParameter("userUsername");

		if (!parametersNotEmpty(username)) {
			out.print("<i>not all fields set</i>");
		} else {
			User user = new User();
			user.setUsername(username);
			user.setId(userdao.insertUser(user));

			out.print("User added");

		}
	}

	private boolean parametersNotEmpty(String... parameters) {
		for (String parameter : parameters) {
			if ((parameter == null) || (parameter.trim().equals(""))) {
				return false;
			}
		}
		return true;
	}
}
