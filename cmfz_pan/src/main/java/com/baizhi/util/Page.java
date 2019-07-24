package com.baizhi.util;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Page<T> {
	/**
	 * 页面大小
	 * */
	private int pageSize=10;
	/**
	 * 当前页面
	 * */
	@JsonProperty("page")
	private int currentPage=1;
	/**
	 * 总条数
	 * */
	@JsonProperty("records")
	private long total;
	/**
	 * 开始条数
	 */
	private long begin;
	/**
	 * 结束条数
	 */
	private long end;
	/**
	 * 总页数
	 * */
	@JsonProperty(value = "total")
	private long totalPage;
	/**
	 * 结果集
	 * */
	@JsonProperty(value = "rows")
	private List<T> result;




	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<T> getResult() {
		return result;
	}
	public void setResult(List<T> result) {
		this.result = result;
	}
	/**
	 * 总页数
	 * */
	public long getTotalPage() {
		return this.total%this.pageSize != 0 ? this.total/this.pageSize + 1 : this.total/this.pageSize;
	}
	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}
	/**
	 * 开始条数
	 */
	public long getBegin() {
		return (this.currentPage-1)*this.pageSize;
	}
	public void setBegin(long begin) {
		this.begin = begin;
	}
	/**
	 * 结束条数
	 */
	public long getEnd() {
		return this.currentPage*this.pageSize;
	}
	public void setEnd(long end) {
		this.end = end;
	}
	public Page() {
		super();
	}
	public Page(int currentPage, int pageSize) {
		super();
		this.pageSize = pageSize;
		this.currentPage = currentPage;
	}
	@Override
	public String toString() {
		return "Page [pageSize=" + pageSize + ", currentPage=" + currentPage
				+ ", total=" + total + ", begin=" + begin + ", end=" + end
				+ ", totalPage=" + this.getTotalPage() + ", result=" + result + "]";
	}
	
}