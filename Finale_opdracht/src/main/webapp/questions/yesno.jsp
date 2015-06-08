<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
 
</script>

<form name="form" action="">
<c:forEach items="${answerMap}" var="answer">
    <br/>
    <label>
        <input type="radio" name="options" value="true">
        Yes
      </label>
      <label>
        <input type="radio" name="options" value="false">
        No
      </label>
</c:forEach>
<input type="button" value="Post answer" onclick='ajaxGetResult("postMultiplechoiceAnswer",this.form, "answerResult","result")'/>
</form>		
<div id="result" class="ajaxResult"></div>
