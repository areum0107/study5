<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>index.jsp</title>
</head>
<body>
<div class="mainContainer">
	<h2>회원 목록</h2>
	<ul>
	<c:forEach items="${members}" var="mem">
		<li>${mem.memId} , ${mem.memName} , ${mem.memHp} 
	</c:forEach>
	</ul>
</div> <!-- mainContainer -->
</body>
</html>