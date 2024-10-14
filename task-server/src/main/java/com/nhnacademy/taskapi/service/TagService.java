package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.dto.request.TagDto;
import com.nhnacademy.taskapi.dto.request.TagPostDto;
import com.nhnacademy.taskapi.entity.*;
import com.nhnacademy.taskapi.exception.ResourceAlreadyExistException;
import com.nhnacademy.taskapi.exception.ResourceNotFoundException;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import com.nhnacademy.taskapi.repository.TagRepository;
import com.nhnacademy.taskapi.repository.TaskRepository;
import com.nhnacademy.taskapi.repository.TaskTagRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final TaskTagRepository taskTagRepository;

    public TagService(TagRepository tagRepository, ProjectRepository projectRepository, TaskRepository taskRepository, TaskTagRepository taskTagRepository) {
        this.tagRepository = tagRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.taskTagRepository = taskTagRepository;
    }

    public void saveTag(Long projectId, TagPostDto tagDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project" + "ID" + projectId));

        Tag tag = new Tag(project, tagDto.tagName());
        tagRepository.save(tag);
    }

    public List<TagDto> getTagsByProjectId(Long projectId) {
        if(!projectRepository.existsById(projectId)){
            throw new ResourceNotFoundException("ProjectId: " + projectId);
        }
        List<Tag> tags = tagRepository.findAllByProjectProjectId(projectId);
        List<TagDto> tagDtos = new ArrayList<>();
        for(Tag tag : tags){
            TagDto tagDto = new TagDto(tag.getTagId(), tag.getTagName());
            tagDtos.add(tagDto);
        }
        return tagDtos;
    }

    public TagDto updateTag(Long projectId, TagDto tagDto) {
        if(!projectRepository.existsById(projectId)){
            throw new ResourceNotFoundException("ProjectId: " + projectId);
        }
        Tag tag = tagRepository.findById(tagDto.tagId())
                        .orElseThrow(() -> new ResourceNotFoundException("TagId: " +tagDto.tagId()));
        tag.setTagName(tagDto.tagName());
        tagRepository.save(tag);
        return new TagDto(tag.getTagId(), tag.getTagName());
    }

    public void deleteTag(Long projectId, TagDto tagDto){
        if(!projectRepository.existsById(projectId)){
            throw new ResourceNotFoundException("ProjectId: " + projectId);
        }
        if(!tagRepository.existsById(tagDto.tagId())){
            throw new ResourceNotFoundException("TagId: " +tagDto.tagId());
        }
        tagRepository.deleteById(tagDto.tagId());
    }

    public void saveTaskTag(Long taskId, Long tagId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("TaskId: " +taskId));
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("TagId: " +tagId));

        if(taskTagRepository.existsByTaskAndTag(task, tag)){
            throw new ResourceAlreadyExistException("tasktag already exist");
        }

        taskTagRepository.save(new TaskTag(task, tag));

    }

    public void deleteTaskTag(Long taskId, Long tagId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("TaskId: " +taskId));
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("TagId: " +tagId));

        TaskTag taskTag = taskTagRepository.findByTaskAndTag(task, tag)
                .orElseThrow(() -> new ResourceNotFoundException("TaskId: " +taskId + " TagId: " +tagId));

        taskTagRepository.delete(taskTag);
    }


}
