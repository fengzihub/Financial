package cn.fengzihub.p2p.base.mapper;

import cn.fengzihub.p2p.base.domain.UserFile;
import cn.fengzihub.p2p.base.query.UserFileQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFileMapper {

    int insert(UserFile record);

    UserFile selectByPrimaryKey(Long id);

    int updateByPrimaryKey(UserFile record);

    List<UserFile> querySelectFileTypeList(Long id);

    List<UserFile> selectFileTypeByCondition(@Param("userId") Long userId, @Param("isSelectFileType") boolean isSelectFileType);

    int queryForCount(UserFileQueryObject qo);

    List<UserFile> queryForList(UserFileQueryObject qo);

    List<UserFile> queryByUserId(@Param("userId") Long userId, @Param("state") int state);


}