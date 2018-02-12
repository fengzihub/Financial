package cn.fengzihub.p2p.base.service;

import cn.fengzihub.p2p.base.domain.SystemDictionary;
import cn.fengzihub.p2p.base.domain.SystemDictionaryItem;
import cn.fengzihub.p2p.base.query.SystemDictionaryItemQueryObject;
import cn.fengzihub.p2p.base.util.PageResult;

import java.util.List;

/**
 * Created by Administrator on 2018.02.12.
 */

public interface ISystemDictionaryItemService {
    int save(SystemDictionaryItem systemDictionaryItem);

    int update(SystemDictionaryItem systemDictionaryItem);

    SystemDictionaryItem get(Long id);

    List<SystemDictionaryItem> selectAll();

    PageResult pageResult(SystemDictionaryItemQueryObject qo);

    List<SystemDictionary> queryListByParentSn(String sn);
}
