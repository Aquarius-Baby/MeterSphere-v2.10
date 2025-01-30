package io.metersphere.base.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoadTestReport implements Serializable {
    private String id;

    private String testId;

    private String name;

    private Long createTime;

    private Long updateTime;

    private String status;

    private String userId;

    private String triggerMode;

    private String fileId;

    private String maxUsers;

    private String avgResponseTime;

    private String tps;

    private String projectId;

    private String testName;

    private String testResourcePoolId;

    private Long testStartTime;

    private Long testEndTime;

    private Long testDuration;

    private String versionId;

    private String relevanceTestPlanReportId;

    private static final long serialVersionUID = 1L;
}