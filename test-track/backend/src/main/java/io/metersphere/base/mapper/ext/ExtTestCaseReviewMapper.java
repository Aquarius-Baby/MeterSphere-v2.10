package io.metersphere.base.mapper.ext;

import io.metersphere.dto.TestCaseReviewDTO;
import io.metersphere.dto.TestReviewDTOWithMetric;
import io.metersphere.request.testreview.QueryCaseReviewRequest;
import io.metersphere.request.testreview.QueryTestReviewRequest;
import io.metersphere.request.testreview.ReviewBatchMoveRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ExtTestCaseReviewMapper {

    List<TestCaseReviewDTO> list(@Param("request") QueryCaseReviewRequest params);

    List<TestCaseReviewDTO> listByWorkspaceId(@Param("workspaceId") String workspaceId, @Param("userId") String userId, @Param("projectId") String projectId);

    List<TestReviewDTOWithMetric> listRelate(@Param("request") QueryTestReviewRequest request);

    /**
     * 检查某项目是否有某测试评审
     *
     * @param reviewId
     * @param projectIds
     * @return Review ID
     */
    int checkIsHave(@Param("reviewId") String reviewId, @Param("projectIds") Set<String> projectIds);

    String selectStatusById(@Param("id") String id);

    void batchUpdateNode(@Param("request") ReviewBatchMoveRequest request);
}
