// Get the browser-specific request object, either for
// Firefox, Safari, Opera, Mozilla, Netscape, IE 8, or IE 7 (top entry);
// or for Internet Explorer 5 and 6 (bottom entry). 

function getRequestObject() {
  if (window.XMLHttpRequest) {
    return(new XMLHttpRequest());
  } else if (window.ActiveXObject) { 
    return(new ActiveXObject("Microsoft.XMLHTTP"));
  } else {
    return(null); 
  }
}

//Return escaped value of textfield that has given id.
//The builtin "escape" function url-encodes characters.
function getValue(id) 
{
return(escape(document.getElementById(id).value));
}

function parseInput(form)
{
	 var parameters = "";
	 for(var i=0; i<form.elements.length; i++){
		 if(form.elements[i].type == "button")
		 continue;
		 if(i > 0)
		 parameters += "&";
		 parameters += form.elements[i].name + "=";
		 switch (form.elements[i].type){
			 case "text":
				 parameters += encodeURIComponent(form.elements[i].value);
			 break;
			 case "radio":
				 var radioel = form.elements[form.elements[i].name];
				 for(var j=0; j<radioel.length; j++){
				 i+=j;
				 if(form.elements[i].checked)
					 parameters += form.elements[i].value;
				 }
			 break;
			 case "checkbox":
				 parameters += encodeURIComponent(form.elements[i].checked);
			 break;
		 }
	 }
	 return parameters;
}
//Make an HTTP request to the given address and data. 
//Display result in the HTML element that has given ID.
//GET
function ajaxGetResult(address, form, resultRegion) {
	var request = getRequestObject();
	var parameters = parseInput(form);
	request.onreadystatechange = 
	function() { showResponseText(request, 
	resultRegion); };
	request.open("GET", address+"?"+parameters, true);
	request.send(null);
}
//POST
function ajaxPostResult(address, form, resultRegion) {
	var request = getRequestObject();
	var parameters = parseInput(form);
	request.onreadystatechange = function() { 
	showResponseText(request,resultRegion); };
	request.open("GET", address, true);
	request.send(parameters);
}
//Generalized version of ajaxResultPost. In this
//version, you pass in a response handler function
//instead of just a result region.
function ajaxPost(address, data, responseHandler) 
{
var request = getRequestObject();
request.onreadystatechange = function() { responseHandler(request); };
request.open("POST", address, true);
request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
request.send(data);
}
//Request functions
function jsonRequest(address,form, resultRegion, content) 
{
  	var data = parseInput(form);
	data += "&format=json";
	ajaxPost(address, data, function(request) { 
		showJsonTable(request, resultRegion, content); 
      });
}
//Handler functions
function showJsonTable(request, resultRegion, content) 
{
  if ((request.readyState == 4) &&
      (request.status == 200)) {
    var rawData = request.responseText;
    var data = eval("(" + rawData + ")");
    //use [ ] in order to be able to provide a dynamic key value
    createTable(data.headings, data[content], resultRegion);
  }
}
//Put response text in the HTML element that has given ID.

function showResponseText(request, resultRegion) {
  if ((request.readyState == 4) &&
      (request.status == 200)) {
    htmlInsert(resultRegion, request.responseText);
  }
}

//Insert the html data into the element that has the specified id.

function htmlInsert(id, htmlData) {
  document.getElementById(id).innerHTML = htmlData;
}
//****************Table functions**************************************************************************************************************
function getTable(headings, rows) 
{
  var table = "<table border='1' class='ajaxTable'>\n" +
              getTableHeadings(headings) +
              getTableBody(rows) +
              "</table>";
  return(table);
}
function getTableHeadings(headings) 
{
  var firstRow = "  <tr>";
  for(var i=0; i<headings.length; i++) {
    firstRow += "<th>" + headings[i] + "</th>";
  }
  firstRow += "</tr>\n";
  return(firstRow);
}
function getTableBody(rows) 
{
  var body = "";
  for(var i=0; i<rows.length; i++) {
    body += "  <tr>";
    var row = rows[i];
    for(var j=0; j<row.length; j++) {
      body += "<td>" + row[j] + "</td>";
    }
    body += "</tr>\n";
  }
  return(body);
}
function createTable(headings, rows, displayRegion) 
{
   var table = getTable(headings, rows);
  htmlInsert(displayRegion, table);
}


//Trick so that the Firebug console.log function will
//be ignored (instead of crashing) in Internet Explorer.
//Also see Firebug Lite and Faux Console if you want 
//logging to actually do something in IE.
//Firebug Lite: http://getfirebug.com/lite.html
//Faux Console: http://icant.co.uk/sandbox/fauxconsole/

try { console.log("Loading script"); 
} catch(e) { console = { log: function() {} }; }