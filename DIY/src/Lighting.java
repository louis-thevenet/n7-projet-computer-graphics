import algebra.*;
import java.util.*;

/**
 * The Lighting class describes a scene lighting environment
 *
 * @author: gmorin, smondet
 */
public class Lighting {

  static final int NONE = 0;
  static final int AMBIENT = 1;
  static final int POINT = 2;

  List<Light> lights;

  /** Internal Class describing a light source */
  private class Light {
    public int type = NONE;
    public double[] params;

    public Light(int type, double[] params) {
      this.type = type;
      this.params = params;
    }
  }

  public Lighting() {
    lights = new LinkedList<Light>();
  }

  /** Adds a new ambient light source of intensity @ia to the environment. */
  public void addAmbientLight(double ia) {
    double[] v = new double[1];
    v[0] = ia;
    lights.add(new Light(AMBIENT, v));
  }

  /** Addsa */
  public void addPointLight(double x, double y, double z, double id) {
    double[] v = new double[5];
    v[0] = x;
    v[1] = y;
    v[2] = z;
    v[3] = id;
    lights.add(new Light(POINT, v));
  }

  /**
   * Computes the illuminated color of a 3D points of given position, normal and
   * color, and given
   * the camera position and material parameters. Returns an array of size 3.
   */
  public double[] applyLightsGouraud(
      Vector3 position,
      Vector3 normal,
      double[] color,
      Vector3 cameraPosition,
      double ka,
      double kd,
      double ks,
      double s) {
    double[] litColor = new double[3];

    // total light intensity
    double I = 0.0;

    Iterator<Light> it = lights.iterator();
    while (it.hasNext()) {
      Light light = (Light) it.next();
      switch (light.type) {
        case AMBIENT:
          // ambient light contribution
          I += light.params[0] * ka;
          break;

        case POINT:
          try {
            // vector from point to camera center
            Vector3 e = new Vector3(cameraPosition);
            e.subtract(position);
            e.normalize();

            // vector from point to light
            Vector3 l = new Vector3(light.params[0], light.params[1], light.params[2]);
            l.subtract(position);
            l.normalize();

            // half-vector between e and l
            Vector3 h = new Vector3(e);
            h.add(l);
            h.normalize();

            // diffuse contribution
            double I_diffuse = light.params[3] * kd * (normal.dot(l)) / (normal.norm() * l.norm());

            // specular contribution
            double I_specular = light.params[3] * ks * (Math.pow(h.dot(normal), s)) / (normal.norm() * h.norm());
            I += I_diffuse + I_specular;

          } catch (InstantiationException ex) {
            /* should not reach */ } catch (SizeMismatchException ex) {
            /* should not reach */ }

          break;
        default:
          // ignore unknown lights
          break;
      }
    }

    litColor[0] = I * color[0];
    litColor[1] = I * color[1];
    litColor[2] = I * color[2];

    return litColor;
  }

  public double[] applyLightsPhong(Vector3 position,
      Vector3 normal,
      double[] color,
      Vector3 cameraPosition,
      double ka,
      double kd,
      double ks,
      double s) {
    double[] litColor = new double[3];

    double I = 0.0;

    // Add ambient light
    for (Light light : lights) {
      if (light.type == AMBIENT) {
        I += light.params[0] * ka ;
      }
    }

    for (Light light : lights) {
      if (light.type == POINT) {
        try {
          // vector from point to light
          Vector3 l = new Vector3(light.params[0], light.params[1], light.params[2]);
          l.subtract(position);
          l.normalize();

          // vector from point to camera center (eye) == vector V
          Vector3 v = new Vector3(cameraPosition);
          v.subtract(position);
          v.normalize();

          // reflection vector
          Vector3 r = new Vector3(normal);
          r.scale(2 * normal.dot(l));
          r.subtract(l);
          r.normalize();

          // diffuse contribution
          double I_diffuse = light.params[3] * kd * Math.max(0, normal.dot(l));

          // specular contribution
          double I_specular = light.params[3] * ks * Math.pow(Math.max(0, r.dot(v)), s);

          I+= (I_diffuse + I_specular);

        } catch (InstantiationException ex) {
          /* should not reach */
        } catch (SizeMismatchException ex) {
          /* should not reach */
        }
      }
    }

    litColor[0] = I*color[0];
    litColor[1] = I*color[1];
    litColor[2] = I*color[2];

    return litColor;

  }

}
