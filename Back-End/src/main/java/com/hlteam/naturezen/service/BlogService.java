package com.hlteam.naturezen.service;

import com.hlteam.naturezen.dto.request.CreateBlogRequest;
import com.hlteam.naturezen.entity.Blog;

import java.util.List;



public interface BlogService {
    
    List<Blog> getList();

    List<Blog> getListNewest(int limit);

    Blog getBlog(long id);

    Blog createBlog(CreateBlogRequest request);

    Blog updateBlog(long id,CreateBlogRequest request);

    void deleteBlog(long id);

}
