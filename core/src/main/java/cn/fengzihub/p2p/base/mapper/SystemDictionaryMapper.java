package cn.fengzihub.p2p.base.mapper;

import cn.fengzihub.p2p.base.domain.SystemDictionary;
import cn.fengzihub.p2p.base.query.SystemDictionaryQueryObject;

import java.util.List;

public interface SystemDictionaryMapper {
    int insert(SystemDictionary record);

    SystemDictionary selectByPrimaryKey(Long id);

    List<SystemDictionary> selectAll();

    int updateByPrimaryKey(SystemDictionary record);

    List<SystemDictionary> queryPage(SystemDictionaryQueryObject qo);

    int queryForCount(SystemDictionaryQueryObject qo);

    List<SystemDictionary> queryForList(SystemDictionaryQueryObject qo);
}