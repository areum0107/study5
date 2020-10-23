package com.study.hospital.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.study.hospital.service.HospitalService;
import com.study.hospital.vo.HospitalVO;

@Controller
public class HospitalController {
	
	@Autowired
	HospitalService hospitalService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	// api 호출하는 URL. 실행시 API의 XML데이터를 받아온다
	@ResponseBody
	@GetMapping("/apiCall")
	public String apicall(Model model) {
		StringBuffer result = new StringBuffer();
		String serviceKey = "WpPpDXkpSNbgh9rzwONWNzlzX2F2H%2FDQPhzeDwIFTMxTnEzGS%2BToA%2BBNzf1c4jWIX4GKY4fwK16KJVD92sIkfQ%3D%3D";
		
		try {
			String urlstr = "http://apis.data.go.kr/B551182/pubReliefHospService/getpubReliefHospList?" + 
							"ServiceKey=" + serviceKey +
							"&numOfRows=100" +
							"&pageNo=1" +
							"&spclAdmTyCd=A0";
			
			URL url = new URL(urlstr);
			HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
			urlconnection.setRequestMethod("GET");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream()));
			
			String returnLine;
			while ((returnLine = br.readLine()) != null) {
				result.append(returnLine + "\n");
			}
			urlconnection.disconnect();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} 
		// 결과를 문자열로 반환
		return result.toString();
	}
	
	// hospital.jsp로 연결만 해준다. 데이터는 jsp파일에서 스크립트로 호출해서 사용할 것
	@GetMapping("/hospital")
	public String hospitalApi() {
		return "hospital";
	}
	
	@RequestMapping(value = "/hospitalList", produces = "application/json;charset=UTF-8")
	@ResponseBody		// Map<String, Object>을 알아서 파싱해줌. 바꿔줄 라이브러리만 존재한다면
	public Map<String, Object> hospitalList(HospitalVO hospitalVO) {
		logger.debug("hospitalVO={}", hospitalVO);
		
		List<HospitalVO> hospitalVOList = hospitalService.getHospitalList(hospitalVO);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		map.put("data", hospitalVOList);
		map.put("count", hospitalVOList.size());
		
		return map;
	}
	
	// 병원 정보 상세보기 화면. 
	@RequestMapping("/hospitalView")
	public String hospitalView(int no, ModelMap model) {
		logger.debug("no={}", no);
		
		HospitalVO hospitalVO =  hospitalService.getHospitalVO(no);
		// 받아온 no로 조회, 데이터를 model에 hospitalVO라는 이름으로 담아서 hospitalView에서 사용할 수 있음
		model.addAttribute("hospitalVO", hospitalVO);
		
		return "/hospitalView";
	}
	
	// API 데이터를 DB에 insert
	@PostMapping("/insertDB")
	public String insertDB(HospitalVO hospitalVO) {
		logger.debug("hospitalVO={}", hospitalVO);
		
		hospitalService.insertHospital(hospitalVO);
		
		return "hospital";
	}
	
	// DB 리스트를 엑셀로 다운로드
	@RequestMapping("/exceldownload")
	public void excelDownload(HospitalVO hospitalVO, HttpServletResponse response, Model model) throws Exception {
		HSSFWorkbook objWorkBook = new HSSFWorkbook();	// 워크북 생성
		HSSFSheet	 objSheet = null;		// 시트 생성
		HSSFRow 	 objRow = null;			// 행 생성
		HSSFCell 	 objCell = null;        // 셀 생성

	  objSheet = objWorkBook.createSheet("hospital1");     //워크시트 생성

	  // 1행 생성. 컬럼 역할
	  objRow = objSheet.createRow(0);
	  objRow.setHeight ((short) 0x150);

	  // 여기서부터 셀 추가			  ↓ 번호에 따라
	  objCell = objRow.createCell(0);
	  objCell.setCellValue("NO");

	  objCell = objRow.createCell(1);
	  objCell.setCellValue("ADTFRDD");
	  
	  objCell = objRow.createCell(2);
	  objCell.setCellValue("HOSPTYTPCD");

	  objCell = objRow.createCell(3);
	  objCell.setCellValue("SGGUNM");
	  
	  objCell = objRow.createCell(4);
	  objCell.setCellValue("SIDONM");

	  objCell = objRow.createCell(5);
	  objCell.setCellValue("SPCLADMTYCD");
	  
	  objCell = objRow.createCell(6);
	  objCell.setCellValue("TELNO");

	  objCell = objRow.createCell(7);
	  objCell.setCellValue("YADMNM");

	  List<HospitalVO> hospitalVOList = hospitalService.getHospitalList(hospitalVO);
	  
	  int index = 1;
	  for (HospitalVO hospitalVO2 : hospitalVOList) {

		  // index가 1에서부터 시작하므로, 2번째 행부터 생성하며 hospitalVOList가 끝날때까지 반복
		  objRow = objSheet.createRow(index);
		  objRow.setHeight ((short) 0x150);
		  
		  objCell = objRow.createCell(0);
		  objCell.setCellValue(hospitalVO2.getNo());		// 번호
		  
		  objCell = objRow.createCell(1);
		  objCell.setCellValue(hospitalVO2.getAdtfrdd());	// 운영가능일자
		  
		  objCell = objRow.createCell(2);
		  objCell.setCellValue(hospitalVO2.getHosptytpcd());	// 선정유형
		  
		  objCell = objRow.createCell(3);
		  objCell.setCellValue(hospitalVO2.getSggunm());	// 시도명
		  
		  objCell = objRow.createCell(4);
		  objCell.setCellValue(hospitalVO2.getSidonm());	// 시군구명	
		  
		  objCell = objRow.createCell(5);
		  objCell.setCellValue(hospitalVO2.getSpcladmtycd());	// 구분코드
		  
		  objCell = objRow.createCell(6);
		  objCell.setCellValue(hospitalVO2.getTelno());		// 전화번호
		  
		  objCell = objRow.createCell(7);
		  objCell.setCellValue(hospitalVO2.getYadmnm());	// 기관명
		  
		  index++;
	  }
	  
	  for (int i = 0; i < hospitalVOList.size(); i++) {
		  objSheet.autoSizeColumn(i);	// 셀 너비 자동지정
	  }

	  response.setContentType("Application/Msexcel");
	  // 다운로드 될 파일의 이름 지정
	  response.setHeader("Content-Disposition", "ATTachment; Filename="+URLEncoder.encode("hospital_api_dw","UTF-8")+".xls");
	  
	  OutputStream fileOut  = response.getOutputStream();
	  objWorkBook.write(fileOut);
	  fileOut.close();

	  response.getOutputStream().flush();
	  response.getOutputStream().close();	
	  
	  objWorkBook.close();
	}
	
	// 엑셀 업로드
	@ResponseBody
	@PostMapping("/excelupload")
	public String excelUpload(MultipartFile uploadFile, MultipartHttpServletRequest request) throws Exception {
		logger.debug("업로드 파일={}", uploadFile);
		
		// hospital.jsp에 name이 excelFile인 input이 있음 .-
		MultipartFile excelFile = request.getFile("excelFile");
		
		// 추가할 파일이 선택되지 않은 경우
		if(excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("엑셀 파일을 선택해 주세요.");
		}
		
		// 파일을 경로에 저장하고, destFile라는 이름으로 저장
		File destFile = new File("C:\\tools\\upload"+excelFile.getOriginalFilename());
		
		try {
			excelFile.transferTo(destFile);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} 
		
		// destFile 엑셀의 내용을 읽어 DB에 저장하고
		hospitalService.excelUpload(destFile);
		
		// 경로에 있는 파일은 삭제
		destFile.delete();
		
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName("/hospitalList");
		
		return "/hospitalList";
	}

}
