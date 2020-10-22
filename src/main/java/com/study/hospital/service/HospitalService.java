package com.study.hospital.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.hospital.vo.HospitalVO;

@Service
public class HospitalService {
	
	@Autowired
	HospitalMapper hospitalMapper;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public List<HospitalVO> getHospitalList(HospitalVO hospitalVO) {
		List<HospitalVO> hospitalList = hospitalMapper.getHospitalList(hospitalVO);
		return hospitalList;
	}
	
	public void insertHospital(HospitalVO hospitalVO) {
		hospitalMapper.insertHospital(hospitalVO);
	}
	
	public void excelUpload(File destFile) {
			
			ExcelReadOption excelReadOption = new ExcelReadOption();
			
			//파일경로 추가
			excelReadOption.setFilePath(destFile.getAbsolutePath());
			
			//추출할 컬럼명 추가
			excelReadOption.setOutputColumns("A", "B", "C", "D", "E", "F", "G", "H");
			
			//시작행
			excelReadOption.setStartRow(2);
			
			List<Map<String, String>> excelContentList  = ExcelRead.read(excelReadOption);
			
			try {
				for (Map<String, String> map : excelContentList) {
				
					logger.debug("map={}", map);
					hospitalMapper.insertExcel(map);
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	
}