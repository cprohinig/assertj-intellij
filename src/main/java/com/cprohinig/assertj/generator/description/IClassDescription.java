package com.cprohinig.assertj.generator.description;

import java.util.Set;

public interface IClassDescription {
    String getFullyQualifiedClassName();

    String getFullyQualifiedOuterClassName();

    String getClassNameWithOuterClass();

    String getPackageName();

    Set<GetterDescription> getGettersDescriptions();

    Set<FieldDescription> getFieldsDescriptions();

    Set<GetterDescription> getDeclaredGettersDescriptions();

    Set<FieldDescription> getDeclaredFieldsDescriptions();

    boolean hasGetterForField(FieldDescription field);

    String getFullyQualifiedAssertClassName();
}
