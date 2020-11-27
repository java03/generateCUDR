package com.yueqian.mgrEmp.service;

import java.util.List;
import com.yueqian.mgrEmp.domain.EmpTabVO;

public interface EmpTabService {
	public EmpTabVO getEmpTabVOById(String id);

	public List<EmpTabVO> getEmpTabVOs();

	public int addEmpTabVO(EmpTabVO vo);

	public int delEmpTabVO(String id);

	public int modifyEmpTabVO(EmpTabVO vo);

}