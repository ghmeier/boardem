package boardem.server;

import io.dropwizard.lifecycle.Managed;

import org.quartz.Scheduler;

public class QuartzManager implements Managed
{
	private Scheduler scheduler;
	
	public QuartzManager(Scheduler sched)
	{
		scheduler = sched;
	}
	
	@Override
	public void start() throws Exception
	{
		scheduler.start();
	}
	
	@Override
	public void stop() throws Exception
	{
		scheduler.shutdown();
	}
}
