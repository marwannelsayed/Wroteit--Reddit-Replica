package com.wroteit.ThreadsApp.command;

import com.wroteit.ThreadsApp.model.Comment;
import com.wroteit.ThreadsApp.model.Vote;
import com.wroteit.ThreadsApp.model.Thread;
import com.wroteit.ThreadsApp.repository.CommentRepository;
import com.wroteit.ThreadsApp.repository.ThreadRepository;
import com.wroteit.ThreadsApp.repository.VoteRepository;

public class DownvoteCommand implements VoteCommand {
    private final Long userId;
    private final String contentId;
    private final VoteRepository voteRepository;
    private final ThreadRepository threadRepository;
    private final CommentRepository commentRepository;

    public DownvoteCommand(Long userId, String contentId,
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
                if (existingVote.getVoteType() == Vote.VoteType.DOWNVOTE) return "Already downvoted";

                existingVote.setVoteType(Vote.VoteType.DOWNVOTE);
                voteRepository.save(existingVote);
                thread.removeUpvote();
                thread.addDownvote();
                threadRepository.save(thread);
                return "Thread downvoted and upvote removed";
            } else {
                Comment comment = commentRepository.findById(contentId).orElse(null);
                if (comment == null || comment.isDeleted()) return "Comment is deleted";
                if (existingVote.getVoteType() == Vote.VoteType.DOWNVOTE) return "Already downvoted";

                existingVote.setVoteType(Vote.VoteType.DOWNVOTE);
                voteRepository.save(existingVote);
                comment.removeUpvote();
                comment.addDownvote();
                commentRepository.save(comment);
                return "Comment downvoted and upvote removed";
            }
        } else {
            if (threadRepository.existsById(contentId)) {
                Thread thread = threadRepository.findById(contentId).get();
                if (thread.isDeleted()) return "Thread is deleted";

                Vote vote = new Vote(userId, contentId, Vote.TargetType.THREAD, Vote.VoteType.DOWNVOTE);
                voteRepository.save(vote);
                thread.addDownvote();
                threadRepository.save(thread);
                return "Thread downvoted successfully";
            } else if (commentRepository.existsById(contentId)) {
                Comment comment = commentRepository.findById(contentId).get();
                if (comment.isDeleted()) return "Comment is deleted";

                Vote vote = new Vote(userId, contentId, Vote.TargetType.COMMENT, Vote.VoteType.DOWNVOTE);
                voteRepository.save(vote);
                comment.addDownvote();
                commentRepository.save(comment);
                return "Comment downvoted successfully";
            } else {
                return "Content not found";
            }
        }
    }
}

