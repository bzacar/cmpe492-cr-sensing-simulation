# Table of Contents #


# ABSTRACT #

**Cognitive radio (CR)** (1)improves spectrum efficiency by enabling CR users to opportunistically reuse the idle spectrum bands of licensed users, i.e., primary users. To avoid causing interference to the primary users, spectrum sensing, which detects idle licensed bands, is one of the most important issues.

**Spectrum Sensing:** (2)detecting the unused spectrum and sharing it without harmful interference with other users. It is an important requirement of the Cognitive Radio network to sense spectrum holes. Detecting primary users is the most efficient way to detect spectrum holes. Spectrum sensing techniques can be classified into three categories (2):

<ul>
<li><i>Transmitter detection</i>: cognitive radios must have the capability to determine if a signal from a primary transmitter is locally present in a certain spectrum. There are several approaches proposed:<br>
<ul>
<li>matched filter detection</li>
<li>energy detection</li>
<li>cyclostationary feature detection</li>

</ul>
</li>
<li><i>Cooperative detection</i>: refers to spectrum sensing methods where information from multiple Cognitive radio users are incorporated for primary user detection.</li>
<li><i>Interference based detection</i>.</li>
</ul>

In this project we implemented a simulator to analyze the efficiency of _**Zone-Based Sensing Scheduling in Cognitive Radio**_. The project constructs a CR cell and zones in this cell. Then it generates primary and secondary users both trying to communicate in the cell. It measures the performance of the system with respect to various parameter sets. Here we assume that the secondary users use energy detection method for spectrum sensing.


# 1. INTRODUCTION #

In today’s world, wireless technologies are the basic mean of the communication. Obviously these technologies utilize available wireless channel bands which can be divided into two main groups. These groups are licensed bands and unlicensed bands. Most of the available spectrum is already allocated to current wireless technologies to communicate. However, these bands are not well utilized. So, even though there is available bandwidth to communicate for other emerging technologies since the bands are already allocated to previous technologies, these new technologies cannot have their own frequency bands.

Cognitive Radio (CR) has emerged as a solution for this problem. CR does not require its own licensed band instead it uses unutilized available portion bandwidth of other current technologies (3). CR’s communication is based on detecting spectrum holes of licensed or unlicensed users’ bandwidths. That is, it uses other technologies’ bandwidths while they are not using it themselves. During the communication of CR if a licensed (primary) user tries to use its own bandwidth, CR user changes its communication parameters, such as communication frequency, medium access protocol, to communicate from another available band. This whole communication cycle of CR contains four phases (3):

<ul>
<li> Spectrum Sensing: A CR monitors the available 	spectrum bands, captures their information, and detects the spectrum holes. </li>
<li> Spectrum Decision: Based on the spectrum availability, CR users can determine a channel. This operation not only depends on spectrum availability, but it is also determined based on internal (and possibly external) policies. </li>
<li> Spectrum Sharing: Multiple CR users try to access the spectrum. CR network access should be coordinated in order to prevent multiple users colliding in overlapping portions of the spectrum. </li>
<li> Spectrum Mobility: CR users are regarded as “visitors” to the spectrum. If primary users need a specific portion of the spectrum, then the CR users must continue in another vacant portion of the spectrum.</li>
</ul>

Among these phases spectrum sensing is one of the most important, difficult, and costly task. Because in case of miss detection both primary users’ and CR users’ communication will be garbage. On the other hand, in case of false alarm CR users will not use available channel although there is no primary user uses it, which will cause underutilization of available bandwidth. Due to these problems, in our project we analyze performance of spectrum sensing. Both miss-detection and false alarm are inevitable. In both situations, system suffers from performance loss as in the case of miss-detection, interference level to primary users increases and on the other hand, in the case of false alarm, secondary users waste available spectrum holes (4).

# 2. COGNITIVE RADIO SPECTRUM SENSING #


In spectrum sensing acknowledgment of spectrum sensing results from each of the cognitive users suffices a broad bandwidth since all of them will try to announce their own measurements at the same time. There are some approaches to decrease that bandwidth requirement, such as using CDMA for reporting (1), (5).

Moreover, all collaborative sensing of spectrum in whole cell would cause some inefficient situations in spectrum sharing. For example, while in some region of the cell a frequency is not available, in some other regions it may be available. However, since spectrum sensing is done collaboratively in the entire cell it will be decided that if one frequency is not available in some region of the cell, it is not available anywhere. This situation will cause underutilization of available spectrum, and so inefficiency in bandwidth allocation.

In our project, we developed a simulator to measure the performance of zone-based spectrum sensing approach and to optimize it. In zone based approach, a cognitive radio cell is divided into radial sectors, each sector is divided into radial slices, each slice is divided into sections. This final division constructs zones in the cell.

In each zone all CR users broadcast their sensing measurements with low power. The first CR user who broadcasts first declares itself as the leader of the zone. The selection of the leader of the zone in this manner is achieved without any messaging overhead. Since all CR users in the zone can hear each other, the leader receives the information about spectrum sensing measurements of the other users in the zone. Therefore, the leader has the information of sensing results in the entire zone. Only the leader reports the zone’s sensing decision to the CR base station. Hence, the bandwidth requirement of spectrum sensing result acknowledgement will be minimized.

The zone-based spectrum sensing approach also produces a solution to another problem mentioned above. Since the sensing results are reported for each zone separately, the CR base station can keep track of available frequencies for each zone separately. By doing so, it can decide a frequency as available in one zone while it is not in another. So, CR system can utilize frequencies by assigning them to available zones.

# 3. GENERAL DESIGN OF THE SOFTWARE #

The simulation software we developed consists of two main modules. Each of these modules consists of two parts. Also, there is a shared part that both modules are commonly using. In the following sections, we will describe these modules and parts.

All random numbers and variates, used in simulation, are generated using Colt library developed by CERN (6).

## 3.1. MAIN PROGRAM ##

The main structure of the program consists of parsing input from the user, calling associated module according to the user’s input, and collecting and displaying output statistics. Program parses 5 main groups of input to start simulation. These groups are:

<ul>
<li><i>Main Options:</i>
<ul>
<li>How many primary users will be located in the Cognitive Radio cell and its surroundings</li>
<li>How many secondary users will be located in the Cognitive Radio cell</li>
<li>Duration of the simulation in terms of minutes</li>
<li>Transmit power of users in terms of dB</li>
<li>dB value of the noise floor</li>
<li>Standard variation of the noise</li>
<li>Power threshold for sensing decision in terms of dB</li>
<li>Random seed or constant seed option</li>
<li>Constant seed value if selected</li>
<li>Option to run animation or not</li>
</ul></li>
<li><i>Traffic Options:</i>
<ul>
<li>Traffic model of both primary and secondary users</li>
<li>Expected number of calls per hour</li>
<li>Expected call duration in terms of minutes</li>
</ul></li>
<li><i>Frame Options: (All of duration are in terms of milliseconds)</i>
<ul>
<li>Duration of sensing schedule advertisement</li>
<li>Number of sensing slots and their durations</li>
<li>Duration of sensing result reporting</li>
<li>Duration of communication schedule advertisement</li>
<li>Duration of communication</li>
</ul></li>
<li><i>Frequency Options:</i>
<ul>
<li>Number of available channels</li>
<li>Bandwidth of each channel in terms of KHz</li>
</ul></li>
<li><i>Zone Options:</i>
<ul>
<li>Number of sectors in a cell</li>
<li>Number of slices in each sector</li>
<li>Number of sections in each slice</li>
<li>Radius of the cell in terms of 100 meters</li>
<li>Number of zones to simulate</li>
<li>Position of the zones in the cell</li>
<li>Number of CR users in zones</li>
</ul></li>
</ul>

The program chooses a module according to user whether wants to display animation or not.

## 3.2. MULTITHREADED SIMULATION MODULE ##

This module handles the simulation with animation. It runs two types of threads to perform the simulation. In the following subsections details of these threads are explained.

### 3.2.1. Primary Traffic Generator Thread ###

This thread handles operation of a primary traffic generator node in the simulation. It simply stays idle for a random amount of time. Then, it tries to occupy a free frequency to communicate and after random call duration it releases the frequency it occupied and restarts its cycle unless the simulation is finished. Also, at each time before it starts to communicate it changes its position randomly with respect to its idle duration.

### 3.2.2. Cognitive Radio Traffic Generator Thread ###

This thread handles basic frame operations of all cognitive radio users simultaneously assuming that they are all synchronized. It keeps track of the current frame numbers and if a CR node has a communication event in this frame it handles it. It checks the current communication status of the node, if the node is communicating, it ends its communication; otherwise it sets the node as ready to communicate so that communication schedule advertiser can assign a frequency to that node if possible.

After handling communication events, it schedules sensing frames for all nodes, collects their sensing result, schedule communication frequencies for nodes that are ready to communicate or nodes that are collided in the previous frame. Finally it communicates the nodes and restarts it frame cycle unless the simulation is finished.

Moreover, in order to show collision warnings on the animation screen, it holds recently occurred collisions in memory and displays a warning related to these collisions on the animation screen.

## 3.3. DISCRETE EVENT SIMULATION MODULE ##

This module handles the simulation without animation. It uses a Discrete Event Simulation Framework explained in (7). It constructs two types of simulation entities to perform the simulation. In the following subsections details of these simulation entities are explained.

### 3.3.1. Primary Traffic Generator Simulation Entity ###

This simulation entity handles operation of a primary traffic generator node in the simulation. It uses three types of events to send and receive between simulation entities. Then, it takes actions according to receiving events. The descriptions of these events are as follows:

<ul>
<li><i>Communication Start Event:</i> Upon receiving of this event, primary node’s communication is started. Random call duration for this node is assigned and end communication event for this node is sent.</li>
<li><i>Communication End Event:</i> Upon receiving of this event, primary node’s communication is ended and wait event for this node is sent.</li>
<li><i>Wait Event:</i> Upon receiving of this event random idle duration for this node is assigned and start communication event for this node is sent.</li>
</ul>

Furthermore, when starting communication this entity sends a communication start event to the cognitive radio simulation entity if there is a secondary user who uses the same frequency with this entity’s associated primary user. This way by knowing exact starting time of  the communication of the primary user simulator can evaluate more exact throughput for secondary users communication.

### 3.3.2. Cognitive Radio Traffic Generator Simulation Entity ###

This simulation entity handles operation of a CR traffic generator node in the simulation. It uses seven types of events to send and receive between simulation entities. Then, it takes actions according to receiving events. The descriptions of these events are as follows:

<ul>
<li><i>Sense Schedule Advertise Event:</i> Upon receiving of this event, CR base assigns CR nodes frequencies to sense and entity sends a sensing slot event to itself.</li>
<li><i>Sensing Slot Event:</i> Upon receiving of this event, CR sense the frequencies that are assigned to themselves and entity send a sensing slot event to itself if current slot is  not the last slot or sends sensing result advertise event if the current slot is the last slot.</li>
<li><i>Sensing Result Advertise Event:</i> Upon receiving of this event, CR nodes report their sensing measurements to the CR base and this entity sends a communication schedule event to itself.</li>
<li><i>Communication Schedule Advertise Event:</i> Upon receiving of this event, CR base assigns communication frequencies to the nodes, that collided in the previous frame or that want to start communication in this frame, if possible.</li>
<li><i>Communicate Event:</i> Upon receiving of this event, CR nodes that are currently communicating generates traffic and updates channel capacity if necessary, i.e. if a primary users started communication at the same frequency with that secondary user. Then this entity sends itself sense schedule advertise event.</li>
<li><i>Start Communication Event:</i> Upon receiving of this event, the node associated to this event is declared as ready to communicate.</li>
<li><i>End Communication Event:</i> Upon receiving of this event, random idle duration for the node associated with the arriving event is found and that nodes communication start frame is set. Also the communication frequency of the node is released. Finally, a communication start event for this node is sent to this entity.</li>
</ul>
## 3.4. SHARED PART OF THE MODULES ##

The main shared part of the software consists of communication schedule advertiser for CR nodes, sensing of CR nodes, and reporting of the sensing results.

Communication schedule advertiser part finds free frequencies for each zone and checks whether there is any collided CR node. If so, it first tries to hand-off these collided nodes. After that operation if there are still free frequencies, it assigns them to the nodes that want to start communication in the current frame.

Sensing operations of the CR nodes consists following steps: First of all, if there is a primary user using the channel, the CR users obtains received power from channel model described in FARAMIR project (8). According to this model received power of a user at a distance d apart from the transmitter is computed as follows:

http://cmpe492-cr-sensing-simulation.googlecode.com/files/channelModelFormula.PNG


where P<sub>rx</sub> is received power, P<sub>tx</sub> is transmit power, which is used as -10 dB (9), l<sub>0</sub> is path loss constant (38.4 dB), α is path loss coefficient (3.5 dB/km), d distance between transmitter and receiver and s is Gaussian random variate with mean 0 and standard deviation 8 dB, which represents shadowing in dB.

When there is no transmitter using the sensed channel, received power is computed as Gaussian random variate with mean -85 dB (noise floor), and standard deviation 20 dB (noise deviation).

After sensing signal powers, secondary users compare the received power with the power threshold and decide whether the channel is available or not. At that point, a user deciding that there is no transmission while a primary user is communicating is called miss-detection. On the contrary, a user deciding that there is transmission while no primary user is communicating is called false alarm. Probabilities of these occasions give the sensing reliability and sensing efficiency, respectively (10).

CR base station gathers sensing decisions zone by zone and applies cooperation rule in each zone. Using majority as the cooperation rule yields false alarm and miss-detection probabilities according to the following formulation:

http://cmpe492-cr-sensing-simulation.googlecode.com/files/majorityLogicFormula.PNG

According to these final cooperative decisions, it assigns available frequencies to the secondary users who want to send or receive data.


# 4. RESULTS #

## 4.1. Simulation Parameters and Analysis ##

Table 1 Simulation Parameters
<table border='1'>
<blockquote><tr>
<blockquote><th>Parameter</th>  <th>Value</th>
</blockquote></tr>
<tr>
<blockquote><td>P<sub>tx</sub></td>  <td>-10 dB</td>
</blockquote></tr>
<tr>
<blockquote><td>l<sub>0</sub></td>  <td>38.4 dB (Urban)</td>
</blockquote></tr>
<tr>
<blockquote><td>α</td>  <td>3.5 (Urban)</td>
</blockquote></tr>
<tr>
<blockquote><td>μ<sub>s</sub></td>  <td>0 dB</td>
</blockquote></tr>
<tr>
<blockquote><td>σ<sub>s</sub></td>  <td>8 dB</td>
</blockquote></tr>
<tr>
<blockquote><td>μ<sub>0</sub></td>  <td>-85 dB</td>
</blockquote></tr>
<tr>
<blockquote><td>σ<sub>0</sub></td>  <td>20 dB</td>
</blockquote></tr>
<tr>
<blockquote><td>r<sub>cell</sub></td>  <td>1.5 km</td>
</blockquote></tr>
<tr>
<blockquote><td>N<sub>PU</sub></td>  <td>1500</td>
</blockquote></tr></blockquote>

</table>

Simulation parameters are shown in Table 1 where N<sub>PU</sub> stands for the number of PUs. In the simulations, statistics are collected in terms of false alarm, missed detection, collision, blocking of SUs, dropping of SUs, and channel utilization by both SUs and PUs. The
reader should note that we do not plot all probability values for each zone. Instead, we plot their mean against the number of SUs.

In the simulations, radius of the cognitive radio cell is taken as 1.5 km, and the number of PUs is 1500. Also, the PUs are deployed in a wider area than the SUs. They are deployed in a circle with approximately 5 km radius since a PU outside of this area cannot be sensed by SUs for the given simulation parameters.

## 4.2. P<sub>F</sub> ANALYSIS ##

![http://cmpe492-cr-sensing-simulation.googlecode.com/files/pf.png](http://cmpe492-cr-sensing-simulation.googlecode.com/files/pf.png)

The figure above illustrates the false alarm probability against the number of SUs in the cell for no zone (single zone) and 36 zones cases. The probability of false alarm decreases with the increasing number of SUs in the 36 zones case because the reliability of the cooperation decision increases with number of users cooperating. This result is expected according to the mathematical model since the cooperative false alarm probability is obtained by taking powers of local false alarm probabilities. However, with increasing number of SUs in the no zone case  P<sub>F</sub> does not change much since there are already too many users cooperating. Although the false alarm probability for the 36 zones case is higher than that of the no zone case, it is still well below the 0.10 bound defined by the standard (11).

## 4.3. P<sub>M</sub> AND P<sub>C</sub> ANALYSIS ##

![http://cmpe492-cr-sensing-simulation.googlecode.com/files/pm_c.png](http://cmpe492-cr-sensing-simulation.googlecode.com/files/pm_c.png)

The figure above illustrates the missed detection and collision probabilities against the number of SUs in the cell for no zone and 36 zones cases. We observe that, in 36 zones case, the probability of the missed detection also decreases with increasing number of SUs as in the same manner for the probability of false alarm. We also observe that collision probabilities (P<sub>C</sub>) never exceed the miss-detection probabilities. This is an expected result since all collisions are caused due to missed detections. Another observation from the figure is that, after some point, P<sub>C</sub> starts increasing. That is why the system gets overloaded at that point. However, in the no zone case, these probabilities again remains still with increasing number of SUs such that P<sub>M</sub> remains above 0.20 and P<sub>C</sub> remains above 0.10 all the time. This is why, in the no zone case, for the proper detection, the PU should cause interference at least half of the SUs. Otherwise, although the closer SUs to the PU realize that the frequency is not available, due to cooperation the overall decision will be for the availability of the frequency, resulting in a missed detection and a probable collision. Therefore, applying cooperation across the cell increases the probabilities of both miss detection and collision.

## 4.4. BLOCKING AND DROPPING ANALYSIS ##

![http://cmpe492-cr-sensing-simulation.googlecode.com/files/pb_d.png](http://cmpe492-cr-sensing-simulation.googlecode.com/files/pb_d.png)

The figure above illustrates the blocking and dropping probabilities against number of SUs in the cell. The dropping probability of no zone case is lower than 36 zones due to greater P<sub>M</sub> and P<sub>C</sub> of the no zone case. Since P<sub>M</sub> and P<sub>C</sub> are higher for the no zone case, the SUs do not detect the collision. Therefore, they do not try to vacate the communication frequency they use, resulting in fewer drops than 36 zones case. On the other hand, blocking probability of no zone case is higher than 36 zones case. This situation occurs since in the 36 zones case, cognitive radio base station can decide that some frequencies are available in some zones. However, in the no zone case the cognitive radio base station decides for the whole cell. As a result, if a frequency is not available for half of the cell, it is  not used in the entire cell. This situation causes cognitive radio users to find less spectrum holes to initiate a communication and increases their blocking probability.

## 4.5. UTILIZATION ANALYSIS ##

![http://cmpe492-cr-sensing-simulation.googlecode.com/files/util.png](http://cmpe492-cr-sensing-simulation.googlecode.com/files/util.png)

The figure above illustrates the utilization of PUs and SUs with respect to the number of SUs in the cell for the no zone and the 36 zones cases. In both cases, SUs utilize the spectrum almost equally. However, in the no zone case, SUs harm primary communication due to the high P<sub>M</sub> and P<sub>C</sub> values. Note that the total utilization of PUs and SUs can exceed 100\% because they can use the same frequency simultaneously if they are far enough to experience limited interference which leads no collision.

# 5. CONCLUSION #
Being aware of surroundings is the most crucial part of the CR system since it includes sensing the environment for primary activity, finding spectrum holes and vacating the channel or adjusting the communication parameters which is a must for not to disturb the primary users.

In this project, we focused on distributed cooperative sensing with majority cooperation rule. We observed that with a few number of SUs (350 or less), requirement determined by standards (both probability of false alarm and missed detection should be less than 10 percent (11)) cannot be satisfied. With increasing number of SUs satisfying these requirements gets easier but when system load becomes excessive (over 90 percent) quality of service for SUs decreases severely in terms of blocking and dropping. Moreover, due to overload in wireless channel, collision probabilities start to increase after that point.

Since the last progress report, following operations are completed about the simulator:
<ul>
<li>Sensing capabilities for CR nodes is added.</li>
<li>Detection with binary hypothesis testing is added.</li>
<li>Probability of false alarm and miss detection calculation is added.</li>
<li>Channel model is updated to a more realistic one.</li>
<li>Cooperative sensing is added.</li>
</ul>


# BIBLIOGRAPHY #
1. Cooperative Spectrum Sensing with Realistic Reporting Channel. X. Li, G. Zhao, X. Wang, and C. Yang. s.l. : IEEE, 2010. ICSP Proceedings.

2. Review of Spectrum Sensing in Cognitive Radio. Herath, L. N. T. Perera and H. M. V. R. Sri Lanka : International Conference on Industrial and Information Systems, ICIIS, 2011.

3. Cognitive Radio: Brain-Empowered Wireless Communications. Haykin, S. s.l. : IEEE JOURNAL ON SELECTED AREAS IN COMMUNICATIONS, 2005, Vol. 23.

4. Optimization of Parameters for Spectrum Sensing in Cognitive Radios. Y. Wang, C. Feng, C. Guo, F. Liu. Beijing : Beijing University of Posts and Telecommunications, IEEE, 2009.

5. Cooperative Spectrum Sensing in a Realistic Cognitive Radio Environment. Liza.J, K.Muthumeenakshi, Radha.S. s.l. : IEEE-International Conference on Recent Trends in Information Technology, 2011.

6. Colt 1.2.0 - API Specification. [Online](Online.md) CERN. [Cited: 01 15, 2012.] http://acs.lbl.gov/software/colt/api/index.html.

7. B. Khan, A. Al-Fuqaha, M. Guizani, A. Rayes. Network Modelling and Simulation. s.l. : WILEY, 2010.

8. Polydoros, I. Dagres and A. Flexible and Spectrum Aware Radio Access through Measurements and Modelling in Cognitive Radio Systems (FARAMIR).

9. Cognitive Radio-Based Urban Wireless Broadband in Unused TV Bands. Nekovee, S. Kawade and M. s.l. : 20th International Conference Radioelektronika, 2010.

10. Spectrum Sensing in Cognitive Radio Networks: Up-to-Date Techniques and Future Challenges. Fernando, S. Hussain and X. s.l. : TIC-STH, 2009.

11. Functional Requirements for the 802.22 WRAN Standards. s.l. : IEEE Std. 802.22, 2006.

12. jPlot - A Java Plotting Tool. [Online](Online.md) [Cited: 01 15, 2012.] http://tcptrace.org/jPlot/.