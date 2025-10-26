package lab.is.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.JoinType;

public abstract class MySpecification<T> {
    protected MySpecification() {}

    protected Specification<T> fieldStringValueLike(
        FieldName fieldName,
        String fieldValue
    ) {
        if (fieldValue == null || fieldValue.isBlank()) return null;
        String pattern = "%" + fieldValue.trim().toLowerCase() + "%";
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.like(
                criteriaBuilder.lower(
                    root.get(
                        fieldName.getFieldName()
                    )
                ),
                pattern
            );
    }

    protected Specification<T> fieldValueEquals(
        FieldName fieldName,
        String fieldValue
    ) {
        if (fieldValue == null) return null;
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(
                root.get(
                    fieldName.getFieldName()
                ),
                fieldValue
            );
    }

    protected Specification<T> fieldStringValueFromEntityLike(
        FieldName fieldNameEntity,
        FieldName fieldName,
        String fieldValue
    ) {
        if (fieldValue == null || fieldValue.isBlank()) return null;
        String pattern = "%" + fieldValue.trim().toLowerCase() + "%";
        return (root, query, criteriaBuilder) -> {
            var join = root.join(
                fieldNameEntity.getFieldName(),
                JoinType.INNER
            );
            return criteriaBuilder.like(
                criteriaBuilder.lower(
                    join.get(
                        fieldName.getFieldName()
                    )
                ),
                pattern
            );
        };
    }
}
