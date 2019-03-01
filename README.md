# Repository for work written as Part of UofG COMPSCI40xx.

## Contains all code for the end of semester Assessed Exercise.

This assignment involves creating a peer to peer network and:

  *"implementing an application which will read a file containing a simple human-readable (and writable) description of such a network and then simulate distance-vector routing with synchronised periodic exchanges and updating of node routing tables, over any selected number of iterations. With synchronised periodic exchanges, all nodes exchange their vectors simultaneously and then update tables, repeating this process at regular intervals"*

  The following requirements were provided in the assignment brief:
* compute routing tables for any preset number of exchanges or until stability is achieved;
* preset any link to change cost or fail after any chosen exchange (you may assume for simplicity that neighbours notice cost changes or unreachable neighbours immediately);
* view the best route between any source and destination after any chosen iteration
* trace the routing tables of any set of nodes for any specified number of iterations in a way that can
be easily viewed;
* engage, on request, a split-horizon capability to help combat slow convergence
* run the application under Windows 10 x64.

The assignment will be done in Java to allow for an Object Oriented Implementation.

TODO:
* ~~Create Nodes Class.~~
* ~~Create Links Class.~~
* Allow Reading from file.
* Provide tools to create network i.e.:
  * Create Node instruction.
  * Create Link instruction.
* Create method to calculate routing tables.
* Script in link failures and alterations.
* Interrogate simulation for best route between any two nodes.
* TraceRoot
* Implement a split horizon system.
* Test on Windows 10.
* Multithreading.
