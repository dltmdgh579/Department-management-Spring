package ministryofeducation.sideprojectspring.personnel.infrastructure;

import static ministryofeducation.sideprojectspring.personnel.domain.QPersonnel.*;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.domain.QPersonnel;
import ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelCondRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelListResponse;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class PersonnelCustomRepositoryImpl implements PersonnelCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PersonnelListResponse> findAllByCondition(PersonnelCondRequest condition) {
        return queryFactory
            .select(Projections.constructor(PersonnelListResponse.class,
                personnel.id,
                personnel.name,
                personnel.dateOfBirth,
                personnel.phone,
                personnel.address,
                personnel.profileImage,
                personnel.departmentType
            ))
            .where(departmentTypeEq(condition))
            .from(personnel)
            .fetch();
    }

    private BooleanExpression departmentTypeEq(PersonnelCondRequest condition){
        return departmentType1Eq(condition.getDepartmentType1())
            .or(departmentType2Eq(condition.getDepartmentType2()))
            .or(departmentType3Eq(condition.getDepartmentType3()))
            .or(departmentType4Eq(condition.getDepartmentType4()));
    }

    private BooleanExpression departmentType1Eq(DepartmentType departmentType){
        return departmentType != null ? personnel.departmentType.eq(departmentType) : null;
    }
    private BooleanExpression departmentType2Eq(DepartmentType departmentType){
        return departmentType != null ? personnel.departmentType.eq(departmentType) : null;
    }
    private BooleanExpression departmentType3Eq(DepartmentType departmentType){
        return departmentType != null ? personnel.departmentType.eq(departmentType) : null;
    }
    private BooleanExpression departmentType4Eq(DepartmentType departmentType){
        return departmentType != null ? personnel.departmentType.eq(departmentType) : null;
    }

}
