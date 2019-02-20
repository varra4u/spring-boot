package com.varra.rabbitmq.spring;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Employee
{
    private String name;
    private int age;
    private String address;
}
