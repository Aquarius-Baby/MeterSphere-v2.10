package io.metersphere.request.testCaseReport;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReportRequest {
    private String id;
    String planId;
    String templateId;
}
