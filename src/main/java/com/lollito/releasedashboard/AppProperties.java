package com.lollito.releasedashboard;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("dashboard") // prefix app, find app.* values
public class AppProperties {

    private List<RepositoryInfo> repositoriesInfo = new ArrayList<>();

    public static class RepositoryInfo {
        private String name;
        private String path;
        private String release;
        private String compare;
        
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getPath() {
			return path;
		}
		
		public void setPath(String path) {
			this.path = path;
		}
		
		public String getRelease() {
			return release;
		}
		
		public void setRelease(String release) {
			this.release = release;
		}
		
		public String getCompare() {
			return compare;
		}
		
		public void setCompare(String compare) {
			this.compare = compare;
		}
        
    }

	public List<RepositoryInfo> getRepositoriesInfo() {
		return repositoriesInfo;
	}

	public void setRepositoriesInfo(List<RepositoryInfo> repositoriesInfo) {
		this.repositoriesInfo = repositoriesInfo;
	}

}