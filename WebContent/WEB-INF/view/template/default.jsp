<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<link rel="stylesheet" type="text/css" href="/res/css/common.css">
<link rel="stylesheet" type="text/css" href="/res/css/login.css">
<link rel="stylesheet" type="text/css" href="/res/css/join.css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/view/${view}.jsp"></jsp:include>
	</div>
</body>
</html>