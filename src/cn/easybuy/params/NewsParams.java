package cn.easybuy.params;

import cn.easybuy.entity.News;

//新闻的分页
public class NewsParams extends News{
	
	private Integer startIndex;
	
	private Integer pageSize;

	private String sort;
	
	private boolean isPage=false;
	
	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public boolean isPage() {
		return isPage;
	}

	public void setPage(boolean isPage) {
		this.isPage = isPage;
	}

	public void openPage(Integer startIndex, Integer pageSize) {
		this.isPage = true;
		this.startIndex = startIndex;
		this.pageSize = pageSize;
	}
}
