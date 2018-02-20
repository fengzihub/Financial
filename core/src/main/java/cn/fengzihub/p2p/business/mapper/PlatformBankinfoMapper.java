package cn.fengzihub.p2p.business.mapper;

import cn.fengzihub.p2p.business.domain.PlatformBankinfo;
import cn.fengzihub.p2p.business.query.PlatformDescriptionQueryObject;

import java.util.List;

public interface PlatformBankinfoMapper {

    int insert(PlatformBankinfo record);

    PlatformBankinfo selectByPrimaryKey(Long id);

    List<PlatformBankinfo> selectAll();

    int updateByPrimaryKey(PlatformBankinfo record);

    int queryForCount(PlatformDescriptionQueryObject qo);

    List<PlatformBankinfo> queryForList(PlatformDescriptionQueryObject qo);
}