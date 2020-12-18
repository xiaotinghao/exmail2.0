package com.xs.common.model;

import java.io.Serializable;

/**
 * 分页查询实体组件
 *
 * @author 18871430207@163.com
 */
public class PageQuery implements Serializable {

    /** 序列号 */
    private static final long serialVersionUID = 1L;
    /** 记录总数 */
    private int total;
    /** 表示每页多少条记录 */
    private int pageSize;
    /** 表示当前第几页 */
    private int currentPage;
    /** 表示共有多少页 */
    private int pageCount;
    /** 当前从多少条显示 */
    private int start;
    /** 显示多少条 */
    private int length;
    /** 排序字段 */
    private String sortBy;
    /** 表格排序描述（ASC/DESC） */
    private String direction;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        if (this.pageSize != 0) {
            length = pageSize;

            if (currentPage == 0) {
                currentPage = 1;
            }
            //总页数
            pageCount = total / length;
            if (total % length != 0) {
                pageCount++;
            }

            //如果当前页数大于总页数时，当前页数等于总页数
            if (currentPage > pageCount) {
                currentPage = pageCount;
            }

            start = (currentPage - 1) * pageSize;
            if (start < 0) {
                start = 0;
            }
            if (start > total) {
                start = total;
            }

        }

        this.total = total;
    }

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

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

}
