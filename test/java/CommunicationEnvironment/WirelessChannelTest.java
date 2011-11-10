/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CommunicationEnvironment;

import Nodes.Node;
import java.util.ArrayList;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author acar
 */
public class WirelessChannelTest extends TestCase {
	
	public WirelessChannelTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		TestSuite suite = new TestSuite(WirelessChannelTest.class);
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
	 * Test of registerNode method, of class WirelessChannel.
	 */
	public void testRegisterNode() {
		System.out.println("registerNode");
		Node n = null;
		WirelessChannel instance = null;
		instance.registerNode(n);
		// TODO review the generated test code and remove the default call to fail.
		
	}

	/**
	 * Test of generateSNR method, of class WirelessChannel.
	 */
	public void testGenerateSNR() {
		System.out.println("generateSNR");
		Node sensor = null;
		int frequency = 0;
		WirelessChannel instance = null;
		double expResult = 0.0;
		double result = instance.generateSNR(sensor, frequency);
		assertEquals(expResult, result, 0.0);
		// TODO review the generated test code and remove the default call to fail.
		
	}

	/**
	 * Test of generateSINR method, of class WirelessChannel.
	 */
	public void testGenerateSINR() {
		System.out.println("generateSINR");
		Node transmitter = null;
		Node receiver = null;
		int freq = 0;
		WirelessChannel instance = null;
		double expResult = 0.0;
		double result = instance.generateSINR(transmitter, receiver, freq);
		assertEquals(expResult, result, 0.0);
		// TODO review the generated test code and remove the default call to fail.
		
	}

	/**
	 * Test of occupyFrequency method, of class WirelessChannel.
	 */
	public void testOccupyFrequency() {
		System.out.println("occupyFrequency");
		int frequency = 0;
		Node n = null;
		WirelessChannel instance = null;
		instance.occupyFrequency(frequency, n);
		// TODO review the generated test code and remove the default call to fail.
		
	}

	/**
	 * Test of isOccupied method, of class WirelessChannel.
	 */
	public void testIsOccupied() {
		System.out.println("isOccupied");
		int freq = 0;
		int nodeType = 0;
		WirelessChannel instance = null;
		boolean expResult = false;
		boolean result = instance.isOccupied(freq, nodeType);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		
	}

	/**
	 * Test of releaseFrequency method, of class WirelessChannel.
	 */
	public void testReleaseFrequency() {
		System.out.println("releaseFrequency");
		int frequency = 0;
		Node n = null;
		WirelessChannel instance = null;
		instance.releaseFrequency(frequency, n);
		// TODO review the generated test code and remove the default call to fail.
		
	}

	/**
	 * Test of freeFrequency method, of class WirelessChannel.
	 */
	public void testFreeFrequency() {
		System.out.println("freeFrequency");
		WirelessChannel instance = null;
		int expResult = 0;
		int result = instance.freeFrequency();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		
	}

	/**
	 * Test of dbToMag method, of class WirelessChannel.
	 */
	public void testDbToMag() {
		System.out.println("dbToMag");
		double db = 0.0;
		double expResult = 0.0;
		double result = WirelessChannel.dbToMag(db);
		assertEquals(expResult, result, 0.0);
		// TODO review the generated test code and remove the default call to fail.
		
	}

	/**
	 * Test of magTodb method, of class WirelessChannel.
	 */
	public void testMagTodb() {
		System.out.println("magTodb");
		double mag = 0.0;
		double expResult = 0.0;
		double result = WirelessChannel.magTodb(mag);
		assertEquals(expResult, result, 0.0);
		// TODO review the generated test code and remove the default call to fail.
		
	}

	/**
	 * Test of numberOfFreq method, of class WirelessChannel.
	 */
	public void testNumberOfFreq() {
		System.out.println("numberOfFreq");
		WirelessChannel instance = null;
		int expResult = 0;
		int result = instance.numberOfFreq();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		
	}

	/**
	 * Test of getMeanOnDuration method, of class WirelessChannel.
	 */
	public void testGetMeanOnDuration() {
		System.out.println("getMeanOnDuration");
		WirelessChannel instance = null;
		double expResult = 0.0;
		double result = instance.getMeanOnDuration();
		assertEquals(expResult, result, 0.0);
		// TODO review the generated test code and remove the default call to fail.
		
	}

	/**
	 * Test of getMeanOffDuration method, of class WirelessChannel.
	 */
	public void testGetMeanOffDuration() {
		System.out.println("getMeanOffDuration");
		WirelessChannel instance = null;
		double expResult = 0.0;
		double result = instance.getMeanOffDuration();
		assertEquals(expResult, result, 0.0);
		// TODO review the generated test code and remove the default call to fail.
		
	}

	/**
	 * Test of getTrafficModel method, of class WirelessChannel.
	 */
	public void testGetTrafficModel() {
		System.out.println("getTrafficModel");
		WirelessChannel instance = null;
		int expResult = 0;
		int result = instance.getTrafficModel();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		
	}

	/**
	 * Test of getFreq method, of class WirelessChannel.
	 */
	public void testGetFreq() {
		System.out.println("getFreq");
		int freq = 0;
		WirelessChannel instance = null;
		ArrayList expResult = null;
		ArrayList result = instance.getFreq(freq);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		
	}
}
