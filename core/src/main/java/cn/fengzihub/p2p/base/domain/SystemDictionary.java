package cn.fengzihub.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2018.02.10.
 */
//数据字典分类/分组/目录
@Getter
@Setter
public class SystemDictionary extends BaseDomain {
    private String title;//分类名称,如:教育背景
    private String sn;//分类编码 ,如:educationBackground
}