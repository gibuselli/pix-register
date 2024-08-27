package com.gibuselli.pix_register.domain.pixkey;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PixKeyRepository extends JpaRepository<PixKey, UUID> {

    boolean existsByTypeAndValue(KeyType type, String value);
}
