package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import dao.DAOFactory;
import dao.UserDAO;

@WebServlet("/searchUser")
public class SearchUser extends HttpServlet {
	private UserDAO userdao = DAOFactory.getDAOFactory(DAOFactory.MYSQL).getUserDAO();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		PrintWriter out = response.getWriter();
		List<User> userlist = null;
		String id = request.getParameter("userId");
		String name = request.getParameter("userName");
		String format = request.getParameter("format");
		if (!(id == null || id.trim().equals(""))) {
			userlist = userdao.findById(Integer.parseInt(id));
		}
		if (!(name == null || name.trim().equals(""))) {
			userlist = userdao.findByName(name);
		}

		if (userlist == null || userlist.size() <= 0) {
			out.print("No user found");
		} else if ("json".equals(format)) {
			request.setAttribute("customers", userlist);
			response.setContentType("application/json");
			String outputPage = "/results/customers-json.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(outputPage);
			dispatcher.include(request, response);
		} else {
			StringBuilder message = new StringBuilder();
			message.append("<table><tr><th>Username</th></tr>");
			for (User user : userlist) {
				message.append("<tr><td>" + user.getUsername() + "</td></tr>");
			}
			message.append("</table>");
			out.print(message);
		}

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
