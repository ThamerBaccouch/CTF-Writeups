package org.apache.commons.p004io.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/* renamed from: org.apache.commons.io.serialization.ValidatingObjectInputStream */
public class ValidatingObjectInputStream extends ObjectInputStream {
    private final List<ClassNameMatcher> acceptMatchers = new ArrayList();
    private final List<ClassNameMatcher> rejectMatchers = new ArrayList();

    public ValidatingObjectInputStream(InputStream input) throws IOException {
        super(input);
    }

    private void validateClassName(String name) throws InvalidClassException {
        for (ClassNameMatcher m : this.rejectMatchers) {
            if (m.matches(name)) {
                invalidClassNameFound(name);
            }
        }
        boolean ok = false;
        Iterator it = this.acceptMatchers.iterator();
        while (true) {
            if (it.hasNext()) {
                if (((ClassNameMatcher) it.next()).matches(name)) {
                    ok = true;
                    break;
                }
            } else {
                break;
            }
        }
        if (!ok) {
            invalidClassNameFound(name);
        }
    }

    /* access modifiers changed from: protected */
    public void invalidClassNameFound(String className) throws InvalidClassException {
        StringBuilder sb = new StringBuilder();
        sb.append("Class name not accepted: ");
        sb.append(className);
        throw new InvalidClassException(sb.toString());
    }

    /* access modifiers changed from: protected */
    public Class<?> resolveClass(ObjectStreamClass osc) throws IOException, ClassNotFoundException {
        validateClassName(osc.getName());
        return super.resolveClass(osc);
    }

    public ValidatingObjectInputStream accept(Class<?>... classes) {
        for (Class<?> c : classes) {
            this.acceptMatchers.add(new FullClassNameMatcher(c.getName()));
        }
        return this;
    }

    public ValidatingObjectInputStream reject(Class<?>... classes) {
        for (Class<?> c : classes) {
            this.rejectMatchers.add(new FullClassNameMatcher(c.getName()));
        }
        return this;
    }

    public ValidatingObjectInputStream accept(String... patterns) {
        for (String pattern : patterns) {
            this.acceptMatchers.add(new WildcardClassNameMatcher(pattern));
        }
        return this;
    }

    public ValidatingObjectInputStream reject(String... patterns) {
        for (String pattern : patterns) {
            this.rejectMatchers.add(new WildcardClassNameMatcher(pattern));
        }
        return this;
    }

    public ValidatingObjectInputStream accept(Pattern pattern) {
        this.acceptMatchers.add(new RegexpClassNameMatcher(pattern));
        return this;
    }

    public ValidatingObjectInputStream reject(Pattern pattern) {
        this.rejectMatchers.add(new RegexpClassNameMatcher(pattern));
        return this;
    }

    public ValidatingObjectInputStream accept(ClassNameMatcher m) {
        this.acceptMatchers.add(m);
        return this;
    }

    public ValidatingObjectInputStream reject(ClassNameMatcher m) {
        this.rejectMatchers.add(m);
        return this;
    }
}
