package com.xs.module.exmail.department.dao;

import com.xs.module.exmail.department.model.form.DeptForm;
import com.xs.module.exmail.department.model.dto.DeptDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeptDao {

    int saveDept(DeptForm form);

    int deleteDept(DeptForm form);

    int updateDept(DeptForm form);

    DeptDTO getDept(DeptForm form);

    List<DeptDTO> listDept(DeptForm form);

}
