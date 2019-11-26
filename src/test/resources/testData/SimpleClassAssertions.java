package com.p.helloworld;

import com.p.helloworld.SimpleClass;
import com.compuware.apm.webui.rest.common.api.assertions.AbstractObjectAssert;

public class SimpleClassAssert extends AbstractObjectAssert<SimpleClassAssert, SimpleClass> {
    SimpleClassAssert(SimpleClass actual) {
        super(actual, SimpleClassAssert.class);
    }

    public SimpleClassAssert hasSomething(String expected) {
        return isNotNull().isEqualTo(actual::getSomething, expected, "something");
    }
}