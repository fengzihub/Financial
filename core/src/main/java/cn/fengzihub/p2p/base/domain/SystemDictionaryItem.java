package cn.fengzihub.p2p.base.domain;

/**
 * Created by Administrator on 2018.02.10.
 */

import lombok.Getter;
import lombok.Setter;

//数据字典明细实体
@Getter
@Setter
public class SystemDictionaryItem extends BaseDomain{
    //只是用到了他们的Id,所以不用写他们得关联对象
    private Long parentId;//分类ID
    private String title;//名称
    private int sequence;//顺序
}