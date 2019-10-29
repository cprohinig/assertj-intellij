package com.cprohinig.assertj.actions;

import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AssertionDetailsGeneratorImpl implements AssertionDetailsGenerator {
    @Override
    public String generatePackageStatement(PsiJavaFile javaFile) {
        return javaFile.getPackageName() + ";import " + javaFile.getClasses()[0].getQualifiedName() + ";";
    }

    @Override
    public String generateClassDeclaration(PsiJavaFile javaFile) {
        String className = javaFile.getClasses()[0].getName() + "Assert";
        return String.format("public class %s extends AbstractObjectAssert<%s, %s> {", className, className, javaFile.getClasses()[0].getName());
    }

    @Override
    public String generateAssertions(PsiJavaFile javaFile) {
        List<PsiMethod> getters = Arrays.stream(javaFile.getClasses()[0].getAllMethods())
                .filter(m -> isGetter(m))
                .collect(Collectors.toList());

        String className = javaFile.getClasses()[0].getName() + "Assert";

        // constructor
        String out = String.format("%s(%s actual) {super(actual, %s.class);}", className, javaFile.getClasses()[0].getName(), className);

        // assertions
        for (PsiMethod getter : getters) {
            String assertionMethodName = getter.getName().replace("get", "has");
            String signature = String.format("public %s %s(%s expected) {", className, assertionMethodName, getter.getReturnType().getPresentableText());
            String fieldName = getter.getName().substring(3);
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
            String body = String.format("return isNotNull().isEqualTo(actual::%s, expected, \"%s\");}", getter.getName(), fieldName);
            out += signature + body;
        }

        return out;
    }

    public boolean isGetter(PsiMethod method) {
        return method.getName().startsWith("get") && !method.getName().equals("getClass");
    }

    @Override
    public String generateFooter(PsiJavaFile javFile) {
        return "}";
    }
}
