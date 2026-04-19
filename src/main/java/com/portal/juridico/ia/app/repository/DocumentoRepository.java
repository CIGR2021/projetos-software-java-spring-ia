package com.portal.juridico.ia.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.portal.juridico.ia.app.model.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, Long>{}
