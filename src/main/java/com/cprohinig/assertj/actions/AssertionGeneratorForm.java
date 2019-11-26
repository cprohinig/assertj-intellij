package com.cprohinig.assertj.actions;

import com.intellij.openapi.ui.DialogPanel;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiJvmMember;
import com.intellij.psi.PsiMethod;
import com.intellij.ui.treeStructure.Tree;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AssertionGeneratorForm {
  private final PsiJavaFile input;
  private JPanel panel;
  private JScrollPane checkBoxWrapper;
  private JTree getters;

  AssertionGeneratorForm(PsiJavaFile input) {
    this.input = input;
  }

  private void createUIComponents() {
    panel = new DialogPanel();
    DefaultMutableTreeNode root = new DefaultMutableTreeNode();

    AssertionDetailsGeneratorImpl.extractGetters(input)
        .stream()
        .peek(System.out::println)
        .collect(Collectors.groupingBy(PsiJvmMember::getContainingClass))
        .forEach((key, value) -> {
          DefaultMutableTreeNode node = new DefaultMutableTreeNode(key.getQualifiedName());
          value
              .stream()
              .map(method -> new DefaultMutableTreeNode(new MethodWrapper(method), false))
              .forEach(node::add);
          root.add(node);
        });

    getters = new Tree(new DefaultTreeModel(root));
    for (int i = 0; i < getters.getRowCount(); i++) {
      getters.expandRow(i);
    }

    ((DialogPanel)panel).setPreferredFocusedComponent(getters);
  }

  JComponent asComponent() {
    return panel;
  }

  List<PsiMethod> getSelections() {
    return Arrays.stream(getters.getSelectionModel().getSelectionPaths())
        .map(TreePath::getLastPathComponent)
        .filter(node -> node instanceof DefaultMutableTreeNode)
        .map(node -> ((DefaultMutableTreeNode) node).getUserObject())
        .filter(object -> object instanceof MethodWrapper)
        .map(object -> ((MethodWrapper) object).getMethod())
        .collect(Collectors.toList());
  }

  private static class MethodWrapper {
    private final PsiMethod method;

    MethodWrapper(PsiMethod method) {
      this.method = method;
    }

    PsiMethod getMethod() {
      return method;
    }

    @Override
    public String toString() {
      return method.getName();
    }
  }
}
