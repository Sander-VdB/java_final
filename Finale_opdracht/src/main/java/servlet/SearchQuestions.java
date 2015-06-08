package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Numeric;
import bean.Question;
import bean.Todo;
import bean.User;
import dao.DAOFactory;
import dao.QuestionDAO;
import dao.TodoDAO;

@WebServlet("/searchQuestions")
public class SearchQuestions extends HttpServlet {
	private QuestionDAO questiondao = DAOFactory.getDAOFactory(DAOFactory.MYSQL).getQuestionDAO();
	private TodoDAO tododao = DAOFactory.getDAOFactory(DAOFactory.MYSQL).getTodoDAO();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		PrintWriter out = response.getWriter();

		String series = request.getParameter("selector");
		User user = (User) request.getSession().getAttribute("user");
		List<Question> questionlist;

		if (!(series == null || series.trim().equals(""))) {
			questionlist = questiondao.findBySeries(series);
			Todo todo = tododao.findByUserid(user.getId());
			if (todo == null) {
				todo = new Todo();
				todo.setUser(user);
				tododao.insertTodo(todo);
			}
			for (Question question : questionlist) {
				todo.addQuestion(question);
			}
			tododao.updateTodo(todo);

			Question firstquestion = questionlist.get(0);
			request.getSession().setAttribute("question", firstquestion);

			if (firstquestion instanceof Numeric) {
				response.sendRedirect("numeric.jsp");
			}
		}

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
