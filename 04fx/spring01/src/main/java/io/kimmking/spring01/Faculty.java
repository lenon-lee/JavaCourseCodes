package io.kimmking.spring01;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Faculty implements Serializable, BeanNameAware, ApplicationContextAware {
    private int id;
    private String name;

    private String beanName;
    private ApplicationContext applicationContext;

    public void init(){
        System.out.println("hello..., this is a faculty");
    }

    // 3、使用@Bean说明create()返回的是一个bean，并(在SpringDemo01的main()中)调用create()
    // HelloBeanDefinitionRegistryPostProcessor中也可以调用不含@Bean注解的Create()方法，但需要：
    // 3.1、调用registry.registerBeanDefinition()加入到context中
    // 3.2、调用beanFactory.registerSingleton()加入到context中
    @Bean
    public static Faculty create(){
        return new Faculty(1020,"Faculty1020","Faculty === POJO", null);
    }

    public void print() {
        System.out.println(this.beanName);
        System.out.println("   context.getBeanDefinitionNames() ===>> "
                + String.join(",", applicationContext.getBeanDefinitionNames()));

    }
}
