
import java.io.*;
import algebra.*;

/**
 * Class that describes a simple 3D Scene:
 * * the Mesh objects composing the scene
 * * the World-to-Camera transformation
 * * the Lights and their parameters
 * This description is meant to be read form a scene description file (.scene
 * extension)
 *
 * @author: cdehais based on smondet, gmorin
 */

public class Scene {

    private static String nextLine(BufferedReader in) throws Exception {
        String r = in.readLine();

        while (r.matches("(\\s*#.*)|(\\s*$)")) {
            r = in.readLine();
        }
        return r;
    }

    String meshFilename;
    Vector3 cameraPosition = new Vector3("cam_pos");
    Vector3 cameraLookAt = new Vector3("cam_lookat");
    Vector3 cameraUp = new Vector3("cam_up");
    double cameraFocal;
    int screenW;
    int screenH;
    double ambientI;
    double sourceI;
    double[] sourceCoord = new double[4];
    double[] material = new double[4];

    public Scene(String filename) throws Exception {

        BufferedReader in = new BufferedReader(new FileReader(filename));

        meshFilename = nextLine(in);

        String r = nextLine(in);
        String[] sar = r.split("\\s+");
        cameraPosition.set(Double.parseDouble(sar[0]),
                Double.parseDouble(sar[1]),
                Double.parseDouble(sar[2]));

        r = nextLine(in);
        sar = r.split("\\s+");
        cameraLookAt.set(Double.parseDouble(sar[0]),
                Double.parseDouble(sar[1]),
                Double.parseDouble(sar[2]));

        r = nextLine(in);
        sar = r.split("\\s+");
        cameraUp.set(Double.parseDouble(sar[0]),
                Double.parseDouble(sar[1]),
                Double.parseDouble(sar[2]));

        r = nextLine(in);
        cameraFocal = Double.parseDouble(r);

        r = nextLine(in);
        sar = r.split("\\s+");
        screenW = Integer.parseInt(sar[0]);
        screenH = Integer.parseInt(sar[1]);

        r = nextLine(in);
        ambientI = Double.parseDouble(r);

        r = nextLine(in);
        sar = r.split("\\s+");
        for (int i = 0; i < sourceCoord.length; i++) {
            sourceCoord[i] = Double.parseDouble(sar[i]);
        }
        sourceI = Double.parseDouble(sar[3]);

        r = nextLine(in);
        sar = r.split("\\s+");
        for (int i = 0; i < material.length; i++) {
            material[i] = Double.parseDouble(sar[i]);
        }
    }

    public String getMeshFileName() {
        return meshFilename;
    }

    public Vector3 getCameraPosition() {
        return cameraPosition;
    }

    public Vector3 getCameraLookAt() {
        return cameraLookAt;
    }

    public Vector3 getCameraUp() {
        return cameraUp;
    }

    public double getCameraFocal() {
        return cameraFocal;
    }

    public int getScreenW() {
        return screenW;
    }

    public int getScreenH() {
        return screenH;
    }

    public double getAmbientI() {
        return ambientI;
    }

    public double getSourceI() {
        return sourceI;
    }

    public double[] getSourceCoord() {
        return sourceCoord;
    }

    public double[] getMaterial() {
        return material;
    }
}
