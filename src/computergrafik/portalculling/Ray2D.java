package computergrafik.portalculling;

import computergrafik.framework.Vector3;

/**
 * A 2D ray represented by a start point and a direction vector. The y
 * coordinate is ignored.
 * 
 * @author Philipp Jenke
 *
 */
public class Ray2D {
	/**
	 * Starting point of the ray.
	 */
	private Vector3 p;

	/**
	 * Direction vector of the ray.
	 */
	private Vector3 direction;

	/**
	 * Constructor.
	 */
	public Ray2D(Vector3 p, Vector3 direction) {
		this.p = p;
		this.direction = direction;
	}

	/**
	 * Getter.
	 */
	public Vector3 getDirection() {
		return direction;
	}

	/**
	 * Evaluate the ray, compute the position
	 */
	public Vector3 eval(double lambda) {
		return p.add(direction.multiply(lambda));
	}

	/**
	 * Inner class to represent the intersection result.
	 */
	public class IntersectionResult {
		public double lambda1;
		public double lambda2;

		public IntersectionResult(double lambda1, double lambda2) {
			this.lambda1 = lambda1;
			this.lambda2 = lambda2;
		}
	}

	/**
	 * Compute the intersection point of two rays.
	 * 
	 * @param other
	 *            Other ray used in the computation (together with this-object).
	 * 
	 * @return Parameter values lambda for the this-ray (lambda1) and the other
	 *         ray (lamda2) at the intersection point. If the rays do not
	 *         intersect, the method return null.
	 */
	public IntersectionResult intersect(Ray2D other) {

		return null;
	}
}
