package com.cprohinig.assertj.actions;

import com.intellij.psi.PsiJavaFile;

public class AssertionGeneratorImpl implements AssertionGenerator {
    private AssertionDetailsGenerator assertionDetailsGenerator;

    public AssertionGeneratorImpl(AssertionDetailsGenerator assertionDetailsGenerator) {
        this.assertionDetailsGenerator = assertionDetailsGenerator;
    }

    @Override
    public String generateFilename(PsiJavaFile javaFile) {
        return javaFile.getClasses()[0].getName() + "Assert.java";
    }

    @Override
    public String generateContent(PsiJavaFile javaFile) {
        return assertionDetailsGenerator.generatePackageStatement(javaFile) +
                assertionDetailsGenerator.generateClassDeclaration(javaFile) +
                assertionDetailsGenerator.generateAssertions(javaFile) +
                assertionDetailsGenerator.generateFooter(javaFile);
    }


}
