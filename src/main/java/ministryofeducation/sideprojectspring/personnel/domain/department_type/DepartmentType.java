package ministryofeducation.sideprojectspring.personnel.domain.department_type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DepartmentType {
    KINDERGARTEN("영유치부"),
    HOLYKIDS("초등부"),
    PAULCOMMUNITY("청소년부"),
    JOSHUA("청년부");

    private final String text;
}