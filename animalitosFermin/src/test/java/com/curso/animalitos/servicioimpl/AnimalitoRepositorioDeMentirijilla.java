package com.curso.animalitos.servicioimpl;

import com.curso.animalitos.repositorio.entity.AnimalitoEntity;
import com.curso.animalitos.repositorio.repository.AnimalitoRepositorio;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;


//@Repository
//@Primary
public class AnimalitoRepositorioDeMentirijilla implements AnimalitoRepositorio {
    @Override
    public List<AnimalitoEntity> findByTipo(String tipo) {
        return null;
    }

    @Override
    public List<AnimalitoEntity> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<AnimalitoEntity> findByTipoStartingWith(String tipo) {
        return null;
    }

    @Override
    public List<AnimalitoEntity> findByTipoEndingWith(String tipo) {
        return null;
    }

    @Override
    public List<AnimalitoEntity> findByTipoContaining(String tipo) {
        return null;
    }

    @Override
    public List<AnimalitoEntity> findAll() {
        return null;
    }

    @Override
    public List<AnimalitoEntity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<AnimalitoEntity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<AnimalitoEntity> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(AnimalitoEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends AnimalitoEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends AnimalitoEntity> S save(S entity) {
        if(!entity.getNombre().equals("Firulais"))
            throw new RuntimeException("Dato no valido... No me has pasao a firulais... Me has pasao" + entity.getNombre());
        if(!entity.getTipo().equals("Perro"))
            throw new RuntimeException("Dato no valido... No me has pasao a perro... Me has pasao" + entity.getTipo());
        entity.setId(-123459876L);
        return entity;
    }

    @Override
    public <S extends AnimalitoEntity> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<AnimalitoEntity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends AnimalitoEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends AnimalitoEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<AnimalitoEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public AnimalitoEntity getOne(Long aLong) {
        return null;
    }

    @Override
    public AnimalitoEntity getById(Long aLong) {
        return null;
    }

    @Override
    public AnimalitoEntity getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends AnimalitoEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends AnimalitoEntity> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends AnimalitoEntity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends AnimalitoEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends AnimalitoEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends AnimalitoEntity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends AnimalitoEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
