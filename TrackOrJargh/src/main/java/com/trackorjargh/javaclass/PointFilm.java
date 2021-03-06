package com.trackorjargh.javaclass;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonView;


@Entity
public class PointFilm {
	public interface BasicInformation {}
	
	@JsonView(BasicInformation.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JsonView(BasicInformation.class)
	@ManyToOne
	private Film film;
	
	@JsonView(BasicInformation.class)
	@OneToOne
	private User user;
	
	@JsonView(BasicInformation.class)
	private double points;

	public PointFilm() {
	}

	public PointFilm(double points) {
		this.points = points;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		if(points >= 5) {
			this.points = 5;
		}else {
			this.points = points;
		}
	}
}
