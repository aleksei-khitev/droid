package ru.akhitev.seng.droid.conf.db.entity;

import javax.persistence.*;

@Entity
@Table(name = "config")
@SequenceGenerator(name = "seq", initialValue = 20)
public class DroidConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "property", nullable = false)
    private String property;

    @Column(name = "value", nullable = false)
    private String value;

    public DroidConfig(){}

    public DroidConfig(String property, String value) {
        this.property = property;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
