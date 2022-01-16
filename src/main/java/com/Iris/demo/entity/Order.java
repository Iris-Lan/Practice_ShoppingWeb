package com.Iris.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "ordersForDemo")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "order_id", insertable = false, updatable = false, length = 36)
    private String id;

    //Order tracking number
    @Column(nullable = false, length = 36)
    private String orderId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private LocalDateTime orderDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "member_id_fk")
    private Members member;

    @NotNull
    private Integer totalPrice;

    @NotNull
    @ManyToMany(mappedBy="orders")
    private List<Products> products;
}
