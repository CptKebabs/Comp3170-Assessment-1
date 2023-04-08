package comp3170.ass1.sceneobjects;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import comp3170.GLBuffers;
import comp3170.SceneObject;
import comp3170.Shader;
import comp3170.ass1.Assignment1;
import comp3170.ass1.shaders.ShaderLibrary;

public class River extends SceneObject{
	
	//bezier curve
	static int NPOINTSRIVER = 200;
	static float RIVERWIDTH = 0.2f;
	Vector4f p0 = new Vector4f(1f, -1.5f, 1f, 1f);//endpoint
	Vector4f p1 = new Vector4f(-1f, -0.0f, 1f, 1f);//handle
	
	Vector4f p2 = new Vector4f(1f, 0.0f, 1f, 1f);//handle
	Vector4f p3 = new Vector4f(-1f, 1.5f, 1f, 1f);//endpoint
	Vector4f[] riverPoints;
	
	Vector4f riverColour = 		 new Vector4f(.01f,.01f,1f,1f);//blue
	Vector4f riverRippleColour = new Vector4f(.378f,.547f,.802f,1f);
	
	//
	Vector4f[] riverVertices;
	int riverVertexBuffer;
	int[] riverIndices;
	int riverIndexBuffer;
	
	private Shader riverShader;
	
	public River() {
		riverShader = ShaderLibrary.compileShader("riverVertex.glsl" , "riverFragment.glsl"); 
		riverPoints = new Vector4f[NPOINTSRIVER];
		
		for (int i = 0; i < NPOINTSRIVER; i++)  {
			float t = 1f * i / (NPOINTSRIVER-1);
			Vector4f point = new Vector4f();//final point that is added ie p(t)
			Vector4f temp = new Vector4f();//space to hold the multiplications of 1-t's and t's and add them together

			p0.mul((1-t) * (1-t) * (1-t), temp);//(1-t)^3*p0
			point.add(temp);
			p1.mul(3 * (1-t) * (1-t) * t, temp);//(1-t)^2*t*p1	
			point.add(temp);
			p2.mul(3 * (1-t) * t * t, temp);//(1-t)*t^2*p2
			point.add(temp);
			p3.mul(t * t * t, temp);//t^3*p3
			point.add(temp);
			riverPoints[i] = point;
		}
		
		int j = 0;
		riverVertices = new Vector4f[NPOINTSRIVER*2];
		for(int i = 0; i < NPOINTSRIVER; i++) {//                 base width        variable according to y value
			riverVertices[j++] = new Vector4f(riverPoints[i].x - ((RIVERWIDTH/2f) + (RIVERWIDTH/2f)*(1f-(i/(float)NPOINTSRIVER))),riverPoints[i].y,0f,1f);//point to the left
			riverVertices[j++] = new Vector4f(riverPoints[i].x + ((RIVERWIDTH/2f) + (RIVERWIDTH/2f)*(1f-(i/(float)NPOINTSRIVER))),riverPoints[i].y,0f,1f);//point to the right
		}
		riverVertexBuffer = GLBuffers.createBuffer(riverVertices);
		
		//Generate Indices
		riverIndices = new int[(NPOINTSRIVER-1)*2*3];//3 per triangle 2 points per river point
		j = 0;
		for(int i = 0; i < (NPOINTSRIVER-1)*2; i++) {// 0,1,2, 1,2,3, 2,3,4 ... 
			riverIndices[j++] = i;
			riverIndices[j++] = i+1;
			riverIndices[j++] = i+2;
		}
		riverIndexBuffer = GLBuffers.createIndexBuffer(riverIndices);
	}
	
	@Override
	protected void drawSelf(Matrix4f matrix) {		
		Vector2f screenSize = new Vector2f(Assignment1.width,Assignment1.height);
			
		
		riverShader.enable();
		riverShader.setUniform("u_mvpMatrix", matrix);
	    riverShader.setAttribute("a_position", riverVertexBuffer);	    
		riverShader.setUniform("u_waterColour", riverColour);
		riverShader.setUniform("u_rippleColour", riverRippleColour);
		riverShader.setUniform("u_time", Assignment1.wTimeState);
		//riverShader.setUniform("u_screenSize", screenSize);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, riverIndexBuffer);
	    glDrawElements(GL_TRIANGLES, riverIndices.length, GL_UNSIGNED_INT, 0);
	}
}
