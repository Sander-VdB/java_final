<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
 
</script>

<form name="form" action="">
<c:forEach items="${answerMap}" var="answer">
    <br/>
    <label>
        <input type="checkbox" name="options" value="${answer}">
        ["${answer.value}"]
      </label>
</c:forEach>
<input type="button" value="Post answer" onclick='ajaxGetResult("postMultiplechoiceAnswer",this.form, "answerResult","result")'/>
</form>		
<div id="result" class="ajaxResult"></div>
