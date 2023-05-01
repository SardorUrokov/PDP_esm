package com.example.pdp_esm.entity.template;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class AbsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //qachon qo'shilgan
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdAt;

//    /*    securitydan keyin qo'shiladi */
//    kim qo'shdi user
//    @CreatedBy
//    @Column(nullable = false, name = "created_by")
//    private Long createdBy;
//
//    //kim o'zgartirdi id
//    @LastModifiedBy
//    @Column(nullable = false)
//    private Long updatedBy;

    //qachon o'zgardi
//    @UpdateTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(nullable = false)
//    private Date updatedAt;

}