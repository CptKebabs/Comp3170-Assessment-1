package comp3170.ass1.sceneobjects;

import comp3170.SceneObject;

public class Scene extends SceneObject {

	/**
	 * Construct the scene with this object as root.
	 */
	public Scene() {
		// Example: draw world axes (remove this from your final submission) 
		Axes worldAxes = new Axes();
		worldAxes.setParent(this);
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
