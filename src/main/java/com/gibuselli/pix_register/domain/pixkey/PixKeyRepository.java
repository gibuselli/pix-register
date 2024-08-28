package com.gibuselli.pix_register.domain.pixkey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PixKeyRepository extends JpaRepository<PixKey, UUID> {

    boolean existsByTypeAndValue(KeyType type, String value);

    Optional<PixKey> findByIdAndIsEnabled(UUID id, boolean isEnabled);

    @Query("SELECT p FROM PixKey p JOIN p.customer c WHERE " +
            "(:type IS NULL OR p.type = :type) " +
            "AND (:agency IS NULL OR c.agency = :agency) " +
            "AND (:account IS NULL OR c.account = :account) " +
            "AND (LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')) OR :name IS NULL) " +
            "AND (cast(:createdAt as date) IS NULL OR CAST(p.createdAt AS date) = :createdAt) " +
            "AND (cast(:disabledAt as date) IS NULL OR CAST(p.disabledAt AS date) = :disabledAt)")
    List<PixKey> queryByParams(@Param("type") KeyType type,
                              @Param("agency") String agency,
                              @Param("account") String account,
                              @Param("name") String name,
                              @Param("createdAt") LocalDate createdAt,
                              @Param("disabledAt") LocalDate disabledAt);
}
