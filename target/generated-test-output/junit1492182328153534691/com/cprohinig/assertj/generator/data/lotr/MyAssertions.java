package com.cprohinig.assertj.generator.data.lotr;

/**
 * Entry point for assertions of different data types. Each method in this class is a static factory for the
 * type-specific assertion objects.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class MyAssertions {

  /**
   * Creates a new instance of <code>{@link com.cprohinig.assertj.generator.data.lotr.RingAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static com.cprohinig.assertj.generator.data.lotr.RingAssert assertThat(com.cprohinig.assertj.generator.data.lotr.Ring actual) {
    return new com.cprohinig.assertj.generator.data.lotr.RingAssert(actual);
  }

  /**
   * Creates a new <code>{@link MyAssertions}</code>.
   */
  protected MyAssertions() {
    // empty
  }
}
