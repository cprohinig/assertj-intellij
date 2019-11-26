package com.cprohinig.assertj.actions;

import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;

public interface FileHandler {

    boolean storeFile(PsiFile file, PsiDirectory directory);

    PsiDirectory convertToTestDirectory(PsiDirectory directory);
}
