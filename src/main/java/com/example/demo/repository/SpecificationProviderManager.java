package com.example.demo.repository;

public interface SpecificationProviderManager<T> {
    SpecificationProvider<T> getSpecification(String key);
}
