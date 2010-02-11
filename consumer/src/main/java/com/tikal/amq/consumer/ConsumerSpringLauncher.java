package com.tikal.amq.consumer;

import org.slf4j.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.locks.LockSupport;

/**
 * this launcher gets an argument of a spring context path, loads it,
 * and waits forever.
 * application should be terminated with cntl+c or kill.
 *
 * @author shalom
 *
 */
public class ConsumerSpringLauncher {

	protected static final Logger logger = LoggerFactory.getLogger(ConsumerSpringLauncher.class);



	/**
	 * Delegate to the exiter to (possibly) exit the VM gracefully.
	 *
	 * @param status
	 */
	public void exit(int status) {
		System.exit(status);
	}


	private void start(String contextPath) {

		ConfigurableApplicationContext context = null;

		try {
			context = new ClassPathXmlApplicationContext(contextPath);
            context.registerShutdownHook();
			//register a shutdown hook to close the application context
//			Runtime.getRuntime().addShutdownHook(getShutdownHook(context));
			//park forever...
            LockSupport.park();

		} catch (Throwable e) {
			logger.error("ConsumerSpringLauncher Terminated in error:", e);
		}
	}

	private Thread getShutdownHook(final ConfigurableApplicationContext context) {

		Runnable runnable = new Runnable(){
			@Override
			public void run() {
				logger.info("Running shutdown hook...");
				if(context != null){
					logger.info("Closing application context...");
					context.close();
				}
			}
		};

		return new Thread(runnable);

	}


	public static void main(String[] args) {

		ConsumerSpringLauncher consumerSpringLauncher = new ConsumerSpringLauncher();

		if (args.length < 1) {
			logger.error("At least 1 argument are required: contextPath.");
			consumerSpringLauncher.exit(1);
		}

		String msg = "ConsumerSpringLauncher started with folowing parameters:";
		for (int i = 0; i < args.length; i++) {
			msg += "\""+args[i]+"\",";
		}
		logger.info(msg);

		String contextPath = args[0];


		consumerSpringLauncher.start(contextPath);


		//will never get here because start blocks forever.
//        System.exit(0);

	}

}