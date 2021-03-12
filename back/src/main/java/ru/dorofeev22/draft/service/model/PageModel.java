package ru.dorofeev22.draft.service.model;

import java.util.List;

public class PageModel<M> {

    public PageModel(int totalPages, long totalElements, List<M> items) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.items = items;
    }

    private final int totalPages;
    private final long totalElements;
    private final List<M> items;

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