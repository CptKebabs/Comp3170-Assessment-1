package comp3170.ass1;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import org.joml.Matrix4f;

import comp3170.IWindowListener;
import comp3170.OpenGLException;
import comp3170.Window;
import comp3170.ass1.sceneobjects.Scene;


public class Assignment1 implements IWindowListener {

	public static final float TAU = (float) (2 * Math.PI);		// https://tauday.com/tau-manifesto

	private Window window;
	private int width = 1000;
	private int height = 1000;
	private Scene scene;

	public Assignment1() throws OpenGLException {
		window = new Window("Assignment 1", width, height, this);
		window.run();
	}
	
	@Override
	public void init() {
		// configure GL
		glClearColor(47f/255f,120f/255f,5f/255f,1); // dark green 
		scene = new Scene();
	}

	private Matrix4f mvpMatrix = new Matrix4f();
	
	@Override
	public void draw() {
		glClear(GL_COLOR_BUFFER_BIT);
		scene.draw(mvpMatrix);
	}

	
	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void close() {
		
	}

	public static void main(String args[]) throws OpenGLException {
		new Assignment1();
	}
}
