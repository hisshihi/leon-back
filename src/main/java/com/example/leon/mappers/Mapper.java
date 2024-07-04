package com.example.leon.mappers;

public interface Mapper<A, B> {

    B mapTo(A a);
    A mapFrom(B b);

}
