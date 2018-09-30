package com.lumiinsight.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SendRequestUpdateDataJob extends QuartzJobBean{
	
	private SendRequestUpdateDataTask sendRequestUpdateDataTask;
	
	public void setSendRequestUpdateDataTask(SendRequestUpdateDataTask sendRequestUpdateDataTask) {
		this.sendRequestUpdateDataTask = sendRequestUpdateDataTask;
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		sendRequestUpdateDataTask.requestAndUpdate();
		
	}

}
