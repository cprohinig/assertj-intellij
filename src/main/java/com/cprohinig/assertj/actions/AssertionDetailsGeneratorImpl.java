package com.cprohinig.assertj.actions;

import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.intellij.psi.util.PropertyUtil.isSimpleGetter;

public class AssertionDetailsGeneratorImpl implements AssertionDetailsGenerator {
    @Override
    public String generatePackageStatement(PsiJavaFile javaFile) {
        return javaFile.getPackageName() + ";\n\n";
    }

    @Override
    public String generateClassDeclaration(PsiJavaFile javaFile) {
        String className = javaFile.getClasses()[0].getName() + "Assert";
        return String.format("public class %s extends AbstractObjectAssert<%s, %s> {\n", className, className, javaFile.getClasses()[0].getName());
    }

    @Override
    public String generateAssertions(PsiJavaFile javaFile) {
        List<PsiMethod> getters = Arrays.stream(javaFile.getClasses()[0].getAllMethods())
                .filter(m -> isGetter(m))
                .collect(Collectors.toList());

        String className = javaFile.getClasses()[0].getName() + "Assert";

        String out = String.format("\t%s(%s actual) {\n\t\tsuper(actual, %s.class);\n\t}\n\n", className, javaFile.getClasses()[0].getName(), className);
        for (PsiMethod getter : getters) {
            String assertionMethodName = getter.getName().replace("get", "has");
            String signature = String.format("\tpublic %s %s(%s expected) {\n", className, assertionMethodName, getter.getReturnType().getPresentableText());
            String fieldName = getter.getName().substring(3);
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
            String body = String.format("\t\treturn isNotNull().isEqualTo(actual::%s, expected, \"%s\");\n\t}\n\n", getter.getName(), fieldName);
            out += signature + body;
        }

        return out + "\n";
    }

    public boolean isGetter(PsiMethod method) {
        return method.getName().startsWith("get") && !method.getName().equals("getClass");
    }

    @Override
    public String generateFooter(PsiJavaFile javFile) {
        return "}\n";
    }
}
