package com.tencent.hummingbird.portal.controller;


import com.tencent.hummingbird.portal.pojo.Tag;
import com.tencent.hummingbird.portal.r.R;
import com.tencent.hummingbird.portal.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ytsky
 * @since 2023-04-09
 */
@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private ITagService tagService;
    @GetMapping("/gets")
    public R<List<Tag>> gets(){
        return R.ok(tagService.getTags());
    }
}
