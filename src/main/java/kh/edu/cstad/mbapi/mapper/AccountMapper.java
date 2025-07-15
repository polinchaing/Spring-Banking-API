package kh.edu.cstad.mbapi.mapper;

import kh.edu.cstad.mbapi.domain.Account;
import kh.edu.cstad.mbapi.dto.AccountResponse;
import kh.edu.cstad.mbapi.dto.CreateAccountRequest;
import kh.edu.cstad.mbapi.dto.UpdateAccountRequest;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface AccountMapper {

   @Mapping(target = "accountType", ignore = true)
   Account fromCreateAccountRequest(CreateAccountRequest createAccountRequest);

   @Mapping(target = "accountType", source = "accountType")
   AccountResponse toAccountResponse(Account account);

   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   void toAccountPartially(
           UpdateAccountRequest updateAccountRequest,
           @MappingTarget Account account);
}