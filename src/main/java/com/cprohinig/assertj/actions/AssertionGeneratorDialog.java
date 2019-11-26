package com.cprohinig.assertj.actions;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

public class AssertionGeneratorDialog extends DialogWrapper {
  private final AssertionGeneratorForm form;

  public AssertionGeneratorDialog(@Nullable Project project, PsiJavaFile input) {
    super(project);

    this.form = new AssertionGeneratorForm(input);

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
}
