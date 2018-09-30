package com.lumiinsight.bean.analysis;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * 此类用来保存单位为天的数据，用于周和月查询
 * @author yangfan
 *
 */
@Entity
public class ChartDataDay {
	private String dataID;
	private Integer connection = 0;
	private Integer complete = 0;
	private Integer interaction = 0;
	private Integer events = 0;
	private String serverName;
	private String serverCategory;
	private String year;
	private String month;
	private String day;
	private String weekDay;
	private String weekInYear;
	public ChartDataDay() {}
	public ChartDataDay(String dataID, Integer connection, Integer complete,
			Integer interaction, Integer events, String serverName,
			String serverCategory, String year, String month, String day,
			String weekDay, String weekInYear) {
		super();
		this.dataID = dataID;
		this.connection = connection;
		this.complete = complete;
		this.interaction = interaction;
		this.events = events;
		this.serverName = serverName;
		this.serverCategory = serverCategory;
		this.year = year;
		this.month = month;
		this.day = day;
		this.weekDay = weekDay;
		this.weekInYear = weekInYear;
	}
	@Id @Column(length=36)
	public String getDataID() {
		return dataID;
	}
	public void setDataID(String dataID) {
		this.dataID = dataID;
	}
	@Column(length=20, nullable=false)
	public Integer getConnection() {
		return connection;
	}
	public void setConnection(Integer connection) {
		this.connection = connection;
	}
	@Column(length=20, nullable=false)
	public Integer getComplete() {
		return complete;
	}
	public void setComplete(Integer complete) {
		this.complete = complete;
	}
	@Column(length=20, nullable=false)
	public Integer getInteraction() {
		return interaction;
	}
	public void setInteraction(Integer interaction) {
		this.interaction = interaction;
	}
	@Column(length=20, nullable=false)
	public Integer getEvents() {
		return events;
	}
	public void setEvents(Integer events) {
		this.events = events;
	}
	@Column(length=20, nullable=false)
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	@Column(length=20, nullable=false)
	public String getServerCategory() {
		return serverCategory;
	}
	public void setServerCategory(String serverCategory) {
		this.serverCategory = serverCategory;
	}
	@Column(length=5, nullable=false)
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	@Column(length=5, nullable=false)
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	@Column(length=5, nullable=false)
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	@Column(length=5, nullable=false)
	public String getWeekDay() {
		return weekDay;
	}
	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}
	@Column(length=5, nullable=false)
	public String getWeekInYear() {
		return weekInYear;
	}
	public void setWeekInYear(String weekInYear) {
		this.weekInYear = weekInYear;
	}
}
