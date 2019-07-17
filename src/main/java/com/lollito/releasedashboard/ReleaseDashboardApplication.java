package com.lollito.releasedashboard;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReleaseDashboardApplication {

	public static void main(String[] args) throws IOException, GitAPIException {
		
		
		    
		Git git = Git.open( new File("/Users/lorenzocunto/Documents/workspace-fm/fm"));
//		Repository repository = git.getRepository();
		List<Ref> branches = git.branchList().setListMode(ListMode.REMOTE).call().parallelStream().filter(branch -> { return branch.getName().equals("refs/remotes/origin/develop") || branch.getName().equals("refs/remotes/origin/master");}).collect(Collectors.toList());
		try(RevWalk walk = new RevWalk(git.getRepository())) {
		    for(Ref branch : branches) {
//		    	RevCommit youngestCommit = null;
		        RevCommit commit = walk.parseCommit(branch.getObjectId());
		        Iterable<RevCommit> logs = git.log()
                        .add(branch.getObjectId())
                        .call();
                int count = 0;
                for (RevCommit rev : logs) {
//                    System.out.println("Commit: " + rev /* + ", name: " + rev.getName() + ", id: " + rev.getId().getName() */);
                    count++;
                }
                System.out.println("Had " + count + " commits overall on "+ branch.getName());
		        System.out.println(branch.getName() + commit.getAuthorIdent().getWhen());
//		        if(youngestCommit == null) {
//		        	 youngestCommit = commit;
//		        } else  if(commit.getAuthorIdent().getWhen().compareTo(
//		           youngestCommit.getAuthorIdent().getWhen()) > 0) {
//		           youngestCommit = commit;
//		        }
		    }
		}
		
		
		
		SpringApplication.run(ReleaseDashboardApplication.class, args);
	}

}
