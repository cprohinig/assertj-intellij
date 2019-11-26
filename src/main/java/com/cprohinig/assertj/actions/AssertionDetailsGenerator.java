package com.cprohinig.assertj.actions;

import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;

import java.util.List;

public interface AssertionDetailsGenerator {

    String generatePackageStatement(PsiJavaFile javaFile);

    String generateClassDeclaration(PsiJavaFile javaFile);

    String generateAssertions(PsiJavaFile javaFile, List<PsiMethod> selections);

    String generateFooter(PsiJavaFile javFile);
}
