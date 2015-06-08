<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" %>


	<div align="center">
		<table border="5">
			<tr>
				<th class="title">Numeric question</th>
			</tr>
		</table>
		<fieldset>
			<legend>Numeric question</legend>
			<div id="question" class="question"><c:out value="${sessionScope.question.question}"/></div>
			<form name="form" action="">
				<label>Answer: <input type="number" name="answer" /></label><br /> 
				<input type="button" value="Post answer" onclick='jsonRequest("postNumericAnswer",this.form, "answerResult","answers")'/>
			</form>
			<div id="answerResult" class="ajaxResult"></div>
			<input type="button" value="Next" onclick=''/>
		</fieldset>
		<p />
	</div>