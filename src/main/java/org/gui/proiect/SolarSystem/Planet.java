package org.gui.proiect.SolarSystem;

import com.jogamp.opengl.GL2;
import org.gui.lab6.TextureReader;

import static java.lang.Math.*;


public class Planet extends SolarSystemObject {
    private final float[] axisCoordinates = {0.7f, 0.5f};
    private float axisRadius;

    public Planet(float[] objCoordinates, float objRadius, float[] objColor, TextureReader.Texture objTexture, float axisRadius) {
        super(objCoordinates, objRadius, objColor, objTexture);
        this.axisRadius = axisRadius;
    }

    @Override
    public void drawSolarSystemObject(GL2 gl) {
        int triangles = 360;

        drawAxis(gl);

        for (float i = 0; i <= triangles; i += 2.0) {
            float radian = (float) (i * (PI/180.0f));
            float xcos = (float) cos(radian);
            float ysin = (float) sin(radian);
            float x = xcos * objRadius + objCoordinates[0];
            float y = ysin * objRadius + objCoordinates[1];
            float tx = (float) (xcos * 0.5 + 0.5);
            float ty = (float) (ysin * 0.5 + 0.5);

            gl.glTexCoord2f(tx, ty);
            gl.glVertex2f(x, y);
        }
        gl.glEnd();
    }

    public void drawAxis(GL2 gl) {
        double x, y, angle;

        gl.glColor3f(255f, 255f, 255f);
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
