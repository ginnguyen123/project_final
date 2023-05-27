package cg.model.product;


import cg.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_album_media")
public class ProductAlbumMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "cloud_id")
    private String cloudId;

    @ManyToOne
    @JoinColumn(name = "product_id" , referencedColumnName = "id" , nullable = false)
    private Product product;

}
