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
	public static int width = 1000;
	public static int height = 1000;
	private Scene scene;
	
	private long oldTime = 0;
	public static float wTimeState = 0f;

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
		long time = System.currentTimeMillis();
		float deltaTime = (float)(time - oldTime) / 1000f;	//I understand how to use this to make a framerate independent animation however whenever
		oldTime = time;									  	//I try to add deltaTime to my wTimeState variable, wTimeState becomes a very large constant 
		wTimeState += 0.016;						  	  	//1.68095386E9. I had this same problem in every implementation of this deltaTime system in 
		Scene.helicopterRotorBottomMatrix.rotateZ(3f*0.016f);	// earlier weeks. It works in the examples that are given. I'm 100% at error here
		Scene.helicopterRotorTopMatrix.rotateZ(3f*-0.016f);	// just put this here to clarify why I'm using a constant value instead
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
