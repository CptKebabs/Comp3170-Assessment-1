package comp3170.ass1.sceneobjects;

import java.util.ArrayList;

import org.joml.Matrix4f;

import comp3170.SceneObject;


public class Scene extends SceneObject {
	
	static River river;
	static HelicopterBody helicopterBody;
	static HelicopterRotor helicopterRotorTop;
	static HelicopterRotor helicopterRotorBottom;
	static ArrayList<Tree> trees;
	static final int numTrees = 1000;
	
	public static Matrix4f helicopterRotorTopMatrix;
	public static Matrix4f helicopterRotorBottomMatrix;
	public static Matrix4f helicopterBodyMatrix;
	/**
	 * Construct the scene with this object as root.
	 */
	public Scene() {
//		river = new River();
//		river.setParent(this);
		
		ArrayList<Tree> trees = new ArrayList<Tree>();
		for(int i = 0; i < numTrees; i++) {
			Tree tree = new Tree(); 
			Matrix4f treeMatrix = tree.getMatrix();
			treeMatrix.translate(random(-0.9f,0.9f), ((float)-1.8*i/numTrees)+0.9f, random(-0.9f,0.9f));//generated top to bottom to make lower trees render ontop
			treeMatrix.scale(0.05f);
			
			tree.setParent(this);
			trees.add(tree);
		}
		
		river = new River();
		river.setParent(this);
		
		helicopterBody = new HelicopterBody();
		helicopterBody.setParent(this);
		helicopterBodyMatrix = helicopterBody.getMatrix();
		helicopterBodyMatrix.scale(0.075f);
		
		helicopterRotorTop = new HelicopterRotor();
		helicopterRotorTop.setParent(helicopterBody);
		helicopterRotorTopMatrix = helicopterRotorTop.getMatrix();
		helicopterRotorTopMatrix.translate(-.55f,0f,0f);//move rotor to position on helicopter
		
		helicopterRotorBottom = new HelicopterRotor();
		helicopterRotorBottom.setParent(helicopterBody);
		helicopterRotorBottomMatrix = helicopterRotorBottom.getMatrix();
		helicopterRotorBottomMatrix.translate(.55f,0f,0f);//move rotor to position on helicopter
		
		//TODO TRANSFORM ROTORS
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
