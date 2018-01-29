package com.periodicals.command.util;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import static com.periodicals.utils.resourceHolders.AttributesHolder.*;

/**
 * @author Daniel Volnitsky
 * <p>
 * Util class that contains static methods for commands
 * @see Command
 */
public class CommandUtils {

    public static boolean isGetMethod(HttpServletRequest request) {
        return request.getMethod().equals(GET);
    }

    public static boolean isPostMethod(HttpServletRequest request) {
        return request.getMethod().equals(POST);
    }

    /**
     * Checks if all incoming objects are not empty
     */
    public static boolean requiredFieldsNotEmpty(Object[] fields) {
        for (Object field : fields) {
            if (stringIsNullOrEmpty(field)) {
                return false;
            }
        }
        return true;
    }

    public static boolean stringIsNullOrEmpty(Object object) {
        return Objects.isNull(object) || Objects.equals(object, "");
    }

    /**
     * Checks if some parameter clarified in request
     */
    public static boolean paramClarifiedInQuery(HttpServletRequest request, String paramName) {
        String param = request.getParameter(paramName);
        return !stringIsNullOrEmpty(param);
    }

    /**
     * @return referer path withour servlet path at the beginning
     */
    public static String getRefererWithoutServletPath(HttpServletRequest request) {
        String referer = DEFAULT;

        String header = request.getHeader("referer");
        if (header != null && !header.isEmpty()) {
            try {
                String path = new URI(header).getPath();
                String query = new URI(header).getQuery();
                referer = Objects.isNull(query) ? path : path + "?" + query;
            } catch (URISyntaxException e) {
                /*TODO log*/
            }
        }

        if (referer.startsWith(SERVLET_ROOT)) {
            referer = referer.substring(SERVLET_ROOT.length());
        }

        return referer;
    }
}
