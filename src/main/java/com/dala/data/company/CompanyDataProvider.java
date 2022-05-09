package com.dala.data.company;

import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @Component annotation is used so springs dependency injection can automatically use this class
 * when needed
 */
@Component
public class CompanyDataProvider extends AbstractBackEndDataProvider<Company, CrudFilter> {

    @Autowired
    private CompanyRepository companyRepository;

    private Consumer<Long> sizeChangeListener;

    private static Predicate<Company> predicate(CrudFilter filter) {
        return filter.getConstraints().entrySet().stream()
                .map(constraint -> (Predicate<Company>) company -> {
                    try {
                        Object value = valueOf(constraint.getKey(), company);
                        return value != null && value.toString().toLowerCase()
                                .contains(constraint.getValue().toLowerCase());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .reduce(Predicate::and)
                .orElse(e -> true);
    }

    private static Comparator<Company> comparator(CrudFilter filter) {
        return filter.getSortOrders().entrySet().stream()
                .map(sortClause -> {
                    try {
                        Comparator<Company> comparator = Comparator.comparing(company ->
                                (Comparable) valueOf(sortClause.getKey(), company)
                        );

                        if (sortClause.getValue() == SortDirection.DESCENDING) {
                            comparator = comparator.reversed();
                        }

                        return comparator;

                    } catch (Exception ex) {
                        return (Comparator<Company>) (o1, o2) -> 0;
                    }
                })
                .reduce(Comparator::thenComparing)
                .orElse((o1, o2) -> 0);
    }

    private static Object valueOf(String fieldName, Company company) {
        try {
            Field field = Company.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(company);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected Stream<Company> fetchFromBackEnd(Query<Company, CrudFilter> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();

        Stream<Company> stream = companyRepository.findAll().stream();

        if (query.getFilter().isPresent()) {
            stream = stream
                    .filter(predicate(query.getFilter().get()))
                    .sorted(comparator(query.getFilter().get()));
        }

        return stream.skip(offset).limit(limit);
    }

    @Override
    protected int sizeInBackEnd(Query<Company, CrudFilter> query) {
        long count = fetchFromBackEnd(query).count();

        if (sizeChangeListener != null) {
            sizeChangeListener.accept(count);
        }

        return (int) count;
    }

    void setSizeChangeListener(Consumer<Long> listener) {
        sizeChangeListener = listener;
    }

    public void persist(Company item) {
        companyRepository.save(item);
    }

    public Optional<Company> find(Integer id) {
        return companyRepository.findById(Long.valueOf(id));
    }

    public void delete(Company item) {
        companyRepository.delete(item);
    }

}
