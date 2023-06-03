package org.gui.lab7;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.Animator;
import org.gui.lab6.TextureHandler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import java.awt.*;

public class lab7
        extends JFrame
        implements GLEventListener,
        // These are the new interfaces one needs to implement
        KeyListener,
        MouseListener,
        MouseMotionListener{

    private GLU glu;
    private TextureHandler texture;
    float angle = 0.0f;

    public lab7(){
        super("Java OpenGL");

        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(800, 600);

        this.initializeJogl();

        this.setVisible(true);
    }

    private void initializeJogl(){
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


        // Adding the keyboard and mouse event listeners to the canvas.
        this.canvas.addKeyListener(this);
        this.canvas.addMouseListener(this);
        this.canvas.addMouseMotionListener(this);
    }

    public void init(GLAutoDrawable canvas){
        GL2 gl = canvas.getGL().getGL2();

        texture = new TextureHandler(gl, glu, "Texturi/texture1.jpg", true);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable){}

    public void display(GLAutoDrawable canvas){
        GL2 gl = canvas.getGL().getGL2();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);

        // Start with a fresh transformation i.e. the 4x4 identity matrix.
        gl.glLoadIdentity();

        // Save (push) the current matrix on the stack.
        gl.glPushMatrix();

        // Translate the first sphere to coordinates (0,0,0).
        gl.glTranslatef (0.0f, 0.0f, 0.0f);
        // Then draw it.
        this.drawSphere(gl, glu, 0.5f, false);

        // Save (push) on the stack the matrix holding the transformations produced by translating the first sphere.
        gl.glPushMatrix();

        // NOTE THE FOLLOWING ORDER OF OPERATIONS. THEY ACHIEVE A TRANSLATION FOLLOWED BY A ROTATION IN REALITY.

        // Rotate it with angle degrees around the X axis.
        gl.glRotatef (angle, 1, 0, 0);
        // Translate the second sphere to coordinates (10,0,0).
        gl.glTranslatef (10.0f, 0.0f, 0.0f);
        // Scale it to be half the size of the first one.
        gl.glScalef (0.5f, 0.5f, 0.5f);
        // Draw the second sphere.
        this.drawSphere(gl, glu, 0.5f, false);

        // Restore (pop) from the stack the matrix holding the transformations produced by translating the first sphere.
        gl.glPopMatrix();

        // Restore (pop) from the stack the matrix holding the transformations prior to our translation of the first sphere.
        gl.glPopMatrix();

        gl.glFlush();

        // Increase the angle of rotation by 5 degrees.
        angle += 5;
    }

    public void reshape(GLAutoDrawable canvas, int left, int top, int width, int height){
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


    private void drawSphere(GL gl, GLU glu, float radius, boolean texturing) {

        if (texturing) {
            this.texture.bind();
            this.texture.enable();
        }

        GLUquadric sphere = glu.gluNewQuadric();
        if (texturing) {
            // Enabling texturing on the quadric.
            glu.gluQuadricTexture(sphere, true);
        }
        glu.gluSphere(sphere, radius, 64, 64);
        glu.gluDeleteQuadric(sphere);
    }

    public void keyPressed(KeyEvent event) {
        return;
    }

    public void keyReleased(KeyEvent event) {
        return;
    }

    public void keyTyped(KeyEvent event) {
        return;
    }

    public void mousePressed(MouseEvent event) {
        return;
    }

    public void mouseReleased(MouseEvent event) {
        return;
    }

    public void mouseClicked(MouseEvent event) {
        return;
    }

    public void mouseMoved(MouseEvent event) {
        return;
    }

    public void mouseDragged(MouseEvent event) {
        return;
    }

    public void mouseEntered(MouseEvent event) {
        return;
    }

    public void mouseExited(MouseEvent event) {
        return;
    }

    private GLCanvas canvas;
    private Animator animator;
}