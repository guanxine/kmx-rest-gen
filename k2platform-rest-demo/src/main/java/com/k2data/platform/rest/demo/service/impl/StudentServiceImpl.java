package com.k2data.platform.rest.demo.service.impl;

import com.k2data.platform.rest.demo.entity.vo.StudentVo;
import com.k2data.platform.rest.demo.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by guanxine on 18-4-4.
 */
@Service
public class StudentServiceImpl implements StudentService{
    @Override
    public Integer save(StudentVo vo) {
        return vo.getId();
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public StudentVo get(int id) {
        StudentVo vo = new StudentVo();
        vo.setId(id);
        return vo;
    }

    @Override
    public void update(int id, StudentVo vo) {

    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public List<StudentVo> list(long offset, Long pageSize) {
        return Collections.emptyList();
    }
}
