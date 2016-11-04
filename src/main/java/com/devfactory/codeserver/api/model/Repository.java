package com.devfactory.codeserver.api.model;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class to represent Git repository.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@Data
@EqualsAndHashCode(of = {"shortUrl", "branch"})
@JsonIgnoreProperties({"active","inProgress","language"})
public class Repository {

    private Integer id;

    private String name;

    private String scmUrl;

    private String shortUrl;

    private String branch;

    private String headRevision;

    private Integer parentId;

    private Boolean create;

    private Map<String, Double> languages;

    private Date startDate;

    private Date endDate;

    private RepositoryStatus status = RepositoryStatus.notInitialized() ;

   
    public boolean isActive() {
        return startDate.before(new Date()) && endDate == null;
    }

    public boolean canBeUpdated() {
        return !canBeCloned() && !isInProgress() && isActive();
    }

    public boolean isInProgress() {
        return getStatus().getCheckoutStatus() == RepositoryCheckoutStatus.IMPORTING;
    }

    public boolean canBeCloned() {
        return getStatus().getCheckoutStatus() == RepositoryCheckoutStatus.NOT_INITIALIZED;
    }

    public void normalize(Date validDate) throws URISyntaxException {
        this.setStartDate(validDate != null ? validDate : new Date());

        URIBuilder uriBuilder = new URIBuilder(getScmUrl());
        String branch = uriBuilder.getQueryParams().stream()
                .filter((t) -> t != null && t.getName().compareTo("branch") == 0)
                .findFirst()
                .orElse(new BasicNameValuePair("branch", null))
                .getValue();
        this.setBranch(branch);

        uriBuilder.setFragment(null);
        uriBuilder.setCustomQuery(null);
        setShortUrl(uriBuilder.toString());
    }

    public void makeReady() {
        LocalDateTime now = LocalDateTime.now();
        status.setLastVerified(now);
        status.setCheckoutStatus(RepositoryCheckoutStatus.READY);
        status.setError(null);
    }

    public void importing(LocalDateTime now, int commits) {
        status.setLastVerified(now);
        status.setLastUpdated(now);
        status.setLastUpdateCommits(commits);
        status.setCheckoutStatus(RepositoryCheckoutStatus.IMPORTED);
        status.setError(null);
    }

    public void importing() {
        this.status = new RepositoryStatus(RepositoryCheckoutStatus.IMPORTING);
        status.setLastUpdated(LocalDateTime.now());
        status.setError(null);
    }

    public void makeImported() {
        LocalDateTime now = LocalDateTime.now();
        status.setLastVerified(now);
        status.setLastUpdated(now);
        status.setCheckoutStatus(RepositoryCheckoutStatus.IMPORTED);
        status.setError(null);
    }

    public void importError(String msg) {
        status.setCheckoutStatus(RepositoryCheckoutStatus.IMPORTING_ERROR);
        status.setError(msg);
    }

    public void initializeError(String msg) {
        status.setCheckoutStatus(RepositoryCheckoutStatus.NOT_INITIALIZED);
        status.setError(msg);
    }

    //TODO Detection of languages need to be clarified.
    //XXX: Return most used lang for now.
    public String getLanguage() {

        if (this.languages == null) {
            return "Java";
        }

        String lang = null;
        double max = -1d;
        for (String language : this.languages.keySet()) {
            Double val = this.languages.get(language);

            if (val != null && val > max) {
                max = val;
                lang = language;
            }
        }
        return lang;
    }
}
