package com.ahmetsahinoglu.grave.hibernate.entity;

import io.robe.common.service.search.SearchFrom;
import io.robe.common.service.search.SearchIgnore;
import io.robe.convert.common.annotation.Convert;
import io.robe.hibernate.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Pattern;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"code", "provinceOid"})})
public class District extends BaseEntity implements Comparable {

    @Length(min = 1, max = 32, message = "İlçe kodu minimum 1, maksimum 32 karakter uzunluğunda olmaldır.")
    @NotEmpty(message = "İlçe kodu boş olamaz.")
    @Convert
    @Column(length = 32, nullable = false)
    private String code;

    @Length(min = 2, max = 50, message = "İlçe adı minimum 2, maksimum 50 karakter uzunluğunda olmaldır.")
    @NotEmpty(message = "İlçe adı boş olamaz.")
    @Pattern(regexp = "^[a-zA-ZÇçĞğİıÖöŞşÜü]+$", message = "İlçe adı doğru formatta yazılmalıdır. Sayısal değer veya özel karakter içeremez.")
    @Convert
    @Column(length = 50, nullable = false)
    private String name;

    @SearchIgnore
    @SearchFrom(entity = Province.class, target = "name", id = "oid")
    @Length(min = 32, max = 32, message = "İl kodu minimum 32, maksimum 32 karakter uzunluğunda olmaldır.")
    @NotEmpty(message = "İl kodu boş olamaz.")
    @Convert
    @Column(length = 32, nullable = false)
    private String provinceOid;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvinceOid() {
        return provinceOid;
    }

    public void setProvinceOid(String provinceOid) {
        this.provinceOid = provinceOid;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof District))
            throw new ClassCastException();
        District e = (District) o;
        return this.name.compareTo(e.getName());
    }
}
