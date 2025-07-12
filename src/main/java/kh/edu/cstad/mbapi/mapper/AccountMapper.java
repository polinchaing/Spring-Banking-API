package kh.edu.cstad.mbapi.mapper;

import kh.edu.cstad.mbapi.domain.Account;
import kh.edu.cstad.mbapi.dto.AccountResponse;
import kh.edu.cstad.mbapi.dto.CreateAccountRequest;
import kh.edu.cstad.mbapi.dto.UpdateAccountRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring")
public interface AccountMapper {

   Account fromCreateAccountRequest(CreateAccountRequest createAccountRequest);

   AccountResponse toAccountResponse(Account account);

   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   void toAccountPartially(
           UpdateAccountRequest updateAccountRequest,
           @MappingTarget Account account);
}
