package com.hlteam.naturezen.Filter.Tour;


import com.hlteam.naturezen.entity.Category;
import com.hlteam.naturezen.entity.Tour;
import com.hlteam.naturezen.specification.GenericSpecification;
import com.hlteam.naturezen.specification.SpecificationRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GenericTourSpecification {
    @Autowired
    private GenericSpecification<Tour> genericSpecification;
    @Autowired
    private SpecificationRelation<Tour, Category> tourCategorySpecificationRelation;

    public Specification<Tour> generic(TourFilter tourFilter){
        Specification<Tour> specification = null;
        List<Specification<Tour>> specifications = new ArrayList<>();
        if(tourFilter.getAbstr() != null){
            specifications.add(genericSpecification.like(TourFilter.FIELD_ABSTR, "%"+tourFilter.getAbstr()+"%"));
        }
        if(tourFilter.getActive() != null){
            if(tourFilter.getActive().equals(true)){
                specifications.add(genericSpecification.equals(TourFilter.FIELD_ACTIVE, true));
            } else if (tourFilter.getActive().equals(false)) {
                specifications.add(genericSpecification.equals(TourFilter.FIELD_ACTIVE, false));
                
            }
        }
        List<Specification<Tour>> result = new ArrayList<>();
        for(int i =0; i< specifications.size(); i++){
            if(specifications.get(i) != null){
                result.add(specifications.get(i));
            }
        }
        for(int i=0; i< result.size(); i++){
            if(specification == null){
                specification = Specification.where(result.get(i));
            } else{
                specification = specification.and(result.get(i));
            }
        }
        return  specification;
    }

}
