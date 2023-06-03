package org.gui.lab8;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.Animator;
import org.gui.lab6.TextureHandler;

import javax.swing.*;
import java.awt.*;
import java.nio.FloatBuffer;

import static com.jogamp.opengl.GL.GL_TRIANGLES;
import static com.jogamp.opengl.GL2ES3.GL_QUADS;

public class lab8 extends JFrame implements GLEventListener{
    private GLU glu;

    public lab8(){
        super("Java OpenGL");

        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.initializeJogl();
        this.setVisible(true);
    }

    private void initializeJogl(){
        this.glu = new GLU();

        // Obtaining a reference to the default GL profile
        GLProfile glProfile = GLProfile.getDefault();
        // Creating an object to manipulate OpenGL parameters.
        GLCapabilities capabilities = new GLCapabilities(glProfile);

        // Setting some OpenGL parameters.
        capabilities.setHardwareAccelerated(true);
        capabilities.setDoubleBuffered(true);

        // Creating an OpenGL display widget -- canvas.
        this.canvas = new GLCanvas();

        // Adding the canvas in the center of the frame.
        this.getContentPane().add(this.canvas);

        // Adding an OpenGL event listener to the canvas.
        this.canvas.addGLEventListener(this);

        this.animator = new Animator();
        this.animator.add(this.canvas);
        this.animator.start();
    }

    @Override
    public void init(GLAutoDrawable canvas){
        GL2 gl = canvas.getGL().getGL2();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable){

    }

    @Override
    public void display(GLAutoDrawable canvas){
        GL2 gl = canvas.getGL().getGL2();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        // Disable operations on the Red, Greed, Blue and Alpha pixel values
        gl.glColorMask(false, false, false, false);

        // Draw a colored triangle here
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glBegin(GL_TRIANGLES);
            gl.glVertex2f(-0.7f, -0.5f);
            gl.glVertex2f(0.7f, -0.5f);
            gl.glVertex2f(0f, 0.7f);
        gl.glEnd();

        // Enable coloring
        gl.glColorMask(true, true, true, true);

        gl.glTranslated(10, 0, 0);

        // Draw a colored quad here
        gl.glColor3f(0.3f, 0.3f, 0.3f); // sets color to black.
        gl.glBegin(GL_QUADS);
            gl.glVertex2f(-0.25f, 0.25f); // vertex 1
            gl.glVertex2f(-0.5f, -0.25f); // vertex 2
            gl.glVertex2f(0.5f, -0.25f); // vertex 3
            gl.glVertex2f(0.25f, 0.25f); // vertex 4
        gl.glEnd();

        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable canvas, int left, int top, int width, int height){
        GL2 gl = canvas.getGL().getGL2();

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        double ratio = (double) width / (double) height;
        glu.gluPerspective (38, ratio, 0.1, 100);

        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    private GLCanvas canvas;
    private Animator animator;
}