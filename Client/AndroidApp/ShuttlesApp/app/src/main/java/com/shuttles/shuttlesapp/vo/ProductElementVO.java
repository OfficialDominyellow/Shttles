package com.shuttles.shuttlesapp.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by domin on 2018-05-27.
 */

abstract public class ProductElementVO {
    private String name;
    private int count;
    private transient int originalPrice; //option 제외 가격
    private int price; //option 포함 가격

    private transient int oid; //객체의 고유 id, 자동 생성

    private List<OptionElementVO> option = new ArrayList<>();

    ProductElementVO(String name, int cnt, int orgPrice, int unitPrice, int oid, List<OptionElementVO> optionList){
        this.name = name;
        this.count = cnt;
        this.price = unitPrice;

        this.oid = oid;

        option = optionList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public List<OptionElementVO> getOption() {
        return option;
    }

    public void setOption(List<OptionElementVO> option) {
        this.option = option;
    }

    public void decreaseCnt(){
        this.count -= 1;
    }
    public void increaseCnt(){
        this.count += 1;
    }
}
