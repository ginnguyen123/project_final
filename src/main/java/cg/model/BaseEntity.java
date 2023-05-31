package cg.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;


@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @CreationTimestamp
    @Column(name = "created_at" , updatable = false)
    private Date createdAt;

    @CreatedBy
    @Column(name = "created_by")
    private Long createdBy;

    @UpdateTimestamp
    @Column(name = "update_at")
    private Date updateAt;

    @LastModifiedBy
    @Column(name = "update_by")
    private Long updateBy;

}
