package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Encryptor;
import bean.User;
import dao.DAOFactory;
import dao.UserDAO;

@WebServlet("/login")
public class Login extends HttpServlet {
	private UserDAO userdao = DAOFactory.getDAOFactory(DAOFactory.MYSQL).getUserDAO();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			String username = request.getParameter("Username");
			String password = Encryptor.encrypt(request.getParameter("Password"));

			if (!parametersNotEmpty(username) && !parametersNotEmpty(password)) {
				out.print("<i>not all fields set</i>");
			} else {
				try {

					User user = userdao.find(username, password);

					if (user != null) {
						request.getSession().setAttribute("user", user);
						out.print("Login success");
					} else {
						out.print("Login failed!");
					}
				} catch (Exception e) {
					e.printStackTrace();
					out.print("Error on login");
				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
