package cn.fengzihub.p2p.business.mapper;

import cn.fengzihub.p2p.business.domain.ExpAccountGrantRecord;
import java.util.List;

public interface ExpAccountGrantRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ExpAccountGrantRecord record);

    ExpAccountGrantRecord selectByPrimaryKey(Long id);

    List<ExpAccountGrantRecord> selectAll();

    int updateByPrimaryKey(ExpAccountGrantRecord record);
}