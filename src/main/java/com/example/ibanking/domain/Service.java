package com.example.ibanking.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceName;
    private String serviceDiscription;

    public Service() {
    }

    public Service(String serviceName, String serviceDiscription) {
        this.serviceName = serviceName;
        this.serviceDiscription = serviceDiscription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDiscription() {
        return serviceDiscription;
    }

    public void setServiceDiscription(String serviceDiscription) {
        this.serviceDiscription = serviceDiscription;
    }
}
