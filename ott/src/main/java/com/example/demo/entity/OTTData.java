package com.example.demo.entity;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OTTData {
	    private long id;
	    private String username;
	    private String password;

}
