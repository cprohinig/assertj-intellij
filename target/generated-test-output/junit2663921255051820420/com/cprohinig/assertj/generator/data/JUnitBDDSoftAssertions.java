package com.cprohinig.assertj.generator.data;

/**
 * Like {@link BDDSoftAssertions} but as a junit rule that takes care of calling
 * {@link SoftAssertions#assertAll() assertAll()} at the end of each test.
 * <p>
 * Example:
 * <pre><code class='java'> public class SoftlyTest {
 *
 *     &#064;Rule
 *     public final JUnitBDDSoftAssertions softly = new JUnitBDDSoftAssertions();
 *
 *     &#064;Test
 *     public void soft_bdd_assertions() throws Exception {
 *       softly.then(1).isEqualTo(2);
 *       softly.then(Lists.newArrayList(1, 2)).containsOnly(1, 2);
 *       // no need to call assertAll(), this is done automatically.
 *     }
 *  }</code></pre>
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class JUnitBDDSoftAssertions extends org.assertj.core.api.JUnitBDDSoftAssertions {

  /**
   * Creates a new "soft" instance of <code>{@link com.cprohinig.assertj.generator.data.MovieAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public com.cprohinig.assertj.generator.data.MovieAssert then(com.cprohinig.assertj.generator.data.Movie actual) {
    return proxy(com.cprohinig.assertj.generator.data.MovieAssert.class, com.cprohinig.assertj.generator.data.Movie.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link com.cprohinig.assertj.generator.data.MoviePublicCategoryAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public com.cprohinig.assertj.generator.data.MoviePublicCategoryAssert then(com.cprohinig.assertj.generator.data.Movie.PublicCategory actual) {
    return proxy(com.cprohinig.assertj.generator.data.MoviePublicCategoryAssert.class, com.cprohinig.assertj.generator.data.Movie.PublicCategory.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link com.cprohinig.assertj.generator.data.NameAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public com.cprohinig.assertj.generator.data.NameAssert then(com.cprohinig.assertj.generator.data.Name actual) {
    return proxy(com.cprohinig.assertj.generator.data.NameAssert.class, com.cprohinig.assertj.generator.data.Name.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link com.cprohinig.assertj.generator.data.TreeEnumAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public com.cprohinig.assertj.generator.data.TreeEnumAssert then(com.cprohinig.assertj.generator.data.TreeEnum actual) {
    return proxy(com.cprohinig.assertj.generator.data.TreeEnumAssert.class, com.cprohinig.assertj.generator.data.TreeEnum.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link com.cprohinig.assertj.generator.data.art.ArtWorkAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public com.cprohinig.assertj.generator.data.art.ArtWorkAssert then(com.cprohinig.assertj.generator.data.art.ArtWork actual) {
    return proxy(com.cprohinig.assertj.generator.data.art.ArtWorkAssert.class, com.cprohinig.assertj.generator.data.art.ArtWork.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link com.cprohinig.assertj.generator.data.lotr.RaceAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public com.cprohinig.assertj.generator.data.lotr.RaceAssert then(com.cprohinig.assertj.generator.data.lotr.Race actual) {
    return proxy(com.cprohinig.assertj.generator.data.lotr.RaceAssert.class, com.cprohinig.assertj.generator.data.lotr.Race.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link com.cprohinig.assertj.generator.data.lotr.RingAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public com.cprohinig.assertj.generator.data.lotr.RingAssert then(com.cprohinig.assertj.generator.data.lotr.Ring actual) {
    return proxy(com.cprohinig.assertj.generator.data.lotr.RingAssert.class, com.cprohinig.assertj.generator.data.lotr.Ring.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link com.cprohinig.assertj.generator.data.lotr.TolkienCharacterAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public com.cprohinig.assertj.generator.data.lotr.TolkienCharacterAssert then(com.cprohinig.assertj.generator.data.lotr.TolkienCharacter actual) {
    return proxy(com.cprohinig.assertj.generator.data.lotr.TolkienCharacterAssert.class, com.cprohinig.assertj.generator.data.lotr.TolkienCharacter.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link com.cprohinig.assertj.generator.data.nba.PlayerAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public com.cprohinig.assertj.generator.data.nba.PlayerAssert then(com.cprohinig.assertj.generator.data.nba.Player actual) {
    return proxy(com.cprohinig.assertj.generator.data.nba.PlayerAssert.class, com.cprohinig.assertj.generator.data.nba.Player.class, actual);
  }

}
