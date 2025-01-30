
import algebra.*;

/**
 * Test class from the Scene class.
 * 
 * @author: cdehais
 */
public class TestScene {

    public static void test() throws Exception {

        Scene scene = new Scene("data/example1.scene");
        String mesh_filename = scene.getMeshFileName();
        Vector3 cameraPosition = scene.getCameraPosition();
        Vector3 cameraLookAt = scene.getCameraLookAt();
        Vector3 cameraUp = scene.getCameraUp();
        // double cameraXLimit = scene.getCameraXLimit ();
        double cameraFocal = scene.getCameraFocal();
        int screenW = scene.getScreenW();
        int screenH = scene.getScreenH();
        double ambientI = scene.getAmbientI();
        double sourceI = scene.getSourceI();
        double[] sourceCoord = scene.getSourceCoord();
        double[] material = scene.getMaterial();

        System.out.println("# Test output");
        System.out.println(mesh_filename);

        System.out.println(cameraPosition);
        System.out.println(cameraLookAt);
        System.out.println(cameraUp);

        // System.out.println ("Camera X Limit = " + cameraXLimit);
        System.out.println("Focal = " + cameraFocal);

        System.out.println("Screen: " + screenW + " x " + screenH);

        System.out.println("Light ambient: " + ambientI);
        System.out.println("Light ambient: " + sourceI);

        System.out.println(sourceCoord[0] + " " +
                sourceCoord[1] + " " +
                sourceCoord[2] + " " +
                sourceCoord[3]);

        System.out.println(material[0] + " " +
                material[1] + " " +
                material[2] + " " +
                material[3]);

    }

    public static void main(String argv[]) {
        try {
            test();
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e);
        }
    }

}
