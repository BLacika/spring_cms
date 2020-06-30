package bala.spring_cms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Base64;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Length(min = 5, message = "*Your username must have at least 5 characters")
    private String username;

    @Column(nullable = false)
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @JsonIgnore
    private String password;

    @Transient
    private String passwordConfirm;

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    @Email(message = "*Please provide a valid Email")
    private String email;

    @ManyToMany
    private Set<Role> roles;

    @Lob
    private byte[] file;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    public String getFile() {
        return Base64.getMimeEncoder().encodeToString(file);
    }

    public byte[] getFileInBytes() {
        return file;
    }
}
