package mainUtilities;

public class GetCurrentClassAndMethod {
	
	/**
	 * This method will return currently executing method
	 * @return
	 */
	public static String getMethod() {
		
		    final StackTraceElement e = Thread.currentThread().getStackTrace()[2];
		    return  e.getMethodName();
		
	}
	
	
//	
//	public static String getClass() {
//		
//		    final StackTraceElement e1 = Thread.currentThread().getStackTrace()[2];
//		    final String s1 = e1.getClassName();
//		    return s1.substring(s1.lastIndexOf('.') + 1, s1.length());
//	}
//	

}
