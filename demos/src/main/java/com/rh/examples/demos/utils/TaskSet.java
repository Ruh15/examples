package com.rh.examples.demos.utils;

import com.rh.examples.demos.utils.ResponseMsg;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashSet;
import java.util.Set;

/**
 * description:
 * author: Ruh
 * time: 2018/11/29.
 */
@Component
@Data
public class TaskSet {

    private Set<DeferredResult<ResponseMsg<String>>> set = new HashSet<>();

}