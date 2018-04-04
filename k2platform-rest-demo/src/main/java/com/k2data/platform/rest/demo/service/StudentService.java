package com.k2data.platform.rest.demo.service;

import com.k2data.platform.rest.demo.entity.vo.StudentVo;

import java.util.Collection;
import java.util.List;

/**
 * Created by guanxine on 18-4-4.
 */
public interface StudentService {

    Integer save(StudentVo vo);

    void delete(int id);

    StudentVo get(int id);

    void update(int id, StudentVo vo);

    long count();

    List<StudentVo> list(long offset, Long pageSize);
}
