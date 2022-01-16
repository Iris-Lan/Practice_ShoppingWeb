package com.Iris.demo.entity;

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
@Table(name = "order_product")
public class OrderProduct implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "orderProduct_id", insertable = false, updatable = false, length = 36)
    private String id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order orderId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products productId;
}
