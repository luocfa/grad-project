package com.nuist.task.listener;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.nuist.common.define.Constant;
import com.nuist.task.timetask.WeatherTimerTask;

/**
 * web监听器，用于后台定时任务
 *    
 * 项目名称：grad-project   
 * 类名称：WeatherTaskListener   
 * @version
 * 类描述：
 * @version   
 * 创建人：luocf   
 * @version
 * 创建时间：2015年5月29日 下午10:11:51 
 * @version  
 * 修改人：luocf     修改时间：2015年5月29日 下午10:11:51   
 * @version
 * 修改备注：   
 *
 */
//@WebListener
public class WeatherTaskListener implements ServletContextListener {
	
	private Logger log = Logger.getLogger(WeatherTaskListener.class);
	/**
	 * Default constructor.
	 */
	public WeatherTaskListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		log.info("**********启动**********");
		log.info("**WeatherTaskListener**");
		log.info("*******监听定时器成功*******");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MINUTE, Constant.TIMERTASK_MINUTE);
		calendar.set(Calendar.SECOND, Constant.TIMERTASK_SECONDS);
		Date date = calendar.getTime();
		if (date.before(new Date())) {
			date = this.addHour(date, 1);
		}
		Timer timer = new Timer();
		//要想调用spring管理的bean必须使用这种方法获取对象
		ServletContext context = arg0.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		WeatherTimerTask task = (WeatherTimerTask) ctx.getBean("weathertimertask");
		timer.schedule(task, date, Constant.TIMERTASK_PERIOD);
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		log.info("监听定时器WeatherTaskListener已停止........");
	}

	// 增加或减少天数
	public Date addHour(Date date, int num) {
		Calendar startDT = Calendar.getInstance();
		startDT.setTime(date);
		startDT.add(Calendar.HOUR_OF_DAY, num);
//		startDT.add(Calendar.MINUTE, num);
		return startDT.getTime();
	}

}
