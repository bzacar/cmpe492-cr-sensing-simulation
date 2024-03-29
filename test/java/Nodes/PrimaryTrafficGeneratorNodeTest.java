/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Nodes;

import java.util.HashMap;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author acar
 */
public class PrimaryTrafficGeneratorNodeTest extends TestCase {
	
	public PrimaryTrafficGeneratorNodeTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		TestSuite suite = new TestSuite(PrimaryTrafficGeneratorNodeTest.class);
		return suite;
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test of setRandomPosition method, of class PrimaryTrafficGeneratorNode.
	 */
	public void testSetRandomPosition() {
		System.out.println("setRandomPosition");
		double offDuration = 0.0;
		PrimaryTrafficGeneratorNode instance = null;
		instance.setRandomPosition(offDuration);
		// TODO review the generated test code and remove the default call to fail.
		
	}

	/**
	 * Test of generateTraffic method, of class PrimaryTrafficGeneratorNode.
	 */
	public void testGenerateTraffic() {
		System.out.println("generateTraffic");
		double offDuration = 0.0;
		PrimaryTrafficGeneratorNode instance = null;
		int expResult = 0;
		int result = instance.generateTraffic(offDuration);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		
	}

	/**
	 * Test of incrementTotalCommunicationDuration method, of class PrimaryTrafficGeneratorNode.
	 */
	public void testIncrementTotalCommunicationDuration() {
		System.out.println("incrementTotalCommunicationDuration");
		double commDur = 0.0;
		PrimaryTrafficGeneratorNode instance = null;
		instance.incrementTotalCommunicationDuration(commDur);
		// TODO review the generated test code and remove the default call to fail.
		
	}

	/**
	 * Test of getRoutingRadius method, of class PrimaryTrafficGeneratorNode.
	 */
	public void testGetRoutingRadius() {
		System.out.println("getRoutingRadius");
		PrimaryTrafficGeneratorNode instance = null;
		double expResult = 0.0;
		double result = instance.getRoutingRadius();
		assertEquals(expResult, result, 0.0);
		// TODO review the generated test code and remove the default call to fail.
		
	}

	/**
	 * Test of logStats method, of class PrimaryTrafficGeneratorNode.
	 */
	public void testLogStats() {
		System.out.println("logStats");
		HashMap registeredNodes = null;
		String[][] expResult = null;
		String[][] result = PrimaryTrafficGeneratorNode.logStats(registeredNodes);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		
	}
}
