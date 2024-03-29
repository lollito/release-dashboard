package com.lollito.releasedashboard.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RepositoryResponse {
	private String name;
	private String release;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date releaseDate;
	private Long releaseCommits;

	private String compare;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date compareDate;
	private Long compareCommits;

	private Long diffCommits;
	private Long diffDays;
	
	private String status;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRelease() {
		return release;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Long getReleaseCommits() {
		return releaseCommits;
	}

	public void setReleaseCommits(Long releaseCommits) {
		this.releaseCommits = releaseCommits;
	}

	public String getCompare() {
		return compare;
	}

	public void setCompare(String compare) {
		this.compare = compare;
	}

	public Date getCompareDate() {
		return compareDate;
	}

	public void setCompareDate(Date compareDate) {
		this.compareDate = compareDate;
	}

	public Long getCompareCommits() {
		return compareCommits;
	}

	public void setCompareCommits(Long compareCommits) {
		this.compareCommits = compareCommits;
	}

	public Long getDiffCommits() {
		return diffCommits;
	}

	public void setDiffCommits(Long diffCommits) {
		this.diffCommits = diffCommits;
	}

	public Long getDiffDays() {
		return diffDays;
	}

	public void setDiffDays(Long diffDays) {
		this.diffDays = diffDays;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
