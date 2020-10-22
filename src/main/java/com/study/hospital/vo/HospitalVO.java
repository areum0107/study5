package com.study.hospital.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class HospitalVO {
	public int no;				// 번호
	public int adtfrdd;			// 운영가능일자
	public String sidonm;		// 시도명
	public String sggunm;		// 시군구명
	public String spcladmtycd;	// 구분코드
	public String telno;		// 전화번호
	public String yadmnm;		// 기관명
	public String hosptytpcd;	// 선정유형
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getAdtfrdd() {
		return adtfrdd;
	}

	public void setAdtfrdd(int adtfrdd) {
		this.adtfrdd = adtfrdd;
	}

	public String getSggunm() {
		return sggunm;
	}

	public void setSggunm(String sggunm) {
		this.sggunm = sggunm;
	}

	public String getSidonm() {
		return sidonm;
	}

	public void setSidonm(String sidonm) {
		this.sidonm = sidonm;
	}

	public String getSpcladmtycd() {
		return spcladmtycd;
	}

	public void setSpcladmtycd(String spcladmtycd) {
		this.spcladmtycd = spcladmtycd;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String getYadmnm() {
		return yadmnm;
	}

	public void setYadmnm(String yadmnm) {
		this.yadmnm = yadmnm;
	}

	public String getHosptytpcd() {
		return hosptytpcd;
	}

	public void setHosptytpcd(String hosptytpcd) {
		this.hosptytpcd = hosptytpcd;
	}
	
}
