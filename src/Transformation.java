
import algebra.*;

/**
 * author: cdehais
 */
public class Transformation {

  Matrix worldToCamera;
  Matrix projection;
  Matrix calibration;

  public Transformation() {
    try {
      worldToCamera = Matrix.createIdentity("W2C", 4);
      projection = new Matrix("P", 3, 4);
      calibration = Matrix.createIdentity("K", 3);
    } catch (InstantiationException e) {
      // should not reach
    }
  }

  public void setLookAt(Vector3 eye, Vector3 interestPoint, Vector3 up) {
    try {
      // compute rotation
      Vector3 w = interestPoint;
      w.setName("w");
      w.subtract(eye);
      w.normalize();

      // Vector3 u = w;
      var u = up.cross(w);
      u.setName("u");
      u.normalize();

      // var v = w;
      var v = w.cross(u);
      v.setName("v");

      var R = Matrix.createIdentity(3);
      R.setName("R");
      R.setCol(0, u);
      R.setCol(1, v);
      R.setCol(2, w);
      R = R.transpose();

      // compute translation
      var T = R.multiply(eye);
      T.scale(-1f);

      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          worldToCamera.set(i, j, R.get(i, j));
        }
      }
      for (int i = 0; i < 3; i++) {
        worldToCamera.set(i, 3, T.get(i));
      }

    } catch (Exception e) {
      /* unreached */ }
    ;

    System.out.println("Modelview matrix:\n" + worldToCamera);
  }

  public void setProjection() {
    // TODO

    System.out.println("Projection matrix:\n" + projection);
  }

  public void setCalibration(double focal, double width, double height) {

    // TODO

    System.out.println("Calibration matrix:\n" + calibration);
  }

  /**
   * Projects the given homogeneous, 4 dimensional point onto the screen.
   * The resulting Vector as its (x,y) coordinates in pixel, and its z coordinate
   * is the depth of the point in the camera coordinate system.
   */
  public Vector3 projectPoint(Vector p)
      throws SizeMismatchException, InstantiationException {
    Vector ps = new Vector(3);
    // TODO

    return new Vector3(ps);
  }

  /**
   * Transform a vector from world to camera coordinates.
   */
  public Vector3 transformVector(Vector3 v)
      throws SizeMismatchException, InstantiationException {
    // Doing nothing special here because there is no scaling
    Matrix R = worldToCamera.getSubMatrix(0, 0, 3, 3);
    Vector tv = R.multiply(v);
    return new Vector3(tv);
  }

}
