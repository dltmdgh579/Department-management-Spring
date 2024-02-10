package ministryofeducation.sideprojectspring.personnel.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Gender {
    M("남자"), W("여자");

    private final String text;

}
