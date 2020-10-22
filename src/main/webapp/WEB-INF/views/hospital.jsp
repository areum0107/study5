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

#id_hosInfo {
	position: relative;
	vertical-align: top;
	display: inline-block;
	width: 48%;
	border: 1px solid black;
}

#id_hosDB {
	position: relative;
	vertical-align: top;
	display: inline-block;
	width: 48%;
	height: 500px;
	border: 1px solid red;
}
</style>
<script src="https://cdnjs.cloudflare.com/ajax/libs/x2js/1.2.0/xml2json.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
<input type="button" id="id_save" value="저장하기">
<input type="button" id="id_db_call" value="DB호출">
<div>
<div id="id_hosInfo"></div>

<div id="id_hosDB"></div>
</div>
<script type="text/javascript">

var v_items;
var v_hosInfo = document.getElementById("id_hosInfo");
var v_ajax = new XMLHttpRequest();

v_ajax.open("get","/study5/apiCall",true);
v_ajax.send();

v_ajax.onreadystatechange = function(){
    if(v_ajax.readyState == 4 && v_ajax.status == 200){
    	// Create x2js instance with default config
    	var x2js = new X2JS();
    	var jsonObj = x2js.xml_str2json(v_ajax.responseText);
    	
    	console.log(jsonObj.response.body.items.item);
    	v_items = jsonObj.response.body.items.item;
    	
    	var v_tblStr = "<table border='1'>";
        v_tblStr += "<tr><th>선택</th><th>시도</th><th>군구</th><th>병원이름</th><th>전화번호</th></tr>"
        for(var i=0; i<v_items.length; i++){
            v_tblStr += "<tr>";
            v_tblStr += "<td>" + "<input type='checkbox' " + "value='" + i + "' name='checkInfo'>" + "</td>";
            v_tblStr += "<td>" + v_items[i].sgguNm + "</td>";
            v_tblStr += "<td>" + v_items[i].sidoNm + "</td>";
            v_tblStr += "<td>" + v_items[i].yadmNm + "</td>";
            v_tblStr += "<td>" + v_items[i].telno + "</td>";
            v_tblStr += "</tr>";
        }
        v_tblStr += "</table>";
        v_hosInfo.innerHTML = v_tblStr;
    }
}

// 저장하기 버튼 이벤트
$("#id_save").on("click",function(){
	$("input[type=checkbox]:checked").each(function(){
        var number = parseInt($(this).val());
        console.log(number , v_items[number]);
        
        $.ajax({
			url: '/study5/insertDB', // 서버에 전달할 파일명
			dataType: 'text',
			type: 'post',
			data: {
				/* ↓ VO 객체와 동일하게 			↓ 서버에서 주는 json 키값과 동일하게*/
				'adtfrdd': v_items[number].adtFrDd, 		// 전송할 파라미터 1
				'sggunm': v_items[number].sgguNm, 			// 전송할 파라미터 2
				'sidonm': v_items[number].sidoNm, 			// 전송할 파라미터 3
				'spcladmtycd': v_items[number].spclAdmTyCd, // 전송할 파라미터 4
				'telno': v_items[number].telno, 			// 전송할 파라미터 5
				'yadmnm': v_items[number].yadmNm,  			// 전송할 파라미터 6
				'hosptytpcd' : v_items[number].hospTyTpCd  	// 전송할 파라미터 7
			},
			success: function() {
			      alert('insert 성공'); // 성공시 코드
			}
		});
        
    });
});

$("#id_db_call").on("click",function(){
	$("#id_hosDB").html("");
	
	$.ajax({
		type :"POST" 						// 전송 방식 설정 (Defaut : GET)
	  , url : "/study5/hospitalList"		// 요청 페이지 URL정보
	  , dataType : 'json'  					// 서버로부터 전달받을 데이터 유형 (html, xml, json, script)
	//  , data : 					// 서버에 전송할 파라미터 정보
	  , success : function(data) {
			console.log('data',data.data[1]);
			if(data.result) {
				$hosDB = $('#id_hosDB');
				var dbstr = "<table border='1'>";
				dbstr += "<tr><th>선택</th><th>NO</th><th>시도</th><th>군구</th><th>병원이름</th><th>전화번호</th></tr>"
				$.each(data.data, function(index, element) {
		        	console.log("반복?");
					dbstr += "<tr>";                                                                              
					dbstr += "<td>" + "<input type='checkbox' " + "value='" + index + "' name='checkInfo'>" + "</td>";
					dbstr += "<td>" + element.no + "</td>";                                                
					dbstr += "<td>" + element.sidonm + "</td>"; 
					dbstr += "<td>" + element.sggunm + "</td>"; 
					dbstr += "<td>" + element.yadmnm + "</td>";                                                
					dbstr += "<td>" + element.telno + "</td>";                                                 
					dbstr += "</tr>";      
				console.log(dbstr);
				});
					$hosDB.append(dbstr);
				dbstr += "</table>";
			}
		} 		 		// 요청에 성공한 경우 호출되는 함수 (data, status, xhr )
	  , error :	function(req, st, err) {
			console.log('--------------------------------------');
			console.log('request', req);
			console.log('status', st);
			console.log('errors', err);
			console.log('--------------------------------------');
		}			// 요청에 실패한 경우 호출되는 함수 (xhr, status, error)
	}); 	// ajax
		
});
 
</script>
</body>
</html>