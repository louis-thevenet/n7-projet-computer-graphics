package unit.algebra;

import algebra.Matrix;
import algebra.SizeMismatchException;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestMatrix {

    private static final double EPSILON = 0.001;

    @Test
    public void testMatrixCreation() throws InstantiationException {
        Matrix m = new Matrix("testMatrix", 3, 3);
        assertNotNull(m);
        assertEquals("testMatrix", m.name);
        assertEquals(3, m.nRows());
        assertEquals(3, m.nCols());

        Matrix m2 = new Matrix("rectMatrix", 2, 3);
        assertNotNull(m2);
        assertEquals("rectMatrix", m2.name);
        assertEquals(2, m2.nRows());
        assertEquals(3, m2.nCols());
    }

    @Test(expected = InstantiationException.class)
    public void testInvalidMatrixCreation() throws InstantiationException {
        new Matrix("invalidMatrix", 0, 3);
    }

    @Test(expected = InstantiationException.class)
    public void testInvalidNegativeSizesMatrixCreation() throws InstantiationException {
        new Matrix("invalidMatrix", -5, -4);
    }
    @Test(expected = InstantiationException.class)
    public void testInvalidSingleNegativeSizesMatrixCreation1() throws InstantiationException {
        new Matrix("invalidMatrix", -5, 4);
    }
    @Test(expected = InstantiationException.class)
    public void testInvalidSingleNegativeSizesMatrixCreation2() throws InstantiationException {
        new Matrix("invalidMatrix", 5, -4);
    }

    @Test
    public void testSetAndGets() throws InstantiationException {
        Matrix m = new Matrix("testMatrix", 2, 2);
        m.set(0, 0, 1.0);
        m.set(0, 1, 2.0);
        m.set(1, 0, 3.0);
        m.set(1, 1, 4.0);
        assertEquals(1.0, m.get(0, 0), EPSILON);
        assertEquals(2.0, m.get(0, 1), EPSILON);
        assertEquals(3.0, m.get(1, 0), EPSILON);
        assertEquals(4.0, m.get(1, 1), EPSILON);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetInvalidValue() throws InstantiationException {
        Matrix m = new Matrix("testMatrix", 2, 2);
        m.set(2, 2, 5.0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetInvalidValue() throws InstantiationException {
        Matrix m = new Matrix("testMatrix", 2, 2);
        m.get(2, 2);
    }

    @Test
    public void testTransposeSquareMatrix() throws InstantiationException {
        Matrix m = new Matrix("testMatrix", 4, 4);
        double value = 1.0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
            m.set(i, j, value++);
            }
        }
        Matrix transposed = m.transpose();
        value = 1.0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
            assertEquals(value++, transposed.get(j, i), EPSILON);
            }
        }
    }

    @Test
    public void testTransposeRectangularMatrix() throws InstantiationException {
        Matrix m = new Matrix("testMatrix", 3, 4);
        double value = 1.0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
            m.set(i, j, value++);
            }
        }
        Matrix transposed = m.transpose();
        value = 1.0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
            assertEquals(value++, transposed.get(j, i), EPSILON);
            }
        }
    }

    @Test
    public void testMultiplySquareMatrices() throws InstantiationException, SizeMismatchException {
        Matrix m1 = new Matrix("m1", 2, 2);
        m1.set(0, 0, 1.0);
        m1.set(0, 1, 2.0);
        m1.set(1, 0, 3.0);
        m1.set(1, 1, 4.0);

        Matrix m2 = new Matrix("m2", 2, 2);
        m2.set(0, 0, 2.0);
        m2.set(0, 1, 0.0);
        m2.set(1, 0, 1.0);
        m2.set(1, 1, 2.0);

        Matrix result = m1.multiply(m2);
        assertEquals(4.0, result.get(0, 0), EPSILON);
        assertEquals(4.0, result.get(0, 1), EPSILON);
        assertEquals(10.0, result.get(1, 0), EPSILON);
        assertEquals(8.0, result.get(1, 1), EPSILON);
    }

    @Test
    public void testMultiplyRectangularMatrices() throws InstantiationException, SizeMismatchException {
        Matrix m1 = new Matrix("m1", 2, 3);
        m1.set(0, 0, 1.0);
        m1.set(0, 1, 2.0);
        m1.set(0, 2, 3.0);
        m1.set(1, 0, 4.0);
        m1.set(1, 1, 5.0);
        m1.set(1, 2, 6.0);

        Matrix m2 = new Matrix("m2", 3, 2);
        m2.set(0, 0, 7.0);
        m2.set(0, 1, 8.0);
        m2.set(1, 0, 9.0);
        m2.set(1, 1, 10.0);
        m2.set(2, 0, 11.0);
        m2.set(2, 1, 12.0);

        Matrix result = m1.multiply(m2);
        assertEquals(58.0, result.get(0, 0), EPSILON);
        assertEquals(64.0, result.get(0, 1), EPSILON);
        assertEquals(139.0, result.get(1, 0), EPSILON);
        assertEquals(154.0, result.get(1, 1), EPSILON);
    }

    @Test(expected = SizeMismatchException.class)
    public void testMultiplyInvalidMatrices() throws InstantiationException, SizeMismatchException {
        Matrix m1 = new Matrix("m1", 2, 2);
        Matrix m2 = new Matrix("m2", 3, 3);
        m1.multiply(m2);
    }

    // add test for identity matrix
    @Test
    public void testIdentityMatrix() throws InstantiationException {
        Matrix m = Matrix.createIdentity("identity", 3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == j) {
                    assertEquals(1.0, m.get(i, j), EPSILON);
                } else {
                    assertEquals(0.0, m.get(i, j), EPSILON);
                }
            }
        }
    }

}