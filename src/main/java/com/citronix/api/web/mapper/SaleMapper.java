package com.citronix.api.web.mapper;

import com.citronix.api.DTO.sale.SaleCreateDto;
import com.citronix.api.domain.Sale;
import com.citronix.api.web.VM.Sale.ResponseSaleVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleMapper {
    Sale toSale(SaleCreateDto saleCreateDto);

    @Mapping(target = "harvest", source = "harvest")
    ResponseSaleVM toResponse(Sale sale);
}
