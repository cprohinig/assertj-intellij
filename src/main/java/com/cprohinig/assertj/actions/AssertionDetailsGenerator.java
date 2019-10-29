package com.cprohinig.assertj.actions;

import com.intellij.psi.PsiJavaFile;

public interface AssertionDetailsGenerator {

    String generatePackageStatement(PsiJavaFile javaFile);

    String generateClassDeclaration(PsiJavaFile javaFile);

    String generateAssertions(PsiJavaFile javaFile);

    String generateFooter(PsiJavaFile javFile);
}
