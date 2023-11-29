package com.hlteam.naturezen.service.impl;

import com.hlteam.naturezen.entity.Tour;
import com.hlteam.naturezen.repository.TourRepository;
import com.hlteam.naturezen.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourServiceImpl implements TourService {
    @Autowired
    private TourRepository tourRepository;

    @Override
    public List<Tour> findAll() {
        return null;
    }
}
