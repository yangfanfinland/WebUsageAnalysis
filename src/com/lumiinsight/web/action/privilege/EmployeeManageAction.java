package com.lumiinsight.web.action.privilege;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.persistence.EnumType;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.aspectj.lang.annotation.Aspect;

import com.lumiinsight.bean.privilege.Branch;
import com.lumiinsight.bean.privilege.Employee;
import com.lumiinsight.bean.privilege.IDCard;
import com.lumiinsight.bean.privilege.PrivilegeGroup;
import com.lumiinsight.service.privilege.BranchService;
import com.lumiinsight.service.privilege.EmployeeService;
import com.lumiinsight.service.privilege.PrivilegeGroupService;
import com.lumiinsight.utils.SiteUrl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
/**
 * Employee Management
 * @author yangfan
 *
 */
public class EmployeeManageAction extends ActionSupport implements ServletRequestAware{
	@Resource BranchService branchService;
	@Resource EmployeeService employeeService;
	@Resource PrivilegeGroupService groupService;
	
	private HttpServletRequest request;
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
		
	private String username;
	private String password;
	private String realname;
	private File picture;
	private String pictureFileName;
	private String cardno;
	private String birthday;
	private String address;
	private String phone;
	private String email;
	private String degree;
	private String school;
	private String branchid;
	private String[] groupids;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public File getPicture() {
		return picture;
	}
	public void setPicture(File picture) {
		this.picture = picture;
	}
	public String getPictureFileName() {
		return pictureFileName;
	}
	public void setPictureFileName(String pictureFileName) {
		this.pictureFileName = pictureFileName;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getBranchid() {
		return branchid;
	}
	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}
	public String[] getGroupids() {
		return groupids;
	}
	public void setGroupids(String[] groupids) {
		this.groupids = groupids;
	}
	/**
	 * 设置员工权限组
	 * @return
	 */
	@Permission(module="employee", privilege="privilegeSet")
	public String privilegeGroupSet(){
		Employee employee = employeeService.find(username);
		employee.getGroups().clear();
		
		if(groupids != null){

			for(String groupid : groupids){
				employee.addPrivilegeGroup(new PrivilegeGroup(groupid));
			}
		}
		
		
		employeeService.update(employee);
		
		request.setAttribute("message", "Employee privilege group set successfully!");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.employee.list"));
		return "message";
	}
	
	/**
	 * 员工权限组设置界面
	 * @return
	 */
	@Permission(module="employee", privilege="privilegeSet")
	public String privilegeGroupSetUI(){
		
		request.setAttribute("groups", groupService.getScrollData().getResultlist());
		request.setAttribute("usergroups", employeeService.find(username).getGroups());
		return "success";
	}

	/**
	 * Employee query interface
	 * @return
	 */
	@Permission(module="employee", privilege="view")
	public String query(){
		request.setAttribute("branches", branchService.getScrollData().getResultlist());
		return "success";
	}
	
	/**
	 * Mark employee Leave
	 * @return
	 */
	@Permission(module="employee", privilege="leave")
	public String leave(){
		employeeService.delete((Serializable)username);
		
		request.setAttribute("message", "Mark employee leave successfully!");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.employee.list"));
		return "message";
	}
	
	/**
	 * Modify Employee info
	 * @return
	 */
	@Permission(module="employee", privilege="update")
	public String editEmployee(){
		Employee employee = employeeService.find(username);
		employee.setRealname(realname);
		employee.setDegree(degree);
		employee.setEmail(email);
		employee.setPhone(phone);
		employee.setSchool(school);
		if(branchid!=null && !"".equals(branchid.trim()))
			employee.setBranch(new Branch(branchid.trim()));
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date birthday1 = null;
		try {
			birthday1 = format.parse(birthday);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		employee.getIdCard().setCardno(cardno);
		employee.getIdCard().setAddress(address);
		employee.getIdCard().setBirthday(birthday1);
		
		if(picture!=null){
			String ext = pictureFileName.substring(pictureFileName.lastIndexOf("."));
			String fileName = UUID.randomUUID().toString() + ext;
			String pathdir ="/images/employee/"+username;//构建文件保存的目录
			String realpathdir = ServletActionContext.getServletContext().getRealPath(pathdir);//得到图片保存目录的真实路径
			
			File savedir = new File(realpathdir);
			if(!savedir.getParentFile().exists())
				savedir.mkdirs();//如果目录不存在就创建
			String path = pathdir + "/" + fileName;
			File savefile = new File(savedir,fileName);
			try {
				FileUtils.copyFile(picture, savefile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			employee.setImageName(fileName);
			
			System.out.println(path);
			System.out.println(realpathdir+"/"+fileName);
		}
			
		employeeService.save(employee);
		
		request.setAttribute("message", "Employee is modified successfully!");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.employee.list"));
		
		return "message";
	}
	
	/**
	 * employee modify interface
	 * @return
	 */
	@Permission(module="employee", privilege="update")
	public String editEmployeeUI(){
		Employee employee = employeeService.find(username);
		
		request.setAttribute("realname", employee.getRealname());
		//request.setAttribute("gender", "");
		request.setAttribute("imagePath", employee.getImagePath());
		request.setAttribute("cardno", employee.getIdCard().getCardno());
		request.setAttribute("birthday", employee.getIdCard().getBirthday().toString());
		request.setAttribute("address", employee.getIdCard().getAddress());
		request.setAttribute("phone", employee.getPhone());
		request.setAttribute("email", employee.getEmail());
		request.setAttribute("degree", employee.getDegree());
		request.setAttribute("school", employee.getSchool());
		
		if(employee.getBranch()!=null)
			request.setAttribute("selectbranchid", employee.getBranch().getBranchid());
		request.setAttribute("branches", branchService.getScrollData().getResultlist());
		return "success";
	}
	
	/**
	 * 检验用户是否存在
	 * @return
	 */
	@Permission(module="employee", privilege="insert")
	public String exist(){
		if(employeeService.exist(username.trim())){
			request.setAttribute("message", "User exists!");
		}else{
			request.setAttribute("message", "Valid username!");
		}
		return "message";
	}
	
	
	/**
	 * 员工添加界面
	 * @return
	 */
	@Permission(module="employee", privilege="insert")
	public String regEmployeeUI(){
		request.setAttribute("branches", branchService.getScrollData().getResultlist());
		return "success";
	}
	/**
	 * Add Employee
	 * @return
	 */
	@Permission(module="employee", privilege="insert")
	public String regEmployee(){
		Employee employee = new Employee();
		employee.setUsername(username.trim());
		employee.setPassword(password.trim());
		employee.setRealname(realname);
		employee.setDegree(degree);
		employee.setEmail(email);
		employee.setPhone(phone);
		employee.setSchool(school);
		if(branchid!=null && !"".equals(branchid.trim()))
			employee.setBranch(new Branch(branchid.trim()));
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date birthday1 = null;
		try {
			birthday1 = format.parse(birthday);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		employee.setIdCard(new IDCard(cardno, address, birthday1));
		if(picture!=null){
			String ext = pictureFileName.substring(pictureFileName.lastIndexOf("."));
			String fileName = UUID.randomUUID().toString() + ext;
			String pathdir ="/images/employee/"+username;//构建文件保存的目录
			String realpathdir = ServletActionContext.getServletContext().getRealPath(pathdir);//得到图片保存目录的真实路径
			
			File savedir = new File(realpathdir);
			if(!savedir.getParentFile().exists())
				savedir.mkdirs();//如果目录不存在就创建
			String path = pathdir + "/" + fileName;
			File savefile = new File(savedir,fileName);
			try {
				FileUtils.copyFile(picture, savefile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			employee.setImageName(fileName);
			
			System.out.println(path);
			System.out.println(realpathdir+"/"+fileName);
		}
			
		employeeService.save(employee);
				
		request.setAttribute("message", "Employee is added succesfully!");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.employee.list"));
		
		return "message";
	}
}
