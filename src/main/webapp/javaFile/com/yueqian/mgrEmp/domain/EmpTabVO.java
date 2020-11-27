package com.yueqian.mgrEmp.domain;
public class EmpTabVO {
	private Integer empNo;
	private String eName;
	private Double eSal;
	private java.util.Date eHiredate;

	public Integer getEmpNo(){
		return this.empNo;
	}

	public void setEmpNo( Integer empNo ){
		this.empNo = empNo;
	}

	public String geteName(){
		return this.eName;
	}

	public void seteName( String eName ){
		this.eName = eName;
	}

	public Double geteSal(){
		return this.eSal;
	}

	public void seteSal( Double eSal ){
		this.eSal = eSal;
	}

	public java.util.Date geteHiredate(){
		return this.eHiredate;
	}

	public void seteHiredate( java.util.Date eHiredate ){
		this.eHiredate = eHiredate;
	}

}