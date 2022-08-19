<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<script>
	function a(){
		var dan = document.getElementById("dan").value;
		console.log("dan:"+dan);
		
		if(typeof dan == 'string'){
			alert('dd');
		}
		}
	</script>
</head>
<body>
<form action="">
<table>
<tr>
<input type="text" name="" id="dan">
</tr>
<tr>
<input type="button" value="aa" onclick="a()">
</tr>
</table>
</form>
</body>
</html>
