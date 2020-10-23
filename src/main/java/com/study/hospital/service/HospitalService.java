package com.study.hospital.service;

import java.io.File;
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
	
	// DB에서 전체 리스트 뽑아오기
	public List<HospitalVO> getHospitalList(HospitalVO hospitalVO) {
		List<HospitalVO> hospitalList = hospitalMapper.getHospitalList(hospitalVO);
		return hospitalList;
	}
	
	// 상세정보
	public HospitalVO getHospitalVO(int no) {
		// 번호(no)를 파라미터로 넘겨 sql실행, hospitalVO에 담는다
		HospitalVO hospitalVO = hospitalMapper.getHospitalVO(no);
		
		return hospitalVO;
	}
	
	// API -> DB로 insert
	public void insertHospital(HospitalVO hospitalVO) {
		hospitalMapper.insertHospital(hospitalVO);
	}
	
	// 엑셀 업로드
	public void excelUpload(File destFile) {
			
			ExcelReadOption excelReadOption = new ExcelReadOption();
			
			// 파일경로 추가
			excelReadOption.setFilePath(destFile.getAbsolutePath());
			
			// 추출할 컬럼명 추가
			// 엑셀 프로그램 자체의 열 이름. 데이터의 컬럼명 X
			excelReadOption.setOutputColumns("A", "B", "C", "D", "E", "F", "G", "H");
			
			// 데이터 추출을 시작할 행번호
			excelReadOption.setStartRow(2);
			
			// 리스트 안에 맵
			List<Map<String, String>> excelContentList  = ExcelRead.read(excelReadOption);
			
			try {
				for (Map<String, String> map : excelContentList) {
				
					// map에 엑셀 한 줄 당 정보를 속성:값 으로 저장하고, 저장되었는지 확인
					logger.debug("map={}", map);
					hospitalMapper.insertExcel(map);	// 속성:값으로 저장된 map 정보를 넘겨줌
				}
				
			}catch(Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
	
}