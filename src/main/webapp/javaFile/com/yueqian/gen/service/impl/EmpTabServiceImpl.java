package com.yueqian.gen.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.yueqian.gen.domain.EmpTabVO;

import com.yueqian.gen.service.EmpTabService;

import com.yueqian.gen.mapper.EmpTabMapper;

@Service
public class EmpTabServiceImpl implements EmpTabService{
	@Resource
	private EmpTabMapper empTabMapper;

	public EmpTabVO getEmpTabVOById(String id){
		return empTabMapper.getEmpTabVOById(id);
	}

	public List<EmpTabVO> getEmpTabVOs(){

		return empTabMapper.getEmpTabVOs();
	}

	public int addEmpTabVO(EmpTabVO vo){

		return empTabMapper.addEmpTabVO(vo);
	}

	public int delEmpTabVO(String id){

		return empTabMapper.delEmpTabVO(id);
	}

	public int modifyEmpTabVO(EmpTabVO vo){

		return empTabMapper.modifyEmpTabVO(vo);
	}

}