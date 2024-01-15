package com.example.interview.DTO;

import com.example.interview.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    BookEntity bookToBookEntity (Book book);

    BookDTO bookEntityToBookDTO (BookEntity bookEntity);

}
