package com.gartham.apps.cwogl;

import javax.swing.JFrame;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class CWOGLTemplate {
	private static final int DEFAULT_WIDTH = 1920, DEFAULT_HEIGHT = 1080;

	public static void main(String[] args) {
		GLProfile prof = GLProfile.get(GLProfile.GL4);
		GLCapabilities capabilities = new GLCapabilities(prof);
		// TODO Request GL instance capabilities here.

		GLCanvas canvas = new GLCanvas(capabilities);
		canvas.addGLEventListener(new CWOGLEventListener());// Performs rendering.

		JFrame window = new JFrame();
		window.setTitle("[WINDOW TITLE]");
		window.getContentPane().add(canvas);

		window.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setResizable(false);
		canvas.requestFocusInWindow();

		// Include an FPS-based animator, if desired.
		// Without this, the GLCanvas's EventListener's #display(GLAutoDrawable) method
		// is only called a set number of times.
		new FPSAnimator(canvas, 1, true).start();
	}
}
