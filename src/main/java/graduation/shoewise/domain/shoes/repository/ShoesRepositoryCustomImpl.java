package graduation.shoewise.domain.shoes.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import graduation.shoewise.domain.shoes.Shoes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.ArrayList;
import java.util.List;

import static graduation.shoewise.domain.shoes.QShoes.shoes;

@Slf4j
public class ShoesRepositoryCustomImpl implements ShoesRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public ShoesRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Slice<Shoes> getShoesAll(Pageable pageable) {
        JPAQuery<Long> shoesResponseList = jpaQueryFactory
                .select(shoes.id)
                .from(shoes)
                //.orderBy()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1);

        List<Long> content = new ArrayList<>();
        content = shoesResponseList.fetch();

        boolean hasNext=false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext=true;
        }
        Slice<Long> slice = new SliceImpl<>(content, pageable, hasNext);

        JPAQuery<Shoes> jpaQuery = jpaQueryFactory.selectFrom(shoes)
                .where(shoes.id.in(slice.getContent()));

        return new SliceImpl<>(jpaQuery.fetch(), pageable, hasNext);
    }

    @Override
    public Slice<Shoes> getShoesByCategory(Pageable pageable, String brand) {

        JPAQuery<Long> shoesResponseList = jpaQueryFactory
                .select(shoes.id)
                .from(shoes)
                .where(shoes.brand.eq(brand))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1);


        //log.info("querydsl result = " + shoes.brand.eq(brand));
        List<Long> content = new ArrayList<>();
        content = shoesResponseList.fetch();

        boolean hasNext=false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext=true;
        }
        Slice<Long> slice = new SliceImpl<>(content, pageable, hasNext);

        JPAQuery<Shoes> jpaQuery = jpaQueryFactory.selectFrom(shoes)
                .where(shoes.id.in(slice.getContent()));

        return new SliceImpl<>(jpaQuery.fetch(), pageable, hasNext);
    }
}
