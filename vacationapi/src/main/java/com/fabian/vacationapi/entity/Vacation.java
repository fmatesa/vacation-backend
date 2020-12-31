package com.fabian.vacationapi.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="vacation")
public class Vacation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="start_date")
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate startDate;
	
	@Column(name="end_date")
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate endDate;
	
	@Column(name="note")
	private String note;
	
	@Column(name="is_approved")
	private boolean isApproved;
	
	
	public Vacation() {

	}
	public Vacation(LocalDate startDate, LocalDate endDate, String note, boolean isApproved) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.note = note;
		this.isApproved = isApproved;
	}
	public boolean isApproved() {
		return isApproved;
	}
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "Vacation [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", note=" + note
				+ ", userId="  + "]";
	}
}
