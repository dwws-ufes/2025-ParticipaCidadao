package br.ufes.participacidadao.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "votes")
public class VoteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "voted_by")
    private UserModel votedBy;

    @ManyToOne
    @JoinColumn(name = "issue_related")
    private IssueModel issueRelated;

    // ===================================================================================================
    // Getters and Setters
    // ===================================================================================================

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public UserModel getVotedBy() { return votedBy; }

    public void setVotedBy(UserModel votedBy) { this.votedBy = votedBy; }

    public IssueModel getIssueRelated() { return issueRelated; }

    public void setIssueRelated(IssueModel issueRelated) { this.issueRelated = issueRelated; }
}
