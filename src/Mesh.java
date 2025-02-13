import algebra.*;
import java.io.*;

/**
 * Defines a triangle based mesh. A mesh is constructed by interpreting the data given in an OFF
 * file.
 *
 * @author smondet gg cdehais
 */
public class Mesh {

  private Vector[] vertices;
  private int[] faces;
  private double[] colors;
  private Vector3[] normals;
  private double[] texCoords;

  private static String nextLine(BufferedReader in) throws Exception {
    String r = in.readLine();

    while (r.matches("\\s*#.*")) {
      r = in.readLine();
    }
    return r;
  }

  /**
   * Builds a Mesh object by reading in an OFF file. Does not support non triangular meshes.
   *
   * @filename path to OFF file.
   */
  public Mesh(String filename) throws Exception {
    BufferedReader in = new BufferedReader(new FileReader(filename));

    String r = nextLine(in);
    if (!r.equals("OFF")) {
      throw new IOException("Invalid OFF file !");
    }

    r = nextLine(in);

    String[] sar = r.split("\\s+");

    // Parse object properties
    int verts_nb = Integer.parseInt(sar[0]);
    int faces_nb = Integer.parseInt(sar[1]);

    // Parse vertices and attributes
    vertices = new Vector[verts_nb];
    faces = new int[3 * faces_nb];
    colors = new double[3 * verts_nb];
    for (int i = 0; i < verts_nb; i++) {

      r = nextLine(in);
      sar = r.split("\\s+");

      vertices[i] = new Vector("v" + i, 4);
      vertices[i].set(0, Double.parseDouble(sar[0]));
      vertices[i].set(1, Double.parseDouble(sar[1]));
      vertices[i].set(2, Double.parseDouble(sar[2]));
      vertices[i].set(3, 1.0);
      colors[3 * i + 0] = Double.parseDouble(sar[3]);
      colors[3 * i + 1] = Double.parseDouble(sar[4]);
      colors[3 * i + 2] = Double.parseDouble(sar[5]);
      // optional texture coordinates
      if (sar.length >= 8) {
        if (texCoords == null) {
          texCoords = new double[2 * verts_nb];
        }
        texCoords[2 * i] = Double.parseDouble(sar[6]);
        texCoords[2 * i + 1] = Double.parseDouble(sar[7]);
      }
    }

    // Parse faces
    for (int i = 0; i < faces_nb; i++) {

      r = nextLine(in);
      sar = r.split("\\s+");

      int en = Integer.parseInt(sar[0]);
      if (en != 3) {
        throw new IOException("Non-triangular meshes not supported.");
      }
      faces[3 * i + 0] = Integer.parseInt(sar[1]);
      faces[3 * i + 1] = Integer.parseInt(sar[2]);
      faces[3 * i + 2] = Integer.parseInt(sar[3]);
    }
    in.close();
  }

  /** Gets the number of vertices in the mesh */
  public int getNumVertices() {
    return vertices.length;
  }

  /** Gets the number of faces in the mesh */
  public int getNumFaces() {
    return faces.length / 3;
  }

  /** Constructs a normal for each vertex of the mesh */
  private Vector3[] computeNormals() {

    normals = new Vector3[vertices.length];

    // Compute per face normals and set the vertex normal to the average normals
    // across faces
    // to the vertex.
    try {
      for (int i = 0; i < 3 * getNumFaces(); i += 3) {
        // TODO
        Vector3 q = new Vector3(vertices[faces[i]].get(0), vertices[faces[i]].get(1), vertices[faces[i]].get(2));
        Vector3 r = new Vector3(vertices[faces[i+1]].get(0), vertices[faces[i+1]].get(1), vertices[faces[i+1]].get(2));
        Vector3 s = new Vector3(vertices[faces[i+2]].get(0), vertices[faces[i+2]].get(1), vertices[faces[i+2]].get(2));

        Vector3 qr =r;
        qr.subtract(q);

        Vector3 qs = s;
        qs.subtract(q);

        Vector3 n = qr.cross(qs);

        // add the calculated normal n to each vertex of the face
        for (int j = 0; j < 3; j++) {
          Vector nj = normals[faces[i + j]];

          if (nj == null) {
            normals[faces[i + j]] = new Vector3(n);
            normals[faces[i + j]].setName("n" + faces[i + j]);

          } else {
            nj.add(n);
          }
        }
      }
    } catch (InstantiationException e) {
      System.out.println("Should not reach 1");
    } catch (SizeMismatchException e) {
      System.out.println("Should not reach 2");
    }

    // final round of normalization
    for (int i = 0; i < normals.length; i++) {
      // deal with orphans vertices
      if (normals[i] == null) {
        normals[i] = new Vector3("n_orphan");
      } else {
        normals[i].normalize();
      }
    }

    return normals;
  }

  /** Returns the vertices of the mesh */
  public Vector[] getVertices() {
    return vertices;
  }

  /** Return the normals associated to the vertices. */
  public Vector3[] getNormals() {
    if (normals == null) {
      normals = computeNormals();
    }

    return normals;
  }

  /**
   * Returns the faces of the mesh. The returned array contains 3*n integers, with n the number of
   * faces. Each integer is an index into the array of Vector.
   */
  public int[] getFaces() {
    return faces;
  }

  /** Returns the colors of each vertex in the mesh. */
  public double[] getColors() {
    return colors;
  }

  /** Returns the texture coordinates of each vertex in the mesh. */
  public double[] getTextureCoordinates() {
    return texCoords;
  }
}
