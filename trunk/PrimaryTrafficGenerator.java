
package firstproject;

import cern.jet.random.Exponential;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class PrimaryTrafficGenerator {
	/**
	 * Lock for accessing wireless channel frequencies
	 */
	static ReentrantLock lock = new ReentrantLock();
	/**
	 * Lock for accessing wireless channel frequencies
	 */
	static ReentrantLock interArrivalLock = new ReentrantLock();
	/**
	 * Lock for accessing wireless channel frequencies
	 */
	static ReentrantLock callDurationLock = new ReentrantLock();
	/**
	 * Distribution for inter arrival times
	 */
	static Exponential interArrival;
	/**
	 * Distribution for call durations
	 */
	static Exponential callDuration;
	/**
	 * List of nodes and their associated threads
	 */
	private HashMap<Node,PrimaryTrafficGeneratorThread> registeredNodes;
	/**
	 * Time unit
	 */
	static int unitTime;
	
	/**
	 * Creates a primary traffic generator with no node registered to it
	 * @param alpha number of calls per unit time
	 * @param meanCallDuration expected value for duration of a call in time units
	 * @param unit Unit of time in milliseconds
	 */
	public PrimaryTrafficGenerator(int alpha, double meanCallDuration, int unit)
	{
		interArrival = new Exponential((double)alpha/(double)unit, SimulationRunner.randEngine);
		callDuration = new Exponential((double)1/(meanCallDuration*unit), SimulationRunner.randEngine);
		registeredNodes = new HashMap<Node, PrimaryTrafficGeneratorThread>();
		unitTime = unit;
	}
	
	/**
	 * Registers a node and creates a thread for it to generate traffic
	 * @param n node to be registered
	 * @param simulationDuration Duration of simulation in unit times
	 */
	public void registerNode(Node n, long simulationDuration)
	{
		registeredNodes.put(n,new PrimaryTrafficGeneratorThread(n, simulationDuration*unitTime));
	}
}