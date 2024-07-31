package com.emeritus.cms.enrollment_service.FeignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "COURSE-SERVICE")
public interface CourseServiceClient {

    @GetMapping("/courses/course/{id}")
    boolean doesCourseExistsById(@PathVariable Long id);

}
