package jp.ac.hal;

public class OperPriority {
	public int OperPriority01(String priority) {
		int pri01 = 0;
		if (priority.equals("+") || priority.equals("-")) {
			pri01 = 1;
		}else {
			pri01 = 2;
		}
		return pri01;
	}
	
	public int OperPriority02(String priority) {
		int pri02 = 0;
		if (priority.equals("+") || priority.equals("-")) {
			pri02 = 1;
		}else {
			pri02 = 2;
		}
		return pri02;
	}
}
