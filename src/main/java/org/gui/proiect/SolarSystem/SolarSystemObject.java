package org.gui.proiect.SolarSystem;

import com.jogamp.opengl.GL2;
import org.gui.lab6.TextureReader;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class SolarSystemObject {
    protected float[] objCoordinates;
    protected float objRadius;
    private float[] objColor;
    private TextureReader.Texture objTexture;

    public SolarSystemObject(float[] coordinates, float radius, float[] color, TextureReader.Texture texture) {
        this.objCoordinates = coordinates;
        this.objRadius = radius;
        this.objColor = color;
        this.objTexture = texture;
    }
    void drawSolarSystemObject(GL2 gl){
        throw new NotImplementedException();
    }
}
