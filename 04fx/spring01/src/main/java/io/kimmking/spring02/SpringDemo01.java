package io.kimmking.spring02;

import io.kimmking.aop.ISchool;
import io.kimmking.spring01.Faculty;
import io.kimmking.spring01.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringDemo01 {
    
    public static void main(String[] args) {
        
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Student student101 = (Student)context.getBean("s101");
        System.out.println(student101.toString());
        
        Student student123 = (Student) context.getBean("student123");
        System.out.println(student123.toString());

        student123.print();
        
        Student student100 = (Student) context.getBean("student100");
        System.out.println(student100.toString());

        student100.print();
    
        Klass class1 = context.getBean(Klass.class);
        System.out.println(class1);
        System.out.println("Klass对象AOP代理后的实际类型："+class1.getClass());
        System.out.println("Klass对象AOP代理后的实际类型是否是Klass子类："+(class1 instanceof Klass));
    
        ISchool school = context.getBean(ISchool.class);
        System.out.println(school);
        System.out.println("ISchool接口的对象AOP代理后的实际类型："+school.getClass());
        
        ISchool school1 = context.getBean(ISchool.class);
        System.out.println(school1);
        System.out.println("ISchool接口的对象AOP代理后的实际类型："+school1.getClass());
        
        school1.ding();
        
        class1.dong();
    
        System.out.println("   context.getBeanDefinitionNames() ===>> "+ String.join(",", context.getBeanDefinitionNames()));

        Student s101 = (Student) context.getBean("s101");
        if (s101 != null) {
            System.out.println(s101);
        }
        Student s102 = (Student) context.getBean("s102");
        if (s102 != null) {
            System.out.println(s102);
        }
//        Student s108 = (Student) context.getBean("s108");
//        if (s102 != null) {
//            System.out.println(s108);
//        }


        // 3、使用@Bean说明create()返回的是一个bean，并(在SpringDemo01的main()中)调用create()
        // HelloBeanDefinitionRegistryPostProcessor中也可以调用不含@Bean注解的Create()方法，但需要：
        // 3.1、调用registry.registerBeanDefinition()加入到context中
        // 3.2、调用beanFactory.registerSingleton()加入到context中
        Faculty f1020 = Faculty.create();
        if (f1020 != null) {
            System.out.println(f1020);
        }
    }
}
