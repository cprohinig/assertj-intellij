package com.cprohinig.assertj.actions;

import com.intellij.psi.PsiMethod;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AssertClassContentWrapperImpl implements AssertClassContentWrapper {

    private final String packageName;
    private final List<String> importClassesQualifiedNames;
    private final String originalClassName;
    private final String assertClassName;
    private final List<PsiMethod> getters;

    AssertClassContentWrapperImpl(@Nonnull String packageName,
                                  @Nonnull List<String> importClassesReferences,
                                  @Nonnull String originalClassName,
                                  @Nonnull List<PsiMethod> getters) {
        this.packageName = Objects.requireNonNull(packageName);
        this.importClassesQualifiedNames = Objects.requireNonNull(importClassesReferences);
        this.originalClassName = Objects.requireNonNull(originalClassName);
        this.getters = Objects.requireNonNull(getters);
        assertClassName = originalClassName + "Assert";
    }

    @Override
    public String getContent() {
        return getPackageStatement() +
                getImportsStatements() +
                getClassStatement() +
                getStaticVariablesDeclarations() +
                getConstructorStatement() +
                getAssertionsStatements() +
                getFooter();
    }

    private String getPackageStatement() {
        return "package " + packageName + ";";
    }

    private String getImportsStatements() {
        return importClassesQualifiedNames.stream()
                .map(classReference -> "import " + classReference + ";")
                .collect(Collectors.joining());
    }

    private String getClassStatement() {
        String classStatement = String.format("public class %s extends AbstractObjectAssert<%s, %s> {", assertClassName, assertClassName, originalClassName);
        return classStatement;
    }

    private String getStaticVariablesDeclarations() {
        return "private static final String ERROR_MESSAGE = \"Expected %s to be <%s> but was <%s> (%s)\"";
    }

    private String getAssertionsStatements() {
        StringBuilder out = new StringBuilder();

        // assertions
        for (PsiMethod getter : getters) {
            out.append(getAssertionMethodSignature(getter, assertClassName));
            out.append(getActualDeclaration(getter));
            out.append(getAssertionStatement(getter));
            out.append("return this;");
            out.append("}");
        }

        return out.toString();
    }

    private String getFooter() {
        return "}";
    }

    private String getConstructorStatement() {
        return String.format("%s(%s actual) {super(actual, %s.class);}", assertClassName, originalClassName, assertClassName);
    }

    private String getAssertionMethodSignature(PsiMethod getter, String className) {
        String assertionMethodName = getter.getName().replace("get", "has");
        return String.format("public %s %s(%s expected) {", className, assertionMethodName, getter.getReturnType().getPresentableText());
    }

    private String getActualDeclaration(PsiMethod getter) {
        return String.format("%s %s = actual.%s();", getter.getReturnType().getPresentableText(), getActualVariableName(getter), getter.getName());
    }

    private String getAssertionStatement(PsiMethod getter) {
        return String.format("Assertions.assertThat(%s)\n" +
                        ".overridingErrorMessage(ERROR_MESSAGE, \"%s\", expected, %s, descriptionText())\n" +
                        ".isEqualTo(expected);",
                getActualVariableName(getter),
                getFieldName(getter).toLowerCase(),
                getActualVariableName(getter)
        );
    }

    private String getActualVariableName(PsiMethod getter) {
        return String.format("actual%s", getFieldName(getter));
    }

    private String getFieldName(PsiMethod getter) {
        if (getter.getName().startsWith("get")) {
            return getter.getName().substring(3);
        }

        if (getter.getName().startsWith("is")) {
            return getter.getName().substring(2);
        }

        throw new IllegalArgumentException("Method has an unknown prefix. Allowed prefixes: get, is");
    }
}
