package org.apache.commons.p004io.serialization;

import java.util.regex.Pattern;

/* renamed from: org.apache.commons.io.serialization.RegexpClassNameMatcher */
final class RegexpClassNameMatcher implements ClassNameMatcher {
    private final Pattern pattern;

    public RegexpClassNameMatcher(String regex) {
        this(Pattern.compile(regex));
    }

    public RegexpClassNameMatcher(Pattern pattern2) {
        if (pattern2 != null) {
            this.pattern = pattern2;
            return;
        }
        throw new IllegalArgumentException("Null pattern");
    }

    public boolean matches(String className) {
        return this.pattern.matcher(className).matches();
    }
}
