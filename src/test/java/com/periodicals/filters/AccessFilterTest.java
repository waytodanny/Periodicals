package com.periodicals.filters;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccessFilterTest {

    private static final String DEFAULT_URI = "/";
    private static final String URI_WITH_PAGE_SUFFIX = "/pages/admin.jsp";
    private static final String CORRECT_URI = "/pages/admin";
    private static final String EMPTY_URI = "";

    private static AccessFilter filter;

    @BeforeAll
    static void init() {
        filter = new AccessFilter();
    }

    @AfterAll
    static void destroy() {
        filter = null;
    }

    /**
     * @see AccessFilter#getCorrectedRequestURI(HttpServletRequest)
     */
    @Test
    void getCorrectedRequestURI_correct_uri_return_same() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getRequestURI()).thenReturn(CORRECT_URI);

        String resultUri = filter.getCorrectedRequestURI(mockRequest);
        assertEquals(CORRECT_URI, resultUri);
    }

    @Test
    void getCorrectedRequestURI_uri_with_page_suffix_cut_suffix() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getRequestURI()).thenReturn(URI_WITH_PAGE_SUFFIX);

        String expectedUri = "/pages/admin";
        String resultUri = filter.getCorrectedRequestURI(mockRequest);
        assertEquals(expectedUri, resultUri);
    }

    @Test
    void getCorrectedRequestURI_empty_uri_return_default() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getRequestURI()).thenReturn(EMPTY_URI);

        String resultUri = filter.getCorrectedRequestURI(mockRequest);
        assertEquals(DEFAULT_URI, resultUri);
    }
}