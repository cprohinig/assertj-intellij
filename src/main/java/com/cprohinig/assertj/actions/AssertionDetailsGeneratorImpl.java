package com.cprohinig.assertj.actions;

import com.intellij.psi.PsiJavaFile;

public class AssertionDetailsGeneratorImpl implements AssertionDetailsGenerator {
    @Override
    public String generatePackageStatement(PsiJavaFile javaFile) {
        return "";
    }

    @Override
    public String generateClassDeclaration(PsiJavaFile javaFile) {
        return "";
    }

    @Override
    public String generateAssertions(PsiJavaFile javaFile) {
        return "";
    }

    @Override
    public String generateFooter(PsiJavaFile javFile) {
        return "";
    }
}
