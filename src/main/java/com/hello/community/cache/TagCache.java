package com.hello.community.cache;

import com.hello.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagCache {
    public static List<TagDTO> get(){
        ArrayList<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("js","php","java","python","node","c","c++","golang","typescript","html"));
        TagDTO framework = new TagDTO();
        framework.setCategoryName("架构框架");
        framework.setTags(Arrays.asList("spring","mybatis","express","django","struts","javaweb"));
        tagDTOS.add(program);
        tagDTOS.add(framework);
        return tagDTOS;
    }
    public static String filterInvalid(String tags){
        String[] split = StringUtils.split(tags,",");
        List<TagDTO> tagDTOS = get();
        List<String> collect = tagDTOS.stream()
                .flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !collect.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
