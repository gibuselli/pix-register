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

    @Query("SELECT p FROM PixKey p JOIN p.account a WHERE " +
            "(:type IS NULL OR p.type = :type) " +
            "AND (:agency IS NULL OR a.agency = :agency) " +
            "AND (:accountNumber IS NULL OR a.accountNumber = :accountNumber) " +
            "AND (LOWER(a.customerName) LIKE LOWER(CONCAT('%', :customerName, '%')) OR :customerName IS NULL) " +
            "AND (cast(:createdAt as date) IS NULL OR CAST(p.createdAt AS date) = :createdAt) " +
            "AND (cast(:disabledAt as date) IS NULL OR CAST(p.disabledAt AS date) = :disabledAt)")
    List<PixKey> queryByParams(@Param("type") KeyType type,
                              @Param("agency") String agency,
                              @Param("accountNumber") String accountNumber,
                              @Param("customerName") String customerName,
                              @Param("createdAt") LocalDate createdAt,
                              @Param("disabledAt") LocalDate disabledAt);

    @Query("SELECT CASE WHEN COUNT(pk) > 0 THEN TRUE ELSE FALSE END " +
            "FROM PixKey pk " +
            "JOIN pk.account acc " +
            "WHERE acc.agency = :agency " +
            "AND acc.accountNumber = :accountNumber " +
            "AND pk.id <> :id")
    boolean existsByAgencyAndAccountAndNotByPixKeyId(@Param("agency") String agency,
                                                @Param("accountNumber") String accountNumber,
                                                @Param("id") UUID id);
}
