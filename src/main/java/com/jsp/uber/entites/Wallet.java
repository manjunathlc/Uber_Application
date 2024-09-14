package com.jsp.uber.entites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double balance;

    @OneToMany(mappedBy = "wallet")
    private List<WalletTransaction> transactions;

    @OneToOne
    private User user;

}
