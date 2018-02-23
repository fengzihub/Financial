package cn.fengzihub.p2p.business.service.Impl;

import cn.fengzihub.p2p.business.domain.ExpAccountGrantRecord;
import cn.fengzihub.p2p.business.mapper.ExpAccountGrantRecordMapper;
import cn.fengzihub.p2p.business.service.IExpAccountGrantRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2018.02.23.
 */
@Service@Transactional
public class ExpAccountGrantRecordServiceImpl implements IExpAccountGrantRecordService {
    @Autowired
    private ExpAccountGrantRecordMapper expAccountGrantRecordMapper;

    @Override
    public int save(ExpAccountGrantRecord expAccountGrantRecord) {
        expAccountGrantRecordMapper.insert(expAccountGrantRecord);
        return 0;
    }

    @Override
    public int update(ExpAccountGrantRecord expAccountGrantRecord) {
        expAccountGrantRecordMapper.updateByPrimaryKey(expAccountGrantRecord);
        return 0;
    }

    @Override
    public ExpAccountGrantRecord get(Long id) {
        return expAccountGrantRecordMapper.selectByPrimaryKey(id);
    }
}
