package cn.fengzihub.p2p.business.service;

import cn.fengzihub.p2p.business.domain.ExpAccountGrantRecord;

/**
 * Created by Administrator on 2018.02.23.
 */
public interface IExpAccountGrantRecordService {
    int save(ExpAccountGrantRecord expAccountGrantRecord);

    int update(ExpAccountGrantRecord expAccountGrantRecord);

    ExpAccountGrantRecord get(Long id);

}
