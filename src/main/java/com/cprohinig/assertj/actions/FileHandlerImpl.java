package com.cprohinig.assertj.actions;

import com.google.common.collect.ImmutableSet;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.lang.jvm.JvmClassKind;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.impl.PsiManagerImpl;
import com.intellij.psi.impl.file.PsiDirectoryImpl;
import com.intellij.psi.search.scope.packageSet.PatternPackageSet;

import java.io.File;
import java.util.Set;
import java.util.StringTokenizer;

public class FileHandlerImpl implements FileHandler {
    private static final String SCOPE_TEST = PatternPackageSet.SCOPE_TEST;
    private static final String SCOPE_MAIN = "main";
    private static final Set<JvmClassKind> classKinds = ImmutableSet.of(JvmClassKind.CLASS, JvmClassKind.INTERFACE);

    private final Project project;
    private final Editor editor;
    private final PsiManagerImpl psiManager;
    private final PsiDirectory root;

    FileHandlerImpl(final Project project, final Editor editor) {
        this.project = project;
        this.editor = editor;
        this.psiManager = (PsiManagerImpl) PsiManager.getInstance(project);
        this.root = new PsiDirectoryImpl(psiManager, project.getBaseDir());
    }

    @Override
    public boolean storeFile(final PsiFile file, final PsiDirectory directory) {
        JavaCodeStyleManager.getInstance(project).optimizeImports(file);
        CodeStyleManager.getInstance(project).reformat(file);
        Runnable addFileOperation = () -> directory.add(file);
        WriteCommandAction.runWriteCommandAction(project, addFileOperation);
        return true;
    }

    @Override
    public boolean fileAllowsGeneration(PsiJavaFile file) {
        if (file != null) {
            for (PsiClass clazz : file.getClasses()) {
                if (classKinds.contains(clazz.getClassKind())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public PsiDirectory convertToTestDirectory(final PsiDirectory directory) {
        PsiDirectory dir = root;
        if (directory != null) {
            String dirName = directory.getVirtualFile().getPresentableUrl();
            StringTokenizer t = new StringTokenizer(dirName, File.separator, false);
            boolean rootFound = false;
            while (t.hasMoreElements()) {
                String subDir = (String) t.nextElement();
                subDir = subDir.equals(SCOPE_MAIN) ? SCOPE_TEST : subDir;

                if (rootFound) {
                    if (dir.findSubdirectory(subDir) == null) {
                        dir = dir.createSubdirectory(subDir);
                    } else {
                        dir = dir.findSubdirectory(subDir);
                    }
                }
                if (subDir.equals(root.getName())) {
                    rootFound = true;
                }
            }
        }
        return dir;
    }

    @Override
    public PsiJavaFile getActiveJavaFile() {
        PsiJavaFile javaFile = null;
        if (editor != null && project != null) {
            final PsiFile file = PsiDocumentManager.getInstance(project).getPsiFile(editor.getDocument());
            if (file != null && JavaFileType.DEFAULT_EXTENSION.equals(file.getVirtualFile().getExtension())) {
                javaFile = (PsiJavaFile) file;
            }
        }
        return javaFile;
    }

}
