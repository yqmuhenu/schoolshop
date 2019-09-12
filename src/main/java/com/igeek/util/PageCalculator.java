package com.igeek.util;

public class PageCalculator {
	/**
	 * 返回数据列表总页数
	 * @param totalCount
	 * @param pageSize
	 * @return
	 */
	public static int calculatePageCount(int totalCount, int pageSize) {
		int idealPage = totalCount / pageSize;
		int totalPage = (totalCount % pageSize == 0) ? idealPage
				: (idealPage + 1);
		return totalPage;
	}

	/**
	 * 获取当前页从第几行（条）数据开始（起始索引）
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static int calculateRowIndex(int pageIndex, int pageSize) {
		return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
	}
}
