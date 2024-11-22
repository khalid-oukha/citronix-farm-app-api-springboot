package com.citronix.api.web.mapper;

import com.citronix.api.DTO.sale.SaleCreateDto;
import com.citronix.api.domain.Sale;
import com.citronix.api.web.VM.Sale.ResponseSaleVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleMapper {
    @Mapping(target = "harvest.id", source = "harvestId")
    Sale toSale(SaleCreateDto saleCreateDto);

    @Mapping(source = "harvest.id", target = "harvestId")
    @Mapping(source = "harvest.totalQuantity", target = "harvestTotalQuantity")
    @Mapping(source = "harvest.harvestDate", target = "harvestDate")
    @Mapping(source = "harvest.season", target = "harvestSeason")
    ResponseSaleVM toResponse(Sale sale);
}
