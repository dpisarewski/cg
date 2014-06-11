package computergrafik.portalculling;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import computergrafik.framework.Vector3;

/**
 * Importer for portal scenes 2D file format.
 * 
 * @author Philipp Jenke
 *
 */
public class PortalSceneImporter {

	/**
	 * The numbers of components in the scene.
	 */
	private int numberOfNodes = -1;
	private int numberOfEdges = -1;
	private int numberOfCells = -1;

	/**
	 * Import scene from file.
	 */
	public void importScene(PortalScene2D scene, String filename) {

		try {
			InputStream is = new FileInputStream(filename);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(is);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			// Read the component numbers
			numberOfNodes = Integer.parseInt(readNextNonCommentLine(br));
			numberOfEdges = Integer.parseInt(readNextNonCommentLine(br));
			numberOfCells = Integer.parseInt(readNextNonCommentLine(br));

			scene.clear();
			for (int i = 0; i < numberOfNodes; i++) {
				scene.addNode(readNode(br));
			}
			for (int i = 0; i < numberOfEdges; i++) {
				scene.addEdge(readEdge(br));
			}
			for (int i = 0; i < numberOfCells; i++) {
				scene.addCell(readCell(scene, br));
			}
		} catch (IOException e) {
			System.out.println("Failed to open file " + filename);
		}

	}

	/**
	 * Read the information about a cell from a line from the input file.
	 */
	private PortalCell readCell(PortalScene2D scene, BufferedReader br)
			throws IOException {
		String line = readNextNonCommentLine(br);
		String[] tokens = line.split("\\s+");
		PortalCell cell = new PortalCell(Integer.parseInt(tokens[0]),
				Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
		return cell;
	}

	/**
	 * Read the information about an edge from a line from the input file.
	 */
	private PortalEdge readEdge(BufferedReader br) throws IOException {
		String line = readNextNonCommentLine(br);
		String[] tokens = line.split("\\s+");
		try {
			PortalEdge.State state = PortalEdge.State.valueOf(tokens[2]);
			PortalEdge edge = new PortalEdge(state,
					Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
			return edge;
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid state identifier: " + tokens[2]);
			return null;
		}
	}

	/**
	 * Read the information about a node from a line from the input file.
	 */
	private Vector3 readNode(BufferedReader br) throws IOException {
		String line = readNextNonCommentLine(br);
		String[] tokens = line.split("\\s+");
		return new Vector3(Float.parseFloat(tokens[0]), 0,
				Float.parseFloat(tokens[1]));
	}

	/**
	 * Read the next non-comment line.
	 */
	private String readNextNonCommentLine(BufferedReader br) throws IOException {
		String line = br.readLine().trim();
		while (isComment(line)) {
			line = br.readLine().trim();
		}
		return line;
	}

	/**
	 * Check if the line is a comment.
	 */
	private boolean isComment(String line) {
		return line.startsWith("#");
	}

}
