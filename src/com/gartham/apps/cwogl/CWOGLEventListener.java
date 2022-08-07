package com.gartham.apps.cwogl;

import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Scanner;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

public final class CWOGLEventListener implements GLEventListener {

	public static String loadText(InputStream is) {
		Scanner s = new Scanner(is);
		StringBuilder sb = new StringBuilder();
		while (s.hasNextLine())
			sb.append(s.nextLine()).append('\n');
		s.close();
		return sb.toString();
	}

	private final static float[] CUBE = { -1.0f, -1.0f, -1.0f, // triangle 1 : begin
			-1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, // triangle 1 : end
			1.0f, 1.0f, -1.0f, // triangle 2 : begin
			-1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, // triangle 2 : end
			1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f,
			-1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f,
			1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
			-1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
			1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
			1.0f, -1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f };

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		drawable.getGL().glViewport(0, 0, 1000, 1000);

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL4 gl = drawable.getGL().getGL4();

		// Create shaders
		int vertshader = gl.glCreateShader(GL4.GL_VERTEX_SHADER),
				fragshader = gl.glCreateShader(GL4.GL_FRAGMENT_SHADER);
		gl.glShaderSource(vertshader, 1, new String[] { loadText(getClass().getResourceAsStream("vertexShader.vs")) },
				null);
		gl.glCompileShader(vertshader);
		gl.glShaderSource(fragshader, 1, new String[] { loadText(getClass().getResourceAsStream("fragmentShader.fs")) },
				null);
		gl.glCompileShader(fragshader);

		// Create GPU program
		int prog = gl.glCreateProgram();
		gl.glAttachShader(prog, vertshader);
		gl.glAttachShader(prog, fragshader);
		gl.glLinkProgram(prog);

		// VBO storing cube
		int bufferHandle;
		{
			IntBuffer b = IntBuffer.allocate(1);
			gl.glGenBuffers(1, b);
			bufferHandle = b.get(0);
		}

		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, bufferHandle);
		gl.glBufferData(GL.GL_ARRAY_BUFFER, CUBE.length * Float.BYTES, FloatBuffer.wrap(CUBE), GL.GL_STATIC_DRAW);

		// VAO storing interpretation of cube's buffer data.
		int vaoHandle;
		{
			IntBuffer b = IntBuffer.allocate(1);
			gl.glGenVertexArrays(1, b);
			vaoHandle = b.get(0);
		}

		gl.glUseProgram(prog);

		gl.glBindVertexArray(vaoHandle);
		gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		// Render cube

	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL4 gl = drawable.getGL().getGL4();
		gl.glDrawArrays(GL.GL_TRIANGLES, 0, CUBE.length / 3);
	}
}