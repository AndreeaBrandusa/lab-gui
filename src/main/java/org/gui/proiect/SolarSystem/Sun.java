package org.gui.proiect.SolarSystem;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import org.gui.lab6.TextureReader;

import static com.jogamp.opengl.GL.GL_TEXTURE_2D;
import static com.jogamp.opengl.GL.GL_TRIANGLE_FAN;
import static java.lang.Math.*;


public class Sun extends SolarSystemObject {
    private GLU glu;

    public Sun(float[] objCoordinates, float objRadius, float[] objColor, int objTextureId) {
        super(objCoordinates, objRadius, objColor, objTextureId);
        this.glu = new GLU();
    }

    @Override
    public void drawSolarSystemObject(GL2 gl) {
        gl.glColor3f(objColor[0], objColor[1], objColor[2]);
        gl.glEnable(GL_TEXTURE_2D);
        gl.glBindTexture(GL_TEXTURE_2D, objTextureId);

        GLUquadric sun = glu.gluNewQuadric ();
        glu.gluQuadricTexture(sun, true);
        glu.gluSphere (sun, objRadius, 32, 32);
        glu.gluDeleteQuadric (sun);

        gl.glDisable(GL_TEXTURE_2D);
    }
}
