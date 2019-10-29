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

import com.cprohinig.assertj.generator.data.OuterClass;
import org.junit.experimental.theories.DataPoint;

/**
 * This class contains a set of constants for nested classes.
 */
public interface NestedClassesTest {
  @DataPoint
  NestedClass SNC = new NestedClass(OuterClass.StaticNestedPerson.class,
                                    "OuterClass.StaticNestedPerson",
                                    "OuterClassStaticNestedPersonAssert",
                                    "com.cprohinig.assertj.generator.data.OuterClass",
                                    "OuterClassStaticNestedPersonAssert.java",
                                    "AbstractOuterClassStaticNestedPersonAssert",
                                    "AbstractOuterClassStaticNestedPersonAssert.java");
  @DataPoint
  NestedClass SNC_SNC = new NestedClass(OuterClass.StaticNestedPerson.SNP_StaticNestedPerson.class,
                                        "OuterClass.StaticNestedPerson.SNP_StaticNestedPerson",
                                        "OuterClassStaticNestedPersonSNP_StaticNestedPersonAssert",
                                        "com.cprohinig.assertj.generator.data.OuterClass",
                                        "OuterClassStaticNestedPersonSNP_StaticNestedPersonAssert.java",
                                        "AbstractOuterClassStaticNestedPersonSNP_StaticNestedPersonAssert",
                                        "AbstractOuterClassStaticNestedPersonSNP_StaticNestedPersonAssert.java");
  @DataPoint
  NestedClass SNC_IC = new NestedClass(OuterClass.StaticNestedPerson.SNP_InnerPerson.class,
                                       "OuterClass.StaticNestedPerson.SNP_InnerPerson",
                                       "OuterClassStaticNestedPersonSNP_InnerPersonAssert",
                                       "com.cprohinig.assertj.generator.data.OuterClass",
                                       "OuterClassStaticNestedPersonSNP_InnerPersonAssert.java",
                                       "AbstractOuterClassStaticNestedPersonSNP_InnerPersonAssert",
                                       "AbstractOuterClassStaticNestedPersonSNP_InnerPersonAssert.java");
  @DataPoint
  NestedClass IC = new NestedClass(OuterClass.InnerPerson.class,
                                   "OuterClass.InnerPerson",
                                   "OuterClassInnerPersonAssert",
                                   "com.cprohinig.assertj.generator.data.OuterClass",
                                   "OuterClassInnerPersonAssert.java",
                                   "AbstractOuterClassInnerPersonAssert",
                                   "AbstractOuterClassInnerPersonAssert.java");
  @DataPoint
  NestedClass IC_IC = new NestedClass(OuterClass.InnerPerson.IP_InnerPerson.class,
                                      "OuterClass.InnerPerson.IP_InnerPerson",
                                      "OuterClassInnerPersonIP_InnerPersonAssert",
                                      "com.cprohinig.assertj.generator.data.OuterClass",
                                      "OuterClassInnerPersonIP_InnerPersonAssert.java",
                                      "AbstractOuterClassInnerPersonIP_InnerPersonAssert",
                                      "AbstractOuterClassInnerPersonIP_InnerPersonAssert.java");

  class NestedClass {
    public final Class<?> nestedClass;
    public final String assertClassName;
    public final String assertClassFilename;
    public final String abstractAssertClassName;
    public final String abstractAssertClassFilename;
    public final String classNameWithOuterClass;
    public final String fullyQualifiedOuterClassName;

    public NestedClass(Class<?> nestedClass, String classNameWithOuterClass, String assertClassName, String fullyQualifiedOuterClassName, String assertClassFilename,
                       String abstractAssertClassName, String abstractAssertClassFilename) {
      this.nestedClass = nestedClass;
      this.assertClassName = assertClassName;
      this.fullyQualifiedOuterClassName = fullyQualifiedOuterClassName;
      this.classNameWithOuterClass = classNameWithOuterClass;
      this.assertClassFilename = assertClassFilename;
      this.abstractAssertClassName = abstractAssertClassName;
      this.abstractAssertClassFilename = abstractAssertClassFilename;
    }

  }
}
