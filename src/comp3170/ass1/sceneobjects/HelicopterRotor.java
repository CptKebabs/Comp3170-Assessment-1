package comp3170.ass1.sceneobjects;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import comp3170.GLBuffers;
import comp3170.SceneObject;
import comp3170.Shader;
import comp3170.ass1.shaders.ShaderLibrary;

public class HelicopterRotor extends SceneObject{
	
	Vector4f[] helicopterRotorVertices;
	int helicopterRotorVertexBuffer;
	int[] helicopterRotorIndices;
	int helicopterRotorIndexBuffer;
	
	float rotorRadius = 0.5f;
	
	Vector4f helicopterRotorColour = new Vector4f(1f,1f,1f,1f);//white
	
	Shader helicopterRotorShader;
	
	public HelicopterRotor() {
		
		helicopterRotorShader = ShaderLibrary.compileShader("simpleVertex.glsl", "simpleFragment.glsl");
		
		//generate vertices
		helicopterRotorVertices = new Vector4f[] {
				new Vector4f(0f,0f,0f,1f),
				new Vector4f((float)(rotorRadius*Math.cos(Math.toRadians(55))),(float)(rotorRadius*Math.sin(Math.toRadians(55))),0f,1f),
				new Vector4f((float)(rotorRadius*Math.cos(Math.toRadians(65))),(float)(rotorRadius*Math.sin(Math.toRadians(65))),0f,1f),
				new Vector4f((float)(rotorRadius*Math.cos(Math.toRadians(175))),(float)(rotorRadius*Math.sin(Math.toRadians(175))),0f,1f),
				new Vector4f((float)(rotorRadius*Math.cos(Math.toRadians(185))),(float)(rotorRadius*Math.sin(Math.toRadians(185))),0f,1f),
				new Vector4f((float)(rotorRadius*Math.cos(Math.toRadians(295))),(float)(rotorRadius*Math.sin(Math.toRadians(295))),0f,1f),
				new Vector4f((float)(rotorRadius*Math.cos(Math.toRadians(305))),(float)(rotorRadius*Math.sin(Math.toRadians(305))),0f,1f),
		};
		
		helicopterRotorVertexBuffer = GLBuffers.createBuffer(helicopterRotorVertices);
		
		//generate indices
		helicopterRotorIndices = new int[] {
				0,1,2,
				0,3,4,
				0,5,6,
		};
		
		helicopterRotorIndexBuffer = GLBuffers.createIndexBuffer(helicopterRotorIndices);
	}
	
	@Override
	protected void drawSelf(Matrix4f matrix) {
		helicopterRotorShader.enable();
		helicopterRotorShader.setUniform("u_mvpMatrix", matrix);
	    helicopterRotorShader.setAttribute("a_position", helicopterRotorVertexBuffer);	    
		helicopterRotorShader.setUniform("u_colour", helicopterRotorColour);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, helicopterRotorIndexBuffer);
	    glDrawElements(GL_TRIANGLES, helicopterRotorIndices.length, GL_UNSIGNED_INT, 0);
	}
}
