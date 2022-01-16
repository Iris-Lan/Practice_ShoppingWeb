package com.Iris.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "membersForDemo")
public class Members implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "member_id", insertable = false, updatable = false, length = 36)
    private String id;

    @Column(nullable = false, length = 36)
    private String memberId;

    @NotBlank(message = "Please write the correct request data.")
    @Column(length = 25)
    private String memberName;

    @Column(length = 10)
    private String gender;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Order> orders = new LinkedList<>();

    //Only for Ans5 use. To show how many orders did this member have.
    private Integer orderNumberTotal;

}
