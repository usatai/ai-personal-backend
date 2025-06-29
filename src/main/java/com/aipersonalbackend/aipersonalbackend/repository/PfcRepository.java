package com.aipersonalbackend.aipersonalbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aipersonalbackend.aipersonalbackend.model.PfcData;

@Repository
public interface PfcRepository extends JpaRepository<PfcData,Long>{

}
