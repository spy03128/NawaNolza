package ssafy.nawanolza.server.domain.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssafy.nawanolza.server.domain.entity.Collection;
import ssafy.nawanolza.server.domain.entity.QCharacterType;
import ssafy.nawanolza.server.domain.entity.QCollection;
import ssafy.nawanolza.server.domain.entity.QType;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CollectionCustomRepositoryImpl implements CollectionCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
    private final QCollection collection = new QCollection("collection");
    private final QCharacterType characterType = new QCharacterType("character_type");
    private final QType types = new QType("type");

    @Override
    public List<Collection> getCollection(Long memberId, String type, String sort) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(eqMemberId(memberId));
        builder.and(eqType(type));

        if(sort.equals("level")){
            return jpaQueryFactory
                    .selectDistinct(collection)
                    .from(collection)
                    .join(characterType).on(collection.character.id.eq(characterType.character.id))
                    .join(types).on(characterType.type.id.eq(types.id))
                    .where(builder)
                    .orderBy(collection.currentLevel.desc())
                    .fetch();
        }

        return jpaQueryFactory
                .selectDistinct(collection)
                .from(collection)
                .join(characterType).on(collection.character.id.eq(characterType.character.id))
                .join(types).on(characterType.type.id.eq(types.id))
                .where(builder)
                .orderBy(collection.character.id.asc())
                .fetch();
    }

    private BooleanExpression eqMemberId(Long memberId){
        return collection.member.id.eq(memberId);
    }

    private BooleanExpression eqType(String type){
        return !type.isEmpty()? types.name.eq(type):null;
    }
}
