package com.example.newbeemall.vo;

import com.example.newbeemall.entity.TbNewbeeMallGoodsCategory;

import java.util.List;

public class SearchPageCategoryVO {

    private String firstLevelCategoryName;

    private List<TbNewbeeMallGoodsCategory> secondLevelCategoryList;

    private String secondLevelCategoryName;

    private List<TbNewbeeMallGoodsCategory> thirdLevelCategoryList;

    private String currentCategoryName;




    public String getFirstLevelCategoryName() {
        return firstLevelCategoryName;
    }

    public void setFirstLevelCategoryName(String firstLevelCategoryName) {
        this.firstLevelCategoryName = firstLevelCategoryName;
    }

    public List<TbNewbeeMallGoodsCategory> getSecondLevelCategoryList() {
        return secondLevelCategoryList;
    }

    public void setSecondLevelCategoryList(List<TbNewbeeMallGoodsCategory> secondLevelCategoryList) {
        this.secondLevelCategoryList = secondLevelCategoryList;
    }

    public String getSecondLevelCategoryName() {
        return secondLevelCategoryName;
    }

    public void setSecondLevelCategoryName(String secondLevelCategoryName) {
        this.secondLevelCategoryName = secondLevelCategoryName;
    }

    public List<TbNewbeeMallGoodsCategory> getThirdLevelCategoryList() {
        return thirdLevelCategoryList;
    }

    public void setThirdLevelCategoryList(List<TbNewbeeMallGoodsCategory> thirdLevelCategoryList) {
        this.thirdLevelCategoryList = thirdLevelCategoryList;
    }

    public String getCurrentCategoryName() {
        return currentCategoryName;
    }

    public void setCurrentCategoryName(String currentCategoryName) {
        this.currentCategoryName = currentCategoryName;
    }
}
