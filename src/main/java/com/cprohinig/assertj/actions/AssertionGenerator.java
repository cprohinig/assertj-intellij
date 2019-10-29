package com.cprohinig.assertj.actions;

import com.intellij.psi.PsiJavaFile;

public interface AssertionGenerator {

    String generateFilename(PsiJavaFile javaFile);

    String generateContent(PsiJavaFile javaFile);

}
