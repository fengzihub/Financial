package cn.fengzihub.p2p.base.service;

import cn.fengzihub.p2p.base.domain.SystemDictionary;
import cn.fengzihub.p2p.base.query.SystemDictionaryQueryObject;
import cn.fengzihub.p2p.base.util.PageResult;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by Administrator on 2018.02.12.
 */
public interface ISystemDictionaryService {
    int save(SystemDictionary systemDictionary);

    int update(SystemDictionary systemDictionary);

    SystemDictionary get(Long id);

    List<SystemDictionary> selectAll();

    PageInfo queryPage(SystemDictionaryQueryObject qo);

    PageResult pageResult(SystemDictionaryQueryObject qo);
}
