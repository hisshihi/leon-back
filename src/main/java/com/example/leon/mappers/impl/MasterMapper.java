package com.example.leon.mappers.impl;

import com.example.leon.domain.dto.MasterDto;
import com.example.leon.domain.entities.Masters;
import com.example.leon.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MasterMapper implements Mapper<Masters, MasterDto> {

    private final ModelMapper modelMapper;

    @Override
    public MasterDto mapTo(Masters masters) {
        return modelMapper.map(masters, MasterDto.class);
    }

    @Override
    public Masters mapFrom(MasterDto masterDto) {
        return modelMapper.map(masterDto, Masters.class);
    }
}
