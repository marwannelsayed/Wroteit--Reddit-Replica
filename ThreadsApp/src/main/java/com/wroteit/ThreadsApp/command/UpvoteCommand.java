package com.wroteit.ThreadsApp.command;

import com.wroteit.ThreadsApp.model.Comment;
import com.wroteit.ThreadsApp.model.Vote;
import com.wroteit.ThreadsApp.model.Thread;
import com.wroteit.ThreadsApp.repository.CommentRepository;
import com.wroteit.ThreadsApp.repository.ThreadRepository;
import com.wroteit.ThreadsApp.repository.VoteRepository;

public class UpvoteCommand implements VoteCommand {
    private final Long userId;
    private final String contentId;
    private final VoteRepository voteRepository;
    private final ThreadRepository threadRepository;
    private final CommentRepository commentRepository;

    public UpvoteCommand(Long userId, String contentId,
                         VoteRepository voteRepository,
                         ThreadRepository threadRepository,
                         CommentRepository commentRepository) {
        this.userId = userId;
        this.contentId = contentId;
        this.voteRepository = voteRepository;
        this.threadRepository = threadRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public String execute() {
        Vote existingVote = voteRepository.findByUserIdAndTargetId(userId, contentId).orElse(null);
        if (existingVote != null) {
            if (existingVote.getTargetType() == Vote.TargetType.THREAD) {
                Thread thread = threadRepository.findById(contentId).orElse(null);
                if (thread == null || thread.isDeleted()) return "Thread is deleted";
                if (existingVote.getVoteType() == Vote.VoteType.UPVOTE) return "Already upvoted";

                existingVote.setVoteType(Vote.VoteType.UPVOTE);
                voteRepository.save(existingVote);
                thread.addUpvote();
                thread.removeDownvote();
                threadRepository.save(thread);
                return "Thread upvoted and downvote removed";
            } else {
                Comment comment = commentRepository.findById(contentId).orElse(null);
                if (comment == null || comment.isDeleted()) return "Comment is deleted";
                if (existingVote.getVoteType() == Vote.VoteType.UPVOTE) return "Already upvoted";

                existingVote.setVoteType(Vote.VoteType.UPVOTE);
                voteRepository.save(existingVote);
                comment.addUpvote();
                comment.removeDownvote();
                commentRepository.save(comment);
                return "Comment upvoted and downvote removed";
            }
        } else {
            if (threadRepository.existsById(contentId)) {
                Thread thread = threadRepository.findById(contentId).get();
                if (thread.isDeleted()) return "Thread is deleted";

                Vote vote = new Vote(userId, contentId, Vote.TargetType.THREAD, Vote.VoteType.UPVOTE);
                voteRepository.save(vote);
                thread.addUpvote();
                threadRepository.save(thread);
                return "Thread upvoted successfully";
            } else if (commentRepository.existsById(contentId)) {
                Comment comment = commentRepository.findById(contentId).get();
                if (comment.isDeleted()) return "Comment is deleted";

                Vote vote = new Vote(userId, contentId, Vote.TargetType.COMMENT, Vote.VoteType.UPVOTE);
                voteRepository.save(vote);
                comment.addUpvote();
                commentRepository.save(comment);
                return "Comment upvoted successfully";
            } else {
                return "Content not found";
            }
        }
    }
}
