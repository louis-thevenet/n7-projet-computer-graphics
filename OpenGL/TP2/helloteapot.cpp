#include <algorithm>
#include <cstdlib>
#include <GL/freeglut_std.h>

// for mac osx
#ifdef __APPLE__
#include <GLUT/glut.h>
#include <OpenGL/gl.h>
#include <OpenGL/glu.h>
#else
// only for windows
#ifdef _WIN32
#include <windows.h>
#endif
// for windows and linux
#include <GL/freeglut.h>
#include <GL/gl.h>
#include <GL/glu.h>
#endif

bool wire = false;

// function called everytime the windows is refreshed
void display()
{
    // clear window
    glClear(GL_COLOR_BUFFER_BIT);

    // draw scene
    if (wire) {
        glutSolidTeapot(0.7);

    }
    else {
        glutWireTeapot(0.7);
    }

    // flush drawing routines to the window
    glFlush();
}



// Function called everytime a key is pressed
void key(unsigned char key, int, int)
{
    switch(key)
    {
        case 'w':
            wire=!wire;
            break;
        // the 'esc' key
        case 27:
        // the 'q' key
        case 'q': exit(EXIT_SUCCESS); break;
        default: break;
    }
    glutPostRedisplay();
}

// Function called every time the main window is resized
void reshape(int width, int height)
{
    // define the viewport transformation;
    int minwh = std::min(width, height);
    int maxwh = std::max(width, height);

    int offset = maxwh - minwh;
    int offset_x = (width > height) ? offset : 0;
    int offset_y = (width < height) ? offset : 0;
    
    glViewport(offset_x/2, offset_y/2, minwh , minwh );
}

// Main routine
int main(int argc, char* argv[])
{
    // initialize GLUT, using any commandline parameters passed to the
    //   program
    glutInit(&argc, argv);

    // setup the size, position, and display mode for new windows
    glutInitWindowSize(500, 500 );
    glutInitWindowPosition(0, 0);
    glutInitDisplayMode(GLUT_RGB);

    // create and set up a window
    glutCreateWindow("Hello, teapot!");

    // Set up the callback functions:
    // for display
    glutDisplayFunc(display);
    // for the keyboard
    glutKeyboardFunc(key);
    // for reshaping
    glutReshapeFunc(reshape);

    // tell GLUT to wait for events
    glutMainLoop();
}
