package com.hlteam.naturezen.service;

import com.hlteam.naturezen.dto.request.CreateTagRequest;
import com.hlteam.naturezen.entity.Tag;

import java.util.List;


public interface TagService {
    
    List<Tag> getListTag();

    Tag createTag(CreateTagRequest request);

    Tag updateTag(long id,CreateTagRequest request);

    void enableTag(long id);

    void deleleTag(long id);

}
