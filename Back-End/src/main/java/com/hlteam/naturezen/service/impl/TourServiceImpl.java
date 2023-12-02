package com.hlteam.naturezen.service.impl;

import com.hlteam.naturezen.dto.request.TourDto;
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

    @Override
    public void changeState(int id) {

    }

    @Override
    public List<Tour> findByCatgegory(int id) {
        return null;
    }

    @Override
    public Tour get(int id) {
        return null;
    }

    @Override
    public int totalTour() {
        return 0;
    }

    @Override
    public List<Tour> findAllActive() {
        return null;
    }

    @Override
    public List<Tour> findAllUnActive() {
        return null;
    }

    @Override
    public List<Tour> findAllInDate() {
        return null;
    }

    @Override
    public List<Tour> findAllOutDate() {
        return null;
    }

    @Override
    public List<Tour> findAllStillSlot() {
        return null;
    }

    @Override
    public List<Tour> findALlAllSlot() {
        return null;
    }

    @Override
    public List<Tour> findAllFinished() {
        return null;
    }

    @Override
    public List<Tour> findAllUnFinished() {
        return null;
    }

    @Override
    public void create(TourDto tourDto) {

    }

    @Override
    public void update(int id, TourDto tourDto) {

    }

    @Override
    public void enable(int id) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Tour findByName(String name) {
        return null;
    }
}
