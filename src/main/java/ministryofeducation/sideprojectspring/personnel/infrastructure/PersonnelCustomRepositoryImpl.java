package ministryofeducation.sideprojectspring.personnel.infrastructure;

import static ministryofeducation.sideprojectspring.personnel.domain.QAttendance.*;
import static ministryofeducation.sideprojectspring.personnel.domain.QPersonnel.*;
import static ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelOrderCondRequest.*;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.DepartmentMemberListResponse;
import ministryofeducation.sideprojectspring.personnel.domain.Gender;
import ministryofeducation.sideprojectspring.personnel.domain.attendance.AttendanceStatus;
import ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelFilterCondRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelOrderCondRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelListResponse;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PersonnelCustomRepositoryImpl implements PersonnelCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<DepartmentMemberListResponse> findPersonnelListAttendanceByDate(PersonnelFilterCondRequest filterCond,
        PersonnelOrderCondRequest orderCond, Long departmentId, LocalDate date) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(orderCond);

        return queryFactory
                .select(Projections.constructor(DepartmentMemberListResponse.class,
                        personnel.id,
                        personnel.name,
                        personnel.dateOfBirth,
                        personnel.phone,
                        personnel.address,
                        personnel.gender,
                        personnel.profileImage,
                        personnel.departmentType,
                        attendance.attendanceStatus,
                        calAttendanceCountLastYear()
                ))
                .from(personnel)
                .leftJoin(personnel.attendanceList, attendance)
                .on(attendance.attendanceDate.eq(date))
                .where(personnel.department.id.eq(departmentId)
                .and(genderEq(filterCond.getGender())))
                .orderBy(orderSpecifiers)
                .fetch();
    }

    @Override
    public List<PersonnelListResponse> findAllByCondition(PersonnelFilterCondRequest filterCond,
        PersonnelOrderCondRequest orderCond) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(orderCond);

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
            .where(filterEq(filterCond))
            .from(personnel)
            .orderBy(orderSpecifiers)
            .fetch();
    }

    @Override
    public List<PersonnelListResponse> findPersonnelByName(PersonnelFilterCondRequest filterCond,
        PersonnelOrderCondRequest orderCond, String[] searchWordRange) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(orderCond);

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
            .where(filterEq(filterCond), searchWord(searchWordRange))
            .from(personnel)
            .orderBy(orderSpecifiers)
            .fetch();
    }

    private BooleanExpression filterEq(PersonnelFilterCondRequest filterCond) {
        if (filterCond == null) {
            return null;
        }

        BooleanExpression departmentTypeExpression = getDepartmentTypeExpression(filterCond.getDepartmentTypeList());
        BooleanExpression genderExpression = getGenderExpression(filterCond.getGender());

        return combineExpressions(departmentTypeExpression, genderExpression);
    }

    private BooleanExpression getDepartmentTypeExpression(List<DepartmentType> departmentTypes) {
        if (departmentTypes == null || departmentTypes.isEmpty()) {
            return null;
        }

        return departmentTypes.stream()
            .map(this::departmentTypeEq)
            .reduce(BooleanExpression::or)
            .orElse(null);
    }

    private BooleanExpression getGenderExpression(Gender gender) {
        return genderEq(gender);
    }

    private BooleanExpression combineExpressions(BooleanExpression expr1, BooleanExpression expr2) {
        if (expr1 == null) return expr2;
        if (expr2 == null) return expr1;
        return expr1.and(expr2);
    }

    private BooleanExpression departmentTypeEq(DepartmentType departmentType) {
        return departmentType != null ? personnel.departmentType.eq(departmentType) : null;
    }

    private BooleanExpression departmentTypeEqOr(BooleanExpression booleanExpression, DepartmentType departmentType) {
        return booleanExpression.or(departmentTypeEq(departmentType));
    }

    private BooleanExpression genderEq(Gender gender) {
        return gender != null ? personnel.gender.eq(gender) : null;
    }

    private OrderSpecifier[] createOrderSpecifier(PersonnelOrderCondRequest orderCond) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        if (Objects.isNull(orderCond)) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, personnel.name));
        } else if (orderCond.equals(AGE)) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, personnel.dateOfBirth));
        } else if (orderCond.equals(NAME)) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, personnel.name));
        } else if (orderCond.equals(ATTENDANCE)) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, calAttendanceCountLastYear()));
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }

    private BooleanExpression searchWord(String[] searchWordRange){
        return searchWordRange != null? personnel.name.between(searchWordRange[0], searchWordRange[1]) : null;
    }

    private static JPQLQuery<Long> calAttendanceCountLastYear() {
        return JPAExpressions.select(attendance.count())
                .from(attendance)
                .where(attendance.personnel.eq(personnel)
                        .and(attendance.attendanceStatus.eq(AttendanceStatus.ATTENDANCE)));
    }
}
