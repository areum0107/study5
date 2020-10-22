package com.study.hospital.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.study.hospital.vo.HospitalVO;

@Mapper
public interface HospitalMapper {

	public List<HospitalVO> getHospitalList(HospitalVO hospitalVO);		// DB에 저장된 리스트 불러오기
	public void insertHospital(HospitalVO hospitalVO);					// 체크된 항목 DB에 insert
	void insertExcel(Map<String, String> paramMap) throws Exception;	// excel을 DB에 insert
}
