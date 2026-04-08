package lk.ijse.zoneservice.repo;
import lk.ijse.zoneservice.entity.Zone;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ZoneRepository implements JpaRepository<Zone, String> {

    @Override
    public void flush() {

    }

    @Override
    public <S extends Zone> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Zone> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Zone> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Zone getOne(String s) {
        return null;
    }

    @Override
    public Zone getById(String s) {
        return null;
    }

    @Override
    public Zone getReferenceById(String s) {
        return null;
    }

    @Override
    public <S extends Zone> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Zone> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Zone> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Zone> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Zone> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Zone> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Zone, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Zone> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Zone> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Zone> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Zone> findAll() {
        return List.of();
    }

    @Override
    public List<Zone> findAllById(Iterable<String> strings) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Zone entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Zone> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Zone> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Zone> findAll(Pageable pageable) {
        return null;
    }
}
