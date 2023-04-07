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

public class Tree extends SceneObject{

		//LEAF
		private static final int NSIDES = 20;
		private static final float leafRadius = 0.4f;
		private static final float leafYPos = 0.6f;
		
		private Vector4f[] leafVertices;
		private int leafVertexBuffer;
		private int[] leafIndices;
		private int leafIndexBuffer;
		
		private Vector4f leafColour;//h 0.00 to 0.33/2 is rgb(255,0,0) to rgb(255,255,0)
									//h from 0.33/2 to 0.33 is rgb(255,255,0) to rgb(0,255,0)
									//so i am going to subdivide these two possibities and use random to decide which to use
									//this is all assuming 100% s (saturation) and 100% v (value)
		//WOOD
		private Vector4f[] woodVertices;
		private int woodVertexBuffer;
		private int[] woodIndices;
		private int woodIndexBuffer;
		private Vector4f woodColour = new Vector4f(.5f,.3f,.2f,1f);
		
		//SHADER
		final private String VERTEX_SHADER = "simpleVertex.glsl";
		final private String FRAGMENT_SHADER = "simpleFragment.glsl";
		private Shader shader;
	
		public Tree() {//constructor
			shader = ShaderLibrary.compileShader(VERTEX_SHADER, FRAGMENT_SHADER);
			
			//Random Colour between 0 and 0.33 hsv
			if(Math.random() > 0.5) {
				leafColour = new Vector4f(1.0f,(float)Math.random(),0.0f,0.0f); //h 0.00 to 0.33/2
			}else {
				leafColour = new Vector4f((float)Math.random(),1.0f,0.0f,0.0f); //h 0.33/2 to 0.33
			}
			
			//generate vertices and indices for leaf
			leafVertices = new Vector4f[NSIDES+1];//all corners of polygon plus center
			leafVertices[0] = new Vector4f(0,leafYPos/2,0,1);//center
			
			for(int i = 1; i < leafVertices.length; i++) {//for each except the central vertex
				float angleInRadians = (float)Math.toRadians(360f*((float)i/NSIDES));//
				float leafX = (float)(leafRadius*Math.cos(angleInRadians));
				float leafY = (float)(leafRadius*Math.sin(angleInRadians))+leafYPos;
				//System.out.println("X: " + leafX + " Y: " + leafY);
				leafVertices[i] = new Vector4f(leafX,leafY,0f,1f);
			}
			leafVertexBuffer = GLBuffers.createBuffer(leafVertices);
			
			leafIndices = new int[NSIDES*3];
			
		    int k = 0;
			for (int i = 1; i <= NSIDES; i++) {
				leafIndices[k++] = 0;//each triangle is built off the centre vertex
				leafIndices[k++] = i;
				leafIndices[k++] = (i % NSIDES) + 1;
			}
			
			leafIndexBuffer = GLBuffers.createIndexBuffer(leafIndices);
			
			
			//generate vertices and indices for wood
			woodVertices = new Vector4f[4];
			
			woodVertices[0] = new Vector4f(-.1f,leafYPos-leafRadius,0,1);//leafYPos-leafRadius = bottom of circle
			woodVertices[1] = new Vector4f(.1f,leafYPos-leafRadius,0,1);
			woodVertices[2] = new Vector4f(-.1f,leafYPos-leafRadius-0.2f,0,1);
			woodVertices[3] = new Vector4f(.1f,leafYPos-leafRadius-0.2f,0,1);
			
			woodVertexBuffer = GLBuffers.createBuffer(woodVertices);
			
			woodIndices = new int[]{0,1,2, 
									1,2,3};
			
			woodIndexBuffer = GLBuffers.createIndexBuffer(woodIndices);
			
		}
		
		protected void drawSelf(Matrix4f matrix) {//
			shader.enable();
			shader.setUniform("u_mvpMatrix", matrix);
			
			shader.setAttribute("a_position", leafVertexBuffer);	    
			shader.setUniform("u_colour", leafColour);	    
	
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, leafIndexBuffer);
		    glDrawElements(GL_TRIANGLES, leafIndices.length, GL_UNSIGNED_INT, 0);
		    
		    //render 
		    shader.setAttribute("a_position", woodVertexBuffer);	    
			shader.setUniform("u_colour", woodColour);	    
	
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, woodIndexBuffer);
		    glDrawElements(GL_TRIANGLES, woodIndices.length, GL_UNSIGNED_INT, 0);
		}
}
