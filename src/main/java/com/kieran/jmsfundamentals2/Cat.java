package com.kieran.jmsfundamentals2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cat implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private Double weight;
}
