package com.eale.base.repository;

import com.eale.base.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LabelRepository extends JpaRepository<Label,String>, JpaSpecificationExecutor<Label> {

}
