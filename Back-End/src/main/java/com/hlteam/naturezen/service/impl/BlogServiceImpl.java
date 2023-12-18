package com.hlteam.naturezen.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hlteam.naturezen.dto.request.CreateBlogRequest;
import com.hlteam.naturezen.entity.Blog;
import com.hlteam.naturezen.entity.Image;
import com.hlteam.naturezen.entity.Tag;
import com.hlteam.naturezen.entity.User;
import com.hlteam.naturezen.exception.NotFoundException;
import com.hlteam.naturezen.repository.BlogRepository;
import com.hlteam.naturezen.repository.ImageRepository;
import com.hlteam.naturezen.repository.TagRepository;
import com.hlteam.naturezen.repository.UserRepository;
import com.hlteam.naturezen.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;


@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Blog> getList() {
        // TODO Auto-generated method stub
        return blogRepository.findAll(Sort.by("id").descending());
    }

    @Override
    public Blog getBlog(long id){
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Blog"));
        return blog;
    }

    @Override
    public Blog createBlog(CreateBlogRequest request) {
        // TODO Auto-generated method stub
        Blog blog = new Blog();
        blog.setTitle(request.getTitle());
        blog.setDescription(request.getDescription());
        blog.setContent(request.getContent());
        Image image = imageRepository.findById(request.getImageId()).orElseThrow(() -> new NotFoundException("Not Found Image"));
        blog.setImage(image);
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(()-> new NotFoundException("Not Found User"));
        blog.setUser(user);
        blog.setCreateAt(new Timestamp(System.currentTimeMillis()));
        Set<Tag> tags = new HashSet<>();
        for(Long tagId : request.getTags()){
            Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new NotFoundException("Not Found Tag"));
            tags.add(tag);
        }
        blog.setTags(tags);
        blogRepository.save(blog);
        return blog;
    }

    @Override
    public Blog updateBlog(long id, CreateBlogRequest request) {
        // TODO Auto-generated method stub
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Blog"));
        blog.setTitle(request.getTitle());
        blog.setDescription(request.getDescription());
        blog.setContent(request.getContent());
        Image image = imageRepository.findById(request.getImageId()).orElseThrow(() -> new NotFoundException("Not Found Image"));
        blog.setImage(image);
        Set<Tag> tags = new HashSet<>();
        for(Long tagId : request.getTags()){
            Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new NotFoundException("Not Found Tag"));
            tags.add(tag);
        }
        blog.setTags(tags);
        blogRepository.save(blog);
        return blog;
    }

    @Override
    public void deleteBlog(long id) {
        // TODO Auto-generated method stub
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Blog"));
        blog.getTags().remove(this);
        blogRepository.delete(blog);
    }

    @Override
    public List<Blog> getListNewest(int limit) {
        // TODO Auto-generated method stub
        List<Blog> list = blogRepository.getListNewest(limit);
        return list;
    }


}
