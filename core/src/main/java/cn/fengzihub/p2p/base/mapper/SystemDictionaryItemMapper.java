package cn.fengzihub.p2p.base.mapper;

import cn.fengzihub.p2p.base.domain.SystemDictionary;
import cn.fengzihub.p2p.base.domain.SystemDictionaryItem;
import cn.fengzihub.p2p.base.query.SystemDictionaryItemQueryObject;

import java.util.List;

public interface SystemDictionaryItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemDictionaryItem record);

    SystemDictionaryItem selectByPrimaryKey(Long id);

    List<SystemDictionaryItem> selectAll();

    int updateByPrimaryKey(SystemDictionaryItem record);

    int queryForCount(SystemDictionaryItemQueryObject qo);

    List<SystemDictionaryItem> queryForList(SystemDictionaryItemQueryObject qo);

    List<SystemDictionary> queryListByParentSn(String sn);
}