/*
 * By Hoang Do, Daniel Schnabel, Sherry Zhu
 */
package simulator;
import java.util.*;
import java.util.concurrent.TimeUnit;
import simulator.Process;
class priorityAO implements Comparator<Process>{
	public int compare(Process process1, Process process2) {
		return process2.getArrivalOrder()-process1.getArrivalOrder();
	}
}
class priority implements Comparator<Process>{
	public int compare(Process process1, Process process2) {
		return process2.getPriority()-process1.getPriority();
	}
}	

public class CPUtypeA{ 
	private PriorityQueue<Process> readyQueue = new PriorityQueue<Process>(100,new priority());
	private int arrivalOrder = 0;
	private int processCount = 0;
	private int cycleCount = 0;
	private int switchTime = 0;
	Random rand = new Random();
	private int interval = rand.nextInt(5)+1;
	private int totalArrivalRate = interval;
	private Process[] list = new Process[100];
	private int finishedProcess = 0;
	
	public CPUtypeA() {
		
	} 
	
	public void newProcess() {
		Process newProcess = new Process(rand.nextInt(5)+1, rand.nextInt(51), cycleCount, arrivalOrder);
		readyQueue.add(newProcess);
		arrivalOrder++;
	}
	public void CPUrun() throws InterruptedException {
		Process currentRunning = null;
		while(true) {
			if(!readyQueue.isEmpty()) {
				currentRunning = readyQueue.poll();
				cycleCount++;
				TimeUnit.MILLISECONDS.sleep(1);
				switchTime++;
				while(currentRunning.getRemainingTime()>0) {
					currentRunning.run();
					cycleCount++;
					interval--;
					if(interval == 0 && processCount < 95) {
						interval = rand.nextInt(5)+1;
						totalArrivalRate += interval;
						processCount++;
						newProcess();
					}
				}
				currentRunning.setCompletionTime(cycleCount);
				list[finishedProcess] = currentRunning;
				finishedProcess++;
			}else {
				interval--;
				cycleCount++;
				TimeUnit.MILLISECONDS.sleep(1);
				if(interval == 0 && processCount < 95) {
					interval = rand.nextInt(5)+1;
					totalArrivalRate += interval;
					processCount++;
					newProcess();
				}
				else if (processCount >= 95) {
					break;
				}
			}
		
		}

		int highpriowait = 0;
		int lowpriowait = 0;
		int highpriocounter = 0;
		int totburst = 0;
		int totTAT = 0;
		int totwait = 0;
		System.out.println("Total Cycle Count:"+cycleCount);
		System.out.println("Percentage of Time Spent on Context Switching:"+(float)switchTime/(float)cycleCount*100.0+"%");
		if(Simulation.charts.equals("y")) {
			System.out.println("Each process in order of completion:");
			System.out.println("Priority \t Arrival Order \t Arrival Time \t Completion Time \t Burst Time \t Turn Around Time \t Wait Time");
		}
		for (int i=0; i<100;i++ ) {
			if(Simulation.charts.equals("y")) {
				System.out.println(list[i].getPriority() + "\t\t\t\t" + list[i].getArrivalOrder() + "\t\t\t\t" + list[i].getArrivalTime() + "\t\t\t\t" +
						list[i].getCompletionTime() + "\t\t\t\t\t" + list[i].getBurstTime() + "\t\t\t\t" + (list[i].getCompletionTime() - list[i].getArrivalTime()) + "\t\t\t\t" +
						(list[i].getCompletionTime() - list[i].getArrivalTime() - list[i].getBurstTime()));
			}
			if(list[i].getPriority()>25){
				highpriowait +=list[i].getCompletionTime()-list[i].getArrivalTime()-list[i].getBurstTime();
				highpriocounter++;
			}
			else{
				lowpriowait +=list[i].getCompletionTime()-list[i].getArrivalTime()-list[i].getBurstTime();
			}
			totburst +=list[i].getBurstTime();
			totTAT += list[i].getCompletionTime()-list[i].getArrivalTime();
			totwait += list[i].getCompletionTime()-list[i].getArrivalTime()-list[i].getBurstTime();
		}
		System.out.println("Average Burst-Completion Time: "+(float)totTAT/(float)(processCount+5));
		System.out.println();
		System.out.println("Average Arrival Rate:"+(float)totalArrivalRate/(float)processCount);
		System.out.println("Turn Around per Burst Time: "+(float)totTAT/(float)(totburst));
		System.out.println("Wait per Burst Time: "+(float)totwait/(float)totburst);
		System.out.println("Average Wait Time: "+(float)totwait/(float)(processCount+5));
		System.out.println("Average High Priority Wait Time: "+(float)highpriowait/(float)highpriocounter);
		System.out.println("Average Low Priority Wait Time: "+(float)lowpriowait/(100.0-(float)highpriocounter));
		System.out.println();
		System.out.println();
	}
	
}
