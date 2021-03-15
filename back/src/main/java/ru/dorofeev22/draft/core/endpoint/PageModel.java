package ru.dorofeev22.draft.core.endpoint;

import java.util.List;

public class PageModel<M> {
    
    // for jackson
    public PageModel() {
    }
    
    public PageModel(int totalPages, long totalElements, List<M> items) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.items = items;
    }

    private int totalPages;
    private long totalElements;
    private List<M> items;

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public List<M> getItems() {
        return items;
    }
}