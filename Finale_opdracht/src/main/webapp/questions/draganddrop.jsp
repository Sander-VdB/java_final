<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
 
$( init );
 
function init() {
	 $('#makeMeDraggable').draggable();
	 $('#makeMeDroppable').droppable( {
	   drop: handleDropEvent
	 } );
}
	 
function handleDropEvent( event, ui ) {
	  var draggable = ui.draggable;
	  alert( 'The answer "' + draggable.text() + '" was dropped on "'+$(this).text()+'"');
}
 
</script>

<c:forEach items="${answerMap}" var="answer">
    <br/>
  	<div id="makeMeDraggable">${answer.key}</div>
  	<div id="makeMeDroppable">${answer.value}</div>
</c:forEach>

