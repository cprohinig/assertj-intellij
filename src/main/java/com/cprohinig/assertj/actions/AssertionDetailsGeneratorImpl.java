package com.cprohinig.assertj.actions;

import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AssertionDetailsGeneratorImpl implements AssertionDetailsGenerator {

  @Override
  public String generatePackageStatement(PsiJavaFile javaFile) {
    return "package " + javaFile.getPackageName() + ";" + generateImports(javaFile);
  }

    private String generateImports(PsiJavaFile javaFile) {
        String assertedClassImportStatement = "import " + javaFile.getClasses()[0].getQualifiedName() + ";";
        String abstractObjectAssertImport = "import org.assertj.core.api.AbstractObjectAssert;";
        String assertionsImport = "import org.assertj.core.api.Assertions;";
        return assertedClassImportStatement + abstractObjectAssertImport + assertionsImport;
    }

    @Override
    public String generateClassDeclaration(PsiJavaFile javaFile) {
        String targetClassName = javaFile.getClasses()[0].getName();
        String assertClassName = targetClassName + "Assert";
        return getClassDeclaration(targetClassName, assertClassName);
    }

    @Override
  public String generateAssertions(PsiJavaFile javaFile, List<PsiMethod> selections) {
    String className = javaFile.getClasses()[0].getName() + "Assert";
        String targetClassName = javaFile.getClasses()[0].getName();
        String assertClassName = targetClassName + "Assert";

        // constructor
        StringBuilder out = new StringBuilder(getConstructorStatement(targetClassName, assertClassName));

        // assertions
        for (PsiMethod getter : selections) {
            out.append(getAssertionMethodSignature(getter, assertClassName));
            out.append(getActualDeclaration(getter));
            out.append(getAssertionStatement(getter));
            out.append("return this;");
            out.append("}");
        }

        return out.toString();
    }

  private static boolean isGetter(PsiMethod method) {
    return (method.getName().startsWith("get")
        || method.getName().startsWith("is"))
        && !method.getName().equals("getClass")
        && method.getParameterList().isEmpty();
  }

  public static List<PsiMethod> extractGetters(PsiJavaFile javaFile) {
    return Arrays.stream(javaFile.getClasses())
        .peek(System.out::println)
        .flatMap(clazz -> Arrays.stream(clazz.getAllMethods()))
        .filter(AssertionDetailsGeneratorImpl::isGetter)
        .collect(Collectors.toList());
  }

  private String getClassDeclaration(String targetClassName, String assertClassName) {
        return String.format("public class %s extends AbstractObjectAssert<%s, %s> {", assertClassName, assertClassName, targetClassName) +
                "private static final String ERROR_MESSAGE = \"Expected %s to be <%s> but was <%s> (%s)\";";
    }

    private String getConstructorStatement(String targetClassName, String assertClassName) {
        return String.format("%s(%s actual) {super(actual, %s.class);}", assertClassName, targetClassName, assertClassName);
    }
    private String getAssertionMethodSignature(PsiMethod getter, String className) {
        String assertionMethodName = getter.getName().replace("get", "has");
        return String.format("public %s %s(%s expected) {", className, assertionMethodName, getter.getReturnType().getPresentableText());
    }

    private String getActualDeclaration(PsiMethod getter) {
        return String.format("%s %s = actual.%s();", getter.getReturnType().getPresentableText(), getActualVariableName(getter), getter.getName());
    }

    private String getAssertionStatement(PsiMethod getter) {
        return String.format("Assertions.assertThat(%s)\n" +
                        ".overridingErrorMessage(ERROR_MESSAGE, \"%s\", expected, %s, descriptionText())\n" +
                        ".isEqualTo(expected);",
                getActualVariableName(getter),
                getter.getName().substring(3).toLowerCase(),
                getActualVariableName(getter)
        );
    }

    private String getActualVariableName(PsiMethod getter) {
        return String.format("actual%s", getter.getName().substring(3));
    }

    @Override
    public String generateFooter(PsiJavaFile javFile) {
        return "}";
    }
}
