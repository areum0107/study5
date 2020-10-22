<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script> 
<script src="http://malsup.github.com/jquery.form.js"></script> 
</head>
<body>
<table>
	<colgroup>
		<col width="10%" />
		<col width="20%" />
		<col width="20%" />
		<col />
	</colgroup>
	<thead>
		<tr>
			<th>NO</th>
			<th>시도</th>
			<th>시군구</th>
			<th>병원명</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="hospitalVOList" items="${hospitalVOList}">
			<tr>
				<td>${hospitalVOList.no}</td>
				<td>${hospitalVOList.sidonm}</td>
				<td>${hospitalVOList.sggunm}</td>
				<td>${hospitalVOList.yadmnm}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<form method="post" action="exceldownload">
	<input type="submit" value="엑셀 다운로드" />
</form>

<form id="excelUploadForm" method="post" action="excelupload" enctype="mulripart/form-data">
	<input type="file" name="excelFile" id="excelFile" />
	<button type="button" id="addExcelImpoartBtn" onclick="check()" ><span>추가</span></button>
</form>

<script>
function checkFileType(filepath) {
    var fileFormat = filepath.split(".");

    if(fileFormat.indexOf("xls") > -1 || fileFormat.indexOf("xlsx") > -1) {
        return true;
    } else {
        return false;
    }
}

function check(){
    var file = $("#excelFile").val();

    if(file=="" || file==null) {
        alert("파일을 선택해주세요.");

        return false;
    } else if (!checkFileType(file)) {
        alert("엑셀 파일만 업로드 가능합니다.");
        
        return false;
    }

    if(confirm("업로드 하시겠습니까?")) {
        var options = {
            success : function(data) {
                console.log(data);
                alert("모든 데이터가 업로드 되었습니다.");
            },
            type : "POST"
        };
        $("#excelUploadForm").ajaxSubmit(options);
    }
}

    </script>
</body>
</html>