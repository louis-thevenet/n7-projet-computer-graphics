package unit.algebra;

import static org.junit.Assert.*;

import algebra.SizeMismatchException;
import algebra.Vector;
import org.junit.Test;

public class TestVector {

  private static final double EPSILON = 0.001;

  @Test
  public void testVectorCreation() throws InstantiationException {
    Vector v = new Vector("testVector", 3);
    assertNotNull(v);
    assertEquals("testVector", v.name);
    assertEquals(3, v.size());
  }

  @Test
  public void testDefaultName() throws InstantiationException {
    Vector v = new Vector(5);
    assertEquals("v", v.name);
  }

  @Test
  public void testInvalidSize() {
    try {
      new Vector("invalidVector", 0);
      fail("Expected an InstantiationException to be thrown");
    } catch (InstantiationException e) {
      assertEquals("Vector size must be strictly positive", e.getMessage());
    }
  }

  @Test
  public void testSetAndGetValues() throws InstantiationException {
    Vector v = new Vector("testVector", 3);
    v.set(0, 1.0);
    v.set(1, 2.0);
    v.set(2, 3.0);
    assertEquals(1.0, v.get(0), EPSILON);
    assertEquals(2.0, v.get(1), EPSILON);
    assertEquals(3.0, v.get(2), EPSILON);
  }

  // tests for scale(), dot(), add() subtract() zeros() ones()

  @Test
  public void testScale() throws InstantiationException {
    Vector v = new Vector("testVector", 3);
    v.set(0, 1.0);
    v.set(1, 2.0);
    v.set(2, 3.0);
    v.scale(2.0);
    assertEquals(2.0, v.get(0), EPSILON);
    assertEquals(4.0, v.get(1), EPSILON);
    assertEquals(6.0, v.get(2), EPSILON);
  }

  @Test
  public void testDot() throws InstantiationException {
    Vector v1 = new Vector("testVector1", 3);
    v1.set(0, 1.0);
    v1.set(1, 2.0);
    v1.set(2, 3.0);
    Vector v2 = new Vector("testVector2", 3);
    v2.set(0, 4.0);
    v2.set(1, 5.0);
    v2.set(2, 6.0);
    try {
      assertEquals(32.0, v1.dot(v2), EPSILON);
    } catch (SizeMismatchException e) {
      e.printStackTrace();
      fail("Unexpected SizeMismatchException exception");
    }
  }

  @Test
  public void testAdd() throws InstantiationException {
    Vector v1 = new Vector("testVector1", 3);
    v1.set(0, 1.0);
    v1.set(1, 2.0);
    v1.set(2, 3.0);
    Vector v2 = new Vector("testVector2", 3);
    v2.set(0, 4.0);
    v2.set(1, 5.0);
    v2.set(2, 6.0);
    try {
      v1.add(v2);
      assertEquals(5.0, v1.get(0), EPSILON);
      assertEquals(7.0, v1.get(1), EPSILON);
      assertEquals(9.0, v1.get(2), EPSILON);
    } catch (SizeMismatchException e) {
      e.printStackTrace();
      fail("Unexpected SizeMismatchException exception");
    }
  }

  @Test
  public void testSubtract() throws InstantiationException {
    Vector v1 = new Vector("testVector1", 3);
    v1.set(0, 1.0);
    v1.set(1, 2.0);
    v1.set(2, 3.0);
    Vector v2 = new Vector("testVector2", 3);
    v2.set(0, 4.0);
    v2.set(1, 5.0);
    v2.set(2, 6.0);
    try {
      v1.subtract(v2);
      assertEquals(-3.0, v1.get(0), EPSILON);
      assertEquals(-3.0, v1.get(1), EPSILON);
      assertEquals(-3.0, v1.get(2), EPSILON);
    } catch (SizeMismatchException e) {
      fail("Unexpected SizeMismatchException exception");
      e.printStackTrace();
    }

    try {
      Vector v3 = new Vector("testVector3", 2);
      v1.subtract(v3);
      fail("Expected a SizeMismatchException to be thrown");
    } catch (SizeMismatchException e) {

    }
  }

  @Test
  public void testZeros() throws InstantiationException {
    Vector v = new Vector("testVector", 3);
    v.zeros();
    assertEquals(0.0, v.get(0), EPSILON);
    assertEquals(0.0, v.get(1), EPSILON);
    assertEquals(0.0, v.get(2), EPSILON);
  }

  @Test
  public void testOnes() throws InstantiationException {
    Vector v = new Vector("testVector", 3);
    v.ones();
    assertEquals(1.0, v.get(0), EPSILON);
    assertEquals(1.0, v.get(1), EPSILON);
    assertEquals(1.0, v.get(2), EPSILON);
  }
}
