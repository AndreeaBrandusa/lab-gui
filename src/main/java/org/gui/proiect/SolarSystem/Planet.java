package org.gui.proiect.SolarSystem;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import org.gui.lab6.TextureReader;

import static com.jogamp.opengl.GL.GL_TEXTURE_2D;
import static com.jogamp.opengl.GL.GL_TRIANGLE_FAN;
import static java.lang.Math.*;


public class Planet extends SolarSystemObject {
    private final float[] axisCoordinates = {0.0f, 0.0f};
    private float axisRadius;
    private GLU glu;

    public Planet(float[] objCoordinates, float objRadius, float[] objColor, int objTextureId, float axisRadius) {
        super(objCoordinates, objRadius, objColor, objTextureId);
        this.axisRadius = axisRadius;
        this.glu = new GLU();
    }

    @Override
    public void drawSolarSystemObject(GL2 gl) {
        drawAxis(gl);

        gl.glColor3f(objColor[0], objColor[1], objColor[2]);
        gl.glEnable(GL_TEXTURE_2D);
        gl.glBindTexture(GL_TEXTURE_2D, objTextureId);
        gl.glPushMatrix();
        gl.glTranslatef(objCoordinates[0], objCoordinates[1], 0.0f);

        GLUquadric planet = glu.gluNewQuadric ();
        glu.gluQuadricTexture(planet, true);
        glu.gluSphere (planet, objRadius, 32, 32);
        glu.gluDeleteQuadric (planet);

        gl.glPopMatrix();
        gl.glDisable(GL_TEXTURE_2D);
    }

    public void drawAxis(GL2 gl) {
        double x, y, angle;

        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glBegin(GL2.GL_LINE_LOOP);
            for (int i = 0; i < 360; i++) {
                angle = Math.toRadians(i);
                x = axisRadius * cos(angle);
                y = axisRadius * sin(angle);
                gl.glVertex2d(axisCoordinates[0] + x, axisCoordinates[1] + y);
            }
        gl.glEnd();
    }
}
