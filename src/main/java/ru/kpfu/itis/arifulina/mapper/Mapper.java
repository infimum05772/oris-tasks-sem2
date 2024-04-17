package ru.kpfu.itis.arifulina.mapper;

public interface Mapper<E, T> {
    T map(E e);
}
