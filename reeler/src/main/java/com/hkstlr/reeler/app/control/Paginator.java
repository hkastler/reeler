package com.hkstlr.reeler.app.control;

import java.util.logging.Logger;

import javax.inject.Inject;

/**
 *
 * @author henry.kastler
 */
public final class Paginator {

    private int pageSize;
    //pages have a 1 based index
    //list elements have 0 based index
    private int page = 1;
    private int numberOfPages = 1;
    private int numberOfItems = 0;

    
    public Paginator() {
        //required constructor
    }
   

    public Paginator(int pageSize, int page, int numberOfItems) {
        this.pageSize = pageSize;
        this.page = page;
        this.numberOfItems = numberOfItems;
        setNumberOfPages();
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public void setNumberOfPages() {
        this.numberOfPages = (int) Math.ceil((double) getNumberOfItems() / getPageSize());
    }
    
    public void setPages(int pageSize, int numberOfItems){
        this.pageSize = pageSize;
        this.numberOfItems = numberOfItems;
        setNumberOfPages();
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public int getPageFirstItem() {
        return getPageFirstItem(this.page, this.pageSize);
    }

    public int getPageFirstItem(int page, int pageSize) {

        int pageFirstItem = ((page * pageSize) - pageSize) + 1;
        if (pageFirstItem < 0) {
            pageFirstItem = 1;
        }

        return pageFirstItem;
    }

    public int getPageLastItem() {
        return getPageLastItem(getPageFirstItem(), this.pageSize, this.numberOfItems);
    }

    public int getPageLastItem(int firstPageItem, int pageSize, int numberOfItems) {

        int lastItemIndex = (firstPageItem - 1) + pageSize;
        int count = numberOfItems;
        
        if (lastItemIndex > count) {
            lastItemIndex = count;
        }
        if (lastItemIndex < 0) {
            lastItemIndex = 0;
        }

        
        return lastItemIndex;
    }

    public boolean hasNextPage() {
        return (page * pageSize) + 1 <= numberOfItems;
    }

    public void nextPage() {
        if (hasNextPage()) {
            page++;
        }
    }

    public boolean hasPreviousPage() {
        return page - 1 > 0;
    }

    public void previousPage() {
        if (hasPreviousPage()) {
            page--;
        }
    }
    
    public String toString(){
        return new JsonBuilder().toJsonString(this);
    }

}
