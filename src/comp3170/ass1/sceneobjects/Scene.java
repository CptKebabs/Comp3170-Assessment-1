package comp3170.ass1.sceneobjects;

import java.util.ArrayList;

import org.joml.Matrix4f;

import comp3170.SceneObject;


public class Scene extends SceneObject {

	static ArrayList<Tree> trees;
	static final int numTrees = 1000;
	/**
	 * Construct the scene with this object as root.
	 */
	public Scene() {
		ArrayList<Tree> trees = new ArrayList<Tree>();
		for(int i = 0; i < numTrees; i++) {
			Tree tree = new Tree(); 
			Matrix4f treeMatrix = tree.getMatrix();
			treeMatrix.translate(random(-0.9f,0.9f), ((float)-1.8*i/numTrees)+0.9f, random(-0.9f,0.9f));//generated top to bottom to make lower trees render ontop
			treeMatrix.scale(0.05f);
			
			tree.setParent(this);
			trees.add(tree);
		}
		
		for(int i = 0; i < trees.size(); i++) {
			
		}
		//Axes worldAxes = new Axes();// Example: draw world axes (remove this from your final submission)
		//worldAxes.setParent(this);

	}
	
	/**
	 * Generate a random float between the specified minumum and maximum bounds
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static float random(float min, float max) {
		return (float) (Math.random() * (max - min) + min);
	}
	
	
}
