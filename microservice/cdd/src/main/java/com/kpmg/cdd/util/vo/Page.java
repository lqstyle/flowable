package com.kpmg.cdd.util.vo;

import java.util.List;

/**
 * @author lucasliang
 * @version 0.0.1-SNAPSHOT
 * @description: ${todo}
 * @date 28/06/2018 7:34 下午
 */
public class Page {

    public Page() {
    }

    public Page(long total, List items) {
        this.total = total;
        this.items = items;
    }

    private long total;
    //返回记录数
    private List items;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }
}
