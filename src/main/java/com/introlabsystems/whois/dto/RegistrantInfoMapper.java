package com.introlabsystems.whois.dto;

import com.introlabsystems.whois.model.whois.RegistrantInfo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RegistrantInfoMapper {

    RegistrantInfoDTO toDo(RegistrantInfo source);
}
