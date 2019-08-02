package com.twm.community.entity;

/**
 *  页面封装相关信息
 */

public class Page {
    // 当前页码
    private int current = 1;
    // 显示上限
    private int limit=10;
    // 数据总数，rows(用于计算总页数)
    private int rows;
    // 查询路径（用于复用分页链接）
    private String path;


    public int getCurrent() {
        return current;
    }
    // 设置当前页码，防止不合理的页数
    public void setCurrent(int current) {
        if(current>=1) {
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if(limit>=1 && limit<=100) {
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if(rows>=0){
          this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    // 获取当前页的起始行
    public int getOffset(){
        return (current-1)*limit;
    }

    // 获取总的页数
    public int getTotal(){
        if (rows%limit==0){
            return rows/limit;
        }
        else{
            return rows/limit+1;
        }
    }
    // 页面上显示从那一页开始(每一页显示5个页数)
    public int getFrom(){

        if (current/5==0){ // 如果当前页小于5
            return  1;  // 从第一页的超链接显示
        }
        else {
            return (current / 5) * 5; // 从整除5的页码开始
        }
    }
    //  页面上显示到那一页结束
    public int getTo(){
        int to = this.getFrom()+4;
        if (to> this.getTotal()){
            return this.getTotal();
        }
        else {
            return to;
        }
    }


}
