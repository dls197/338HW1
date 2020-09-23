/*
 * By Hoang Do, Daniel Schnabel, Sherry Zhu
 */
package simulator;
import java.util.concurrent.TimeUnit;

public class Process {
	private int RemainingTime;
	private int Priority;	
	private final int arrivalTime;
	private final int arrivalOrder;
	private int completionTime;
	private final int burstTime;
	public Process(int RemainingTime, int Priority, int arrivalTime, int arrivalOrder) {
		this.RemainingTime = RemainingTime;
		this.Priority = Priority;
		this.arrivalTime = arrivalTime;
		this.arrivalOrder =arrivalOrder;
		this.burstTime = RemainingTime;
	}
	public void run() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(1);
		RemainingTime--;
	}
	public int getRemainingTime() {
		return RemainingTime;
	}
	public int getBurstTime() {
		return burstTime;
	}
	public int getArrivalTime() {
		return arrivalTime;
	}
	public int getArrivalOrder() {
		return arrivalOrder;
	}
	public int getPriority() {
		return Priority;
	}
	public void setCompletionTime(int x) {
		completionTime = x;
	}
	public int getCompletionTime() {
		return completionTime;
	}
}