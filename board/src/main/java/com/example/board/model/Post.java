package com.example.board.model;

import java.util.Date;
import java.util.StringJoiner;

public class Post {
    long postId;
    long memberId;
    String title;
    String content;
    int count;
    Date createDateTime;
    String name;

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Post.class.getSimpleName() + "[", "]")
                .add("postId=" + postId)
                .add("memberId=" + memberId)
                .add("title='" + title + "'")
                .add("content='" + content + "'")
                .add("count=" + count)
                .add("createDateTime=" + createDateTime)
                .add("name='" + name + "'")
                .toString();
    }
}