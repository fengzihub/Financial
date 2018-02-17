package cn.fengzihub.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Administrator on 2018.02.17.
 */
@Getter@Setter@ToString
public class UserFile extends BaseAuthDomain {

    private String image;
    private int score;
    private SystemDictionaryItem fileType;



}
