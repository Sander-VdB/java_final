package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Question;
import bean.Todo;
import bean.User;
import dao.DAOFactory;
import dao.QuestionDAO;
import dao.TodoDAO;

@WebServlet("/postMultiplechoiceAnswer")
public class Multiplechoice extends HttpServlet {
	private QuestionDAO questiondao = DAOFactory.getDAOFactory(DAOFactory.MYSQL).getQuestionDAO();
	private TodoDAO tododao = DAOFactory.getDAOFactory(DAOFactory.MYSQL).getTodoDAO();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		PrintWriter out = response.getWriter();
		boolean correctAnswer;
		Question question = (Question) request.getSession().getAttribute("question");
		User user = (User) request.getSession().getAttribute("user");

		String answer = request.getParameter("options");
		if (!(answer == null || answer.trim().equals(""))) {

			Todo todo = tododao.findByUserid(user.getId());
			todo.setAnswer(question, answer);
			tododao.updateTodo(todo);

			correctAnswer = question.checkAnswer(answer);
			if (correctAnswer) {
				out.print("Correct!");
			} else {
				out.print("Wrong!");
			}
		} else {
			out.print("No answer given");
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
