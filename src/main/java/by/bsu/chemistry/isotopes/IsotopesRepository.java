package by.bsu.chemistry.isotopes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Year;
import java.util.List;

/**
 * Created by Ivan on 03.09.2017.
 */

public interface IsotopesRepository extends JpaRepository<Isotope, Integer>{
    @Query("SELECT isotope FROM Isotope isotope WHERE isotope.nucid =:nucid")
    List<Isotope> findById(@Param("nucid") String nucid);

    @Query("SELECT isotope FROM Isotope isotope WHERE isotope.z =:z ORDER BY isotope.n ASC")
    List<Isotope> findByZ(@Param("z") int z);

    @Query("SELECT isotope FROM Isotope isotope WHERE isotope.symbol =:symbol ORDER BY isotope.n ASC")
    List<Isotope> findBySymbol(@Param("symbol")String symbol);

    @Query("SELECT isotope FROM Isotope isotope WHERE isotope.z + isotope.n =:massNumber ORDER BY isotope.z ASC")
    List<Isotope> findByMassNumber(@Param("massNumber")int massNumber);

    @Query("SELECT isotope.nucid FROM Isotope isotope WHERE isotope.nucid =:nucid")
    List<String> findByIdOnlyId(@Param("nucid")String nucid);

    @Query("SELECT isotope.nucid FROM Isotope isotope WHERE isotope.z =:z ORDER BY isotope.n ASC")
    List<String> findByZOnlyId(@Param("z")int z);

    @Query("SELECT isotope.nucid FROM Isotope isotope WHERE isotope.symbol =:symbol ORDER BY isotope.n ASC")
    List<String> findBySymbolOnlyId(@Param("symbol")String symbol);

    @Query("SELECT isotope.nucid FROM Isotope isotope WHERE isotope.z + isotope.n =:massNumber ORDER BY isotope.z ASC")
    List<String> findByMassNumberOnlyId(@Param("massNumber")int massNumber);
}
