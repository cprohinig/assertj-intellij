package com.cprohinig.assertj.actions;

import com.cprohinig.assertj.testutils.TestUtils;
import com.intellij.psi.PsiJavaFile;
import com.intellij.testFramework.LightPlatformCodeInsightTestCase;
import org.junit.Assert;


public class AssertionDetailsGeneratorImplTest extends LightPlatformCodeInsightTestCase {

    AssertionDetailsGeneratorImpl target = new AssertionDetailsGeneratorImpl();

    public void testSimpleClassPackageStatement() throws Exception {
        // given
        setupJavaFile("SimpleClass.java");
        PsiJavaFile currentFile = (PsiJavaFile) getFile();

        // when
        String packageStatement = target.generatePackageStatement(currentFile);

        // then
        String expected = "package com.p.helloworld;" +
                          "import com.p.helloworld.SimpleClass;" +
                          "import org.assertj.core.api.AbstractObjectAssert;" +
                          "import org.assertj.core.api.Assertions;";
        Assert.assertEquals(packageStatement, expected);
    }

    public void testSimpleClassDeclaration() throws Exception {
        // given
        setupJavaFile("SimpleClass.java");
        PsiJavaFile currentFile = (PsiJavaFile) getFile();

        // when
        String classDeclaration = target.generateClassDeclaration(currentFile);

        // then
        String expected = "public class SimpleClassAssert extends AbstractObjectAssert<SimpleClassAssert, SimpleClass> {" +
                "private static final String ERROR_MESSAGE = \"Expected %s to be <%s> but was <%s> (%s)\";";
        Assert.assertEquals(classDeclaration, expected);
    }

    public void testSimpleClassAssertions() throws Exception {
        // given
        setupJavaFile("SimpleClass.java");
        PsiJavaFile currentFile = (PsiJavaFile) getFile();

        // when
        String assertions = target.generateAssertions(currentFile);

        // then
        String expected = "SimpleClassAssert(SimpleClass actual) {" +
                               "super(actual, SimpleClassAssert.class);" +
                            "}" +
                          "public SimpleClassAssert hasSomething(String expected) {" +
                            "String actualSomething = actual.getSomething();" +
                            "Assertions.assertThat(actualSomething)\n" +
                            ".overridingErrorMessage(ERROR_MESSAGE, \"something\", expected, actualSomething, descriptionText())\n" +
                            ".isEqualTo(expected);" +
                            "return this;" +
                          "}";

        Assert.assertEquals(assertions, expected);
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
        String fileContent = TestUtils.getFileContent(fileName);
        configureFromFileText(fileName, fileContent);
    }
}