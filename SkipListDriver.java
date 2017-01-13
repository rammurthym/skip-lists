/** 
 * Driver program for Skip List implementation.
 *
 * @author Rammurthy Mudimadugula
 * @version 1.0
 * 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

//Driver program for skip list implementation.

public class SkipListDriver {
    public static void main(String[] args) throws FileNotFoundException {
	Scanner sc;
	if (args.length > 0) {
	    File file = new File(args[0]);
	    sc = new Scanner(file);
	} else {
	    sc = new Scanner(System.in);
	}
	String operation = "";
	long operand = 0;
	int modValue = 999983;
	long result = 0;
	Long returnValue = null;
	SkipListImpl<Long> skipList = new SkipListImpl<>();
	// Initialize the timer
	Timer timer = new Timer();

	while (!((operation = sc.next()).equals("End"))) {
	    switch (operation) {
	    case "Add": {
		operand = sc.nextLong();
		if(skipList.add(operand)) {
		    result = (result + 1) % modValue;
		    // System.out.println("32: " + result);
		}
		break;
	    }
	    case "Ceiling": {
		operand = sc.nextLong();
		returnValue = skipList.ceiling(operand);
		if (returnValue != null) {
		    result = (result + returnValue) % modValue;
		    // System.out.println("41: " + result);
		}
		break;
	    }
	    case "FindIndex": {
		int intOperand = sc.nextInt();
		returnValue = skipList.findIndex(intOperand);
		if (returnValue != null) {
		    result = (result + returnValue) % modValue;
		    // System.out.println("50: " + result);
		}
		break;
	    }
	    case "First": {
		returnValue = skipList.first();
		if (returnValue != null) {
		    result = (result + returnValue) % modValue;
		    // System.out.println("58: " + result);
		}
		break;
	    }
	    case "Last": {
		returnValue = skipList.last();
		if (returnValue != null) {
		    result = (result + returnValue) % modValue;
		    // System.out.println("66: " + result);
		}
		break;
	    }
	    case "Floor": {
		operand = sc.nextLong();
		returnValue = skipList.floor(operand);
		if (returnValue != null) {
		    result = (result + returnValue) % modValue;
		    // System.out.println("75: " + result);
		}
		break;
	    }
	    case "Remove": {
		operand = sc.nextLong();
		if (skipList.remove(operand) != null) {
		    result = (result + 1) % modValue;
		    // System.out.println("83: " + result);
		}
		break;
	    }
	    case "Contains":{
		operand = sc.nextLong();
		if (skipList.contains(operand)) {
		    result = (result + 1) % modValue;
		    // System.out.println("91: " + result);
		}
		break;
	    }
		
	    }
	}

	// End Time
	timer.end();

	System.out.println(result);
	System.out.println(timer);
    }
}