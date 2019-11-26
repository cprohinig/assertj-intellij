package com.cprohinig.assertj.actions;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.impl.PsiManagerImpl;
import com.intellij.psi.impl.file.PsiDirectoryImpl;

import java.io.File;
import java.util.StringTokenizer;

public class FileHandlerImpl implements FileHandler {

    private final Project project;
    private final PsiManagerImpl psiManager;
    private final PsiDirectory root;

    public FileHandlerImpl(final Project project) {
        this.project = project;
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
    public PsiDirectory convertToTestDirectory(PsiDirectory directory) {
        PsiDirectory dir = root;
        if (directory != null) {
            String dirName = directory.getVirtualFile().getPresentableUrl();
            StringTokenizer t = new StringTokenizer(dirName, File.pathSeparator, false);
            boolean rootFound = false;
            while (t.hasMoreElements()) {
                String subDir = (String) t.nextElement();
                subDir = subDir.equals("main") ? "test" : subDir;

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

}
