package com.example.interview.DTO;

import com.example.interview.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {
    BookMapper BookMapperDTO = Mappers.getMapper(BookMapper.class);

    BookDTO bookToBookDTO(Book book);

    BookEntity bookToBookEntity(Book book);

    BookDTO bookEntityToBookDTO(BookEntity bookEntity);
}
