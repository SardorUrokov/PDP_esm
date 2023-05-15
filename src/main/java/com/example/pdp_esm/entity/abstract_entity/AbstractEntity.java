package com.example.pdp_esm.entity.abstract_entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AbstractEntity implements Serializable {
    public Long id;
    public String name;
    public String className;
    public Date created;
    public Date updated;
}
