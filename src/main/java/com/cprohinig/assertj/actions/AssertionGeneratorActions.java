package com.cprohinig.assertj.actions;

import com.intellij.lang.jvm.JvmClassKind;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import org.jetbrains.annotations.NotNull;

public class AssertionGeneratorActions extends AnAction {

    private static final String JAVA_FILE_EXT = "java";

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

    }

    @Override
    public void update(@NotNull AnActionEvent anActionEvent) {
        boolean visible = false;
        final Editor editor = anActionEvent.getData(CommonDataKeys.EDITOR);
        final Project project = anActionEvent.getProject();

        if (editor != null && project != null) {
            final PsiFile file = PsiDocumentManager.getInstance(project).getPsiFile(editor.getDocument());
            if (file != null && JAVA_FILE_EXT.equals(file.getVirtualFile().getExtension())) {
                final PsiJavaFile javaFile = (PsiJavaFile) file;
                for (PsiClass clazz : javaFile.getClasses()) {
                    if (JvmClassKind.CLASS.equals(clazz.getClassKind())) {
                        visible = true;
                    }
                }
            }
        }

        anActionEvent.getPresentation().setVisible(visible);
    }
}
