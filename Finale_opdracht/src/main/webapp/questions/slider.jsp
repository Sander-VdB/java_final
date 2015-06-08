<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>jQuery UI Slider - Default functionality</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <script>
  $(function() {
    $( "#slider" ).slider({
    	min: 0
    	max: 20
    	step: 1
    });
  });
  </script>
</head>
<body>
	<div align="center">
		<table border="5">
			<tr>
				<th class="title">Slider question</th>
			</tr>
		</table>
		<fieldset>
			<legend>Slider question</legend>
			<div id="question" class="question"></div>
			<div id="slider"></div>
			<input type="button" value="Post answer" onclick='jsonRequest("postNumericAnswer",('#slider').slider("option", "value"), "answerResult","answers")'/>
			<div id="answerResult" class="ajaxResult"></div>
		</fieldset>
		<p />
	</div>
</body>
</html>