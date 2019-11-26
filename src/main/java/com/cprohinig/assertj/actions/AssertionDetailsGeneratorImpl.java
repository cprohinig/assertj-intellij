package com.cprohinig.assertj.actions;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiPrimitiveType;
import com.intellij.psi.util.PsiTypesUtil;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AssertionDetailsGeneratorImpl implements AssertionDetailsGenerator {

    private static boolean isNotPrimitiveType(PsiMethod m) {
        return !(m.getReturnType() instanceof PsiPrimitiveType);
    }

    @Nonnull
    private static String getQualifiedName(PsiMethod m) {
        PsiClass parameterClass = PsiTypesUtil.getPsiClass(m.getReturnType());
        if (parameterClass != null && parameterClass.getQualifiedName() != null) {
            return parameterClass.getQualifiedName();
        } else {
            return "";
        }
    }

    @Override
    public AssertClassContentWrapper generateAssertClass(PsiJavaFile javaFile, List<PsiMethod> selections) {
        String originalClassName = javaFile.getClasses()[0].getName();

        List<String> importClassesReferences = selections.stream()
                .filter(AssertionDetailsGeneratorImpl::isNotPrimitiveType)
                .map(AssertionDetailsGeneratorImpl::getQualifiedName)
                .collect(Collectors.toList());

        importClassesReferences.add(javaFile.getClasses()[0].getQualifiedName());
        importClassesReferences.add("org.assertj.core.api.AbstractObjectAssert");
        importClassesReferences.add("org.assertj.core.api.Assertions");

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