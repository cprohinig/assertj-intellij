package com.cprohinig.assertj.actions;

import com.intellij.psi.PsiJavaFile;
import com.intellij.testFramework.LightPlatformCodeInsightTestCase;
import org.junit.Assert;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;


public class AssertionDetailsGeneratorImplTest extends LightPlatformCodeInsightTestCase {

    AssertionDetailsGeneratorImpl target = new AssertionDetailsGeneratorImpl();

    public void testSimpleClassPackageStatement() throws Exception {
        // given
        setupJavaFile("SimpleClass.java");
        PsiJavaFile currentFile = (PsiJavaFile) getFile();

        // when
        String packageStatement = target.generatePackageStatement(currentFile);

        // then
        Assert.assertEquals(packageStatement, "package com.p.helloworld;import com.p.helloworld.SimpleClass;import com.compuware.apm.webui.rest.common.api.assertions.AbstractObjectAssert;");
    }

    public void testSimpleClassDeclaration() throws Exception {
        // given
        setupJavaFile("SimpleClass.java");
        PsiJavaFile currentFile = (PsiJavaFile) getFile();

        // when
        String classDeclaration = target.generateClassDeclaration(currentFile);

        // then
        Assert.assertEquals(classDeclaration, "public class SimpleClassAssert extends AbstractObjectAssert<SimpleClassAssert, SimpleClass> {");
    }

    public void testSimpleClassAssertions() throws Exception {
        // given
        setupJavaFile("SimpleClass.java");
        PsiJavaFile currentFile = (PsiJavaFile) getFile();

        // when
        String assertions = target.generateAssertions(currentFile);

        // then
        Assert.assertEquals(assertions, "SimpleClassAssert(SimpleClass actual) {super(actual, SimpleClassAssert.class);}public SimpleClassAssert hasSomething(String expected) {return isNotNull().isEqualTo(actual::getSomething, expected, \"something\");}");
    }

    public void testSimpleClassFooter() throws Exception {
        // given
        setupJavaFile("SimpleClass.java");
        PsiJavaFile currentFile = (PsiJavaFile) getFile();

        // when
        String footer = target.generateFooter(currentFile);

        // then
        Assert.assertEquals(footer, "}");
    }

    private void setupJavaFile(String fileName) throws Exception {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        URL resource = contextClassLoader.getResource("testData/" + fileName);
        Path path = Paths.get(resource.getPath());
        String fileContent = Files.lines(path).collect(Collectors.joining());
        configureFromFileText(fileName, fileContent);
    }
}