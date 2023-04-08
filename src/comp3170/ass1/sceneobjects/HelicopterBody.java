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

public class HelicopterBody extends SceneObject{
	Vector4f[] helicopterVertices;
	int helicopterVertexBuffer;
	int[] helicopterIndices;
	int helicopterIndexBuffer;
	
	Vector4f helicopterColour = new Vector4f(0f,0f,0f,1f);//black
	
	Shader helicopterShader;

	public HelicopterBody() {
		
		helicopterShader = ShaderLibrary.compileShader("simpleVertex.glsl","simpleFragment.glsl");
		
		//generate vertices
		helicopterVertices = new Vector4f[] {
				new Vector4f(-0.55f,0.3f,0f,1f),
				new Vector4f(0.55f,0.3f,0f,1f),
				new Vector4f(-0.9f,0f,0f,1f),
				new Vector4f(0.9f,0f,0f,1f),
				new Vector4f(-0.55f,-0.3f,0f,1f),
				new Vector4f(0.55f,-0.3f,0f,1f),
		};
		helicopterVertexBuffer = GLBuffers.createBuffer(helicopterVertices);
		
		//generate indices
		helicopterIndices = new int[] {
				0,2,4,
				0,1,4,
				1,4,5,
				1,3,5,
		};
		helicopterIndexBuffer = GLBuffers.createIndexBuffer(helicopterIndices);
		
	}
	
	@Override
	protected void drawSelf(Matrix4f matrix) {
		helicopterShader.enable();
		helicopterShader.setUniform("u_mvpMatrix", matrix);
	    helicopterShader.setAttribute("a_position", helicopterVertexBuffer);	    
		helicopterShader.setUniform("u_colour", helicopterColour);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, helicopterIndexBuffer);
	    glDrawElements(GL_TRIANGLES, helicopterIndices.length, GL_UNSIGNED_INT, 0);
	}
}
