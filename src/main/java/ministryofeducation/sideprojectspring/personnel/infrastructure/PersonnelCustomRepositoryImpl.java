package ministryofeducation.sideprojectspring.personnel.infrastructure;

import static ministryofeducation.sideprojectspring.personnel.domain.QPersonnel.*;
import static ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelOrderCondRequest.*;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import ministryofeducation.sideprojectspring.personnel.domain.Gender;
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
    public List<PersonnelListResponse> findAllByCondition(PersonnelFilterCondRequest filterCond, PersonnelOrderCondRequest orderCond) {
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

    private BooleanExpression filterEq(PersonnelFilterCondRequest filterCond){
        final BooleanExpression[] booleanExpressionDepartmentType = new BooleanExpression[1];
        if(!filterCond.getDepartmentTypeList().isEmpty()){
            booleanExpressionDepartmentType[0] = (departmentTypeEq(filterCond.getDepartmentTypeList().get(0)));

            filterCond.getDepartmentTypeList().stream()
                .filter(departmentType -> !departmentType.equals(filterCond.getDepartmentTypeList().get(0)))
                .forEach(
                    departmentType -> booleanExpressionDepartmentType[0] = (
                        departmentTypeEqOr(booleanExpressionDepartmentType[0], departmentType))
                );
        } else {
            booleanExpressionDepartmentType[0] = null;
        }

        BooleanExpression booleanExpressionGender;
        if(!Objects.isNull(filterCond.getGender())){
            booleanExpressionGender = genderEq(filterCond.getGender());
        } else {
            booleanExpressionGender = null;
        }

        if(booleanExpressionDepartmentType[0] == null){
            return booleanExpressionGender;
        }

        return booleanExpressionDepartmentType[0].and(booleanExpressionGender);
    }
    private BooleanExpression departmentTypeEq(DepartmentType departmentType){
        return departmentType != null ? personnel.departmentType.eq(departmentType) : null;
    }

    private BooleanExpression departmentTypeEqOr(BooleanExpression booleanExpression, DepartmentType departmentType){
        return booleanExpression.or(departmentTypeEq(departmentType));
    }

    private BooleanExpression genderEq(Gender gender){
        return gender != null ? personnel.gender.eq(gender) : null;
    }

    private OrderSpecifier[] createOrderSpecifier(PersonnelOrderCondRequest orderCond) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        if(Objects.isNull(orderCond)){
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, personnel.name));
        } else if(orderCond.equals(AGE)){
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, personnel.dateOfBirth));
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }
}
