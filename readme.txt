243 OOP: Project 02 README
===============================
(please use the RETURN key to make multiple lines; don't assume autowrap.)

0. Author Information
---------------------

CS Username:	zxy1677 txl2747		Name:  	Ziwei Ye Tommy Li

1. Problem Analysis
---------

Summarize the analysis work you did. 
What new information did it reveal?  How did this help you?
How much time did you spend on problem analysis?

We made a UML-like graph on some paper to figure out what files, methods and orders we should code into the project. 
It is revealed through the process that the account system could heavily utilize polymorphism and inheritance for
different desposit, withdraw, and format methods. It is revealed that the GUI could use a state machine where each step
of an ATM transaction is a state. 1 or so hour was spent on problem analysis, looking over the requirements on the website
 and scraching stuff onto a paper.

2. Design
---------

Explain the design you developed to use and why. What are the major 
components? What connects to what? How much time did you spend on design?
Make sure to clearly show how your design uses the MVC model by
separating the UI "view" code from the back-end game "model".

We used a modular design principle where any separable part forms a modular part of a framework. This includes the MVC design
for ATM and bank, where the model of each are the observables, and their complementry views are the observers. The Bank GUI encapsulates
 the bank model inside and adds itself as an observer of the model, and each time a method from the bank model calls setChanged
, the GUI also updates. The Bank GUI is capable of opening up a ATM GUI with an ATM model and the bank model encapsulated inside the ATM model,
 which the ATM modifies.

3. Implementation and Testing
-------------------

Describe your implementation efforts here; this is the coding and testing.

What did you put into code first?
How did you test it?
How well does the solution work? 
Does it completely solve the problem?
Is anything missing?
How could you do it differently?
How could you improve it?

How much total time would you estimate you worked on the project? 
If you had to do this again, what would you do differently?

We developed the account system first and the bank model after that to cement a proper base for future code.
 We tested the system by creating the batch mode system after, which is a system of readers, formatters, and
 writers that either reads or write a terminal or a file. Now with a proper I/O to test the system, we made
 test bank files to test the program. After making sure everything works, we developed the ATM model, the
 bank view, and the ATM view to create a function GUI and tested it rigrously. The solution does what it 
supposes to do and there is nothing missing. The codebase is a bit messy and variable names can be more specific.

4. Development Process
-----------------------------

Describe your development process here; this is the overall process.

How much problem analysis did you do before your initial coding effort?
How much design work and thought did you do before your initial coding effort?
How many times did you have to go back to assess the problem?

What did you learn about software development?

We did enough problem analysis and design work to create a roadmap to figure out what needs to done in what order
, and what data structures we should use to create the mechanisms to code pieces by pieces. We learned state-based 
GUI prgramming and design that untangles and simplify as much as possible. We figured out how to do the hardest part
 of GUI programming. We learned proper time scheduling in order to complete project in time.

