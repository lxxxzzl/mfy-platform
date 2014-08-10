package com.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

public class TaskExecutorExample {
	private TaskExecutor taskExecutor;

	private static Logger LOG = LoggerFactory.getLogger(TaskExecutorExample.class);
	
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
			LOG.info(message + " start");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			LOG.info(message + " end");
		}
	}
}