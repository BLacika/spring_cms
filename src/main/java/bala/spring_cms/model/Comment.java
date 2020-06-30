package bala.spring_cms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String author;

    @NotBlank
    @Email
    private String email;

    @Lob
    @NotBlank
    private String content;

    private String status;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
