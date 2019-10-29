package com.cprohinig.assertj.actions;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.lang.jvm.JvmClassKind;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AssertionGeneratorActions extends AnAction {

    private static final String JAVA_FILE_EXT = "java";

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        final Project project = e.getProject();
        final PsiJavaFile javaFile = getActiveJavaFile(editor, project);
        if (javaFile != null) {
            final AssertionDetailsGenerator assertionDetailsGenerator = new AssertionDetailsGeneratorImpl();
            final AssertionGenerator assertionGenerator = new AssertionGeneratorImpl(assertionDetailsGenerator);
            final String assertionContents = assertionGenerator.generateContent(javaFile);
            final PsiFileFactory factory = PsiFileFactory.getInstance(project);

            factory.createFileFromText(assertionGenerator.generateFilename(javaFile), JavaFileType.INSTANCE, assertionContents);
        }
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        final Project project = e.getProject();

        boolean visible = false;
        final PsiJavaFile javaFile = getActiveJavaFile(editor, project);
        if (javaFile != null) {
            for (PsiClass clazz : javaFile.getClasses()) {
                if (JvmClassKind.CLASS.equals(clazz.getClassKind())) {
                    visible = true;
                }
            }
        }

        e.getPresentation().setVisible(visible);
    }

    @Nullable
    private PsiJavaFile getActiveJavaFile(@Nullable Editor editor, @Nullable Project project) {

        PsiJavaFile javaFile = null;
        if (editor != null && project != null) {
            final PsiFile file = PsiDocumentManager.getInstance(project).getPsiFile(editor.getDocument());
            if (file != null && JAVA_FILE_EXT.equals(file.getVirtualFile().getExtension())) {
                javaFile = (PsiJavaFile) file;
            }
        }
        return javaFile;
    }
}
