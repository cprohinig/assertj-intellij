package com.cprohinig.assertj.generator.data;

/**
 * A version of {@link BDDSoftAssertions} that uses try-with-resources statement to automatically call
 * {@link BDDSoftAssertions#assertAll()} so that you don't forget to.
 * <p>
 * Example:
 * <pre><code class='java'> public class AutoCloseableBDDSoftAssertionsTest {
 * 
 * &#064;Test
 * public void host_dinner_party_where_nobody_dies() {
 *   Mansion mansion = new Mansion();
 * 
 *   mansion.hostPotentiallyMurderousDinnerParty();
 * 
 *   try (AutoCloseableBDDSoftAssertions softly = new AutoCloseableBDDSoftAssertions()) {
 *     softly.then(mansion.guests()).as(&quot;Living Guests&quot;).isEqualTo(7);
 *     softly.then(mansion.kitchen()).as(&quot;Kitchen&quot;).isEqualTo(&quot;clean&quot;);
 *     // no need to call assertAll, it is done when softly is closed.
 *   }
 * }
 * </pre>
 */
public class AutoCloseableBDDSoftAssertions extends org.assertj.core.api.BDDSoftAssertions implements AutoCloseable {

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

  @Override
  public void close() throws SoftAssertionError {
    assertAll();
  }
}
