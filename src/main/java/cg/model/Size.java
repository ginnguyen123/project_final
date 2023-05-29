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
@Table(name = "sizes")
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 5)
    private ESize size;

    @OneToMany(targetEntity = Product.class , fetch = FetchType.EAGER)
    private List<Product> productList;

//    @OneToMany(targetEntity = Color.class , fetch = FetchType.EAGER)
//    private List<Color> colorList;



}
