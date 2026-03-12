package com.example.demo.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Embeddable
@Data
public class AffectationId implements Serializable {
    private Long employeId;
    private Long phaseId;
}