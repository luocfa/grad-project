package com.nuist.task.timetask;

import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nuist.ecm.service.manager.ManagerService;

@Component("weathertimertask")
public class WeatherTimerTask extends TimerTask {
	
	@Autowired
	private ManagerService managerservice;
	private Logger log = Logger.getLogger(WeatherTimerTask.class);
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean isExist = managerservice.checkIsInsertedHourInfo();
		if (!isExist) {
			log.info("本小时的值已经存在");
		} else {
			boolean issucc = managerservice.autoInsertHourInfo();
			int i = 1;
			while (!issucc) {
				log.info("数据入库失败，正在重试");
				if(i > 5) break;
				try {
					Thread.sleep(5000);
					i++;
					issucc = managerservice.autoInsertHourInfo();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
