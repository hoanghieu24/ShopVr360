package com.shopvr360.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "table_lang")
public class TableLang {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 500)
    @NotNull
    @Column(name = "lang_vi", nullable = false, length = 500)
    private String langVi;

    @Size(max = 255)
    @Column(name = "lang_en")
    private String langEn;

    @Size(max = 255)
    @Column(name = "lang_cn")
    private String langCn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLangVi() {
        return langVi;
    }

    public void setLangVi(String langVi) {
        this.langVi = langVi;
    }

    public String getLangEn() {
        return langEn;
    }

    public void setLangEn(String langEn) {
        this.langEn = langEn;
    }

    public String getLangCn() {
        return langCn;
    }

    public void setLangCn(String langCn) {
        this.langCn = langCn;
    }

}