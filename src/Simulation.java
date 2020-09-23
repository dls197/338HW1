/*
 * By Hoang Do, Daniel Schnabel, Sherry Zhu
 */
package simulator;
import java.util.Scanner;

public class Simulation {
	static public String charts = "n";
	static private CPUtypeA CPU1 = new CPUtypeA();
	static private CPUtypeB CPU2 = new CPUtypeB();
	static private CPUtypeA_alt CPU3 = new CPUtypeA_alt();
	static private CPUtypeB_alt CPU4 = new CPUtypeB_alt();
	
	public static void main(String[] args) throws InterruptedException  {
		Scanner scan = new Scanner(System.in);
		System.out.println("Would you like to see a chart with each process in order of completion? [y/n] ");
		charts = scan.nextLine();
		for(int i =0; i<5;i++) {
			CPU1.newProcess();
			CPU2.newProcess();
			CPU3.newProcess();
			CPU4.newProcess();
		}

		System.out.println("Queue-selection-type preemption (type A) sorted by priority:");
		CPU1.CPUrun();
		System.out.println("Cpu-time-slice-interrupt preemption (type B) sorted by priority:");
		CPU2.CPUrun();
		System.out.println("Queue-selection-type preemption (type A) sorted by arrival order:");
		CPU3.CPUrun();
		System.out.println("Cpu-time-slice-interrupt preemption (type B) sorted by arrival order:");
		CPU4.CPUrun();

		System.out.println();
		System.out.println("N.B. Wait Time and Response Time are the same as none of the CPUs use a round robin style scheduling system");
	}
}
