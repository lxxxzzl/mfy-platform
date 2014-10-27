package com.hcb.thread.exception;

public class CatchThreadException extends ThreadGroup {
	
	private CatchThreadException() {
		super("ApplicationLoader");
	}

	// We overload this method from our parent
	// ThreadGroup , which will make sure that it
	// gets called when it needs to be. This is
	// where the magic occurs.
	public void uncaughtException(Thread thread, Throwable exception) {
		// Handle the error/exception.
		// Typical operations might be displaying a
		// useful dialog, writing to an event log, etc.
		
		System.out.println("CatchThreadException[ThreadGroup] uncaughtException execute");
		System.out.println("Catch " + thread + " exception: " + exception.getMessage());
	}
	
	public static void main(String[] args) {
		Runnable appStarter = new Runnable() {
			public void run() {
				// invoke your application
				System.out.println("appStarter run..");
				
				if (true) {
					throw new RuntimeException("test appStarter throw runtimeException");
				}
			}
		};
		
		new Thread(new CatchThreadException(), appStarter).start();
	}
	
}