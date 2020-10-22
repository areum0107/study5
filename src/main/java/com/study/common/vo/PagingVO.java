package com.study.common.vo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings("serial")
public class PagingVO implements Serializable{
	
	private int curPage = 1;		/* 현재 페이지 번호 (def 1) */
	private int firstRow;			/* 페이징 SQL의 시작 rownum */
	private int lastRow;			/* 페이징 SQL의 마지막 rownum */
	private int totalPageCount;		/* 총 페이지 수 */
	private int rowSizePerPage =10; /* 한 페이지당 보여줄 행(게시물) 수 (def 10) */
	private int totalRowCount;		/* 총 게시물 수 */
	private int pageSize =5;		/* 페이지 리스트에 게시되는 페이지 건수 (def 5) */
	private int firstPage;			/* 페이지 리스트의 첫 페이지 번호 */
	private int lastPage;			/* 페이지 리스트의 마지막 페이지 번호 */
	
	
	/**
	 * 페이지 계산
	 */
	public void pageSetting() {
		// firstRow, lastRow 구하기
		// curPage = 1, rowSizePerPage = 10lastPage
		// curPage = 1 이면 -> firstRow = 1, lastRow = 10
		// curPage = 3 이면 -> firstRow = 21, lastRow = 30
		firstRow = (curPage-1) * rowSizePerPage + 1;
		lastRow = curPage * rowSizePerPage;
		
		totalPageCount = (totalRowCount -1) / rowSizePerPage + 1;
//		totalPageCount = (23 - 1)/10 + 1;  // 자바에서는 23 /10 계산하면 둘다 int형이라 2를 리턴하므로 +1 해준 것
						 // totalRowCount가 10의자리수이면 계산했을 때 소수점 안자르고 딱 떨어져서 +1했을 때 계산이 안맞으므로 -1한 상태에서 rowSizePerPage로 나누게 하는 것
	
		// firstPage, lastPage 계산식 완성하기
		firstPage = ((curPage-1) / pageSize) * pageSize + 1;
		lastPage = firstPage + pageSize - 1;
		if (lastPage > totalPageCount) {
			lastPage = totalPageCount;
		}
	}
	
	public static void main(String[] args) {
		PagingVO page = new PagingVO();
		page.setTotalRowCount(23);
		page.pageSetting();
		System.out.println(page);
		page.setCurPage(3);
		page.pageSetting();
		System.out.println(page);
	}
	
	// getter, setter, toString

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	} 
	
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getFirstRow() {
		return firstRow;
	}
	public void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}
	public int getLastRow() {
		return lastRow;
	}
	public void setLastRow(int lastRow) {
		this.lastRow = lastRow;
	}
	public int getTotalPageCount() {
		return totalPageCount;
	}
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	public int getRowSizePerPage() {
		return rowSizePerPage;
	}
	public void setRowSizePerPage(int rowSizePerPage) {
		this.rowSizePerPage = rowSizePerPage;
	}
	public int getTotalRowCount() {
		return totalRowCount;
	}
	public void setTotalRowCount(int totalRowCount) {
		this.totalRowCount = totalRowCount;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getFirstPage() {
		return firstPage;
	}
	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}
	public int getLastPage() {
		return lastPage;
	}
	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	
	
}
