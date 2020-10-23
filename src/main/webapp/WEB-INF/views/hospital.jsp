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

.inline-block {	/* 왜 안먹지.. */
	position: relative;
	display: inline-block;
}

#id_hosInfo {
	position: relative;
	vertical-align: top;
	display: inline-block;
	width: 550px;
	height: 500px;
	border: 1px solid black;
	overflow: scroll;
}

#myGrid {
	position: relative;
	vertical-align: top;
	display: inline-block;
	width: 550px;
	height: 500px;
	border: 1px solid black;
}
</style>
<script src="https://cdnjs.cloudflare.com/ajax/libs/x2js/1.2.0/xml2json.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="http://malsup.github.com/jquery.form.js"></script> 
<script src="https://unpkg.com/ag-grid-community/dist/ag-grid-community.min.js"></script>
</head>
<body>

<div>

<div id="id_hosInfo" >
	<input type="button" id="id_save" value="DB에 저장하기">
</div>

<div id="myGrid" class="ag-theme-alpine" style="height: 500px; width:500px;">
<h1>DB에 저장된 데이터</h1>
<input type="button" id="id_db_call" value="DB 데이터 불러오기" class="inline-block">
<form method="post" action="exceldownload" class="inline-block">
	<input type="submit" value="엑셀로 다운로드" />
</form>
<br><br>
<form id="excelUploadForm" method="post" action="excelupload" enctype="mulripart/form-data" class="inline-block">
	<input type="file" name="excelFile" id="excelFile" />
	<button type="button" id="addExcelImpoartBtn" onclick="check()" ><span>DB에 엑셀 데이터 삽입</span></button>
</form>
<br><br>
</div>

</div>
<script type="text/javascript">

var v_newWin = null;		// 새 창
var v_items;				// API에서 가져온 데이터를 DB에 저장할 때도 사용해야하므로 전역에 선언함
var jsonObj;
var v_hosInfo = document.getElementById("id_hosInfo");
var v_ajax = new XMLHttpRequest();

v_ajax.open("get", "/study5/apiCall", true);
v_ajax.send();

v_ajax.onreadystatechange = function () {
    if (v_ajax.readyState == 4 && v_ajax.status == 200) {
        // XML을 JSON형식으로 변환 (가공하기 쉽게)
        var x2js = new X2JS();
        jsonObj = x2js.xml_str2json(v_ajax.responseText);

        // 잘 가져오는지 확인
        console.log("API에서 가져온 데이터", jsonObj.response.body.items.item);
        v_items = jsonObj.response.body.items.item;

        var v_tblStr = "<h1>API에서 호출한 데이터</h1><table border='1'>"; 
        v_tblStr += "<tr><th>선택</th><th>시도</th><th>군구</th><th>병원이름</th><th>전화번호</th></tr>";
        for (var i = 0; i < v_items.length; i++) {
            v_tblStr += "<tr>";
            v_tblStr += "<td>" + "<input type=checkbox value=" + i + " name=checkInfo>" + "</td>";
            v_tblStr += "<td>" + v_items[i].sgguNm + "</td>";
            v_tblStr += "<td>" + v_items[i].sidoNm + "</td>";
            v_tblStr += "<td>" + v_items[i].yadmNm + "</td>";
            v_tblStr += "<td>" + v_items[i].telno + "</td>";
            v_tblStr += "</tr>";
        }
        v_tblStr += "</table>";
        v_hosInfo.innerHTML += v_tblStr;
    }
    
    //저장하기 버튼 이벤트
    $("#id_save").on("click",function(){
	    $("input[type=checkbox]:checked").each(function(){
		     var number = parseInt($(this).val());
		     console.log(number , v_items[number]);
	          $.ajax({
		            url: '/study5/insertDB', // 서버에 전달할 파일명
		            dataType: 'text',
		            type: 'post',
		            data: {
		               //  ↓ VO 객체와 동일하게 			↓ 서버에서 주는 json 키값과 동일하게
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
};


// 테이블 형식으로 DB 불러오기
/*
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
 */

 
// Grid 사용하여 DB 불러오기
$("#id_db_call").on("click", function () {
    f_data();
});

var f_data = function () {
    // ES6버전의 Promise는 국내 현실상(IE11버전 호환성) 아직 쓰기가 무리가 있음
    //그래서 AJAX부분은 직접 구현해서 사용하는 것이 현실적인 대안
    var v_ajax = new XMLHttpRequest();
    v_ajax.open("get", "/study5/hospitalList", true);
    v_ajax.onreadystatechange = function () {
        if (v_ajax.readyState == 4 && v_ajax.status == 200) {
            console.log(JSON.parse(v_ajax.responseText).data);  // 데이타 가져왔는지 체크

            //aggrid에서 rowdata세팅해주는 API
            gridOptions.api.setRowData(JSON.parse(v_ajax.responseText).data);
        }
    }
    v_ajax.send();
};

var columnDefs = [
    {
     headerName: "번호", field: "no", sortable: true, sort: 'asc', width: 110,
     headerCheckboxSelection: true,
     headerCheckboxSelectionFilteredOnly: true,
     checkboxSelection: true
    },
    { headerName: "시도명", field: "sidonm", sortable: true, filter: true, width: 110 },
    { headerName: "시군구명", field: "sggunm", sortable: true, filter: true, width: 110 },
    { headerName: "전화번호", field: "telno", sortable: true },
    { headerName: "기관명", field: "yadmnm", sortable: true }
];

// specify the data
var rowData = [];

// let the grid know which columns and what data to use
var gridOptions = {
    pagination: true,
    paginationPageSize: 8,
    columnDefs: columnDefs,
    rowData: rowData,
    rowSelection: 'multiple',
    domLayout: 'autoHeight',
    onRowClicked: function (event) {
        console.log(gridOptions.api.getSelectedRows()[0].no);

        // 상세보기 화면 새 창으로 열고 파라미터로 번호 전달
        v_newWin = window.open("/study5/hospitalView?no=" + gridOptions.api.getSelectedRows()[0].no, "",
            "left=500, top=200, width=450,height=350");
    }
};

// DOMContentLoaded가 onload 이벤트보다 더 빠름
document.addEventListener('DOMContentLoaded', function () {
    var gridDiv = document.querySelector('#myGrid');
    new agGrid.Grid(gridDiv, gridOptions);	// 그리드 생성
});

// 엑셀파일이 맞는지 체크
function checkFileType(filepath) {
    var fileFormat = filepath.split(".");

    if (fileFormat.indexOf("xls") > -1 || fileFormat.indexOf("xlsx") > -1) {
        return true;
    } else {
        return false;
    }
};

// DB에 데이터 삽입 버튼을 누르면 발생하는 이벤트
function check() {
    var file = $("#excelFile").val();

    if (file == "" || file == null) {
        alert("파일을 선택해주세요.");

        return false;
    } else if (!checkFileType(file)) {
        alert("엑셀 파일만 업로드 가능합니다.");

        return false;
    }

    if (confirm("업로드 하시겠습니까?")) {
        var options = {
            success: function (data) {
                alert("모든 데이터가 업로드 되었습니다.");
            },
            type: "POST"
        };
        $("#excelUploadForm").ajaxSubmit(options);
    }
};
</script>
</body>
</html>