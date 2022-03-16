package com.jocoos.spring.domain.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jocoos.spring.domain.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class Users extends Timestamped {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;
    private String name;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    private boolean deleted = true;
    private String createdBy;
    private String modifiedBy;

    public Users(Users entity, UserRole userRole) {
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.password = entity.getPassword();
        this.name = entity.getName();
        this.role = userRole;
        this.deleted = entity.isDeleted();
        this.createdBy = entity.getCreatedBy();
        this.modifiedBy = entity.getModifiedBy();
    }
}
