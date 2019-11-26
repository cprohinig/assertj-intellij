package com.cprohinig.assertj.actions;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiJavaFile;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class AssertionGeneratorActions extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        final Project project = e.getProject();
        final FileHandler fileHandler = new FileHandlerImpl(project, editor);

        final PsiJavaFile javaFile = fileHandler.getActiveJavaFile();
        if (javaFile == null) {
            return;
        }
        List<PsiJavaFile> knownFilesForRegistration = fileHandler.findKnownFile(KnownFiles.CUSTOM_ASSERTIONS);

        final AssertionGeneratorDialog dialog = new AssertionGeneratorDialog(project, javaFile, knownFilesForRegistration);
        if (dialog.showAndGet()) {
            final AssertionDetailsGenerator assertionDetailsGenerator = new AssertionDetailsGeneratorImpl();
            final AssertionGenerator assertionGenerator = new AssertionGeneratorImpl(assertionDetailsGenerator);
            final String assertionContents = assertionGenerator.generateContent(javaFile, dialog.getSelections());
            final Optional<PsiJavaFile> selectedFileForRegistration = dialog.getSelectedFileForRegistration();
            if (selectedFileForRegistration.isPresent()) {
                PsiJavaFile psiJavaFile = selectedFileForRegistration.get();
                System.out.println(psiJavaFile.toString());
            } else {
                // we must create our own class for registration
            }

            final PsiFileFactory factory = PsiFileFactory.getInstance(project);

            final PsiFile file = factory.createFileFromText(assertionGenerator.generateFilename(javaFile), JavaFileType.INSTANCE, assertionContents);
            final PsiDirectory parent = javaFile.getParent();
            final PsiDirectory testDir = fileHandler.convertToTestDirectory(parent);
            final boolean success = fileHandler.storeFile(file, testDir);
            if (success) {
                PsiJavaFile generatedFile = (PsiJavaFile) testDir.findFile(assertionGenerator.generateFilename(javaFile));
                generatedFile.navigate(true);

                // register file to assertion class
            }
        }
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        final FileHandler fileHandler = new FileHandlerImpl(e.getProject(), e.getData(CommonDataKeys.EDITOR));
        final PsiJavaFile javaFile = fileHandler.getActiveJavaFile();

        e.getPresentation().setVisible(fileHandler.fileAllowsGeneration(javaFile));
    }

}
