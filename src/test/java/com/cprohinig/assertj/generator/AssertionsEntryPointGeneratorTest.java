/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2017 the original author or authors.
 */
package com.cprohinig.assertj.generator;

import com.cprohinig.assertj.generator.data.Movie;
import com.cprohinig.assertj.generator.data.Name;
import com.cprohinig.assertj.generator.data.TreeEnum;
import com.cprohinig.assertj.generator.data.art.ArtWork;
import com.cprohinig.assertj.generator.data.lotr.Race;
import com.cprohinig.assertj.generator.data.lotr.Ring;
import com.cprohinig.assertj.generator.data.lotr.TolkienCharacter;
import com.cprohinig.assertj.generator.data.nba.Player;
import com.cprohinig.assertj.generator.data.nba.team.Team;
import com.cprohinig.assertj.generator.description.IClassDescription;
import com.cprohinig.assertj.generator.description.converter.ClassToClassDescriptionConverter;
import com.google.common.base.Optional;
import com.google.common.reflect.TypeToken;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.cprohinig.assertj.generator.AssertionsEntryPointType.*;
import static com.cprohinig.assertj.generator.util.ClassUtil.collectClasses;
import static com.google.common.collect.Sets.newLinkedHashSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

public class AssertionsEntryPointGeneratorTest {
  private BaseAssertionGenerator generator;

  @Rule
  public final GenerationPathHandler genHandle = new GenerationPathHandler(AssertionsEntryPointGeneratorTest.class,
                                                                           Paths.get("src/test/resources"));

  @Before
  public void beforeEachTest() throws IOException {
    generator = genHandle.buildAssertionGenerator();
  }

  @Test
  public void should_generate_standard_assertions_entry_point_class_file() throws Exception {
    // GIVEN : classes we want to have entry point assertions for
    Set<IClassDescription> classDescriptionSet = getClassDescriptionsOf(Ring.class, Race.class, ArtWork.class,
                                                                       Name.class, Player.class, Movie.class,
                                                                       TolkienCharacter.class, TreeEnum.class,
                                                                       Movie.PublicCategory.class, Optional.class);
    // WHEN
    File assertionsEntryPointFile = generator.generateAssertionsEntryPointClassFor(classDescriptionSet, STANDARD, null);
    // THEN
    String expectedContent = readExpectedContentFromFile("Assertions.expected.txt");
    assertThat(assertionsEntryPointFile).as("check entry point class content").hasContent(expectedContent);

    // TODO compile with genHandle.compileGeneratedFiles();
  }

  @Test
  public void should_generate_correctly_standard_assertions_entry_point_class_for_classes_with_same_name()
                                                                                                           throws Exception {
    // GIVEN : classes we want to have entry point assertions for
    Set<IClassDescription> classDescriptionSet = getClassDescriptionsOf(Team.class,
                                                                       com.cprohinig.assertj.generator.data.Team.class);
    // WHEN
    String assertionsEntryPointContent = generator.generateAssertionsEntryPointClassContentFor(classDescriptionSet,
                                                                                               STANDARD, "org");
    // THEN
    String expectedContent = readExpectedContentFromFile("AssertionsForClassesWithSameName.expected.txt");
    assertThat(assertionsEntryPointContent).isEqualTo(expectedContent);
  }

  @Test
  public void should_generate_assertion_entry_point_class_file_with_custom_package() throws Exception {
    // GIVEN : classes we want to have entry point assertions for
    Set<IClassDescription> classDescriptionSet = getClassDescriptionsOf(Ring.class, Race.class, ArtWork.class,
                                                                       Name.class, Player.class, Movie.class,
                                                                       TolkienCharacter.class, TreeEnum.class,
                                                                       Movie.PublicCategory.class);
    // WHEN
    File assertionsEntryPointFile = generator.generateAssertionsEntryPointClassFor(classDescriptionSet,
                                                                                   STANDARD, "my.custom.package");
    // THEN
    String expectedContent = readExpectedContentFromFile("AssertionsWithCustomPackage.expected.txt");
    assertThat(assertionsEntryPointFile).as("check entry point class content")
                                        .hasContent(expectedContent)
                                        .hasParent(genHandle.getRoot().toPath().resolve("my/custom/package").toFile());
  }

  @Test
  public void should_generate_bdd_assertion_entry_point_class_file() throws Exception {
    // GIVEN : classes we want to have entry point assertions for
    Set<IClassDescription> classDescriptionSet = getClassDescriptionsOf(Ring.class, Race.class, ArtWork.class,
                                                                       Name.class, Player.class, Movie.class,
                                                                       TolkienCharacter.class, TreeEnum.class,
                                                                       Movie.PublicCategory.class);
    // WHEN
    File assertionsEntryPointFile = generator.generateAssertionsEntryPointClassFor(classDescriptionSet, BDD, null);
    // THEN
    String expectedContent = readExpectedContentFromFile("BddAssertions.expected.txt");
    assertThat(assertionsEntryPointFile).as("check BDD entry point class content").hasContent(expectedContent);
  }

  @Test
  public void should_generate_soft_assertions_entry_point_class_file() throws Exception {
    // GIVEN : classes we want to have entry point assertions for
    Set<IClassDescription> classDescriptionSet = getClassDescriptionsOf(Ring.class, Race.class, ArtWork.class,
                                                                       Name.class, Player.class, Movie.class,
                                                                       TolkienCharacter.class, TreeEnum.class,
                                                                       Movie.PublicCategory.class);
    // WHEN
    File assertionsEntryPointFile = generator.generateAssertionsEntryPointClassFor(classDescriptionSet, SOFT, null);
    // THEN
    String expectedContent = readExpectedContentFromFile("SoftAssertions.expected.txt");
    assertThat(assertionsEntryPointFile).as("check soft assertions entry point class content")
                                        .hasContent(expectedContent);
  }

  @Test
  public void should_generate_junit_soft_assertions_entry_point_class_file() throws Exception {
    // GIVEN : classes we want to have entry point assertions for
    Set<IClassDescription> classDescriptionSet = getClassDescriptionsOf(Ring.class, Race.class, ArtWork.class,
                                                                       Name.class, Player.class, Movie.class,
                                                                       TolkienCharacter.class, TreeEnum.class,
                                                                       Movie.PublicCategory.class);
    // WHEN
    File assertionsEntryPointFile = generator.generateAssertionsEntryPointClassFor(classDescriptionSet, JUNIT_SOFT,
                                                                                   null);
    // THEN
    String expectedContent = readExpectedContentFromFile("JUnitSoftAssertions.expected.txt");
    assertThat(assertionsEntryPointFile).as("check JUnit soft assertions entry point class content")
                                        .hasContent(expectedContent)
                                        .hasName("JUnitSoftAssertions.java");
  }

  @Test
  public void should_generate_bdd_soft_assertions_entry_point_class_file() throws Exception {
    // GIVEN : classes we want to have entry point assertions for
    Set<IClassDescription> classDescriptionSet = getClassDescriptionsOf(Ring.class, Race.class, ArtWork.class,
                                                                       Name.class, Player.class, Movie.class,
                                                                       TolkienCharacter.class, TreeEnum.class,
                                                                       Movie.PublicCategory.class);
    // WHEN
    File assertionsEntryPointFile = generator.generateAssertionsEntryPointClassFor(classDescriptionSet, BDD_SOFT,
                                                                                   null);
    // THEN
    String expectedContent = readExpectedContentFromFile("BDDSoftAssertions.expected.txt");
    assertThat(assertionsEntryPointFile).as("check BDD soft assertions entry point class content")
                                        .hasContent(expectedContent);
  }

  @Test
  public void should_generate_junit_bdd_soft_assertions_entry_point_class_file() throws Exception {
    // GIVEN : classes we want to have entry point assertions for
    Set<IClassDescription> classDescriptionSet = getClassDescriptionsOf(Ring.class, Race.class, ArtWork.class,
                                                                       Name.class, Player.class, Movie.class,
                                                                       TolkienCharacter.class, TreeEnum.class,
                                                                       Movie.PublicCategory.class);
    // WHEN
    File assertionsEntryPointFile = generator.generateAssertionsEntryPointClassFor(classDescriptionSet, JUNIT_BDD_SOFT,
                                                                                   null);
    // THEN
    String expectedContent = readExpectedContentFromFile("JUnitBDDSoftAssertions.expected.txt");
    assertThat(assertionsEntryPointFile).as("check JUnit BDD soft assertions entry point class content")
                                        .hasContent(expectedContent);
  }

  @Test
  public void should_generate_auto_closeable_soft_assertions_entry_point_class_file() throws Exception {
    // GIVEN : classes we want to have entry point assertions for
    Set<IClassDescription> classDescriptionSet = getClassDescriptionsOf(Ring.class, Race.class, ArtWork.class,
                                                                       Name.class, Player.class, Movie.class,
                                                                       TolkienCharacter.class, TreeEnum.class,
                                                                       Movie.PublicCategory.class);
    // WHEN
    File assertionsEntryPointFile = generator.generateAssertionsEntryPointClassFor(classDescriptionSet,
                                                                                   AUTO_CLOSEABLE_SOFT,
                                                                                   null);
    // THEN
    String expectedContent = readExpectedContentFromFile("AutoCloseableSoftAssertions.expected.txt");
    assertThat(assertionsEntryPointFile).as("check auto closeable soft assertions entry point class content")
                                        .hasContent(expectedContent);
  }

  @Test
  public void should_generate_auto_closeable_bdd_soft_assertions_entry_point_class_file() throws Exception {
    // GIVEN : classes we want to have entry point assertions for
    Set<IClassDescription> classDescriptionSet = getClassDescriptionsOf(Ring.class, Race.class, ArtWork.class,
                                                                       Name.class, Player.class, Movie.class,
                                                                       TolkienCharacter.class, TreeEnum.class,
                                                                       Movie.PublicCategory.class);
    // WHEN
    File assertionsEntryPointFile = generator.generateAssertionsEntryPointClassFor(classDescriptionSet,
                                                                                   AUTO_CLOSEABLE_BDD_SOFT,
                                                                                   null);
    // THEN
    String expectedContent = readExpectedContentFromFile("AutoCloseableBDDSoftAssertions.expected.txt");
    assertThat(assertionsEntryPointFile).as("check auto closeable BDD soft assertions entry point class content")
                                        .hasContent(expectedContent);
  }

  @Test
  public void should_generate_an_assertions_entry_point_class_file_that_matches_given_class_name() throws Exception {
    // GIVEN : custom entry point class template changing the class name.
    Set<IClassDescription> classDescriptionSet = getClassDescriptionsOf(Ring.class);
    generator.register(new Template(Template.Type.ASSERTIONS_ENTRY_POINT_CLASS,
                                    new File("customtemplates" + File.separator,
                                             "my_assertion_entry_point_class.txt")));
    // WHEN
    File assertionsEntryPointFile = generator.generateAssertionsEntryPointClassFor(classDescriptionSet, STANDARD, null);

    // THEN
    String expectedContent = readExpectedContentFromFile("MyAssertions.expected.txt");
    assertThat(assertionsEntryPointFile).as("check custom assertions entry point class")
                                        .hasContent(expectedContent)
                                        .hasName("MyAssertions.java");
  }

  @Test
  public void should_return_null_assertion_entry_point_file_if_no_classes_description_are_given() throws Exception {
    // GIVEN no ClassDescription
    Set<IClassDescription> classDescriptionSet = newLinkedHashSet();
    // THEN generated entry points file are null
    for (AssertionsEntryPointType assertionsEntryPointType : AssertionsEntryPointType.values()) {
      assertThat(generator.generateAssertionsEntryPointClassFor(classDescriptionSet, assertionsEntryPointType,
                                                                null)).isNull();
    }
  }

  @Test
  public void should_return_empty_assertion_entry_point_class_content_if_no_classes_description_are_given()
                                                                                                            throws Exception {
    // GIVEN no ClassDescription
    Set<IClassDescription> emptySet = newLinkedHashSet();
    // THEN generated entry points content are empty
    for (AssertionsEntryPointType assertionsEntryPointType : AssertionsEntryPointType.values()) {
      assertThat(generator.generateAssertionsEntryPointClassContentFor(emptySet, assertionsEntryPointType,
                                                                       null)).isEmpty();
    }
  }

  @Test
  public void should_return_null_assertion_entry_point_file_if_null_classes_description_are_given() throws Exception {
    // GIVEN no ClassDescription
    // THEN generated entry points file are null
    for (AssertionsEntryPointType assertionsEntryPointType : AssertionsEntryPointType.values()) {
      assertThat(generator.generateAssertionsEntryPointClassFor(null, assertionsEntryPointType, null)).isNull();
    }
  }

  @Test
  public void should_return_empty_assertion_entry_point_class_if_null_classes_description_are_given() throws Exception {
    // GIVEN no ClassDescription
    // THEN generated entry points file are null
    for (AssertionsEntryPointType assertionsEntryPointType : AssertionsEntryPointType.values()) {
      assertThat(generator.generateAssertionsEntryPointClassContentFor(null, assertionsEntryPointType, null)).isEmpty();
    }
  }

  @Test
  public void should_not_generate_assertion_entry_point_for_non_public_class_in_package() throws Exception {
    // GIVEN package including a package private class
    Set<IClassDescription> classDescriptions = getClassDescriptionsOf(collectClasses("com.cprohinig.assertj.generator.data"));
    // WHEN
    String assertionsEntryPointContent = generator.generateAssertionsEntryPointClassContentFor(classDescriptions,
                                                                                               STANDARD,
                                                                                               "com.cprohinig.assertj.generator.data");
    // THEN
    assertThat(assertionsEntryPointContent).doesNotContain("PackagePrivate");
  }

  @Test
  public void should_not_generate_assertion_entry_point_for_non_public_class() throws Exception {
    // GIVEN package including a package private class
    Set<IClassDescription> classDescriptions = getClassDescriptionsOf(collectClasses("com.cprohinig.assertj.generator.data.inner.PackagePrivate"));
    // WHEN
    String assertionsEntryPointContent = generator.generateAssertionsEntryPointClassContentFor(classDescriptions,
                                                                                               STANDARD,
                                                                                               "com.cprohinig.assertj.generator.data");
    // THEN
    assertThat(assertionsEntryPointContent).doesNotContain("PackagePrivate");
  }

  private Set<IClassDescription> getClassDescriptionsOf(Class<?>... classes) {
    Set<IClassDescription> classDescriptionSet = new LinkedHashSet<>(classes.length);
    ClassToClassDescriptionConverter converter = new ClassToClassDescriptionConverter();
    for (Class<?> clazz : classes) {
      classDescriptionSet.add(converter.convertToClassDescription(TypeToken.of(clazz)));
    }
    return classDescriptionSet;
  }

  private Set<IClassDescription> getClassDescriptionsOf(Set<TypeToken<?>> typeTokens) {
    Set<IClassDescription> classDescriptionSet = new LinkedHashSet<>(typeTokens.size());
    ClassToClassDescriptionConverter converter = new ClassToClassDescriptionConverter();
    for (TypeToken<?> typeToken : typeTokens) {
      classDescriptionSet.add(converter.convertToClassDescription(typeToken));
    }
    return classDescriptionSet;
  }

  private String readExpectedContentFromFile(String fileWithExpectedContent) {
    return contentOf(new File("src/test/resources", fileWithExpectedContent));
  }

}
