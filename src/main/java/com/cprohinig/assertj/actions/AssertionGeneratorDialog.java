package com.cprohinig.assertj.actions;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

public class AssertionGeneratorDialog extends DialogWrapper {
  private final AssertionGeneratorForm form;

  public AssertionGeneratorDialog(@Nullable Project project, PsiJavaFile input, List<PsiJavaFile> knownFilesForRegistration) {
    super(project);

    this.form = new AssertionGeneratorForm(input, knownFilesForRegistration);

    init();
    setTitle("Select Getters to Generate AssertJ Assertion");
    setOKActionEnabled(true);
  }

  @Nullable
  @Override
  protected JComponent createCenterPanel() {
    return form.asComponent();
  }

  public List<PsiMethod> getSelections() {
    return form.getSelections();
  }

  public Optional<PsiJavaFile> getSelectedFileForRegistration() {
    return form.getSelectedFileForRegistration();
  }
}
