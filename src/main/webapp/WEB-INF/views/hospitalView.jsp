<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
table {
    border-collapse: collapse;
}
</style>
</head>
<body>
<h3 style="text-align: center;">병원 상세보기</h3>
	<table border='1'>
			<tbody>
				<tr>
					<th>번호</th>
					<td>${hospitalVO.no}</td>
				</tr>
				<tr>
					<th>운영가능일자</th>
					<td>${hospitalVO.adtfrdd}</td>
				</tr>
				<tr>
					<th>시도명</th>
					<td>${hospitalVO.sidonm}</td>
				</tr>
				<tr>
					<th>시군구명</th>
					<td>${hospitalVO.sggunm}</td>
				</tr>
				<tr>
					<th>기관명</th>
					<td>${hospitalVO.yadmnm}</td>
				</tr>
				<tr>
					<th>전화번호</th>
					<td>${hospitalVO.telno}</td>
				</tr>
				<tr>
					<th>선정유형</th>
					<td>${hospitalVO.hosptytpcd}</td>
				</tr>
				<tr>
					<th>구분코드</th>
					<td>${hospitalVO.spcladmtycd}</td>
				</tr>
			</tbody>
		</table>
</body>
</html>