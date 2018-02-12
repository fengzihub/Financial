package cn.fengzihub.p2p.base.service.impl;

import cn.fengzihub.p2p.base.domain.SystemDictionary;
import cn.fengzihub.p2p.base.domain.SystemDictionaryItem;
import cn.fengzihub.p2p.base.mapper.SystemDictionaryItemMapper;
import cn.fengzihub.p2p.base.query.SystemDictionaryItemQueryObject;
import cn.fengzihub.p2p.base.service.ISystemDictionaryItemService;
import cn.fengzihub.p2p.base.util.PageResult;
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
public class SystemDictionaryItemServiceImpl implements ISystemDictionaryItemService {
    @Autowired
    private SystemDictionaryItemMapper systemDictionaryItemMapper;

    @Override
    public int save(SystemDictionaryItem systemDictionaryItem) {
        systemDictionaryItemMapper.insert(systemDictionaryItem);
        return 0;
    }

    @Override
    public int update(SystemDictionaryItem systemDictionaryItem) {
        systemDictionaryItemMapper.updateByPrimaryKey(systemDictionaryItem);
        return 0;
    }

    @Override
    public SystemDictionaryItem get(Long id) {
        return systemDictionaryItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SystemDictionaryItem> selectAll() {
        return systemDictionaryItemMapper.selectAll();
    }

    @Override
    public PageResult pageResult(SystemDictionaryItemQueryObject qo) {
        int total = systemDictionaryItemMapper.queryForCount(qo);
        if (total == 0) {
            return new PageResult(total, Collections.EMPTY_LIST);
        }
        List<SystemDictionaryItem>list = systemDictionaryItemMapper.queryForList(qo);
        return new PageResult(total,list);
    }

    @Override
    public List<SystemDictionary> queryListByParentSn(String sn) {
        return systemDictionaryItemMapper.queryListByParentSn(sn);
    }
}
