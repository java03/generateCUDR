package com.yueqian.gen.mapper;

import java.util.List;
import com.yueqian.gen.domain.EmpTabVO;

public interface EmpTabMapper {
	public EmpTabVO getEmpTabVOById(String id);

	public List<EmpTabVO> getEmpTabVOs();

	public int addEmpTabVO(EmpTabVO vo);

	public int delEmpTabVO(String id);

	public int modifyEmpTabVO(EmpTabVO vo);

}