package com.Bank.BankBackend.modules.account.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TypeIdentify {
    private Integer typeIdentifyId;
    private String name;
    private String description;
    private TypeStatus status;
}
