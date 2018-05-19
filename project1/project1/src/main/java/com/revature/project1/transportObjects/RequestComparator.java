package com.revature.project1.transportObjects;

import java.util.Comparator;

public class RequestComparator<T extends Request> implements Comparator<T>{

	@Override
	public int compare(T req1, T req2) {
		int e1 = req1.getEmployeeId();
		int e2 = req2.getEmployeeId();
		int r1 = req1.getRequestId();
		int r2 = req2.getRequestId();
		
		if (e1 == e2) {
			return (r1 == r2) ? 0 : ((r1 < r2) ? -1 : 1);
		} else if(e1 < e2) {
			return -1;
		} else {
			return 1;
		}
	}
}
