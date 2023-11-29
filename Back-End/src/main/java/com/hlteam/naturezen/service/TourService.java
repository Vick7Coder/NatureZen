package com.hlteam.naturezen.service;

import com.hlteam.naturezen.entity.Tour;

import java.util.List;

public interface TourService {
    List<Tour> findAll();
    void changeState(int id);

}
