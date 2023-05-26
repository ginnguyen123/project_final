package cg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "colors")
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private EColor color;

    @OneToMany(targetEntity = Product.class , fetch = FetchType.EAGER)
    private List<Product> productList;

    @OneToMany(targetEntity = Size.class , fetch = FetchType.EAGER)
    private List<Size> sizeList;
}
