# Report of Meeting Saturday, Nov 26, 2011 #
Sensing functionality for CR users is added:
<dl>
<blockquote><dt>After retrieving SNR value from wireless channel CR nodes computes a random energy value from Chi Square distribution with degree of freedom TW and noncentralality SNR. Then by comparing this value with a threshold they decide whether the channel is vacant or not.</dt>
</dl></blockquote>

Throughput calculation of CR nodes is implemented:
<dl>
<blockquote><dt>First of we implemented accurate detection of the time of a collision and its reporting. We removed periodic reporting of SINR values of communicated channels. For DES module accurate detection of collision time is implemented as:</dt>
<blockquote><dd>1. At the beginning of the communicate just store current SINR value and time</dd>
<dd>2. If anywhere else compute the time elapsed since the last report and channel capacity with last SINR value. Then compute the bits transmitted during that duration and accumulate that bits. Finally, update current SINR value and time.</dd>
<dd>3. At the end of simulation divide total number of bits transmitted with total communication duration.</dd>
</dl></blockquote></blockquote>



---



# Report of Meeting Saturday, Nov 19, 2011 #
New statistics for CR nodes are calculated:
<dl>
<blockquote><dt>1. Collision probability of a CR node calculated as total number of collisions over total number of calls.</dt>
<dt>2. Drop probability of a CR node calculated as total number of drops over total number of calls.</dt>
<dt>3. Block probability of a CR node calculated as total number of blocks over total number of call attempts.</dt>
</dl></blockquote>

Some changes are done on the animation screen:
<dl>
<blockquote><dt>1. Images of CR nodes are changed.</dt>
<dt>2. Collisions of CR nodes are demonstrated.</dt>
</dl></blockquote>



---


# Report of Meeting Sunday, Nov 13, 2011 #
General design of the project is revised, and progress report is prepared.


---


# Report of Meeting Tuesday, Nov 1, 2011 #
Output statistics that are given at the stats table at the end of the simulation are revised as follows:
<dl>
<blockquote><dt><b>1. CR Node Statistics:</b></dt>
<blockquote><dd>1.1. Number of call attempts</dd>
<dd>1.2. Number of calls</dd>
<dd>1.3. Number of frames communicated</dd>
<dd>1.4. Number of blocks</dd>
<dd>1.5. Number of drops</dd>
<dd>1.6. Number of force hand-offs</dd>
<dd>1.7. Number of collisions</dd>
</blockquote><dt><b>2. Primary node statistics:</b></dt>
<blockquote><dd>2.1. Utilization</dd>
<dd>2.2. Number of calls</dd>
</dl></blockquote></blockquote>



---


# Report of Meeting Saturday, Oct 22, 2011 #

## Issues & Decisions ##
In order to keep track of the statistics about secondary users' force handoffs and drops the following is implemented:
<dl>
<blockquote><dt>1. Variables for force handoff count and drop count are created. A boolean variable that holds information about last frames collision is also created.</dt></blockquote>

<blockquote><dt>2. At the beginning of communication schedule advertisement collided secondary users are given priority over others and tried to be given free frequency. If they can handoff to another available frequency, number of force handoffs is incremented. Otherwise, number of drops is incremented.</dt></blockquote>

<blockquote><dt>3. If secondart user drops, its communication end event is cancelled and a new communication start event is created.</dt></blockquote>

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
</table></blockquote>


---


# Report of Meeting Tuesday, Oct 18, 2011 #

## Issues & Decisions ##
Poisson traffic implementation for secondary users at multithreaded simulation module is completed by implementing the followings:
<dl>
<blockquote><dt>1. An array that holds communication start & communication end times (depending on user's current communication state) of CR user is added to CR sensor thread class.</dt></blockquote>

<blockquote><dt>2. First communication start times for each CR users is found at the beginning of the simulation.</dt></blockquote>

<blockquote><dt>3. If a CR user is currently not communicating the value in the array indicates communication start time for that user. If s/he is currently communicating then the value in the array indicates communication end time.</dt></blockquote>

<blockquote><dt>4. After the initial state all random number generations occur at the beginning of the frame loops.</dt></blockquote>

<blockquote><dt><b>PS:</b> All communication and off times of the users are calculated in terms of frames.</dt>
</dl></blockquote>


---


# Report of Meeting Sunday, Oct 16, 2011 #

## Issues & Decisions ##
Necessary changes in communication schedule advertisement and sensing schedule advertisement to simulate more than one zone in the simulation is implemented as follows:
<dl>
<blockquote><dt>1. The array that holds the average SNR values of the previous frame is implemented as two dimensional array. First dimension represents zones and second dimension represents frequencies. The same approach is also applied for the array which holds the average SNR values of the current frame.</dt></blockquote>

<blockquote><dt>2. Free frequency list is divided for each zone. If there is no node in a zone then no free frequency is assigned to that zone.</dt></blockquote>

<blockquote><dt>3. After free frequencies are decided for each zone, a frequency is assigned to a zone which has the minimum average SNR value for that frequency.</dt></blockquote>

<blockquote><dt>4. When the number of assigned frequencies to a zone becomes equal to the number of secondary users who want to start communication, no more frequencies is assigned to that zone.</dt></blockquote>

<blockquote><dt>5. Secondary users who are not given any frequency to communicate are reported as blocked.</dt></blockquote>

<blockquote><dt><b>PS:</b> Note that all these SNR values are due to primary nodes.</dt>
</dl>