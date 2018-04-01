package com.shadov.test.database.entity;

import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ToString
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String nick;

	private String name;

	@Column(nullable = false)
	private String lastName;
}
