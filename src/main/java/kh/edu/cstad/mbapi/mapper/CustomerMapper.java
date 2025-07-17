package kh.edu.cstad.mbapi.mapper;

import kh.edu.cstad.mbapi.domain.Customer;
import kh.edu.cstad.mbapi.dto.CreateCustomerRequest;
import kh.edu.cstad.mbapi.dto.CustomerResponse;
import kh.edu.cstad.mbapi.dto.UpdateCustomerRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.*;


@Mapper(componentModel = "spring") // using componentModel To Create BEAN
public interface CustomerMapper {

    //MAP DTO -> MODEL
    //MAP MODEL -> DTO
    //WHAT IS SOURCE DATA ? (PARAMETER)
    //WHAT IS TARGET DATA ? (RETURN TYPE)

    CustomerResponse toCustomerResponse(Customer customer);

    @Mapping(target = "customerSegment", ignore = true)
    Customer fromCreateCustomerRequest(CreateCustomerRequest createCustomerRequest);

    //2 reference
    //BeanMapping use for specific condition of bean
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toCustomerPartially(
            UpdateCustomerRequest updateCustomerRequest,
            @MappingTarget Customer customer);
    
}
