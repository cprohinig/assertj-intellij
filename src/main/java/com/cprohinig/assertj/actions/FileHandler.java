package com.cprohinig.assertj.actions;

import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;

import java.util.List;

public interface FileHandler {

    boolean storeFile(PsiFile file, PsiDirectory directory);

    boolean fileAllowsGeneration(PsiJavaFile file);

    PsiDirectory convertToTestDirectory(PsiDirectory directory);

    PsiJavaFile getActiveJavaFile();

    List<PsiJavaFile> findKnownFile(KnownFiles file);
}
