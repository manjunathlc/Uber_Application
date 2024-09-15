package com.jsp.uber.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDto {

    private Long id;

    private Double balance;

    private List<WalletTransactionDto> transactions;

    private UserDto user;

}
