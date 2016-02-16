# CR Sensing Simulator [Revision 2](https://code.google.com/p/cmpe492-cr-sensing-simulation/source/detail?r=2) Design Document #

## Table of Contents ##


## 1. INTRODUCTION ##

In this project we have implemented basic cell structures and traffic generation classes. This classes provide a basis for our further project which is a simulator to analyze performance of Zone-Based Sensing Scheduling Approach in Cognitive Radio Spectrum Sensing.

The project constructs a cognitive radio cell. In this cell it deploys a given amount of cognitive radio (secondary) users in a specified zone. Finally, it deploys primary users and produces Poisson traffic. Secondary users try to communicate according sensing results of a previous frame and logs SINR values observed during the communication.

## 2. DESIGN ##

The project consists of 13 classes which can be grouped into 5 groups. Details of these groups and classes are explained in the following sections.

### 2.1. Main Class ###

The main class of the project is SimulationRunner class. This class initializes the GUI components of the project. Then it parses the input provided by the user. It instantiates objects of Cell, WirelessChannel, CRBase, PrimaryTrafficGenerator, DrawCell, and Plot classes according to that input. Finally, it registers PrimaryTrafficGeneratorNode objects to PrimaryTrafficGenerator. By doing so it starts the threads which will produce Poisson traffic. Also it creates an object of CRSensorThread and so starts sensing and communication actions of the CR nodes.

### 2.2. Node Classes ###

These classes concern with primary users, the actual owners of the system, which generate traffic in the wireless channel and cognitive radio users which try to find an available frequency to talk by sensing the channel within assigned time interval, and they sense only assigned frequencies to them.

#### 2.2.1. Node ####

This class is an abstract class that has all common fields of all node classes. It has velocity, position and id fields for nodes, and it has all the get and set functions about these fields.

#### 2.2.2. CRNode ####

This class is an extended version of Node class. It has a “sense” method which simply updates the snr value of a given frequency and helps to compute average snr of that frequency by adding the new snr value to the old record and updates the old record with new one. While updating the SNR value of a frequency, it uses the generateSNR method of the Wireless Channel class by sending the crnode(itself) and the frequency to that method. It has logSnrValues, logAverageSnr and some other methods to save some SNR informations into our log file.

There is another important method in this class, communicate, which checks if there is a collision or not and writes the conclusion to log file in both cases. This method computes sinr values of frequencies and compares them with sinrThreshold value in wireless channel class and then makes a decision about collisions.

#### 2.2.3. Primary Traffic Generator Node ####

This class is also an extended class of Node class.There is a method, called setRandomPosition, which simply calls deployNodeinCell method of Cell class and then returns a random position in the Cell.

### 2.3. Traffic Generation and Monitoring Classes ###

These classes are responsible for primary traffic generation and monitoring its effects on the CR nodes. While the primary users produces a specified traffic, CR nodes sense the channels and communicate by using available channels.

#### 2.3.1. CR Sensor Thread ####

This class handles the frame structure of the CR nodes. The main assumption about this class is all CR nodes are synchronized. It uses CRBase to advertise sensing and communication schedules and uses CRNodes registered to channel to sense the frequencies, advertise sensing results, and communicate. The main frame structure the class provides is as follows:

![http://cmpe492-cr-sensing-simulation.googlecode.com/files/Frame.jpg](http://cmpe492-cr-sensing-simulation.googlecode.com/files/Frame.jpg)

#### 2.3.2. Primary Traffic Generator ####

This class provides necessary probability distribution classes and semaphores for primary traffic generation by many threads. That is, PrimaryTrafficGenerationThread objects uses fields of this class for traffic generation and necessary critical section processes. Also initiations of these objects are accomplished by registering PrimaryTrafficGenerationNode objects to an instance of this class. This class also provides an interface to terminate all working PrimaryTrafficGenerationThreads.

#### 2.3.3. Primary Traffic Generator Thread ####

This class produces a specified traffic model for an individual PrimaryTrafficGenerationNode. For now they can only produce Poisson traffic. All of the threads uses the same random number generator by doing so the overall inter arrival time and call duration of primary users follows exponential distributions. Object of this class simply generates a random number inter arrival time, sleeps for that amount, selects a random free frequency, occupy it, generates a random call duration, sleeps for that amount, and so on.

### 2.4. Cell and Channel Classes ###

This classes handles main cell structure of the CR cell and properties of the wireless channel.

#### 2.4.1. CR Base ####

This class includes the fields and functions of the base station in the cell. There is a method called deploy\_freq in this class which returns frequency list which consists of frequency per crnode number of consecutive frequencies. Also, there is a method named assignFrequencies which assigns new frequencies to crnodes by using deploy\_freq and setFrequencyList of CRNode class and it updates the frequency\_list which basically keeps the number of listeners(crnodes) for each frequency.

There is another method called communicationScheduleAdvertiser, which firstly computes snr value of the base station by computing the max distance between crnodes and crbase, after that it computes the threshold value for collision decisions by using magTodb and dbTomag methods of Wireless Channel class. By using threshold value, it finds all available frequencies and adds them to free\_freq list and then, it deploys the free frequencies to the crnodes in a way that first deployed frequency has the min snr value.

#### 2.4.2. Cell ####

The basic structure of the cell is as follows:

<img src='http://cmpe492-cr-sensing-simulation.googlecode.com/files/Cell%20Structurev2.JPG' alt='Cell Structure' width='451' height='449' />

In this class, there is a deployNode function which simply takes two angles and two distance values and returns a random position in Cell with respect to these values. Returned position’s angle with crbase should be between the given angles and the same rule is valid for the distance parameter. There are two other methods; deployNodeinCell, deployNodeinZone in this class which use the deployNode method with their specific parameters.

#### 2.4.3. Wireless Channel ####

This class provides available channels to both primary and secondary users. It keeps the information about which frequency is occupied by which node. It calculates SNR value for a CR node for a specified frequency according to whether the frequency is occupied by a primary user or not. It also calculates SINR value between two given CR nodes (one these nodes can CR base station) for a given frequency.

### 2.5. Animation and Plotting Classes ###

This classes provides an animation interface for sensing simulation and draws plots of SNR and SINR values evaluated during the simulation.

#### 2.5.1. Point Color ####

This class hold information about position of a node on the simulation screen, its color, and radius.

#### 2.5.2. Draw Area ####

This class extend JPanel class by adding additional functionalities for drawing basic cell structure, primary nodes, and secondary nodes.

#### 2.5.3. Draw Cell ####

This class creates a JFrame object and adds a DrawArea object to it. Initially it does not put any CR node or primary node in to the cell. It only draws the Cell and the zone in which the CR nodes will be deployed. It provides an interface for adding primary and CR nodes to the cell or changing the properties of currently existing ones.

#### 2.5.4. Plot ####

This class plots a graph with provided x and y values. There can be many x values and associated y values. It can plot all of them on different windows or on the same window. It can also groups different combinations. In this project it is used to draw the plots of SNR and SINR values versus time for each frequency.

## 3. CONCLUSION ##
As observed from the animation, traffic generation and node deployment is achieved successfully. According to the data collected in the log file, frame structure of CR nodes is achieved successfully. If the number of sensed frequencies is less than number of total frequencies, the program malfunctions.

Moreover, when the mean call duration and number of calls per unit time of primary users is increased, decrease in communication duration of the CR nodes is observed.