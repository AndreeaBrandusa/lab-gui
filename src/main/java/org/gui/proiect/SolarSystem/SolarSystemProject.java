package org.gui.proiect.SolarSystem;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import org.gui.lab6.TextureReader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static com.jogamp.opengl.GL.*;

public class SolarSystemProject extends JFrame implements GLEventListener {
    private GLCanvas canvas;
    private Animator animator;
    private GLU glu;

    private final int NO_TEXTURES = 5;
    private int texture[] = new int[NO_TEXTURES];
    TextureReader.Texture[] tex = new TextureReader.Texture[NO_TEXTURES];

    private final int canvasWidth = 800;
    private final int canvasHeight = 600;
    private final int numStars = 1000;

    private ArrayList<Float> coordArray;
    private ArrayList<SolarSystemObject> objArray;


    public SolarSystemProject() {
        super("Solar System");

        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(canvasWidth, canvasHeight);
        this.initializeJogl();
        this.setVisible(true);

        this.coordArray = new ArrayList<>();
        this.objArray = new ArrayList<>();
    }

    private void initializeJogl() {
        GLProfile glProfile = GLProfile.getDefault();
        GLCapabilities capabilities = new GLCapabilities(glProfile);

        capabilities.setHardwareAccelerated(true);
        capabilities.setDoubleBuffered(true);

        this.canvas = new GLCanvas();
        this.getContentPane().add(this.canvas);
        this.canvas.addGLEventListener(this);

        this.animator = new Animator();
        this.animator.add(this.canvas);
        this.animator.start();
    }

    public void init(GLAutoDrawable canvas) {
        GL2 gl = canvas.getGL().getGL2();
        glu = GLU.createGLU();

        generateStars();

        Sun sun = new Sun(new float[]{0.7f, 0.5f}, 0.05f, new float[]{255/255f, 210/255f, 0/255f}, tex[4]);
        objArray.add(sun);

        Planet mercury = new Planet(new float[]{0.65f, 0.585f}, 0.015f, new float[]{0.67f, 0.46f, 0.34f}, tex[2], 0.10f);
        objArray.add(mercury);
        Planet venus = new Planet(new float[]{0.81f, 0.4f}, 0.02f, new float[]{252/255f, 94/255f, 3/255f}, tex[1], 0.15f);
        objArray.add(venus);
        Planet earth = new Planet(new float[]{0.66f, 0.69f}, 0.025f, new float[]{3/255f, 115/255f, 252/255f}, tex[3], 0.20f);
        objArray.add(earth);
        Planet mars = new Planet(new float[]{0.62f, 0.26f}, 0.024f, new float[]{0.56f, 0.21f, 0.1f}, tex[2], 0.25f);
        objArray.add(mars);
        Planet jupiter = new Planet(new float[]{0.85f, 0.24f}, 0.035f, new float[]{0.52f, 0.32f, 0.07f}, tex[1], 0.30f);
        objArray.add(jupiter);
        Planet saturn = new Planet(new float[]{0.9f, 0.79f}, 0.031f, new float[]{0.5f, 0.41f, 0.3f}, tex[2], 0.35f);
        objArray.add(saturn);
        Planet uranus = new Planet(new float[]{0.39f, 0.25f}, 0.031f, new float[]{0.4f, 0.46f, 0.47f}, tex[1], 0.40f);
        objArray.add(uranus);
        Planet neptune = new Planet(new float[]{0.3f, 0.7f}, 0.04f, new float[]{46/255f, 42/255f, 156/255f}, tex[1], 0.45f);
        objArray.add(neptune);


        gl.glGenTextures(NO_TEXTURES, texture, 0);

        gl.glTexParameteri(GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);

        gl.glEnable(GL_TEXTURE_2D);
        for (int i = 0; i < NO_TEXTURES; i++) {
            gl.glBindTexture(GL.GL_TEXTURE_2D, texture[i]);

            try {
                tex[i] = TextureReader.readTexture(String.format("texturi/textura%s.jpg", i+1));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

            this.makeRGBTexture(gl, glu, tex[i], GL.GL_TEXTURE_2D, true);
        }

    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {}

    public void display(GLAutoDrawable canvas) {
        GL2 gl = canvas.getGL().getGL2();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);


        drawStars(gl);

        for (SolarSystemObject solarSystemObject : objArray) {
            solarSystemObject.drawSolarSystemObject(gl);
        }

/*
        drawSun(gl, 0.7f, 0.5f, 0.05f);

        drawCircle(gl, 0.7f, 0.5f, 0.10f);
        drawMercury(gl, 0.65f, 0.585f, 0.015f);

        drawCircle(gl, 0.7f, 0.5f, 0.15f);
        drawVenus(gl, 0.81f, 0.4f, 0.02f);

        drawCircle(gl, 0.7f, 0.5f, 0.20f);
        drawEarth(gl, 0.66f, 0.69f, 0.025f);

        drawCircle(gl, 0.7f, 0.5f, 0.25f);
        drawMars(gl, 0.62f, 0.26f, 0.024f);

        drawCircle(gl, 0.7f, 0.5f, 0.30f);
        drawJupier(gl, 0.85f, 0.24f, 0.035f);

        drawCircle(gl, 0.7f, 0.5f, 0.35f);
        drawSaturn(gl, 0.9f, 0.79f, 0.031f);

        drawCircle(gl, 0.7f, 0.5f, 0.40f);
        drawUranus(gl, 0.39f, 0.25f, 0.031f);

        drawCircle(gl, 0.7f, 0.5f, 0.45f);
        drawNeptune(gl, 0.3f, 0.7f, 0.04f);
 */

        gl.glDisable(GL2.GL_POINT_SMOOTH);
        gl.glDisable(GL.GL_BLEND);

        gl.glFlush();
    }

    public void reshape(GLAutoDrawable canvas, int left, int top, int width, int height) {
        GL2 gl = canvas.getGL().getGL2();

        // Select the viewport -- the display area -- to be the entire widget.
        gl.glViewport(0, 0, width, height);

        // Determine the width to height ratio of the widget.
        double ratio = (double) width / (double) height;

        // Select the Projection matrix.
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);

        gl.glLoadIdentity();

        // Select the view volume to be x in the range of 0 to 1, y from 0 to 1 and z from -1 to 1.
        // We are careful to keep the aspect ratio and enlarging the width or the height.
        if (ratio < 1)
            gl.glOrtho(0, 1, 0, 1 / ratio, -1, 1);
        else
            gl.glOrtho(0, 1 * ratio, 0, 1, -1, 1);

        // Return to the Modelview matrix.
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
    }


    private void makeRGBTexture(GL gl, GLU glu, TextureReader.Texture img, int target, boolean mipmapped) {
        if (mipmapped) {
            glu.gluBuild2DMipmaps(target, GL.GL_RGB8, img.getWidth(), img.getHeight(), GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img.getPixels());
        } else {
            gl.glTexImage2D(target, 0, GL.GL_RGB, img.getWidth(), img.getHeight(), 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img.getPixels());
        }
    }

/*
    private void drawCircle(GL2 gl, float xCenter, float yCenter, float radius) {
        double x, y, angle;

        gl.glColor3f(255f, 255f, 255f);
        gl.glBegin(GL2.GL_LINE_LOOP);
            for (int i = 0; i < 360; i++) {
                angle = Math.toRadians(i);
                x = radius * cos(angle);
                y = radius * sin(angle);
                gl.glVertex2d(xCenter + x, yCenter + y);
            }
        gl.glEnd();
    }

    void drawSun(GL2 gl, float xCenter, float yCenter, float radius) {
        int triangles = 360;

        gl.glColor3f(255/255f, 210/255f, 0/255f);
        gl.glEnable(GL_TEXTURE_2D);
        gl.glBindTexture(GL_TEXTURE_2D, texture[4]);
        gl.glBegin(GL_TRIANGLE_FAN);
            for (float i = 0; i <= triangles; i += 2.0) {
                float radian = (float) (i * (PI/180.0f));
                float xcos = (float) cos(radian);
                float ysin = (float) sin(radian);
                float x = xcos * radius  + xCenter;
                float y = ysin * radius  + yCenter;
                float tx = (float) (xcos * 0.5 + 0.5);
                float ty = (float) (ysin * 0.5 + 0.5);

                gl.glTexCoord2f(tx, ty);
                gl.glVertex2f(x, y);
            }
        gl.glEnd();
        gl.glDisable(GL_TEXTURE_2D);
    }

    void drawMercury(GL2 gl, float xCenter, float yCenter, float radius) {
        int triangles = 360;

        gl.glColor3f(0.67f, 0.46f, 0.34f);
        gl.glEnable(GL_TEXTURE_2D);
        gl.glBindTexture(GL_TEXTURE_2D, texture[2]);
        gl.glBegin(GL_TRIANGLE_FAN);
            for (float i = 0; i <= triangles; i += 2.0) {
                float radian = (float) (i * (PI/180.0f));
                float xcos = (float) cos(radian);
                float ysin = (float) sin(radian);
                float x = xcos * radius  + xCenter;
                float y = ysin * radius  + yCenter;
                float tx = (float) (xcos * 0.5 + 0.5);
                float ty = (float) (ysin * 0.5 + 0.5);

                gl.glTexCoord2f(tx, ty);
                gl.glVertex2f(x, y);
            }
        gl.glEnd();
        gl.glDisable(GL_TEXTURE_2D);
    }

    void drawVenus(GL2 gl, float xCenter, float yCenter, float radius) {
        int triangles = 360;

        gl.glColor3f(252/255f, 94/255f, 3/255f);
        gl.glEnable(GL_TEXTURE_2D);
        gl.glBindTexture(GL_TEXTURE_2D, texture[1]);
        gl.glBegin(GL_TRIANGLE_FAN);
        for (float i = 0; i <= triangles; i += 2.0) {
            float radian = (float) (i * (PI/180.0f));
            float xcos = (float) cos(radian);
            float ysin = (float) sin(radian);
            float x = xcos * radius  + xCenter;
            float y = ysin * radius  + yCenter;
            float tx = (float) (xcos * 0.5 + 0.5);
            float ty = (float) (ysin * 0.5 + 0.5);

            gl.glTexCoord2f(tx, ty);
            gl.glVertex2f(x, y);
        }
        gl.glEnd();
        gl.glDisable(GL_TEXTURE_2D);
    }

    void drawEarth(GL2 gl, float xCenter, float yCenter, float radius) {
        int triangles = 360;

        gl.glColor3f(3/255f, 115/255f, 252/255f);
        gl.glEnable(GL_TEXTURE_2D);
        gl.glBindTexture(GL_TEXTURE_2D, texture[3]);
        gl.glBegin(GL_TRIANGLE_FAN);
        for (float i = 0; i <= triangles; i += 2.0) {
            float radian = (float) (i * (PI/180.0f));
            float xcos = (float) cos(radian);
            float ysin = (float) sin(radian);
            float x = xcos * radius  + xCenter;
            float y = ysin * radius  + yCenter;
            float tx = (float) (xcos * 0.5 + 0.5);
            float ty = (float) (ysin * 0.5 + 0.5);

            gl.glTexCoord2f(tx, ty);
            gl.glVertex2f(x, y);
        }
        gl.glEnd();
        gl.glDisable(GL_TEXTURE_2D);
    }

    void drawMars(GL2 gl, float xCenter, float yCenter, float radius) {
        int triangles = 360;

        gl.glColor3f(0.56f, 0.21f, 0.1f);
        gl.glEnable(GL_TEXTURE_2D);
        gl.glBindTexture(GL_TEXTURE_2D, texture[2]);
        gl.glBegin(GL_TRIANGLE_FAN);
        for (float i = 0; i <= triangles; i += 2.0) {
            float radian = (float) (i * (PI/180.0f));
            float xcos = (float) cos(radian);
            float ysin = (float) sin(radian);
            float x = xcos * radius  + xCenter;
            float y = ysin * radius  + yCenter;
            float tx = (float) (xcos * 0.5 + 0.5);
            float ty = (float) (ysin * 0.5 + 0.5);

            gl.glTexCoord2f(tx, ty);
            gl.glVertex2f(x, y);
        }
        gl.glEnd();
        gl.glDisable(GL_TEXTURE_2D);
    }

    void drawJupier(GL2 gl, float xCenter, float yCenter, float radius) {
        int triangles = 360;

        gl.glColor3f(0.52f, 0.32f, 0.07f);
        gl.glEnable(GL_TEXTURE_2D);
        gl.glBindTexture(GL_TEXTURE_2D, texture[1]);
        gl.glBegin(GL_TRIANGLE_FAN);
        for (float i = 0; i <= triangles; i += 2.0) {
            float radian = (float) (i * (PI/180.0f));
            float xcos = (float) cos(radian);
            float ysin = (float) sin(radian);
            float x = xcos * radius  + xCenter;
            float y = ysin * radius  + yCenter;
            float tx = (float) (xcos * 0.5 + 0.5);
            float ty = (float) (ysin * 0.5 + 0.5);

            gl.glTexCoord2f(tx, ty);
            gl.glVertex2f(x, y);
        }
        gl.glEnd();
        gl.glDisable(GL_TEXTURE_2D);
    }

    void drawSaturn(GL2 gl, float xCenter, float yCenter, float radius) {
        int triangles = 360;

        gl.glColor3f(0.5f, 0.41f, 0.3f);
        gl.glEnable(GL_TEXTURE_2D);
        gl.glBindTexture(GL_TEXTURE_2D, texture[2]);
        gl.glBegin(GL_TRIANGLE_FAN);
        for (float i = 0; i <= triangles; i += 2.0) {
            float radian = (float) (i * (PI/180.0f));
            float xcos = (float) cos(radian);
            float ysin = (float) sin(radian);
            float x = xcos * radius  + xCenter;
            float y = ysin * radius  + yCenter;
            float tx = (float) (xcos * 0.5 + 0.5);
            float ty = (float) (ysin * 0.5 + 0.5);

            gl.glTexCoord2f(tx, ty);
            gl.glVertex2f(x, y);
        }
        gl.glEnd();
        gl.glDisable(GL_TEXTURE_2D);
    }

    void drawUranus(GL2 gl, float xCenter, float yCenter, float radius) {
        int triangles = 360;

        gl.glColor3f(0.4f, 0.46f, 0.47f);
        gl.glEnable(GL_TEXTURE_2D);
        gl.glBindTexture(GL_TEXTURE_2D, texture[1]);
        gl.glBegin(GL_TRIANGLE_FAN);
        for (float i = 0; i <= triangles; i += 2.0) {
            float radian = (float) (i * (PI/180.0f));
            float xcos = (float) cos(radian);
            float ysin = (float) sin(radian);
            float x = xcos * radius  + xCenter;
            float y = ysin * radius  + yCenter;
            float tx = (float) (xcos * 0.5 + 0.5);
            float ty = (float) (ysin * 0.5 + 0.5);

            gl.glTexCoord2f(tx, ty);
            gl.glVertex2f(x, y);
        }
        gl.glEnd();
        gl.glDisable(GL_TEXTURE_2D);
    }

    void drawNeptune(GL2 gl, float xCenter, float yCenter, float radius) {
        int triangles = 360;

        gl.glColor3f(46/255f, 42/255f, 156/255f);
        gl.glEnable(GL_TEXTURE_2D);
        gl.glBindTexture(GL_TEXTURE_2D, texture[1]);
        gl.glBegin(GL_TRIANGLE_FAN);
        for (float i = 0; i <= triangles; i += 2.0) {
            float radian = (float) (i * (PI/180.0f));
            float xcos = (float) cos(radian);
            float ysin = (float) sin(radian);
            float x = xcos * radius  + xCenter;
            float y = ysin * radius  + yCenter;
            float tx = (float) (xcos * 0.5 + 0.5);
            float ty = (float) (ysin * 0.5 + 0.5);

            gl.glTexCoord2f(tx, ty);
            gl.glVertex2f(x, y);
        }
        gl.glEnd();
        gl.glDisable(GL_TEXTURE_2D);
    }

*/

    private void drawStars(GL2 gl) {
        gl.glColor3f(255f, 255f, 255f);
        gl.glBegin(GL.GL_POINTS);
            for (int i = 0; i < coordArray.size(); i += 3) {
                gl.glPointSize(coordArray.get(i+2));
                gl.glLineWidth(coordArray.get(i+2));
                gl.glVertex2f(coordArray.get(i), coordArray.get(i+1));
            }
        gl.glEnd();
    }

    private float getRandomValue(float min, float max) {
        return (float) (Math.random() * (max - min)) + min;
    }

    private void generateStars() {
        for (int i = 0; i < numStars; i++) {
            float x = getRandomValue(0, canvasWidth/255);
            float y = getRandomValue(0, canvasHeight/255);
            float size = getRandomValue(1, 5);

            coordArray.add(x);
            coordArray.add(y);
            coordArray.add(size);
        }
    }
}