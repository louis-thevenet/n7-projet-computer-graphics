package unit.algebra;

import algebra.SizeMismatchException;
import algebra.Vector3;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestVector3 {

    private static final double EPSILON = 0.001;

    @Test
    public void testVector3Creation() {
        Vector3 v = new Vector3(1.0, 2.0, 3.0);
        assertNotNull(v);
        assertEquals(1.0, v.getX(), EPSILON);
        assertEquals(2.0, v.getY(), EPSILON);
        assertEquals(3.0, v.getZ(), EPSILON);
        assertEquals("v", v.getName());
    }

    @Test
    public void testCrossProduct() {
        Vector3 v1 = new Vector3(1.0, 0.0, 0.0);
        Vector3 v2 = new Vector3(0.0, 1.0, 0.0);
        Vector3 result = v1.cross(v2);
        assertEquals(0.0, result.getX(), EPSILON);
        assertEquals(0.0, result.getY(), EPSILON);
        assertEquals(1.0, result.getZ(), EPSILON);
    }

    @Test
    public void testDotProduct() {
        Vector3 v1 = new Vector3(1.0, 2.0, 3.0);
        Vector3 v2 = new Vector3(4.0, 5.0, 6.0);
        double result = v1.dot(v2);
        assertEquals(32.0, result, EPSILON);
    }

    @Test
    public void testNorm() {
        Vector3 v = new Vector3(1.0, 2.0, 2.0);
        double result = v.norm();
        assertEquals(3.0, result, EPSILON);
    }

    @Test
    public void testAdd() {
        Vector3 v1 = new Vector3(1.0, 2.0, 3.0);
        Vector3 v2 = new Vector3(4.0, 5.0, 6.0);
        try {
            v1.add(v2);
            assertEquals(5.0, v1.getX(), EPSILON);
            assertEquals(7.0, v1.getY(), EPSILON);
            assertEquals(9.0, v1.getZ(), EPSILON);
        } catch (SizeMismatchException e) {
            e.printStackTrace();
            fail("Unexpected SizeMismatchException exception");
        }

    }

    @Test
    public void testSubtract() {
        Vector3 v1 = new Vector3(4.0, 5.0, 6.0);
        Vector3 v2 = new Vector3(1.0, 2.0, 3.0);
        try {
            v1.subtract(v2);
            assertEquals(3.0, v1.getX(), EPSILON);
            assertEquals(3.0, v1.getY(), EPSILON);
            assertEquals(3.0, v1.getZ(), EPSILON);
        } catch (SizeMismatchException e) {
            e.printStackTrace();
            fail("Unexpected SizeMismatchException exception");
        }

    }

    @Test
    public void testScale() {
        Vector3 v = new Vector3(1.0, 2.0, 3.0);
        v.scale(2.0);
        assertEquals(2.0, v.getX(), EPSILON);
        assertEquals(4.0, v.getY(), EPSILON);
        assertEquals(6.0, v.getZ(), EPSILON);
    }

    @Test
    public void testNormalize() {
        Vector3 v = new Vector3(1.0, 2.0, 2.0);
        v.normalize();
        assertEquals(1.0 / 3.0, v.getX(), EPSILON);
        assertEquals(2.0 / 3.0, v.getY(), EPSILON);
        assertEquals(2.0 / 3.0, v.getZ(), EPSILON);
    }
}