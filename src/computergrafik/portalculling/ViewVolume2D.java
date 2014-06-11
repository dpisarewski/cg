package computergrafik.portalculling;

import computergrafik.framework.Vector3;

/**
 * Representation of a viewing volume in 2D. The volume is bounded by an origin
 * point plus a left and right boundary vector. The vector data types are 3d,
 * therefore the y-coordinate is ignored (set to 0).
 * 
 * @author Philipp Jenke
 *
 */
public class ViewVolume2D {

	/**
	 * Origin of the viewing volume.
	 */
	private Vector3 origin;

	/**
	 * Left boundary vector of the volume.
	 */
	private Vector3 leftBoundary;

	/**
	 * Right boundary vector of the volume.
	 */
	private Vector3 rightBoundary;

	/**
	 * Constructor.
	 */
	public ViewVolume2D(Vector3 origin, Vector3 leftBoundary,
			Vector3 rightBoundary) {
		this.origin = origin;
		this.leftBoundary = leftBoundary;
		this.rightBoundary = rightBoundary;
	}

	/**
	 * Getter.
	 */
	public Vector3 getOrigin() {
		return origin;
	}

	/**
	 * Getter.
	 */
	public Vector3 getLeftBoundary() {
		return leftBoundary;
	}

	/**
	 * Getter.
	 */
	public Vector3 getRightBoundary() {
		return rightBoundary;
	}

	/**
	 * Return the angle between the two boundaries in degrees. The angle is
	 * assumed to be smaller than 180 degrees.
	 * 
	 * @return Angle between the two boundaries in degrees (not radiens).
	 */
	public int getAngle() {
		return leftBoundary.getAngle(rightBoundary);
	}
}
