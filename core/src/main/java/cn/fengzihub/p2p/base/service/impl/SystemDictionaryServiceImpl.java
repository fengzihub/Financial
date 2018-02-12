package cn.fengzihub.p2p.base.service.impl;

import cn.fengzihub.p2p.base.domain.SystemDictionary;
import cn.fengzihub.p2p.base.mapper.SystemDictionaryMapper;
import cn.fengzihub.p2p.base.query.SystemDictionaryQueryObject;
import cn.fengzihub.p2p.base.service.ISystemDictionaryService;
import cn.fengzihub.p2p.base.util.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018.02.12.
 */
@Service
@Transactional
public class SystemDictionaryServiceImpl implements ISystemDictionaryService {
    @Autowired
    private SystemDictionaryMapper systemDictionaryMapper;
    @Override
    public int save(SystemDictionary systemDictionary) {
        systemDictionaryMapper.insert(systemDictionary);
        return 0;
    }

    @Override
    public int update(SystemDictionary systemDictionary) {
        systemDictionaryMapper.updateByPrimaryKey(systemDictionary);
        return 0;
    }

    @Override
    public SystemDictionary get(Long id) {
        return systemDictionaryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SystemDictionary> selectAll() {
        return systemDictionaryMapper.selectAll();
    }

    @Override
    public PageInfo queryPage(SystemDictionaryQueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List<SystemDictionary> list = systemDictionaryMapper.queryPage(qo);
        return new PageInfo(list);
    }

    @Override
    public PageResult pageResult(SystemDictionaryQueryObject qo) {

        int total = systemDictionaryMapper.queryForCount(qo);
        if (total == 0) {
            return new PageResult(total, Collections.EMPTY_LIST);
        }
        List<SystemDictionary> systemDictionaries = systemDictionaryMapper.queryForList(qo);
        return new PageResult(total,systemDictionaries);
    }
}
