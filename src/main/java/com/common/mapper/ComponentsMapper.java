package com.common.mapper;

import com.common.SysComponents;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ComponentsMapper {

    public SysComponents  get(String id);
    public List<SysComponents> search(String id);
    public List<SysComponents> searchByTitle(String title);
    public void  save(SysComponents user);
    public int  update(SysComponents user);
}
