package ru.dorofeev22.draft.core.constant;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RequestConstants {
    
    public static final String LIMIT = "$limit";
    public static final String OFFSET = "$offset";
    public static final String SORT_BY = "$sortBy";
    
    public static final Set<String> pageableParameters;
    
    static {
        final String[] setValues = new String[] { LIMIT, OFFSET, SORT_BY };
        pageableParameters = new HashSet<>(Arrays.asList(setValues));
    }
}