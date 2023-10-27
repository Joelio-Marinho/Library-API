package com.joelio.libraryapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Loan  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private  String isbn;
    @Column
    private String customer;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @Column
    private LocalDate localDate;
    @Column
    private Boolean returned;




}
