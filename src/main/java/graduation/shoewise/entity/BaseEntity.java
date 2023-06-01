package graduation.shoewise.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 개념파악필요 - 개파필
@EntityListeners(value = {AuditingEntityListener.class}) // 개파필
public abstract class BaseEntity {

    @CreatedDate
    @Column(name="createddate", updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name="modifieddate")
    private LocalDateTime modifyDate;
}
