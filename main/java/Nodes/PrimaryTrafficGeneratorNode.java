package Nodes;

import CommunicationEnvironment.Cell;
import CommunicationEnvironment.WirelessChannel;
import SimulationRunner.SimulationRunner;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;

/**
 * This class handles basic operations of Primary nodes.
 */
public class PrimaryTrafficGeneratorNode implements Node{
	/**
	 * Position of the node
     */
    protected Point2D.Double position = new Point2D.Double(0,0);
    /**
     * Velocity of the node
     */
    protected double velocity = 0;
    /**
     * Id o the Node
     */
    protected int id;
	int communicationFreq;
	int numberOfCallAttempts = 0;
	int numberOfBlocks = 0;
	double comunicationDuration = 0.0;
	private double routingRadius = 0.0;
    /**
     * Constructor of the PrimaryTrafficGeneratorNode
     * @param pos Position of the node
	 * @param vel Velocity of the node
	 * @param id  ID of this node
     */
    public PrimaryTrafficGeneratorNode(Point2D.Double pos, double vel,int id) {
       this.position = new Point2D.Double(pos.x, pos.y);
       this.velocity = vel;
       this.id = id;
	   communicationFreq = -1;
    } 
    /**
     * Sets a new position for the primary traffic generator node.
	 * @param	offDuration Previous off duration
     */
    public void setRandomPosition(double offDuration){
		routingRadius = offDuration*2.0;
		routingRadius = routingRadius > 20.0 ? 20.0:routingRadius;
        setPosition(Cell.deployNodeInRouteCircle(this, routingRadius));
    }
	
	/**
	 * Finds a free frequency and occupies it. This method is synchronized. That
	 * is only one thread at a time can run it
	 * @param	offDuration Previous off duration
	 * @return ID of the occupied frequency
	 */
	public int generateTraffic(double offDuration)
	{
		numberOfCallAttempts++;
		communicationFreq = SimulationRunner.wc.freeFrequency();    //Find a free frequency
		if(communicationFreq==WirelessChannel.NOFREEFREQ){			//If there is no available frequency
			numberOfBlocks++;
			return communicationFreq;								//Return immediately
		}
		setRandomPosition(offDuration);
		SimulationRunner.wc.occupyFrequency(communicationFreq, this);	//Occupy the frequency
        SimulationRunner.wc.usageOfFreqs.set(communicationFreq, SimulationRunner.wc.usageOfFreqs.get(communicationFreq)+1);
		return communicationFreq;									//Return its ID
	}

	/**
	 * Returns the current communication frequency of this Primary node
	 * @return	Communication frequency
	 */
	public int getCommunicationFreq() {
		return communicationFreq;
	}

	/**
	 * Returns how many times this Primary node attempted to communicate
	 * @return	Number of call attempts
	 */
	public int getNumberOfCallAttempts() {
		return numberOfCallAttempts;
	}

	/**
	 * Returns how many times this Primary node is dropped
	 * @return	Number of drops
	 */
	public int getNumberOfDrops() {
		return numberOfBlocks;
	}

	/**
	 * Returns the total communication duration of this primary node
	 * @return	Communication duration
	 */
	public double getComunicationDuration() {
		return comunicationDuration;
	}
	
	/**
	 * Increments communication duration of this Primary node
	 * @param commDur	Last communication duration
	 */
	public void incrementTotalCommunicationDuration(double commDur)
	{
		comunicationDuration += commDur;
	}
	
	/**
	 * Returns routing radius of a primary node
	 * @return	Routing radius
	 */
	public double getRoutingRadius() {
		return routingRadius;
	}
	
	/**
	 * Logs output statistics of Primary nodes
	 * @param registeredNodes	List of registered Primary nodes
	 * @return	Primary node statistics values
	 */
	public static String[][] logStats(HashMap registeredNodes)
	{
		int totalCallAttempts = 0, totalDrops = 0;
		double totalCommDur = 0;
//		CRNode.writeLogFile("\n-----PRIMARY NODE STATS-----");
		
		ArrayList<PrimaryTrafficGeneratorNode> list = new ArrayList<PrimaryTrafficGeneratorNode>(registeredNodes.keySet());
		String[][] data = new String[list.size()+2][3];
		Collections.sort(list,new Comparator <PrimaryTrafficGeneratorNode>(){

			@Override
			public int compare(PrimaryTrafficGeneratorNode o1, PrimaryTrafficGeneratorNode o2) {
				return o1.getId() - o2.getId();
			}
			
		});
		
		int i = 0;
		for (PrimaryTrafficGeneratorNode n : list) {
			double msec = n.getComunicationDuration();
			int hour = (int)(msec/3600000.0);
			msec -= hour*3600000.0;
			int min = (int)(msec/60000.0);
			msec -= min*60000.0;
			int sec = (int)(msec/1000.0);
			msec-= sec*1000.0;
			
//			CRNode.writeLogFile(String.format(Locale.US,"Primary Node: %2d\tNumber of Calls: %d\t\tCommunication Duration: %2d:%2d:%2d:%.2f",
//				n.getId(), n.getNumberOfCallAttempts() - n.getNumberOfDrops(), hour,min,sec,msec));
			totalCallAttempts += n.getNumberOfCallAttempts();
			totalCommDur += n.getComunicationDuration();
			totalDrops += n.getNumberOfDrops();
			
			data[i][0] = String.valueOf(n.getId());
			data[i][1] = String.valueOf(n.getNumberOfCallAttempts() - n.getNumberOfDrops());
			
			data[i][2] = String.format(Locale.US,"%2d:%2d:%2d:%.2f", hour,min,sec,msec);
			i++;
		}
		
		double msec = totalCommDur;
		int hour = (int)(msec/3600000.0);
		msec -= hour*3600000.0;
		int min = (int)(msec/60000.0);
		msec -= min*60000.0;
		int sec = (int)(msec/1000.0);
		msec-= sec*1000.0;
        
//		CRNode.writeLogFile(String.format(Locale.US,"TOTAL\t\t\t\tNumber of Calls: %d\t\tCommunication Duration: %2d:%2d:%2d:%.2f",
//				totalCallAttempts - totalDrops, hour,min,sec,msec));
		
		msec = totalCommDur/(double)list.size();
		hour = (int)(msec/3600000.0);
		msec -= hour*3600000.0;
		min = (int)(msec/60000.0);
		msec -= min*60000.0;
		sec = (int)(msec/1000.0);
		msec-= sec*1000.0;
		
//		CRNode.writeLogFile(String.format(Locale.US,"Average\t\t\t\tNumber of Calls: %.2f\t\tCommunication Duration: %2d:%2d:%2d:%.2f",
//				(double)(totalCallAttempts - totalDrops) / (double)list.size(), hour,min,sec,msec));
		
        double totalUsageOfFreq = 0.0;
//        CRNode.writeLogFile("");
//        for(int j=0;j<SimulationRunner.wc.numberOfFreq();j++){
//            CRNode.writeLogFile(String.format(Locale.US, "Freq# %d used %d times, by primary users", j,SimulationRunner.wc.usageOfFreqs.get(j)));
//        }
//        CRNode.writeLogFile("");
//        for(int j=0;j<SimulationRunner.wc.indexesOfFreqIntervals.size();j++){
//            totalUsageOfFreq += SimulationRunner.wc.usageOfFreqsInIntervals.get(j);
//        }
//        for(int j=0;j<SimulationRunner.wc.indexesOfFreqIntervals.size();j++){
//            CRNode.writeLogFile(String.format(Locale.US, "Total usage of %d. freq interval is %d (which is %.2f percentage in total)",j,SimulationRunner.wc.usageOfFreqsInIntervals.get(j),SimulationRunner.wc.usageOfFreqsInIntervals.get(j)/totalUsageOfFreq));
//        }
//        CRNode.writeLogFile("");
//        for(int j=0;j<SimulationRunner.wc.indexesOfFreqIntervals.size();j++){
//            CRNode.writeLogFile(String.format(Locale.US, "Last index of %d. freq interval is %d",j,SimulationRunner.wc.indexesOfFreqIntervals.get(j)-1));
//        }
        
		data[i][0] = "Total";
		data[i][1] = String.valueOf(totalCallAttempts - totalDrops);
		data[i][2] = String.format(Locale.US,"%f", totalCommDur);
		i++;
		
		data[i][0] = "Average";
		data[i][1] = String.valueOf((double)(totalCallAttempts - totalDrops)/(double)list.size());
		data[i][2] = String.format(Locale.US,"%2d:%2d:%2d:%.2f", hour,min,sec,msec);
		return data;
	}

    /**
     * Sets a communication frequency for a primary node.
     * @param communicationFreq Communication frequency
     */
	public void setCommunicationFreq(int communicationFreq) {
		this.communicationFreq = communicationFreq;
	}
	
	@Override
	public Point2D.Double getPosition() {
		return position;
	}

	@Override
	public double getVelocity() {
		return velocity;
	}

	@Override
	public void setPosition(Point2D.Double position) {
		this.position = position;
	}

	@Override
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return id;
	}
}
