package lab.is.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import lab.is.bd.entities.MusicBand;

@Repository
public interface MusicBandRepository extends
    JpaRepository<MusicBand, Long>,
    JpaSpecificationExecutor<MusicBand> {
}
