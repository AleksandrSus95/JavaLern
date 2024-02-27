package utils;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;

public class ParamHelper {
    private static final Pattern patternQuery = Pattern.compile("&");

    private ParamHelper() {

    }

    public static Map<String, List<String>> splitQuery(String query) {
        if (query == null || query.isEmpty()) {
            return Collections.emptyMap();
        }

        return patternQuery.splitAsStream(query).map(s -> Arrays.copyOf(s.split("="), 2))
                .collect(groupingBy(s -> decode(s[0]), mapping(s -> decode(s[1]), Collectors.toList())));
    }

    private static String decode(final String encoded) {
        return encoded == null ? null : URLDecoder.decode(encoded, UTF_8);
    }
}
