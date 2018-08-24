package com.springboot.wsh.good.jpa;

import com.springboot.wsh.good.entity.GoodInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodInfoRepository
        extends JpaRepository<GoodInfoEntity, Long> {
}
