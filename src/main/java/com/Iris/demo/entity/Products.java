package com.Iris.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "productsForDemo")
public class Products implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "product_id", insertable = false, updatable = false, length = 36)
    private String id;

    @NotBlank(message = "Please write the correct request data.")
    @Column(nullable = false, length = 25)
    private String productName;

    @Column(nullable = false)
    private Integer price;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="order_product",
            joinColumns = {
                    @JoinColumn(name="order_id")},
            inverseJoinColumns = {
                    @JoinColumn(name="product_id")})
    private List<Order> orders;
}
