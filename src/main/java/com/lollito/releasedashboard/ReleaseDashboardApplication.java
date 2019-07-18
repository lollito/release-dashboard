package com.lollito.releasedashboard;

import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReleaseDashboardApplication {

	public static void main(String[] args) throws IOException, GitAPIException {
		SpringApplication.run(ReleaseDashboardApplication.class, args);
	}

}
