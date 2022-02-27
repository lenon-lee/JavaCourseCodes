package io.kimmking.spring02;

import io.kimmking.spring01.Faculty;
import io.kimmking.spring01.Student;
import lombok.Data;

import javax.annotation.Resource;
import java.util.List;

@Data
public class Klass { 
    
    List<Student> students;

    // 1、xml方式配置 bean = faculty2040，并装入bean = class1 的属性teacher中
    Faculty teacher;

    // 2、xml方式配置 bean = faculty2080，使用@Resource注解注入到Klass实例(目前是class1)的属性teacher2080中
    @Resource(name = "faculty2080")
    Faculty teacher2080;


    public void dong(){
        System.out.println(this.getStudents());
    }
    
}
