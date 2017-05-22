package com.ahmetsahinoglu.grave.hibernate.entity;

import io.robe.convert.common.annotation.Convert;
import io.robe.hibernate.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table
public class Province extends BaseEntity {

    @Length(min = 1, max = 32, message = "İl kodu minimum 1, maksimum 32 karakter uzunluğunda olmaldır.")
    @NotEmpty(message = "İl kodu boş olamaz.")
    @Convert
    @Column(length = 32, unique = true, nullable = false)
    private String code;

    @Pattern(regexp = "^[a-zA-ZÇçĞğİıÖöŞşÜü]+$", message = "İl adı doğru formatta yazılmalıdır. Sayısal değer veya özel karakter içeremez.")
    @Length(min = 3, max = 50, message = "İl adı minimum 3, maksimum 50 karakter uzunluğunda olmaldır.")
    @NotEmpty(message = "İl adı boş olamaz.")
    @Convert
    @Column(length = 50, nullable = false)
    private String name;

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
}
