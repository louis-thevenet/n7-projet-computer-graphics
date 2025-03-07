# 3.2.2 Display
1. A solid teapot instead of a wire one. It seems to be missing lighting and texture.

2. As we add slices and stacks, the resolution of the sphere improves.

# 3.2.3 Viewport

1. 

```cpp
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
```

# 3.2.4 Keyboard

1. See `helloteapot.cpp`

# 4.2

1. When we decrease `fovy`, the object takes more space on the screen since the field of view is narrower.

2. When `zNear` is increased, if parts of the objet escape the clipping volume, it will not be rendered anymore.

# 4.2.2

1. Setting the up vector to (0, -1, 0) makes the camera look at the teapot upside down.

2.
(a)
```cpp
gluLookAt(
        0.0, 1.2,0.0,
        0.0,0.0,0.0,
        0.0001,-1.0,0.0
    ); 
```

(b)
```cpp
 gluLookAt(
        0.0, 0.0,-1.7,
        0.0,0.0,0.0,
        0.5,0.5,1.0
    ); 
```

# 4.3.2

1. When the second `glPopMatrix` is removed, as soon as the window is updated and a new frame is rendered, we see that the objects are not rendered in the same place as before. This is due to the stack not being restored at the return of the `display` function. The current matrix is the previous one so the operations are being accumulated.

2. Since we are making operations on state variables (matrix on the stack), if we set the camera after the second `glPushMatrix`, it will only be affected by the operations perfomed at its scope.

3. In this case, the current matrix gets set to the indentity matrix, and the transformations performed on the teapots don't start from the camera's position anymore, the scene we see is different.