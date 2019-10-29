package com.cprohinig.assertj.generator.description;

import java.lang.reflect.Member;

public interface IDataDescription {
    String getName();

    Member getOriginalMember();

    String getTypeName();

    String getFullyQualifiedTypeName();

    boolean isIterableType();

    boolean isArrayType();

    boolean isPrimitiveType();

    boolean isRealNumberType();

    boolean isWholeNumberType();

    boolean isCharType();

    boolean isPrimitiveWrapperType();

    boolean isPredicate();

    String getPredicate();

    String getNegativePredicate();

    boolean hasNegativePredicate();

    String getElementTypeName();

    String getElementAssertTypeName();

    String getAssertTypeName(String packageName);

    String getPredicateForJavadoc();

    String getNegativePredicateForJavadoc();

    String getPredicateForErrorMessagePart1();

    String getPredicateForErrorMessagePart2();

    String getNegativePredicateForErrorMessagePart1();

    String getNegativePredicateForErrorMessagePart2();

    boolean isPublic();
}
