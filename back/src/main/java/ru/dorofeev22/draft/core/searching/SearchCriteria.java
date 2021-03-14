package ru.dorofeev22.draft.core.searching;

import java.util.List;

public class SearchCriteria {
    
    private final String key;
    private final SearchOperation operation;
    private final List<Object> values;
    
    public SearchCriteria(String key, SearchOperation operation, List<Object> values) {
        this.key = key;
        this.operation = operation;
        this.values = values;
    }
    
    public String getKey() {
        return key;
    }
    
    public SearchOperation getOperation() {
        return operation;
    }
    
    public List<Object> getValues() {
        return values;
    }
}