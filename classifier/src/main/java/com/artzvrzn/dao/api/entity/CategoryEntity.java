package com.artzvrzn.dao.api.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Category")
@Table(name = "category", schema = "app")
public class CategoryEntity extends BaseEntity {
}
