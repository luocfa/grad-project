package com.nuist.ecm.dao.index;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 
 *    
 * 项目名称：grad-project   
 * 类名称：IndexDao   
 * @version
 * 类描述：
 * @version   
 * 创建人：luocf   
 * @version
 * 创建时间：2015年3月22日 下午11:06:54 
 * @version  
 * 修改人：luocf     修改时间：2015年3月22日 下午11:06:54   
 * @version
 * 修改备注：   
 *
 */
@Repository
public class IndexDao {

	@Autowired
	@Qualifier("jdbcTemplateOracle")
	private JdbcTemplate jdbcTemplate;
	private Logger log = Logger.getLogger(IndexDao.class);
	/**
	 * 获取新闻列表
	 * getNewsList  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月18日 下午3:58:03
	 */
	public List<Map<String, Object>> getNewsList() {
		String sql = "SELECT newsid, title FROM ecm_news WHERE typename = 'xdzx' AND ableflag = 'Y' ORDER BY publishtime DESC LIMIT 6";
		List<Map<String, Object>> newsList = new ArrayList<Map<String, Object>>();
		try {
			newsList = jdbcTemplate.queryForList(sql);
			log.info("获取新闻列表成功" + newsList);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			log.info("获取新闻列表失败");
		}
		return newsList;
	}
	
	/**
	 * 获取过去24小时温湿度
	  
	 * get24Info  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月18日 下午9:17:22
	 */
	public List<Map<String, Object>> get24Info(int stIndex, int rowNum) {
		String sql = "SELECT * FROM (SELECT id,DATE_FORMAT(up_day,'%Y-%m-%d') up_day,up_datestr,temp,shidu,aqi FROM ecm_24info ORDER BY up_datetime DESC LIMIT " + stIndex+ ","+ rowNum +") AS a ORDER BY up_datestr";
		List<Map<String, Object>> infoList = new ArrayList<Map<String, Object>>();
		try {
			infoList = jdbcTemplate.queryForList(sql);
			log.info("获取24小时信息成功" + infoList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("获取24小时信息失败！");
		}
		return infoList;
	}
}
