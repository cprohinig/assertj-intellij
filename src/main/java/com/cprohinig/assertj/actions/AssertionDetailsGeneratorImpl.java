package com.cprohinig.assertj.actions;

import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AssertionDetailsGeneratorImpl implements AssertionDetailsGenerator {

    @Override
    public AssertClassContentWrapper generateAssertClass(PsiJavaFile javaFile, List<PsiMethod> selections) {
        String originalClassName = javaFile.getClasses()[0].getName();
        List<String> importClassesReferences = Arrays.asList(
                javaFile.getClasses()[0].getQualifiedName(),
                "org.assertj.core.api.AbstractObjectAssert",
                "org.assertj.core.api.Assertions"
        );

        return new AssertClassContentWrapperImpl(javaFile.getPackageName(), importClassesReferences, originalClassName, selections);
    }

    private static boolean isGetter(PsiMethod method) {
        return (method.getName().startsWith("get")
                || method.getName().startsWith("is"))
                && !method.getName().equals("getClass")
                && method.getParameterList().isEmpty();
    }

    public static List<PsiMethod> extractGetters(PsiJavaFile javaFile) {
        return Arrays.stream(javaFile.getClasses())
                .peek(System.out::println)
                .flatMap(clazz -> Arrays.stream(clazz.getAllMethods()))
                .filter(AssertionDetailsGeneratorImpl::isGetter)
                .collect(Collectors.toList());
    }
}