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
			e.printStackTrace();
		} 
		return result.toString();
	}
	
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
	
	@GetMapping("/hospitalDBList")
	public String hospitalDBList(HospitalVO hospitalVO, ModelMap model) {
		logger.debug("hospitalVO={}", hospitalVO);
		
		List<HospitalVO> hospitalVOList = hospitalService.getHospitalList(hospitalVO);
		model.addAttribute("hospitalVOList", hospitalVOList);
		logger.debug("hospitalVOList={}", hospitalVOList);
		
		return "hospitalList";
	}
	
	@RequestMapping("/hospitalView")
	public String hospitalView(int no, ModelMap model) {
		logger.debug("no={}", no);
		
		HospitalVO hospitalVO =  hospitalService.getHospitalVO(no);
		
		model.addAttribute("hospitalVO", hospitalVO);
		
		return "/hospitalView";
	}
	
	
	@PostMapping("/insertDB")
	public String insertDB(HospitalVO hospitalVO) {
		logger.debug("hospitalVO={}", hospitalVO);
		
		hospitalService.insertHospital(hospitalVO);
		
		return "hospital";
	}
	
	@RequestMapping("/exceldownload")
	public void excelDownload(HospitalVO hospitalVO, HttpServletResponse response, Model model) throws Exception {
		HSSFWorkbook objWorkBook = new HSSFWorkbook();
		HSSFSheet	 objSheet = null;
		HSSFRow objRow = null;
		HSSFCell objCell = null;       //셀 생성

	  objSheet = objWorkBook.createSheet("hospital1");     //워크시트 생성

	  // 1행
	  objRow = objSheet.createRow(0);
	  objRow.setHeight ((short) 0x150);

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

		  objRow = objSheet.createRow(index);
		  objRow.setHeight ((short) 0x150);
		  
		  objCell = objRow.createCell(0);
		  objCell.setCellValue(hospitalVO2.getNo());
		  
		  objCell = objRow.createCell(1);
		  objCell.setCellValue(hospitalVO2.getAdtfrdd());
		  
		  objCell = objRow.createCell(2);
		  objCell.setCellValue(hospitalVO2.getHosptytpcd());
		  
		  objCell = objRow.createCell(3);
		  objCell.setCellValue(hospitalVO2.getSggunm());
		  
		  objCell = objRow.createCell(4);
		  objCell.setCellValue(hospitalVO2.getSidonm());
		  
		  objCell = objRow.createCell(5);
		  objCell.setCellValue(hospitalVO2.getSpcladmtycd());
		  
		  objCell = objRow.createCell(6);
		  objCell.setCellValue(hospitalVO2.getTelno());
		  
		  objCell = objRow.createCell(7);
		  objCell.setCellValue(hospitalVO2.getYadmnm());
		  
		  index++;
	  }
	  
	  for (int i = 0; i < hospitalVOList.size(); i++) {
		  objSheet.autoSizeColumn(i);
	  }

	  response.setContentType("Application/Msexcel");
	  response.setHeader("Content-Disposition", "ATTachment; Filename="+URLEncoder.encode("hospital_api_dw","UTF-8")+".xls");

	  OutputStream fileOut  = response.getOutputStream();
	  objWorkBook.write(fileOut);
	  fileOut.close();

	  response.getOutputStream().flush();
	  response.getOutputStream().close();	
	  
	  objWorkBook.close();
	}
	
	@ResponseBody
	@PostMapping("/excelupload")
	public ModelAndView excelUpload(MultipartFile uploadFile, MultipartHttpServletRequest request) throws Exception {
		logger.debug("업로드 진행");
		
		MultipartFile excelFile = request.getFile("excelFile");
		
		if(excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("엑셀 파일을 선택해 주세요.");
		}
		
		File destFile = new File("C:\\tools\\upload"+excelFile.getOriginalFilename());
		
		try {
			excelFile.transferTo(destFile);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} 
		
		hospitalService.excelUpload(destFile);
		
		destFile.delete();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/hospitalList");
		
		return mav;
	}

}
