# Interview task

## Task 4. New library

Rest application for library.

1) As a User, I want to get list of all books
2) As a User, I want to reserve some books
3) As a User, I want to get list of available books
4) As a User, I want to get list of books that was reserved by me

For PoC let's use one of listed below data sources, but in future database will be used as datasource for books.

### Additional requirements:

* As a User, I want to return some of my books

### Additional architecture requirements:

* high-load
* multiple sources of data
* security vulnerabilities

### JSON Datasource

```json
[
  {
    "id": "1",
    "name": "Harry Potter and the Philosopher's Stone",
    "author": "Joanne Rowling",
    "copies": 50
  },
  {
    "id": "2",
    "name": "The Lord of the Rings: The Fellowship of the Ring",
    "author" : "John Ronald Reuel Tolkien",
    "copies": 10
  },
  {
    "id": "3",
    "name": "Epam handbook",
    "author" : "Epam",
    "copies": 1
  }
]
