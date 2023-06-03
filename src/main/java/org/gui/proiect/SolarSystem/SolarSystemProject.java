package org.gui.proiect.SolarSystem;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
import org.gui.lab6.TextureReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import static com.jogamp.opengl.GL.*;

public class SolarSystemProject extends JFrame implements GLEventListener, KeyListener {
    private GLCanvas canvas;
    private Animator animator;
    private GLU glu;

    private final int NO_TEXTURES = 5;
    private int[] texture = new int[NO_TEXTURES];
    TextureReader.Texture[] tex = new TextureReader.Texture[NO_TEXTURES];

    private final int canvasWidth = 800;
    private final int canvasHeight = 600;
    private final int numStars = 1000;

    private final ArrayList<Float> coordArray;
    private final ArrayList<SolarSystemObject> objArray;

    private static final float ROTATION_SPEED = 2.5f;

    private float rotationX = 0.0f;
    private float rotationY = 0.0f;
    private float rotationZ = 0.0f;

    private float cameraX = 0.0f;
    private float cameraY = 0.0f;
    private float cameraZ = 5.0f;


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
        this.canvas.addKeyListener(this);
        this.canvas.requestFocus();

        this.glu = new GLU();

        this.animator = new Animator();
        this.animator.add(this.canvas);
        this.animator.start();
    }

    public void init(GLAutoDrawable canvas) {
        GL2 gl = canvas.getGL().getGL2();
        glu = GLU.createGLU();

        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LESS);

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

        generate();

        Sun sun = new Sun(new float[]{0.0f, 0.0f}, 0.17f, new float[]{255/255f, 210/255f, 0/255f}, texture[4]);
        objArray.add(sun);

        Planet mercury = new Planet(new float[]{-0.1f, 0.28f}, 0.05f, new float[]{0.67f, 0.46f, 0.34f}, texture[2], 0.3f);
        objArray.add(mercury);
        Planet venus = new Planet(new float[]{0.3f, -0.38f}, 0.075f, new float[]{252/255f, 94/255f, 3/255f}, texture[1], 0.5f);
        objArray.add(venus);
        Planet earth = new Planet(new float[]{-0.05f, 0.69f}, 0.1f, new float[]{3/255f, 115/255f, 252/255f}, texture[3], 0.7f);
        objArray.add(earth);
        Planet mars = new Planet(new float[]{-0.52f, -0.73f}, 0.09f, new float[]{0.56f, 0.21f, 0.1f}, texture[2], 0.9f);
        objArray.add(mars);
        Planet jupiter = new Planet(new float[]{0.2f, -1.09f}, 0.12f, new float[]{0.52f, 0.32f, 0.07f}, texture[1], 1.1f);
        objArray.add(jupiter);
        Planet saturn = new Planet(new float[]{0.8f, 1.02f}, 0.11f, new float[]{0.5f, 0.41f, 0.3f}, texture[2], 1.3f);
        objArray.add(saturn);
        Planet uranus = new Planet(new float[]{-1.47f, 0.25f}, 0.11f, new float[]{0.4f, 0.46f, 0.47f}, texture[1], 1.5f);
        objArray.add(uranus);
        Planet neptune = new Planet(new float[]{1.1f, -1.3f}, 0.15f, new float[]{46/255f, 42/255f, 156/255f}, texture[1], 1.7f);
        objArray.add(neptune);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {}

    public void display(GLAutoDrawable canvas) {
        GL2 gl = canvas.getGL().getGL2();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glTranslatef(0.0f, 0.0f, -cameraZ);
        gl.glRotatef(rotationX, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(rotationY, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(rotationZ, 0.0f, 0.0f, 1.0f);

        drawStars(gl);

        for (SolarSystemObject solarSystemObject : objArray) {
            solarSystemObject.drawSolarSystemObject(gl);
        }

        gl.glDisable(GL2.GL_POINT_SMOOTH);
        gl.glDisable(GL.GL_BLEND);

        gl.glFlush();
    }

    public void reshape(GLAutoDrawable canvas, int left, int top, int width, int height) {
        GL2 gl = canvas.getGL().getGL2();

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        float aspectRatio = (float) width / height;

        glu.gluPerspective(45.0, aspectRatio, 0.1, 100.0);
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    private void makeRGBTexture(GL gl, GLU glu, TextureReader.Texture img, int target, boolean mipmapped) {
        if (mipmapped) {
            glu.gluBuild2DMipmaps(target, GL.GL_RGB8, img.getWidth(), img.getHeight(), GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img.getPixels());
        } else {
            gl.glTexImage2D(target, 0, GL.GL_RGB, img.getWidth(), img.getHeight(), 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img.getPixels());
        }
    }

    private void drawStars(GL2 gl) {
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glBegin(GL.GL_POINTS);
            for (int i = 0; i < coordArray.size(); i += 4) {
                gl.glPointSize(coordArray.get(i+3));
                gl.glLineWidth(coordArray.get(i+3));
                gl.glVertex3f(coordArray.get(i), coordArray.get(i+1), coordArray.get(i+2));
            }
        gl.glEnd();
    }

    private float getRandomValue(float min, float max) {
        return (float) (Math.random() * (max - min)) + min;
    }

    private void generate() {
        for (int i = 0; i < numStars; i++) {
            float x = getRandomValue(-canvasWidth/255f, canvasWidth/255f);
            float y = getRandomValue(-canvasHeight/255f, canvasHeight/255f);
            float z = getRandomValue(-canvasHeight/255f, canvasHeight/255f);
            float size = getRandomValue(1, 5);

            coordArray.add(x);
            coordArray.add(y);
            coordArray.add(z);
            coordArray.add(size);
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                rotationX -= ROTATION_SPEED;
                break;
            case KeyEvent.VK_DOWN:
                rotationX += ROTATION_SPEED;
                break;
            case KeyEvent.VK_LEFT:
                rotationY -= ROTATION_SPEED;
                break;
            case KeyEvent.VK_RIGHT:
                rotationY += ROTATION_SPEED;
                break;
            case KeyEvent.VK_W:
                cameraZ -= 0.1f;
                break;
            case KeyEvent.VK_S:
                cameraZ += 0.1f;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}