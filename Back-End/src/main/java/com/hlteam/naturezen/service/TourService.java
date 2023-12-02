package com.hlteam.naturezen.service;

import com.hlteam.naturezen.dto.request.TourDto;
import com.hlteam.naturezen.entity.Tour;

import java.util.List;

public interface TourService {
    List<Tour> findAll();
    void changeState(int id);

    List<Tour> findByCatgegory(int id);
    Tour get(int id);

    int totalTour ();

    List<Tour> findAllActive();
    List<Tour> findAllUnActive();
    List<Tour> findAllInDate();
    List<Tour> findAllOutDate();
    List<Tour> findAllStillSlot();
    List<Tour> findALlAllSlot();
    List<Tour> findAllFinished();
    List<Tour> findAllUnFinished();

    void create(TourDto tourDto);
    void update(int id, TourDto tourDto);
    void enable(int id);
    void delete(int id);
    Tour findByName(String name);




}
