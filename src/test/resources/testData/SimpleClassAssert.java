package com.p.helloworld;

import com.p.helloworld.SimpleClass;
import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.Assertions;

public class SimpleClassAssert extends AbstractObjectAssert<SimpleClassAssert, SimpleClass> {
    private static final String ERROR_MESSAGE = "Expected %s to be <%s> but was <%s> (%s)";

    SimpleClassAssert(SimpleClass actual) {
        super(actual, SimpleClassAssert.class);
    }

    public SimpleClassAssert hasSomething(String expected) {
        String actualSomething = actual.getSomething();
        Assertions.assertThat(actualSomething)
                .overridingErrorMessage(ERROR_MESSAGE, "something", expected, actualSomething, descriptionText())
                .isEqualTo(expected);
        return this;
    }
}