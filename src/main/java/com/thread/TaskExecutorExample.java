package com.thread;

import org.springframework.core.task.TaskExecutor;

public class TaskExecutorExample {
	private TaskExecutor taskExecutor;

	public TaskExecutorExample(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void printMessages() {
		for (int i = 0; i < 50; i++) {
			taskExecutor.execute(new MessagePrinterTask("Message" + i));
			
		}
	}

	private class MessagePrinterTask implements Runnable {
		private String message;

		public MessagePrinterTask(String message) {
			this.message = message;
		}

		public void run() {
			System.out.println(message + " start");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(message + " end");
		}
	}
}