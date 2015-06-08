package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Question;
import dao.DAOFactory;
import dao.QuestionDAO;

@WebServlet("/postNumericAnswer")
public class Numeric extends HttpServlet {
	private QuestionDAO questiondao = DAOFactory.getDAOFactory(DAOFactory.MYSQL).getQuestionDAO();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		PrintWriter out = response.getWriter();
		boolean correctAnswer;
		Question question = null;
		String id = (String) request.getSession().getAttribute("questionId");

		String answer = request.getParameter("answer");
		if (!(answer == null || answer.trim().equals(""))) {
			question = questiondao.findById(Integer.parseInt(id)).get(0);
		}
		if (question != null) {
			correctAnswer = question.checkAnswer(answer);
			if (correctAnswer) {
				out.print("Correct!");
			} else {
				out.print("Wrong!");
			}
		} else {
			out.print("Error at finding question");
		}

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
