package org.gui.proiect.SolarSystem;

import com.jogamp.opengl.GL2;
import org.gui.lab6.TextureReader;

import static java.lang.Math.*;


public class Sun extends SolarSystemObject {
    public Sun(float[] objCoordinates, float objRadius, float[] objColor, TextureReader.Texture objTexture) {
        super(objCoordinates, objRadius, objColor, objTexture);
    }

    @Override
    public void drawSolarSystemObject(GL2 gl) {
        int triangles = 360;

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
}
