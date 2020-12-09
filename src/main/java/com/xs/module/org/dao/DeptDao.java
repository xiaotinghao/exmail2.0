package com.xs.module.org.dao;

import com.xs.module.org.model.dto.DeptDTO;
import com.xs.module.org.model.form.DeptForm;
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
