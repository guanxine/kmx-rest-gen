package com.k2data.platform.rest.demo.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;
import java.util.Map;

/**
 * Created by guanxine on 18-04-04.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Page<T> {
    public static long DEFAULT_PAGE_SIZE = 10;
    private Info pageInfo;
    private List<T> items;
    private Map<String, Boolean> opts;


    public Page(List<T> items, long pageNum, long pageSize, long total) {
        this.items = items;
        this.pageInfo = new Info(pageNum, pageSize, total, this.items.size());
    }

    public static long getOffset(long page, long pageSize) {

        if (page < 1) {
            page = 1L;
        }

        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        return (page - 1) * pageSize;
    }

    public class Info {


        private long total;
        private long pages;
        private long pageSize;
        private long pageNum;
        private long size;

        public Info(long pageNum, long pageSize, long total, long size) {

            if (pageNum < 1) {
                this.pageNum = 1L;
            }
            else {
                this.pageNum = pageNum;
            }

            if (pageSize < 1) {
                this.pageSize = DEFAULT_PAGE_SIZE;
            }
            else {
                this.pageSize = pageSize;
            }

            this.total = total;

            this.size = size;
            this.pages = this.total % (this.pageSize) > 0 ? this.total / (this.pageSize) + 1 : this.total / (this.pageSize);
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public long getPageSize() {
            return pageSize;
        }

        public void setPageSize(long pageSize) {
            this.pageSize = pageSize;
        }

        public long getPageNum() {
            return pageNum;
        }

        public void setPageNum(long pageNum) {
            this.pageNum = pageNum;
        }

        public long getPages() {
            return pages;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public Info getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(Info pageInfo) {
        this.pageInfo = pageInfo;
    }

    public Map<String, Boolean> getOpts() {
        return opts;
    }

    public void setOpts(Map<String, Boolean> opts) {
        this.opts = opts;
    }
}
