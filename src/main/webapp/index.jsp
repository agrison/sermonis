<html>
<head>
	<script type="text/javascript">
		function s4() {
			return Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
		}

		function guid() {
			return s4() + s4() + '-' + s4() + '-' + s4() + '-' + s4() + '-' + s4() + s4() + s4();
		}
		
		var password = document.location.search.match(/withPassword/) != null;
		document.location = 'room-' + guid() + (password ? '/init' : '');
	</script>
</head>
<body>
<h2>Moving you to a new room...</h2>
</body>
</html>
