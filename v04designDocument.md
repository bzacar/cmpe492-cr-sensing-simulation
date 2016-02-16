# CR Sensing Simulator [Revision 4](https://code.google.com/p/cmpe492-cr-sensing-simulation/source/detail?r=4) Design Document #

## Table of Contents ##


## 1. INTRODUCTION ##
> In this Project, we have implemented basic cell structures and traffic generation classes. This classes provide a basis for our further project which is a simulator to analyze performance of zone sensing approach in Cognitive Radio spectrum sensing.

> The project constructs a cognitive radio cell. In this cell it deploys a given amount of cognitive radio (secondary) users in specified zones. Finally, it deploys primary users and produces Poisson traffic. Secondary users try to communicate according sensing results of a previous frame and logs SINR values observed during the communication. There could be some collisions between cr users and primary users, and when this happens, cr users try to find new available frequencies for themselves to continue to talk. Two outcomes can be achieved from this situation: i)Cr user drops, ii)Cr user handoffs. At the end of each simulation, a statistics table is generated and this table consists of secondary user and primary user statistics.
## 2. DESIGN ##
> The project consists of 19 classes which are grouped into 6 packages. Details of these packages and classes are explained in the following sections.
### 2.1. Simulation Runner Package ###
> > This package consists of Simulation Runner class which is the main class in our simulation, and Pareto Distribution class. The forthcoming distributions for the simulation will be implemented in this package.
#### 2.1.1. Simulation Runner ####
> > The main class of the project is Simulation Runner class. This class initializes the GUI components of the project. Then it parses the input provided by the user. It initiates objects of Cell, Wireless Channel, CR Base, Draw Cell, and Plot classes according to that input. If the animation button is selected then, it starts Cr sensor threads and primary traffic generator threads. Otherwise, it starts CrDes Schedular and priTrafGenDes. If the animation off button is selected, simulation is done in a way of discrete simulation event. In each case, Cr nodes will produce Poisson traffic.
#### 2.1.2. Pareto Distribution ####

> This class concerns with pareto distribution. However, pareto distribution is not usable in the simulation system, for now.
### 2.2. Nodes Package ###
> This package has classes which are concerning with primary users, the actual owners of the system, which generate traffic in the wireless channel and cognitive radio users which try to find an available frequency to talk by sensing the channel within assigned time interval, and they sense only assigned frequencies to them. Also, this package includes the Cr Base, base station of the cell, class which is also a node.
#### 2.2.1. Node ####
> > This class is an abstract class that has all common fields of all node classes. It has velocity, position and id fields for nodes, and it has all the get and set functions about these fields.
#### 2.2.2. CR Node ####
> > This class is an extends Node class. It has a “sense” method which simply updates the snr value of a given frequency and helps to compute average snr of that frequency by adding the new snr value to the old record and updates the old record with new one. While updating the snr value of a frequency, it uses the generateSNR method of the Wireless Channel class by sending the crnode(itself) and the frequency to that method. It has logSnrValues, logAverageSnr and some other methods to save some snr informations into our log file.
> > There is another important method in this class, communicate, which checks if there is a collision or not and writes the conclusion to log file in both cases. This method computes sinr values of frequencies and compares them with sinrThreshold value in wireless channel class and then makes a decision about collisions.
> > There are also nextOnDuration/nextOffDuration functions which simply returns the next on/off duration in terms of number of frames according to the traffic model for Multithreaded Simulation. Similarly, it has nextOnDurationDES/ nextOffDurationDES functions which do the same job with the above functions but these are for the discrete event simulation.
#### 2.2.3. Primary Traffic Generator Node ####

> This class is also an extended class of Node class. There is a method, called setRandomPosition, which simply calls deployNodeinCell method of Cell class and then returns a random position in the Cell.
#### 2.2.4. CR Base ####
> > This class includes the fields and functions of the base station in the cell. There is a method called deploy\_freq in this class which returns frequency list which consists of frequency per crnode number of consecutive frequencies. Also, there is a method named assignFrequencies which assigns new frequencies to crnodes by using deploy\_freq and setFrequencyList of CR Node class and it updates the frequency\_list which basically keeps the number of listeners(crnodes) for each frequency.
> > There is another method called communicationScheduleAdvertiser which does basically:
  * Firstly, this method calls findFreeFrequencies method which computes snr value of the base station by computing the max distance between cr nodes and cr base for each zone, after that it computes the threshold value for collision decisions by using magTodb and dbTomag methods of Wireless Channel class. By using threshold value, it finds all available frequencies and adds them to free\_freq list , again, for each zone.
  * Secondly, it calls handoffCollidedUsersInZones method to make forced handoffs for crnodes which are collided in the previous frame. It releases all of collided crnodes’ communication frequencies to avoid further collisions with the primary nodes which are also use the same frequency to communicate. Then, it tries to find new free frequencies for the collided crnodes and updates numberOfDrops or numberOfForcedHandoff for cr nodes with respect to the result.
  * Lastly, it finds all of the cr nodes(separate for each zone) which want to communicate and tries to find them available frequencies. It updates numberOfDrops for cr nodes.
Note: This method, firstly, finds free frequencies for the collided cr nodes and then, finds frequencies for the cr nodes which are just wanted to talk.
### 2.3. Multi Threaded Simulation Package ###

> These classes are responsible for primary traffic generation and monitoring its effects on the CR nodes when the animationOnButton is selected. While the primary users produce a specified traffic, CR nodes sense the channels and communicate by using available channels.
#### 2.3.1. CR Sensor Thread ####
> This class handles the frame structure of the CR nodes. The main assumption about this class is all CR nodes are synchronized. It uses CR Base to advertise sensing and communication schedules and uses CR Nodes registered to channel to sense the frequencies, advertise sensing results, and communicate. The main frame structure the class provides is as follows:
![http://cmpe492-cr-sensing-simulation.googlecode.com/files/Frame.jpg](http://cmpe492-cr-sensing-simulation.googlecode.com/files/Frame.jpg)

#### 2.3.2. Primary Traffic Generator ####
> This class provides necessary probability distribution classes and semaphores for primary traffic generation by many threads. That is, Primary Traffic Generation Thread objects uses fields of this class for traffic generation and necessary critical section processes. Also initiations of these objects are accomplished by registering Primary Traffic Generation Node objects to an instance of this class. This class also provides an interface to terminate all working Primary Traffic Generation Threads.
#### 2.3.3. Primary Traffic Generator Thread ####
> > This class produces a specified traffic model for an individual Primary Traffic Generation Node. For now they can only produce Poisson traffic. All of the threads uses the same random number generator by doing so the overall inter arrival time and call duration of primary users follows exponential distributions. Object of this class simply generates a random number inter arrival time, sleeps for that amount, selects a random free frequency, occupy it, generates a random call duration, sleeps for that amount, and so on.
### 2.4. Discrete Event Simulation Package ###
> > These classes are responsible for primary traffic generation and monitoring its effects on the CR nodes when the animationOffButton is selected. While the primary users produce a specified traffic, CR nodes sense the channels and communicate by using available channels.
#### 2.4.1. CR DES Scheduler ####
> > > This class handles the frame structure of the CR nodes. Whenever there come an event in the frame structure it does the necessary work and at the end of each part in the frame structure, it calls the next event according to the frame structure.
![http://cmpe492-cr-sensing-simulation.googlecode.com/files/Frame.jpg](http://cmpe492-cr-sensing-simulation.googlecode.com/files/Frame.jpg)

#### 2.4.2. DES Primary Traffic Generator ####

> This class holds primary traffic generation model and registered primary nodes.It also logs and prepares the statistics of the simulation related to primary nodes.
#### 2.4.3. Primary Traffic Generator Sim Ent ####
> This class handles events related to one primary node's traffic generation. Whenever a primary user starts to talks, it finds the communication duration and sends a communication end event when this time passes. Likewise, if a primary node is stopped talking, it finds next communication time and sends a communication start event  when this time passes.
### 2.5. Communication Environment Package ###
> > This package includes two classes which are basically constructing the communication environment.

#### 2.5.1. Cell ####

> The basic structure of the cell is as follows:

<img src='http://cmpe492-cr-sensing-simulation.googlecode.com/files/Cell%20Structurev2.JPG' alt='Cell Structure' width='451' height='449' />
> In this class, there is a deployNode function which simply takes two angles and two distance values and returns a random position in Cell with respect to these values. Returned position’s angle with cr base should be between the given angles and the same rule is valid for the distance parameter. There are three other methods; deployNodeinCell, deployNodeinZone, deployNodeInRouteCircle in this class which use the deployNode method to assign a new position for nodes with their specific parameters.
#### 2.5.2. Wireless Channel ####
> > This class provides available channels to both primary and secondary users. It keeps the information about which frequency is occupied by which node. It calculates SNR value for a CR node for a specified frequency according to whether the frequency is occupied by a primary user or not. It also calculates SINR value between two given CR nodes (one these nodes can CR base station) for a given frequency.
### 2.6. Animation Package ###
> > These classes provide an animation interface for sensing simulation and draw plots of SNR and SINR values evaluated during the simulation and construct a statistic table for crnodes and primary nodes.
#### 2.6.1. Draw Area ####

> This class extend JPanel class by adding additional functionalities for drawing basic cell structure, primary nodes, and secondary nodes.
#### 2.6.2. Draw Cell ####
> > This class creates a JFrame object and adds a Draw Area object to it. Initially it does not put any CR node or primary node in to the cell. It only draws the Cell and the zone in which the CR nodes will be deployed. It provides an interface for adding primary and CR nodes to the cell or changing the properties of currently existing ones.
#### 2.6.3. Plot ####

> This class plots a graph with provided x and y values. There can be many x values and associated y values. It can plot all of them on different windows or on the same window. It can also groups different combinations. In this project it is used to draw the plots of SNR and SINR values versus time for each frequency.
#### 2.6.4. Point Color ####
> > This class hold information about position of a node on the simulation screen, its color, and radius.
#### 2.6.5. Simulation Stats Table ####
> > This class generates a statistic table for cr nodes and primary nodes. In the table, there are statistics for each cr node: # of calls, # of drops, # of blocks, # of forced handoffs, # of collisions, etc.

## 3. CONCLUSION ##

> As observed from the animation, traffic generation and node deployment is achieved successfully. According to the data collected in the log file, frame structure of CR nodes is achieved successfully. Also, according to the stats table, number of blocks, number of collisions, number of drops and number of forced handoffs for cr nodes is achieved, too.
### 3.1. What's new in this version ###
Necessary changes in communication schedule advertisement and sensing schedule advertisement to simulate more than one zone in the simulation is implemented as follows:
<dl>
<blockquote><dt>1. The array that holds the average SNR values of the previous frame is implemented as two dimensional array. First dimension represents zones and second dimension represents frequencies. The same approach is also applied for the array which holds the average SNR values of the current frame.</dt>
<dt>2. Free frequency list is divided for each zone. If there is no node in a zone then no free frequency is assigned to that zone.</dt>
<dt>3. After free frequencies are decided for each zone, a frequency is assigned to a zone which has the minimum average SNR value for that frequency.</dt>
<dt>4. When the number of assigned frequencies to a zone becomes equal to the number of secondary users who want to start communication, no more frequencies is assigned to that zone.</dt>
<dt>5. Secondary users who are not given any frequency to communicate are reported as blocked.</dt></blockquote>

<blockquote><dt><b>PS:</b> Note that all these SNR values are due to primary nodes.</dt>
</dl>
<hr />
Possion traffic implementation for secondary users at multi threaded simulation module is completed by implementing the followings:<br>
<dl>
<dt>1. An array that holds communication start & communication end times (depending on user's current communication state) of CR user is added to CR sensor thread class.</dt>
<dt>2. First communication start times for each CR users is found at the beginning of the simulation.</dt>
<dt>3. If a CR user is currently not communicating the value in the array indicates communication start time for that user. If s/he is currently communicating then the value in the array indicates communication end time.</dt>
<dt>4. After the initial state all random number generations occur at the beginning of the frame loops.</dt></blockquote>

<blockquote><dt><b>PS:</b> All communication and off times of the users are calculated in terms of frames.</dt>
</dl>
<hr />
In order to keep track of the statistics about secondary users' force handoffs and drops the following is implemented:<br>
<dl>
<dt>1. Variables for force handoff count and drop count are created. A boolean variable that holds information about last frames collision is also created.</dt>
<dt>2. At the beginning of communication schedule advertisement collided secondary users are given priority over others and tried to be given free frequency. If they can handoff to another available frequency, number of force handoffs is incremented. Otherwise, number of drops is incremented.</dt>
<dt>3. If secondary user drops, its communication end event is cancelled and a new communication start event is created.</dt></blockquote>

<blockquote><dt><b>PS:</b> The statistics about number of collisions and number of secondary blocks were already implemented.</dt>
</dl></blockquote>

Finally unit times is specified as follows:

<table border='1'>
<blockquote><tr>
<blockquote><th><b>Events</b></th>
<th><b>Frequencies</b></th>
<th><b>Default Values</b></th>
</blockquote></tr>
<tr>
<blockquote><td>Number of Calls</td>
<td># calls per hour</td>
<td>2 calls per hour</td>
</blockquote></tr>
<tr>
<blockquote><td>Call duration</td>
<td># minutes per call</td>
<td>4 minutes per call</td>
</blockquote></tr>
</table>
<table border='1'>
<tr>
<blockquote><th>Secondary Frame Parameters</th>
</blockquote></tr>
<tr>
<blockquote><th>Parameters</th>
<th>Default Values</th>
</blockquote></tr>
<tr>
<blockquote><td>Sensing Schedule Advertisement</td>
<td>10 msec</td>
</blockquote></tr>
<tr>
<blockquote><td>Sensing Slots' Duration</td>
<td>10 msec</td>
</blockquote></tr>
<tr>
<blockquote><td>Sensing Result Acknowledgement</td>
<td>10 msec</td>
</blockquote></tr>
<tr>
<blockquote><td>Communication Schedule Advertisement</td>
<td>10 msec</td>
</blockquote></tr>
<tr>
<blockquote><td>Communication Duration</td>
<td>630 msec</td>
</blockquote></tr>
</table>