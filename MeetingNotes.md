# Report of Meeting Monday, Feb 27, 2012 #

## Issues & Decisions ##
<dl>
<blockquote><dt>1. Dynamic zone approach and modelling zones as clusters are discussed.</dt></blockquote>

<blockquote><dt>2. Optimization steps of sensing scheduling are discussed.</dt></blockquote>

<blockquote><dt>3. In order to solve clustering problem GAMS model implementation for exact solution and implementation of heuristic from <i><b>K. Fleszar, K.S. Hindi, "An effective VNS for the capacitated p-median problem", European Journal of Operational Research (2008)</b></i> for fast but sub-optimal solution is decided.</dt>
</dl>
<h2>Attendees</h2>
<ul><li>Bilal Acar <i>(Developer)</i>
</li><li>Mehmet Akif Ersoy <i>(Developer)</i>
</li><li>Necati Aras <i>(Supervisor)</i>
</li><li>Tuna Tugcu <i>(Supervisor)</i>
</li><li>Salim Eryigit <i>(Assistant Supervisor)</i>
<hr /></li></ul></blockquote>


# Report of Meeting Thursday, Dec 29, 2011 #

## Issues & Decisions ##
<dl>
<blockquote><dt>1. Graphs obtained from the runs decided at the previous meeting are discussed.</dt></blockquote>

<blockquote><dt>2. The reason of the spikes in the graphs is discussed. It is assumed that these spikes are caused due to majority rule, used for cooperative decision, such that while the number of users sensing a frequency is odd, the majority rule gives better results. Therefore, keeping number of users sensing a channel as odd at every run and then inspect the results is decided.</dt></blockquote>

<blockquote><dt>3. A formulation for sensing scheduling is discussed:</dt><dd>3.1. First divided users in the cell into clusters</dd><dd>3.2. According to number of users in the cluster decide number of frequencies to be sensed in this cluster. Also decide which frequencies to be sensed.</dd><dd>3.3. Maximize the seperation of the users that sense the same frequency in order to obtain better results.</dd>
</dl>
<h2>Attendees</h2>
<ul><li>Bilal Acar <i>(Developer)</i>
</li><li>Mehmet Akif Ersoy <i>(Developer)</i>
</li><li>Tuna Tugcu <i>(Supervisor)</i>
</li><li>Salim Eryigit <i>(Assistant Supervisor)</i>
</li><li>Birkan Yilmaz <i>(Assistant Supervisor)</i>
<hr /></li></ul></blockquote>


# Report of Meeting Thursday, Dec 22, 2011 #

## Issues & Decisions ##
<dl>
<blockquote><dt>1. Graphs obtained from the runs decided at the previous meeting are discussed.</dt></blockquote>

<blockquote><dt>2. Due to the spikes in the graphs and too high block and drop probability values changing some parameters is decided.</dt></blockquote>

<blockquote><dt>3. In order to reduce the spikes in the false alarm and miss-detection graphs incresing the gap between the number of users is decided.</dt></blockquote>

<blockquote><dt>4. In order to fix abnormalarities in the graphs:</dt><dd>4.1. Increasing number of frequencies to 60~80,</dd><dd>4.2. Increasing the noise floor value to -70~-80 dB,</dd><dd>4.3. Reducing the variance of the noise to 15~20 dB,</dd>is decided.</blockquote>

<blockquote><dt>5. Plotting also the utilization of primary, secondary, and total users is decided.</dt>
</dl>
<h2>Attendees</h2>
<ul><li>Bilal Acar <i>(Developer)</i>
</li><li>Mehmet Akif Ersoy <i>(Developer)</i>
</li><li>Tuna Tugcu <i>(Supervisor)</i>
</li><li>Salim Eryigit <i>(Assistant Supervisor)</i>
</li><li>Birkan Yilmaz <i>(Assistant Supervisor)</i>
<hr /></li></ul></blockquote>


# Report of Meeting Thursday, Dec 8, 2011 #

## Issues & Decisions ##
<dl>
<blockquote><dt>1. Improvements in the project, after the previous meeting, are explained.</dt></blockquote>

<blockquote><dt>2. Increasing the number of both primary and secondary users in the simulation is decided.</dt></blockquote>

<blockquote><dt>3. Allocating all possible zones in the cell is decided. In that scheme secondary users will be distributed over cell homogeneously.</dt></blockquote>

<blockquote><dt>4. Increasing the area where primary users are locating is decided. The area will be increased such that a primary user in the farthest point of that area could barely cause an interference to a secondary user which is at the border of its cell.</dt></blockquote>

<blockquote><dt>5. Keeping false alarm and miss-detection probabilities under 0.1 is decided.</dt></blockquote>

<blockquote><dt>6. Reducing the number of data points on the output graphs, i.e. taking batch outputs, is decided. Number of batches will be 30.</dt></blockquote>

<blockquote><dt>5. Changing the current channel model is decided. The channel model in FARAMIR project will be used. According to that model P<sub>rx</sub> = P<sub>tx</sub> - l<sub>0</sub> - alpha*log(d) + s, where P<sub>rx</sub> is received power, P<sub>tx</sub> is transmit power, l<sub>0</sub> is constant (38.4), alpha is path loss exponent (constant 35), d is distance between receiver and transmitter in kilometers, and s is a random variate which is distributed Normal(0,8). All of the parameters are in dB.</dt></blockquote>

<blockquote><dt>6. Running the program with the same parameter except changing the number of primary and secondart users is decided. After that runs graph of probability of false alarm, miss-detection, collision, block, and drop vs number of users will be plotted.</dt>
</dl>
<h2>Attendees</h2>
<ul><li>Bilal Acar <i>(Developer)</i>
</li><li>Mehmet Akif Ersoy <i>(Developer)</i>
</li><li>Tuna Tugcu <i>(Supervisor)</i>
</li><li>Salim Eryigit <i>(Assistant Supervisor)</i>
</li><li>Birkan Yilmaz <i>(Assistant Supervisor)</i>
<hr /></li></ul></blockquote>


# Report of Meeting Thursday, Dec 1, 2011 #

## Issues & Decisions ##
<dl>
<blockquote><dt>1. Improvements in the project, after the previous meeting, are explained.</dt></blockquote>

<blockquote><dt>2. From now on making weekly meetings at Thursday between 1pm and 2pm is decided.</dt></blockquote>

<blockquote><dt>3. Assignments for the next meeting is decided:</dt><dd>3.1. Implementing batch mode run from console without using GUI.</dd><dd>3.2. Changing collision detection time. Make collision detection according to sensing decision at communication scheduling.</dd><dd>3.3. P<sub>F</sub> and P<sub>M</sub> calculation for each frequency zone by zone.</dd><dd>3.4. Diverse likelihood of primary communication frequency selection.</dd></blockquote>

<blockquote><dt>4. Making optimization over choosing which frequency to sense at each sensing slot is decided.</dt>
</dl>
<h2>Attendees</h2>
<ul><li>Bilal Acar <i>(Developer)</i>
</li><li>Mehmet Akif Ersoy <i>(Developer)</i>
</li><li>Tuna Tugcu <i>(Supervisor)</i>
</li><li>Salim Eryigit <i>(Assistant Supervisor)</i>
</li><li>Birkan Yilmaz <i>(Assistant Supervisor)</i>
<hr /></li></ul></blockquote>


# Report of Meeting Thursday, Nov 3, 2011 #

## Issues & Decisions ##
<dl>
<blockquote><dt>1. Improvements in the project, after the previous meeting, are explained.</dt></blockquote>

<blockquote><dt>2. Calculation of the throughput of CR users in the simulation is discussed:</dt><dd>2.1. <a href='http://en.wikipedia.org/wiki/Shannon_capacity'>Shannon's Capacity Formula</a> is going to be used for throughput calculation.(Assuming that we have 40 channels with 8MHz Bandwidth)</dd></blockquote>

<blockquote><dt>3. Assignments for the next meeting is decided:</dt>
<dd>3.1. Adjusting simulation parameters to observe secondary-primary users relations within an environment which is much closer to the real-world. These changes are:</dd>
<dd><dl>
<dd> 3.1.1. Increase the number of frequencies and the number of primary-secondary users.</dd>
<dd> 3.1.2. Observe the drop and block probabilities of the secondary users from the beginning of the simulation and determine the point where these probabilities are stabilized; and ignore all of the data gathered before this point(warm-up period of the system)</dd>
</dl></dd>
<dd>3.2. Implement sensing for the secondary users.</dd>
<dd>3.3. Show collisions in the animation(with a time delay)</dd>
<dd>3.4. While animation is running, also show the related plots and update them simultaneously. </dd>
<dd>3.5. Write the progress report regarding what is done so far, and what is next. </dd>
</dl>
<h2>Attendees</h2>
<ul><li>Bilal Acar <i>(Developer)</i>
</li><li>Mehmet Akif Ersoy <i>(Developer)</i>
</li><li>Tuna Tugcu <i>(Supervisor)</i>
</li><li>Birkan Yilmaz <i>(Assistant Supervisor)</i>
<hr />
<h1>Report of Meeting Tuesday, Oct 11, 2011</h1></li></ul></blockquote>

## Issues & Decisions ##
<dl>
<blockquote><dt>1. Summary of project so far is done.</dt></blockquote>

<blockquote><dt>2. Summary of papers read is done.</dt></blockquote>

<blockquote><dt>3. Assignments for the next meeting is decided:</dt>
<dd>3.1. Taking statistics from the point of view of Simulator. These statistics are:</dd>
<dd><dl>
<dd> 3.1.1. Throughput of both primary users and secondary users.</dd>
<dd> 3.1.2. Secondary Drop when primary arrives and no available channel found.</dd>
<dd> 3.1.3. Secondary Force Hand-off when primary arrives and another available channel found.</dd>
<dd> 3.1.4. Secondary Block when there is no available channel to communicate.</dd>
<dd> 3.1.5. Collisions of primary and secondary users.</dd>
</dl></dd>
<dd>3.2. Unit times will be specified.</dd>
<dd>3.3. Documentation, class diagram, and flow chart will be completed.</dd>
</dl>
<h2>Attendees</h2>
<ul><li>Bilal Acar <i>(Developer)</i>
</li><li>Mehmet Akif Ersoy <i>(Developer)</i>
</li><li>Tuna Tugcu <i>(Supervisor)</i>
</li><li>Birkan Yilmaz <i>(Assistant Supervisor)</i>