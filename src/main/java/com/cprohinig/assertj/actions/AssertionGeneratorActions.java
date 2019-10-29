package com.cprohinig.assertj.actions;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.lang.jvm.JvmClassKind;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.impl.PsiManagerImpl;
import com.intellij.psi.impl.file.PsiDirectoryImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.StringTokenizer;

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

            final PsiFile file = factory.createFileFromText(assertionGenerator.generateFilename(javaFile), JavaFileType.INSTANCE, assertionContents);

//            final PsiFile file = factory.createFileFromText("TestAssertion.java", JavaFileType.INSTANCE, "package com.dynatrace.cloud.simulators.infrastructure.components;import com.dynatrace.cloud.simulators.infrastracture.Node; public class Account implements Node { private final String id;public Account(String id) {this.id = id;}@Override public String getId() { return id; }}");
            final PsiDirectory parent = javaFile.getParent();
            final PsiManagerImpl psiManager = (PsiManagerImpl)PsiManager.getInstance(project);
            final PsiDirectory root = new PsiDirectoryImpl(psiManager, project.getBaseDir());
            PsiDirectory dir = root;

            if (parent != null) {
                String dirName = parent.getVirtualFile().getPresentableUrl();
                StringTokenizer t = new StringTokenizer(dirName, "/", false);
                boolean rootFound = false;
                while(t.hasMoreElements()) {
                    String subDir = (String) t.nextElement();
                    subDir = subDir.equals("main") ? "test" : subDir;

                    if(rootFound) {
                        if(dir.findSubdirectory(subDir) == null) {
                            dir = dir.createSubdirectory(subDir);
                        } else {
                            dir = dir.findSubdirectory(subDir);
                        }
                    }
                    if(subDir.equals(root.getName())) {
                        rootFound = true;
                    }
                }

                if(dir != null) {
                    JavaCodeStyleManager.getInstance(project).optimizeImports(file);
                    CodeStyleManager.getInstance(project).reformat(file);
                    PsiDirectory finalDir = dir;
                    Runnable addFileOperation = ()-> finalDir.add(file);
                    WriteCommandAction.runWriteCommandAction(project, addFileOperation);
                    dir.findFile(assertionGenerator.generateFilename(javaFile)).navigate(true);
                }
            }
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
