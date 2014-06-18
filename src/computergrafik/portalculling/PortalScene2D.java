package computergrafik.portalculling;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import computergrafik.framework.MathHelpers;
import computergrafik.framework.Vector3;

/**
 * Representation of a portal scene. The cells of the scene are represented as
 * triangles. The y-coordinates are assumed to be 0 (ignored).
 * 
 * @author Philipp Jenke
 *
 */
public class PortalScene2D {

	/**
	 * Edges of the scene.
	 */
	private List<PortalEdge> edges = new ArrayList<PortalEdge>();

	/**
	 * Triangle-cells in the scene.
	 */
	private List<PortalCell> cells = new ArrayList<PortalCell>();

	/**
	 * List of nodes (end points of the edges).
	 */
	private List<Vector3> nodes = new ArrayList<Vector3>();

	/**
	 * List of already visited cell indices during the pvs computation.
	 */
	private Set<Integer> visitedCells = new HashSet<Integer>();

	/**
	 * Constructor.
	 */
	public PortalScene2D() {
	}

	/**
	 * Compute the potentially visible (PVS) set for the scene. The PVS is
	 * represented as a set of cell indices.
	 */
	public Set<Integer> computePvs(ViewVolume2D viewVolume) {
		Set<Integer> pvs = new HashSet<Integer>();
		visitedCells.clear();
		pvs = traverse(getStartCellIndex(viewVolume.getOrigin()), viewVolume);
		return pvs;
	}

	/**
	 * Traverse a cell.
	 * 
	 * @param cellIndex
	 *            Index of the current cell.
	 * @param viewVolume
	 *            View-Volume which entered the cell.
	 * @return
	 */
	private Set<Integer> traverse(int cellIndex, ViewVolume2D viewVolume) {
		Set<Integer> pvs = new HashSet<Integer>();

		// Invalid cell number (e.g. the view origin is outside of the scene.
		if (cellIndex < 0) {
			return pvs;
		}

		pvs.add(cellIndex);
		visitedCells.add(cellIndex);
		for (int i = 0; i < 3; i++) {
			int edgeIndex = getCell(cellIndex).getEdgeIndex(i);
			PortalEdge edge = getEdge(edgeIndex);
			if (!edge.isWall()) {
				int nextCellIndex = getOppositeCell(cellIndex, edgeIndex);
				if (!visitedCells.contains(nextCellIndex)) {
					ViewVolume2D intersectedVolume = intersect(edge, viewVolume);
					if (intersectedVolume != null
							&& intersectedVolume.getAngle() > 0) {
						pvs.addAll(traverse(nextCellIndex, intersectedVolume));
					}
				}
			}
		}

		return pvs;
	}

	/**
	 * Getter
	 */
	public int getNumberOfEdges() {
		return edges.size();
	}

	/**
	 * Getter.
	 */
	public PortalEdge getEdge(int index) {
		return edges.get(index);
	}

	/**
	 * Getter.
	 */
	public Vector3 getNode(int index) {
		return nodes.get(index);
	}

	/**
	 * Return the index of cell on the other side of the edge. Return -1 if the
	 * other cell cannot be found.
	 */
	public int getOppositeCell(int cellIndex, int edge) {
		for (int otherCellIndex = 0; otherCellIndex < cells.size(); otherCellIndex++) {
			if (cellIndex != otherCellIndex) {
				if (cells.get(otherCellIndex).contains(edge)) {
					return otherCellIndex;
				}
			}
		}
		return 0;
	}

	/**
	 * Getter.
	 */
	public PortalCell getCell(int index) {
		return cells.get(index);
	}

	/**
	 * Find the cell which contains the origin point and return its index.
	 * Return -1 if the cell cannot be found.
	 */
	public int getStartCellIndex(Vector3 origin) {
		for (int cellIndex = 0; cellIndex < cells.size(); cellIndex++) {
			if (cellContainsPoint(cells.get(cellIndex), origin)) {
				return cellIndex;
			}
		}
		return -1;
	}

	/**
	 * Checks if a given point lies in the area of a given cell.
	 * 
	 * @param cell
	 *            Cell to be tested.
	 * @param p
	 *            Point to be tested.
	 * @returns True if the specified cell contains the given point, false
	 *          otherwise.
	 */
	public boolean cellContainsPoint(PortalCell cell, Vector3 p) {
        List<Vector3> vectorList = getCellNodes(cell);
        double area = vectorList.get(0).subtract(vectorList.get(1)).cross(vectorList.get(0).subtract(vectorList.get(2))).getNorm() / 2;
        double area1 = vectorList.get(1).subtract(p).cross(vectorList.get(2).subtract(p)).getNorm() / 2;
        double area2 = vectorList.get(2).subtract(p).cross(vectorList.get(0).subtract(p)).getNorm() / 2;
        double area3 = vectorList.get(0).subtract(p).cross(vectorList.get(1).subtract(p)).getNorm() / 2;

        double a = area1 / area;
        double b = area2 / area;
        double c = area3 / area;

		return Math.abs(a + b + c - 1) < MathHelpers.EPSILON;
	}

	/**
	 * Get the three nodes of a cell.
	 */
	public List<Vector3> getCellNodes(PortalCell cell) {
		Set<Vector3> cellNodes = new HashSet<Vector3>();
		for (int i = 0; i < 3; i++) {
			int edgeIndex = cell.getEdgeIndex(i);
			PortalEdge edge = edges.get(edgeIndex);
			cellNodes.add(nodes.get(edge.getStartNodeIndex()));
			cellNodes.add(nodes.get(edge.getEndNodeIndex()));
		}
		if (cellNodes.size() != 3) {
			System.out.println("Cell does not have three nodes - invalid.");
		}
		List<Vector3> cellNodesList = new ArrayList<Vector3>(cellNodes);
		return cellNodesList;
	}

	/**
	 * Compute the resulting viewing volume after for the intersection between
	 * an edge and the given viewing volume.
	 * 
	 * Algorithm: Compute the intersection between the two boundary rays with the
	 * edge. Therefore the edge is also interpreted as a ray. The array 
	 * 
	 * @param edge
	 *            The view volume is intersected with this edge.
	 * @param viewVolume
	 *            The edge is intersected with this view volume.
	 * 
	 * @return viewing volume after the intersection. null if the viewing volume
	 *         is empty.
	 */
	public ViewVolume2D intersect(PortalEdge edge, ViewVolume2D viewVolume) {
		Ray2D rayLeft = new Ray2D(viewVolume.getOrigin(),
				viewVolume.getLeftBoundary());
		Ray2D rayRight = new Ray2D(viewVolume.getOrigin(),
				viewVolume.getRightBoundary());
		Ray2D rayEdge = new Ray2D(nodes.get(edge.getStartNodeIndex()), nodes
				.get(edge.getEndNodeIndex()).subtract(
						nodes.get(edge.getStartNodeIndex())));

		Ray2D.IntersectionResult resultLeft = rayLeft.intersect(rayEdge);
		Ray2D.IntersectionResult resultRight = rayRight.intersect(rayEdge);

		// Special case: back facing
		if (resultLeft != null && resultRight != null && resultLeft.lambda1 < 0
				&& resultRight.lambda1 < 0) {
			return null;
		}

		// Adjust boundary left if parallel
		double lambdaLeft;
		if (resultLeft == null) {
			lambdaLeft = (rayLeft.getDirection().multiply(
					rayEdge.getDirection()) > 0) ? 1 : 0;
		} else if (resultLeft.lambda1 < 0) {
			// Special case: ray left intersects with negative lambda
			lambdaLeft = (rayLeft.getDirection().multiply(
					rayEdge.getDirection()) > 0) ? Double.POSITIVE_INFINITY
					: Double.NEGATIVE_INFINITY;
		} else {
			lambdaLeft = resultLeft.lambda2;
		}

		// Adjust boundary right if parallel
		double lambdaRight;
		if (resultRight == null) {
			lambdaRight = (rayRight.getDirection().multiply(
					rayEdge.getDirection()) > 0) ? 1 : 0;
		} else if (resultRight.lambda1 < 0) {
			// Special case: ray right intersects with negative lambda
			lambdaRight = (rayRight.getDirection().multiply(
					rayEdge.getDirection()) > 0) ? Double.POSITIVE_INFINITY
					: Double.NEGATIVE_INFINITY;
		} else {
			lambdaRight = resultRight.lambda2;
		}

		// Clamp left and right boundary end point position.
		lambdaLeft = Math.min(Math.max(lambdaLeft, 0), 1);
		lambdaRight = Math.min(Math.max(lambdaRight, 0), 1);

		ViewVolume2D result = new ViewVolume2D(viewVolume.getOrigin(), rayEdge
				.eval(lambdaLeft).subtract(viewVolume.getOrigin()), rayEdge
				.eval(lambdaRight).subtract(viewVolume.getOrigin()));
		return result;
	}

	/**
	 * Clear all content.
	 */
	public void clear() {
		edges.clear();
		nodes.clear();
		cells.clear();
	}

	/**
	 * Add a node to the scene.
	 */
	public void addNode(Vector3 node) {
		nodes.add(node);
	}

	/**
	 * Add an edge to the scene. Return the index of the edge in the edge list.
	 */
	public int addEdge(PortalEdge edge) {
		edges.add(edge);
		return edges.size() - 1;
	}

	/**
	 * Add cell to scene
	 */
	public void addCell(PortalCell cell) {
		cells.add(cell);
	}

	/**
	 * Getter.
	 */
	public int getNumberOfNodes() {
		return nodes.size();
	}

	/**
	 * Getter.
	 */
	public int getNumberOfCells() {
		return cells.size();
	}

    public List<PortalCell> getCells() {
        return cells;
    }
}
