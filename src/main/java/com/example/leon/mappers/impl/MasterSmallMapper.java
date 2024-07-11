package com.example.leon.mappers.impl;

import com.example.leon.domain.dto.MasterDtoSmall;
import com.example.leon.domain.entities.Masters;
import com.example.leon.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MasterSmallMapper implements Mapper<Masters, MasterDtoSmall> {

    private ModelMapper modelMapper;


    @Override
    public MasterDtoSmall mapTo(Masters masters) {
        return modelMapper.map(masters, MasterDtoSmall.class);
    }

    @Override
    public Masters mapFrom(MasterDtoSmall masterDtoSmall) {
        return modelMapper.map(masterDtoSmall, Masters.class);
    }
}
