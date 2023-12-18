package com.hlteam.naturezen.service.impl;

import java.util.List;

import com.hlteam.naturezen.dto.request.CreateTagRequest;
import com.hlteam.naturezen.entity.Tag;
import com.hlteam.naturezen.exception.NotFoundException;
import com.hlteam.naturezen.repository.TagRepository;
import com.hlteam.naturezen.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;



@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<Tag> getListTag() {
        // TODO Auto-generated method stub
        return tagRepository.findAll(Sort.by("id").descending());
    }

    @Override
    public Tag createTag(CreateTagRequest request) {
        Tag tag = new Tag();
        tag.setName(request.getName());
        tag.setEnable(false);
        tagRepository.save(tag);
        return tag;
    }

    @Override
    public Tag updateTag(long id, CreateTagRequest request) {
        // TODO Auto-generated method stub

        Tag tag = tagRepository.findById(id).orElseThrow(()-> new NotFoundException("Not Foud Tag"));
        tag.setName(request.getName());
        tagRepository.save(tag);
        return tag;
    }

    @Override
    public void deleleTag(long id) {
        // TODO Auto-generated method stub
        Tag tag = tagRepository.findById(id).orElseThrow(()-> new NotFoundException("Not Foud Tag"));
        tagRepository.delete(tag);
    }

    @Override
    public void enableTag(long id){
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Tag With Id: " + id));
        if(tag.isEnable()){
            tag.setEnable(false);
        } else{
            tag.setEnable(true);
        }
        tagRepository.save(tag);
    }
    
}
