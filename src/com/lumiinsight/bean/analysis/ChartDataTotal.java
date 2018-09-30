package com.lumiinsight.bean.analysis;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ChartDataTotal {
	private String dataID;
	private Integer totalConnection = 0;
	private Integer totalComplete = 0;
	private Integer totalInteraction = 0;
	private Integer totalEvents = 0;
	private String serverName;
	private String serverCategory;
	private String hour;
	private String day;
	private String month;
	private String year;
	private String weekDay;
	private String weekInYear;
	public ChartDataTotal() {}
	public ChartDataTotal(String dataID, Integer totalConnection,
			Integer totalComplete, Integer totalInteraction,
			Integer totalEvents, String serverName, String serverCategory,
			String hour, String day, String month, String year, String weekDay,
			String weekInYear) {
		super();
		this.dataID = dataID;
		this.totalConnection = totalConnection;
		this.totalComplete = totalComplete;
		this.totalInteraction = totalInteraction;
		this.totalEvents = totalEvents;
		this.serverName = serverName;
		this.serverCategory = serverCategory;
		this.hour = hour;
		this.day = day;
		this.month = month;
		this.year = year;
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
	public Integer getTotalConnection() {
		return totalConnection;
	}
	public void setTotalConnection(Integer totalConnection) {
		this.totalConnection = totalConnection;
	}
	@Column(length=20, nullable=false)
	public Integer getTotalComplete() {
		return totalComplete;
	}
	public void setTotalComplete(Integer totalComplete) {
		this.totalComplete = totalComplete;
	}
	@Column(length=20, nullable=false)
	public Integer getTotalInteraction() {
		return totalInteraction;
	}
	public void setTotalInteraction(Integer totalInteraction) {
		this.totalInteraction = totalInteraction;
	}
	@Column(length=20, nullable=false)
	public Integer getTotalEvents() {
		return totalEvents;
	}
	public void setTotalEvents(Integer totalEvents) {
		this.totalEvents = totalEvents;
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
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	@Column(length=5, nullable=false)
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	@Column(length=5, nullable=false)
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	@Column(length=5, nullable=false)
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
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
