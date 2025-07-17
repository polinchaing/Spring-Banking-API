package kh.edu.cstad.mbapi.mapper;

import kh.edu.cstad.mbapi.domain.Account;
import kh.edu.cstad.mbapi.dto.AccountResponse;
import kh.edu.cstad.mbapi.dto.CreateAccountRequest;
import kh.edu.cstad.mbapi.dto.UpdateAccountRequest;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface AccountMapper {


   @Mapping(source = "accountType.type", target = "accountType")
   AccountResponse toAccountResponse(Account account);

   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   void toAccountPartially(
           UpdateAccountRequest updateAccountRequest,
           @MappingTarget Account account);
}