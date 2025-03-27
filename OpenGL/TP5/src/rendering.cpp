/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

#include "rendering.hpp"
#include "core.hpp"
#include "geometry.hpp"
#include <GL/gl.h>
#include <GL/glu.h>

/**
 * Draw the wireframe of the model
 *
 * @param vertices The list of vertices
 * @param mesh The mesh as a list of faces, each face is a tripleIndex of vertex indices
 * @param params The rendering parameters
 */
void drawWireframe(const std::vector<point3d>& vertices,
                   const std::vector<face>& mesh,
                   const RenderingParameters& params)
{
    //**************************************************
    // we first need to disable the lighting in order to
    // draw colored segments
    //**************************************************
    glDisable(GL_LIGHTING);

    // if we are displaying the object with colored faces
    if ( params.solid )
    {
        // use black ticker lines
        glColor3f( .0f, .0f, .0f );
        glLineWidth( 2.f );
    }
    else
    {
        // otherwise use white thinner lines for wireframe only
        glColor3f( .8f, .8f, .8f );
        glLineWidth( .21f );
    }

    //**************************************************
    // for each face of the mesh...
    //**************************************************
    for (face f: mesh)
    {
        //**************************************************
        // draw the contour of the face as a  GL_LINE_LOOP
        //**************************************************
        glBegin(GL_LINE_LOOP);
            glVertex3f(vertices[f.v1].x, vertices[f.v1].y, vertices[f.v1].z);
            glVertex3f(vertices[f.v2].x, vertices[f.v2].y, vertices[f.v2].z);
            glVertex3f(vertices[f.v3].x, vertices[f.v3].y, vertices[f.v3].z);
        glEnd();

    }

    //**************************************************
    // re-enable the lighting
    //**************************************************
    glEnable( GL_LIGHTING );
}

/**
 * Draw the faces of the model according to the type of shading specified in the parameters
 * @param[in] vertices The list of vertices
 * @param[in] mesh The list of face, each face containing the indices of the vertices
 * @param[in] vertexNormals The list of normals associated to each vertex
 * @param[in] params If smooth is true, the model is drawn with smooth shading, otherwise with flat shading
 */
void drawFaces(const std::vector<point3d>& vertices,
                   const std::vector<face>& mesh,
                   const std::vector<vec3d>& vertexNormals,
                   const RenderingParameters& params)
{
// shading model to use
   if(!params.smooth)
   {
       glShadeModel(GL_FLAT);



   }
   else
   {
       glShadeModel(GL_SMOOTH);


   }

    glDisable(GL_LIGHTING);



       //**************************************************
       // for each face
       //**************************************************
    for (face t : mesh) {
           //**************************************************
           // Compute the normal to the face and then draw the
           // faces as GL_TRIANGLES assigning the proper normal
           //**************************************************

        vec3d n = computeNormal(vertices[t.v1],vertices[t.v2], vertices[t.v3]);
        glBegin(GL_TRIANGLES);

            glNormal3fv((float*) &n);
            glVertex3f(vertices[t.v1].x, vertices[t.v1].y, vertices[t.v1].z);
            glVertex3f(vertices[t.v2].x, vertices[t.v2].y, vertices[t.v2].z);
            glVertex3f(vertices[t.v3].x, vertices[t.v3].y, vertices[t.v3].z);
        glEnd();
       }

  // get the modelview matrix
    GLfloat modelview[16];
    glGetFloatv(GL_MODELVIEW_MATRIX, modelview);


    for (face face: mesh) {
        //**************************************************
        // Compute the normal to the face and then draw the
        // faces as GL_TRIANGLES assigning the proper normal
        //**************************************************
        glBegin( GL_TRIANGLES );

            const auto n1 = vertexNormals[face.v1];

            // transform the normal to the eye space
            vec3d n = vec3d(modelview[0] * n1.x + modelview[4] * n1.y + modelview[8] * n1.z,
                            modelview[1] * n1.x + modelview[5] * n1.y + modelview[9] * n1.z,
                            modelview[2] * n1.x + modelview[6] * n1.y + modelview[10] * n1.z);

            n.normalize();
            // change the values of the normal to be in the range [0, 1]
            n = (n + 1) / 2;

            glColor3f(n.x, n.y, n.z);
            glVertex3fv( (float*) &vertices[face.v1] );

                const auto n2 = vertexNormals[face.v2];

                // transform the normal to the eye space
                n = vec3d(modelview[0] * n2.x + modelview[4] * n2.y + modelview[8] * n2.z,
                            modelview[1] * n2.x + modelview[5] * n2.y + modelview[9] * n2.z,
                            modelview[2] * n2.x + modelview[6] * n2.y + modelview[10] * n2.z);

                n.normalize();
                // change the values of the normal to be in the range [0, 1]
                n = (n + 1) / 2;
                glColor3f(n.x, n.y, n.z);

            glVertex3fv( (float*) &vertices[face.v2] );

            const auto n3 = vertexNormals[face.v3];

                // transform the normal to the eye space
                n = vec3d(modelview[0] * n3.x + modelview[4] * n3.y + modelview[8] * n3.z,
                            modelview[1] * n3.x + modelview[5] * n3.y + modelview[9] * n3.z,
                            modelview[2] * n3.x + modelview[6] * n3.y + modelview[10] * n3.z);
                n = (n + 1) / 2;
                glColor3f(n.x, n.y, n.z);

            glVertex3fv( (float*) &vertices[face.v3] );
        glEnd( );
    }
  
     glEnable( GL_LIGHTING );

}



/**
 * Draw the model using the vertex indices
 *
 * @param vertices The vertices
 * @param indices The list of the faces, each face containing the 3 indices of the vertices
 * @param vertexNormals The list of normals associated to each vertex
 * @param params The rendering parameters
 */
void drawArrayFaces(const std::vector<point3d>& vertices,
                     const std::vector<face>& indices,
                     const std::vector<vec3d>& vertexNormals,
                     const RenderingParameters& params)
{
    if(params.smooth)
    {
        glShadeModel(GL_SMOOTH);
    }
    else
    {
        glShadeModel(GL_FLAT);
    }
    //****************************************
    // Enable vertex arrays
    //****************************************
    glEnableClientState(GL_VERTEX_ARRAY);   

    //****************************************
    // Enable normal arrays
    //****************************************
    glEnableClientState(GL_NORMAL_ARRAY);   


    //****************************************
    // Normal pointer to normal array
    //****************************************
    glNormalPointer(GL_FLOAT, 0, &vertexNormals);

    //****************************************
    // Vertex pointer to Vertex array
    //****************************************
    glVertexPointer(3,GL_FLOAT, 0, &vertices);


    //****************************************
    // Draw the faces
    //****************************************
    glDrawElements(GL_TRIANGLES, vertices.size()/3, GL_FLOAT, &indices);

    //****************************************
    // Disable vertex arrays
    //****************************************
    glDisableClientState(GL_VERTEX_ARRAY);

    //****************************************
    // Disable normal arrays
    //****************************************
    glDisableClientState(GL_NORMAL_ARRAY);

}



//////////////////////////////////////// Nothing to do after this /////////////////////////////////

void drawNormals(const std::vector<point3d>& vertices, const std::vector<vec3d>& vertexNormals)
{
    glDisable(GL_LIGHTING);

    glColor3f(.8f, .0f, .0f);
    glLineWidth(2);

    for(std::size_t i = 0; i < vertices.size(); ++i)
    {
        glBegin(GL_LINES);

        const auto v = vertices[i];
        const auto n = vertexNormals[i];

        vec3d newP = v + 0.05f * n;
        glVertex3fv((float*)&v);

        glVertex3f(newP.x, newP.y, newP.z);

        glEnd();
    }
    glEnable(GL_LIGHTING);
}

void drawSolid(const std::vector<point3d>& vertices,
               const std::vector<face>& indices,
               std::vector<vec3d>& vertexNormals,
               const RenderingParameters& params)
{
    if(params.useIndexRendering)
    {
        drawArrayFaces(vertices, indices, vertexNormals, params);
    }
    else
    {
        drawFaces(vertices, indices, vertexNormals, params);
    }
}

/**
 * Draw the model
 *
 * @param vertices list of vertices
 * @param indices list of faces
 * @param vertexNormals list of normals
 * @param params Rendering parameters
 */
void draw( const std::vector<point3d> &vertices, const std::vector<face> &indices, std::vector<vec3d> &vertexNormals, const RenderingParameters &params )
{
    if ( params.solid )
    {
        drawSolid( vertices, indices, vertexNormals, params );
    }
    if ( params.wireframe )
    {
        ::drawWireframe( vertices, indices, params );
    }
}