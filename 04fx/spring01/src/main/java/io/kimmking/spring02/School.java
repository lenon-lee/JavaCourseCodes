package io.kimmking.spring02;

import io.kimmking.aop.ISchool;
import io.kimmking.spring01.Faculty;
import io.kimmking.spring01.Student;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

@Data
public class School implements ISchool {
    
    // Resource 
    @Autowired(required = true) //primary
    Klass class1;
    
    @Resource(name = "student100")
    Student student100;

    @Resource(name = "student123")
    Student student123;

    @Resource(name = "faculty2040")
    Faculty faculty2040;

    @Override
    public void ding(){
    
        System.out.println("Class1 have " + this.class1.getStudents().size() + " students and one is " + this.student100 + ".");
        
    }
    
}
