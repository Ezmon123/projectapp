package com.cezary.projectboard.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * @NotBlank annotation prevents field to be null or empty String
     * @Size annotation define required length of field
     */
    @NotBlank(message = "Project name required")
    private String projectName;
    /**
     * updatable=false property in Column annotation affects only SQL UPDATE statement,
     * "The column mapped would not be updatable using HQL or via hibernate methods.
     * In case you need to update the value in the DB you should be writing native SQL."
     */
    @NotBlank(message = "Project identifier is required")
    @Size(min=4, max = 5, message = "Please use 4 to 5 characters")
    @Column(updatable = false, unique = true)
    private String projectIdentifier;
    @NotBlank(message = "Description is required")
    private String description;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date start_date;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date end_date;
    @JsonFormat(pattern = "yyyy-mm-dd")
    @Column(updatable = false)
    private Date created_At;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updated_At;

    /**
     * fetch = FetchType.LAZY
     * FetchType.LAZY = Doesn’t load the relationships unless explicitly “asked for” via getter
     * FetchType.EAGER = Loads ALL relationships
     */
    /**
     * cascade = CascadeType.ALL
     * The cascade keyword is frequently applied in relational databases. It is mostly used in
     * in databases that contain tables with one-to-one, one-to-may and many-to-many relationships.
     * I think its name, cascade, is very revealing about its usage. When the cascade keyword is
     * specified in a property of an entity, it means that the change applied on the entity will
     * also be applied in the property as well. It is a fast way to manage the other side of the
     * relationship automatically, without writing extra code.
     */
    /**
     * in most cases The annotation @JoinColumn indicates that this entity is the owner of the relationship
     * (that is: the corresponding table has a column with a foreign key to the referenced table),
     * whereas the attribute mappedBy indicates that the entity in this side is the inverse of the
     * relationship, and the owner resides in the "other" entity. This also means that you can
     * access the other table from the class which you've annotated with "mappedBy"
     * (fully bidirectional relationship).
     *
     * @JoinColumn could be used on both sides of the relationship.
     * The question was about using @JoinColumn on the @OneToMany side (rare case).
     * And the point here is in physical information duplication (column name) along with not
     * optimized SQL query that will produce some additional UPDATE statements.
     * According to documentation:
     * Since many to one are (almost) always the owner side of a bidirectional relationship in the JPA spec,
     * the one to many association is annotated by @OneToMany(mappedBy=...)
     *
     * Cascading only makes sense only for Parent – Child associations (
     * the Parent entity state transition being cascaded to its Child entities).
     * Cascading from Child to Parent is not very useful and usually, it’s a mapping code smell.
     *
     * kaskadowosc:
     * https://vladmihalcea.com/a-beginners-guide-to-jpa-and-hibernate-cascade-types/
     *
     */
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "project", cascade = CascadeType.ALL)
    private Backlog backlog;

    //method will be call and executed before persist operation
    @PrePersist
    protected void onCreate(){
        this.created_At = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updated_At = new Date();
    }
}
