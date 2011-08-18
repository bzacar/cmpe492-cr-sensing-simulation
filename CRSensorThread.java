package firstproject;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * This class handles the frame structure of the CR nodes.
 */
public class CRSensorThread implements Runnable{

	/**
	 * Runner thread
	 */
	private Thread runner=null;
	/**
	 * Remaining simulation time
	 */
	private double simulationDuration;
	private double simulationDur;
	/**
	 * boolean variable to terminate thread
	 */
	private boolean finished = false;
	/**
	 * Duration of one time unit
	 */
	private long unitTime;
	private int numberOfSlots;
	private double slotDur;
	private double senseScheduleAdvertisement;
	private double commScheduleAdvertisement;
	private double commDur;
	private double senseResultAdvertisement;
	private long time;
	
	/**
	 * Creates a thread that performs simulation action for CR sensor nodes
	 * @param simulationDuration			Duration of the simulation in unit time
	 * @param unitTime						Unit of time in milliseconds
	 * @param numberOfSlots					Number of sensing slots in the frame
	 * @param slotDur						Duration of the sensing slots in terms of unit time
	 * @param senseScheduleAdvertisement	Duration of the sensing schedule advertisement in terms of unit time
	 * @param commScheduleAdvertisement		Duration of the communication schedule advertisement in terms of unit time
	 * @param commDur						Duration of the communication in terms of unit time
	 * @param senseResultAdvertisement		Duration of the sensing result advertisement in terms of unit time
	 */
	public CRSensorThread(double simulationDuration,long unitTime, int numberOfSlots, double slotDur,
			double senseScheduleAdvertisement, double commScheduleAdvertisement, double commDur,
			double senseResultAdvertisement)
	{
		this.simulationDuration = simulationDuration*unitTime;	//Perform simulation for every half of time unit
		this.unitTime = unitTime;
		this.numberOfSlots = numberOfSlots;
		this.slotDur = slotDur*unitTime;
		this.senseScheduleAdvertisement = senseScheduleAdvertisement*unitTime;
		this.commScheduleAdvertisement = commScheduleAdvertisement*unitTime;
		this.commDur = commDur*unitTime;
		this.senseResultAdvertisement = senseResultAdvertisement*unitTime;
		
		finished = false;
		if(runner==null){
            runner=new Thread(this);            //Create the thread
            runner.start();			//Start the thread: This method will call run method below
        }
	}
	
	/**
	 * Main thread operation
	 */
	@Override
	public void run() {
		simulationDur = simulationDuration;		//Save initial simulation duration
		while(simulationDuration>0&&!finished){		//Until simulation duration is elapsed or thread is terminated
			senseScheduleAdvertise();
			SimulationRunner.progressBar.setValue((((int)simulationDur-(int)simulationDuration)*100)/(int)simulationDur);	//Update progress bar
			if(simulationDuration<=0&&finished)
				break;
			
			for(int j=0;j<numberOfSlots;j++){
				sensingSlot(j);
				SimulationRunner.progressBar.setValue((((int)simulationDur-(int)simulationDuration)*100)/(int)simulationDur);	//Update progress bar
			}
			if(simulationDuration<=0&&finished)
				break;
			
			senseResultAdvertise();
			SimulationRunner.progressBar.setValue((((int)simulationDur-(int)simulationDuration)*100)/(int)simulationDur);	//Update progress bar
			if(simulationDuration<=0&&finished)
				break;
			
			commScheduleAdvertise();
			SimulationRunner.progressBar.setValue((((int)simulationDur-(int)simulationDuration)*100)/(int)simulationDur);	//Update progress bar
			if(simulationDuration<=0&&finished)
				break;
			
			communicate(3);
			SimulationRunner.progressBar.setValue((((int)simulationDur-(int)simulationDuration)*100)/(int)simulationDur);	//Update progress bar
		}
		SimulationRunner.priTrafGen.terminateAllThreads();		//Terminate other thread in case of they did not
		if(finished)	//If the thread is terminated
			JOptionPane.showMessageDialog(null, "Simulation Terminated", "Simulation", JOptionPane.WARNING_MESSAGE);
		else			//If simulation duration is elapsed
			JOptionPane.showMessageDialog(null, "Simulation Completed", "Simulation", JOptionPane.WARNING_MESSAGE);
		SimulationRunner.progressBar.setVisible(false);			//Hide progress bar
		SimulationRunner.progressBar.setValue(0);				//Set its value to zero
		SimulationRunner.clear();								//Clear data related to simulation
		SimulationRunner.terminateSimulation.setVisible(false);	//Hide "Terminate" button
		CRNode.closeLogFile();									//Close log file
		ArrayList<String> names = new ArrayList<String>();
		names.add("SNR");
		names.add("SINR");
		SimulationRunner.plot.plotAll(names);			//Plot the time vs average SNR graphs
		finished=true;											//Set finished as true
	}
	
	private void senseScheduleAdvertise()
	{
		time = System.currentTimeMillis();		//Save current time
		SimulationRunner.crBase.assignFrequencies();
		time = (long)senseScheduleAdvertisement - (System.currentTimeMillis() - time);	//Calculate time spent by now and subtract it from
		if(time>1){												//unit time if it is greater than 1 milli sec
			try {												//sleep for that amount
				Thread.sleep(time);		//Wait for unit time amount
			} catch (InterruptedException ex) {
				Logger.getLogger(CRSensorThread.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		simulationDuration-=senseScheduleAdvertisement;
	}
	
	private void sensingSlot(int slotNumber)
	{
		time = System.currentTimeMillis();		//Save current time
		/*Perform some semaphore locks to solve reader writer problem*/
		try {
			PrimaryTrafficGenerator.z.acquire();
		} catch (InterruptedException ex) {
			Logger.getLogger(CRSensorThread.class.getName()).log(Level.SEVERE, null, ex);
		}
		try {
			PrimaryTrafficGenerator.readLock.acquire();
		} catch (InterruptedException ex) {
			Logger.getLogger(CRSensorThread.class.getName()).log(Level.SEVERE, null, ex);
		}
		try {
			PrimaryTrafficGenerator.x.acquire();
		} catch (InterruptedException ex) {
			Logger.getLogger(CRSensorThread.class.getName()).log(Level.SEVERE, null, ex);
		}
		PrimaryTrafficGenerator.readerCount++;
		if(PrimaryTrafficGenerator.readerCount==1){
			try {
				PrimaryTrafficGenerator.writeLock.acquire();
			} catch (InterruptedException ex) {
				Logger.getLogger(CRSensorThread.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		PrimaryTrafficGenerator.x.release();
		PrimaryTrafficGenerator.readLock.release();
		PrimaryTrafficGenerator.z.release();

		for(int i=0;i<SimulationRunner.crNodes.size();i++){
			SimulationRunner.crNodes.get(i).sense(slotNumber);		//Sense the frequencies for each CR node
		}

		try {
			PrimaryTrafficGenerator.x.acquire();
		} catch (InterruptedException ex) {
			Logger.getLogger(CRSensorThread.class.getName()).log(Level.SEVERE, null, ex);
		}
		PrimaryTrafficGenerator.readerCount--;
		if(PrimaryTrafficGenerator.readerCount==0)
			PrimaryTrafficGenerator.writeLock.release();
		PrimaryTrafficGenerator.x.release();
		time = (long)slotDur - (System.currentTimeMillis() - time);	//Calculate time spent by now and subtract it from
		if(time>1){												//unit time if it is greater than 1 milli sec
			try {												//sleep for that amount
				Thread.sleep(time);		//Wait for unit time amount
			} catch (InterruptedException ex) {
				Logger.getLogger(CRSensorThread.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		simulationDuration-=slotDur;
	}
	
	private void senseResultAdvertise()
	{
		time = System.currentTimeMillis();		//Save current time
		/*Write time to log file*/
		CRNode.writeLogFile(String.format("Time: %.2f", (double)(simulationDur-simulationDuration)/unitTime));
		for(int i=0;i<SimulationRunner.crNodes.size();i++){
			SimulationRunner.crNodes.get(i).logSnrValues();		//Log SNR values sensed by the CR nodes
		}
		CRNode.logAverageSnr((double)(simulationDur-simulationDuration)/unitTime);	//Log average of SNR values sensed by the CR nodes
		//CRNode.writeLogFile("\n");
		time = (long)senseResultAdvertisement - (System.currentTimeMillis() - time);	//Calculate time spent by now and subtract it from
		if(time>1){												//unit time if it is greater than 1 milli sec
			try {												//sleep for that amount
				Thread.sleep(time);		//Wait for unit time amount
			} catch (InterruptedException ex) {
				Logger.getLogger(CRSensorThread.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		simulationDuration-=senseResultAdvertisement;
	}
	
	private void commScheduleAdvertise()
	{
		time = System.currentTimeMillis();		//Save current time
		SimulationRunner.crBase.communicationScheduleAdvertiser();
		time = (long)commScheduleAdvertisement - (System.currentTimeMillis() - time);	//Calculate time spent by now and subtract it from
		if(time>1){												//unit time if it is greater than 1 milli sec
			try {												//sleep for that amount
				Thread.sleep(time);		//Wait for unit time amount
			} catch (InterruptedException ex) {
				Logger.getLogger(CRSensorThread.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		simulationDuration-=commScheduleAdvertisement;
	}
	
	private void communicate(int numberOfReports)
	{
		if(numberOfReports<1)
			numberOfReports=1;
		for(int i=0;i<numberOfReports;i++){
			time = System.currentTimeMillis();		//Save current time
			CRNode.communicate((double)(simulationDur-simulationDuration)/unitTime); //TODO Communicate
			CRNode.writeLogFile("");
			time = (long)(commDur/numberOfReports) - (System.currentTimeMillis() - time);	//Calculate time spent by now and subtract it from
			if(time>1){												//unit time if it is greater than 1 milli sec
				try {												//sleep for that amount
					Thread.sleep(time);		//Wait for unit time amount
				} catch (InterruptedException ex) {
					Logger.getLogger(CRSensorThread.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			simulationDuration-=(commDur/numberOfReports);
		}
		CRNode.communicate((double)(simulationDur-simulationDuration)/unitTime); //TODO Communicate
		CRNode.writeLogFile("\n");
	}
	
	/**
	 * Returns whether the thread is finished or not
	 * @return finished
	 */
	public boolean isFinished()
	{
		return finished;
	}
	
	/**
	 * Terminates the thread
	 */
	public void terminate()
	{
		finished=true;
	}
}