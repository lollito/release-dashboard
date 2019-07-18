package com.lollito.releasedashboard.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lollito.releasedashboard.AppProperties;
import com.lollito.releasedashboard.AppProperties.RepositoryInfo;
import com.lollito.releasedashboard.dto.RepositoryResponse;

@RestController
@RequestMapping("/git")
public class GitController {

	
	@Autowired private AppProperties appProperties;
	
	@RequestMapping(value = "/repository-info", method = RequestMethod.GET)
	public List<RepositoryResponse> get() {
		List<RepositoryResponse> response = new ArrayList<>();
		 
		appProperties.getRepositoriesInfo().forEach(repositoryInfo-> {
			try {
				response.add(getRepoInfo(repositoryInfo));
			} catch (Exception e) {
				
			}
		});
		
		response.sort(Comparator.comparing(RepositoryResponse::getDiffCommits));
		return response;
	}
	
	private RepositoryResponse getRepoInfo(RepositoryInfo repositoryInfo) throws IOException, NoHeadException, GitAPIException {
		RepositoryResponse repositoryResponse = new RepositoryResponse();
		repositoryResponse.setName(repositoryInfo.getName());
		Git git = Git.open( new File(repositoryInfo.getPath()));
		
		List<Ref> branches = git.branchList().setListMode(ListMode.REMOTE).call().parallelStream().filter(branch -> { return branch.getName().endsWith("/" + repositoryInfo.getCompare()) || branch.getName().endsWith("/" + repositoryInfo.getRelease());}).collect(Collectors.toList());
		try(RevWalk walk = new RevWalk(git.getRepository())) {
		    for(Ref branch : branches) {
		    	if(branch.getName().endsWith("/" + repositoryInfo.getCompare())) {
		    		RevCommit commit = walk.parseCommit(branch.getObjectId());
			        Iterable<RevCommit> logs = git.log()
	                        .add(branch.getObjectId())
	                        .call();
			        repositoryResponse.setCompare(repositoryInfo.getCompare());
			        repositoryResponse.setCompareCommits(StreamSupport.stream(logs.spliterator(), false).count());
			        repositoryResponse.setCompareDate(commit.getAuthorIdent().getWhen());
		    	} else if(branch.getName().endsWith("/" + repositoryInfo.getRelease())) {
		    		RevCommit commit = walk.parseCommit(branch.getObjectId());
			        Iterable<RevCommit> logs = git.log()
	                        .add(branch.getObjectId())
	                        .call();
			        repositoryResponse.setRelease(repositoryInfo.getRelease());
			        repositoryResponse.setReleaseCommits(StreamSupport.stream(logs.spliterator(), false).count());
			        repositoryResponse.setReleaseDate(commit.getAuthorIdent().getWhen());
		    	}
		        
		    }
		}
		long diffInMillies = Math.abs(repositoryResponse.getCompareDate().getTime() - repositoryResponse.getReleaseDate().getTime());
		repositoryResponse.setDiffDays(TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS));
		repositoryResponse.setDiffCommits(repositoryResponse.getCompareCommits() - repositoryResponse.getReleaseCommits());
		return repositoryResponse;
	}
}
