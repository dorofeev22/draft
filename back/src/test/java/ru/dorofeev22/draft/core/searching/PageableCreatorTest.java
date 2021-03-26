package ru.dorofeev22.draft.core.searching;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.dorofeev22.draft.core.searching.PageableCreator.*;

@RunWith(JUnit4.class)
public class PageableCreatorTest {
    
    @Test
    public void createPageableTest() {
        assertEquals(PageRequest.of(0, defaultRowLimit), createPageable(null, null, null));
        final Integer limit = 5;
        final Integer offset = 10;
        final String nameSortParam = "name";
        final String nameSortDirection = "desc";
        final String loginSortParam = "login";
        final String[] sortParams = new String[] {
           nameSortParam + SORT_ORDER_DELIMITER + nameSortDirection,
           loginSortParam
        };
        final List<Sort.Order> expectedSortOrders = new ArrayList<Sort.Order>() {{
            add(new Sort.Order(Sort.Direction.DESC, nameSortParam));
            add(new Sort.Order(Sort.Direction.ASC, loginSortParam));
        }};
        assertEquals(
                PageRequest.of(0, 5, Sort.by(expectedSortOrders)),
                createPageable(limit, Integer.valueOf(0), sortParams));
        assertEquals(
                PageRequest.of(2, 5),
                createPageable(limit, offset, null));
    }
}