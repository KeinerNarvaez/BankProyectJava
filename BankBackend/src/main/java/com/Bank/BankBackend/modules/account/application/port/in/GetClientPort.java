package com.Bank.BankBackend.modules.account.application.port.in;

import com.Bank.BankBackend.modules.account.application.dto.request.GetClientRequest;
import com.Bank.BankBackend.modules.account.application.dto.response.GetClientResponse;
import com.Bank.BankBackend.modules.account.application.dto.response.GetListClientResponse;
import com.Bank.BankBackend.modules.account.domain.model.Client;

import java.util.List;

public interface GetClientPort {
    List<GetListClientResponse> getClientInactive();
    List<GetListClientResponse> getClientActive();
    GetClientResponse getClient(GetClientRequest request);
}
