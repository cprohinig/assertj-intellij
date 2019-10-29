package com.cprohinig.assertj.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class AssertionGeneratorActions extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final Editor editor = e.getData(CommonDataKeys.EDITOR);

        final PsiFile psiFile = PsiDocumentManager.getInstance(e.getProject()).getPsiFile(editor.getDocument());
        System.out.println("test");
//        try {
//            BaseAssertionGenerator baseAssertionGenerator = new BaseAssertionGenerator();
//            ClassToClassDescriptionConverter converter = new ClassToClassDescriptionConverter();
//            ClassDescription classDescription = converter.convertToClassDescription();
//            baseAssertionGenerator.generateCustomAssertionFor(classDescription);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
    }
}
