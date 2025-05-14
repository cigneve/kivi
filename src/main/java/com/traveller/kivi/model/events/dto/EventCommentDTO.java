package com.traveller.kivi.model.events.dto;

import java.time.LocalDateTime;

import com.traveller.kivi.model.events.EventComment;

import jakarta.validation.constraints.NotNull;

/**
 * EventComment DTO.
 * Also used for Event Chat posts
 */
public class EventCommentDTO {
    public static EventCommentDTO fromEventComment(EventComment eventComment) {
        var dto = new EventCommentDTO();
        dto.id = eventComment.getId();
        dto.ownerId = eventComment.getOwner().getId();
        dto.commentDate = eventComment.getCommentDate();
        dto.commentBody = eventComment.getCommentBody();
        return dto;
    }

    @NotNull
    public Integer id;

    @NotNull
    public Integer ownerId;

    public LocalDateTime commentDate;

    public String commentBody;
}
