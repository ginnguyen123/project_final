package cg.repository;

import cg.model.media.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, String> {
    @Override
    <S extends Media> List<S> saveAll(Iterable<S> entities);
}
