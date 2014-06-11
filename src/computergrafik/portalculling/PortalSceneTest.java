package computergrafik.portalculling;

import static org.junit.Assert.*;

import org.junit.Test;

import computergrafik.framework.Vector3;

/**
 * JUnit test class for the portal scene
 * 
 * @author Philipp Jenke
 *
 */
public class PortalSceneTest {

	/**
	 * Create a simple scene for testing purposes.
	 */
	private PortalScene2D createDummyScene() {
		PortalScene2D scene = new PortalScene2D();
		scene.clear();
		scene.addNode(new Vector3(-0.5, 0, -0.5));
		scene.addNode(new Vector3(0.5, 0, -0.5));
		scene.addNode(new Vector3(0.5, 0, 0.5));
		scene.addNode(new Vector3(-0.5, 0, 0.5));
		PortalEdge e01 = new PortalEdge(PortalEdge.State.WALL, 0, 1);
		PortalEdge e12 = new PortalEdge(PortalEdge.State.WALL, 1, 2);
		PortalEdge e23 = new PortalEdge(PortalEdge.State.WALL, 2, 3);
		PortalEdge e30 = new PortalEdge(PortalEdge.State.WALL, 3, 0);
		PortalEdge e02 = new PortalEdge(PortalEdge.State.PORTAL, 0, 2);
		int e01Index = scene.addEdge(e01);
		int e12Index = scene.addEdge(e12);
		int e23Index = scene.addEdge(e23);
		int e30Index = scene.addEdge(e30);
		int e02Index = scene.addEdge(e02);
		scene.addCell(new PortalCell(e01Index, e12Index, e02Index));
		scene.addCell(new PortalCell(e02Index, e23Index, e30Index));
		return scene;
	}

	/**
	 * Test the PortalScene2D.getOppositeCell() method.
	 */
	@Test
	public void testGetOppositeCell() {
		PortalScene2D scene = createDummyScene();
		assertEquals(scene.getOppositeCell(0, 4), 1);
		assertEquals(scene.getOppositeCell(1, 4), 0);
	}

	/**
	 * Test the PortalScene2D.getStartCellIndex() method. This test implicitly
	 * used the PortalCell.cellContainsPoint() method.
	 */
	@Test
	public void testComputeStartCell() {
		PortalScene2D scene = createDummyScene();
		assertEquals(0, scene.getStartCellIndex(new Vector3(0, 0, -0.25)));
		assertEquals(1, scene.getStartCellIndex(new Vector3(0, 0, 0.25)));
	}

	/**
	 * Test the PortalCell.cellContainsPoint() method
	 */
	@Test
	public void testCellContainsPoint() {
		PortalScene2D scene = createDummyScene();
		assertEquals(true, scene.cellContainsPoint(scene.getCell(0),
				new Vector3(0, 0, -0.25)));
		assertEquals(false, scene.cellContainsPoint(scene.getCell(1),
				new Vector3(0, 0, -0.25)));
	}

	/**
	 * Test the Ray2D.intersect() method.
	 */
	@Test
	public void testIntersectRays() {
		Ray2D ray1 = new Ray2D(new Vector3(1, 0, 1), new Vector3(4, 0, 2));
		Ray2D ray2 = new Ray2D(new Vector3(2, 0, 3), new Vector3(1, 0, -1));
		assertEquals(0.5, ray1.intersect(ray2).lambda1, 1e-5);
		assertEquals(1, ray1.intersect(ray2).lambda2, 1e-5);

		Ray2D ray3 = new Ray2D(new Vector3(1, 0, 1), new Vector3(1, 0, 1));
		Ray2D ray4 = new Ray2D(new Vector3(2, 0, 3), new Vector3(1, 0, 1));
		assertEquals(null, ray3.intersect(ray4));
	}

	/**
	 * Test the PortalScene2D.intersect() method.
	 */
	@Test
	public void testIntersectViewVolumeEdge() {
		PortalScene2D scene = createDummyScene();
		ViewVolume2D viewVolume = new ViewVolume2D(new Vector3(0, 0, -0.25),
				new Vector3(-1, 0, 1), new Vector3(1, 0, 1));
		scene.intersect(scene.getEdge(4), viewVolume);
	}

	/**
	 * Test the ViewVolume2D.getAngle() method.
	 */
	@Test
	public void testViewVolumeGetAngle() {
		ViewVolume2D viewVolume1 = new ViewVolume2D(new Vector3(0, 0, 0), new Vector3(1, 0, 0), new Vector3(0, 0, 1));
		assertEquals(90, viewVolume1.getAngle());
		ViewVolume2D viewVolume2 = new ViewVolume2D(new Vector3(0, 0, 0), new Vector3(1, 0, 0), new Vector3(1, 0, 1));
		assertEquals(45, viewVolume2.getAngle());
		ViewVolume2D viewVolume3 = new ViewVolume2D(new Vector3(0, 0, 0), new Vector3(1, 0, 0), new Vector3(1, 0, 0));
		assertEquals(0, viewVolume3.getAngle());
	}
}
