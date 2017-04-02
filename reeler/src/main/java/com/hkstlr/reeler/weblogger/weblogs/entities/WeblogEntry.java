/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.entities;

import com.hkstlr.reeler.app.control.WebloggerException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.hkstlr.reeler.app.entities.AbstractEntity;
import com.hkstlr.reeler.weblogger.users.entities.User;
import com.hkstlr.reeler.weblogger.weblogs.control.WeblogEntryTagComparator;
import com.hkstlr.reeler.weblogger.weblogs.control.WeblogEntryTagFixer;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.TreeSet;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.persistence.Cacheable;
/**
 *
 * @author henry.kastler
 */
@Entity
@Cacheable
@Table(name = "weblogentry")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WeblogEntry.findAll", query = "SELECT w FROM WeblogEntry w")
    , @NamedQuery(name = "WeblogEntry.findById", query = "SELECT w FROM WeblogEntry w WHERE w.id = :id")
    , @NamedQuery(name = "WeblogEntry.findByAnchor", query = "SELECT w FROM WeblogEntry w WHERE w.anchor = :anchor")
    , @NamedQuery(name = "WeblogEntry.findByCreator", query = "SELECT w FROM WeblogEntry w WHERE w.creatorUserName = :creatorUserName")
    , @NamedQuery(name = "WeblogEntry.findByTitle", query = "SELECT w FROM WeblogEntry w WHERE w.title = :title")
    , @NamedQuery(name = "WeblogEntry.findByText", query = "SELECT w FROM WeblogEntry w WHERE w.text = :text")
    , @NamedQuery(name = "WeblogEntry.findByPubTime", query = "SELECT w FROM WeblogEntry w WHERE w.pubTime = :pubTime")
    , @NamedQuery(name = "WeblogEntry.findByUpdateTime", query = "SELECT w FROM WeblogEntry w WHERE w.updateTime = :updateTime")
    , @NamedQuery(name = "WeblogEntry.findByPublishEntry", query = "SELECT w FROM WeblogEntry w WHERE w.publishEntry = :publishEntry")
    , @NamedQuery(name = "WeblogEntry.findByLink", query = "SELECT w FROM WeblogEntry w WHERE w.link = :link")
    , @NamedQuery(name = "WeblogEntry.findByPlugins", query = "SELECT w FROM WeblogEntry w WHERE w.plugins = :plugins")
    , @NamedQuery(name = "WeblogEntry.findByAllowComments", query = "SELECT w FROM WeblogEntry w WHERE w.allowComments = :allowComments")
    , @NamedQuery(name = "WeblogEntry.findByCommentDays", query = "SELECT w FROM WeblogEntry w WHERE w.commentDays = :commentDays")
    , @NamedQuery(name = "WeblogEntry.findByRightToLeft", query = "SELECT w FROM WeblogEntry w WHERE w.rightToLeft = :rightToLeft")
    , @NamedQuery(name = "WeblogEntry.findByPinnedToMain", query = "SELECT w FROM WeblogEntry w WHERE w.pinnedToMain = :pinnedToMain")
    , @NamedQuery(name = "WeblogEntry.findByLocale", query = "SELECT w FROM WeblogEntry w WHERE w.locale = :locale")
    , @NamedQuery(name = "WeblogEntry.findByStatus", query = "SELECT w FROM WeblogEntry w WHERE w.status = :status")
    , @NamedQuery(name = "WeblogEntry.findBySummary", query = "SELECT w FROM WeblogEntry w WHERE w.summary = :summary")
    , @NamedQuery(name = "WeblogEntry.findByContentType", query = "SELECT w FROM WeblogEntry w WHERE w.contentType = :contentType")
    , @NamedQuery(name = "WeblogEntry.findByContentSrc", query = "SELECT w FROM WeblogEntry w WHERE w.contentSrc = :contentSrc")
    , @NamedQuery(name = "WeblogEntry.findBySearchDescription", query = "SELECT w FROM WeblogEntry w WHERE w.searchDescription = :searchDescription")
    , @NamedQuery(name = "WeblogEntry.findByWebsiteAndAnchor", query = "SELECT w FROM WeblogEntry w WHERE w.website = :website AND w.anchor = :anchor")
    , @NamedQuery(name = "WeblogEntry.getByCategory", query = "SELECT w FROM WeblogEntry w WHERE w.category = ?1")
    , @NamedQuery(name = "WeblogEntry.getByPinnedToMain&statusOrderByPubTimeDesc", query = "SELECT w FROM WeblogEntry w WHERE w.pinnedToMain = ?1 AND w.status = ?2 ORDER BY w.pubTime DESC")
    , @NamedQuery(name = "WeblogEntry.getByWebsite&AnchorOrderByPubTimeDesc", query = "SELECT w FROM WeblogEntry w WHERE w.website = ?1 AND w.anchor = ?2 ORDER BY w.pubTime DESC")
    , @NamedQuery(name = "WeblogEntry.getByWebsite&Anchor", query = "SELECT w FROM WeblogEntry w WHERE w.website = ?1 AND w.anchor = ?2")
    , @NamedQuery(name = "WeblogEntry.getByWebsite", query = "SELECT w FROM WeblogEntry w WHERE w.website = ?1 ORDER BY w.pubTime DESC")
    , @NamedQuery(name = "WeblogEntry.getCountDistinctByStatus", query = "SELECT COUNT(e) FROM WeblogEntry e WHERE e.status = ?1")
    , @NamedQuery(name = "WeblogEntry.getCountDistinctByStatus&Website", query = "SELECT COUNT(e) FROM WeblogEntry e WHERE e.status = ?1 AND e.website = ?2")
    , @NamedQuery(name = "WeblogEntry.updateAllowComments&CommentDaysByWebsite", query = "UPDATE WeblogEntry e SET e.allowComments = ?1, e.commentDays = ?2 WHERE e.website = ?3")
    , @NamedQuery(name = "WeblogEntry.getByWeblogEntriesByWeblogCategoryName", query = "SELECT w FROM WeblogEntry w, WeblogCategory c WHERE w.category = c AND c.name = :weblogCategoryName")
    , @NamedQuery(name = "WeblogEntry.getByWeblogEntriesByCategoryNameAndWeblog", query = "SELECT w FROM WeblogEntry w JOIN w.category c JOIN c.weblog b WHERE b = :weblog AND c.name = :weblogCategoryName ")
    , @NamedQuery(name = "WeblogEntry.getWeblogEntriesByDateAndWeblog", query = "SELECT w FROM WeblogEntry w WHERE w.website = :weblog AND w.pubTime = :pubTime AND w.publishEntry = true")    
    , @NamedQuery(name = "WeblogEntry.getLatestEntryForWeblog", query = "SELECT we FROM WeblogEntry we WHERE we.website = :weblog ORDER BY we.pubTime DESC")})
public class WeblogEntry extends AbstractEntity implements Serializable {
    
    @Transient
    private Logger log = Logger.getLogger(WeblogEntry.class.getName());

    public enum PubStatus {
        DRAFT, PUBLISHED, PENDING, SCHEDULED
    }

    

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @NotNull(message = "{WeblogEntry.anchor.NotNull}")
    @Size(min = 1, max = 255, message="{WeblogEntry.anchor.NotNull}")
    @Column(name = "anchor", nullable = false, length = 255)
    private String anchor;

    @Basic(optional = false)
    @NotNull(message = "{WeblogEntry.creatorUserName.NotNull}")
    @Size(min = 1, max = 255)
    @Column(name = "creator", nullable = false, length = 255)
    private String creatorUserName;

    @Basic(optional = false)
    @NotNull(message = "{WeblogEntry.title.NotNull}")
    @Size(min = 1, max = 255, message="{WeblogEntry.title.NotNull}")
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Basic(optional = false)
    @NotNull(message = "{WeblogEntry.anchor.NotNull}")
    @Size(min = 1, max = 2147483647)
    @Column(name = "text", nullable = false, length = 2147483647)
    private String text = "";

    @Column(name = "pubtime")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar pubTime;

    @Basic(optional = false)
    @NotNull(message = "{WeblogEntry.updateTime.NotNull}")
    @Column(name = "updatetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updateTime;

    @Basic(optional = false)
    @NotNull
    @Column(name = "publishentry", nullable = false)
    private boolean publishEntry = true;

    @Size(max = 255)
    @Column(name = "link", length = 255)
    private String link;

    @Size(max = 255)
    @Column(name = "plugins", length = 255)
    private String plugins;

    @Basic(optional = false)
    @NotNull
    @Column(name = "allowcomments", nullable = false)
    private boolean allowComments = false;

    @Basic(optional = false)
    @NotNull
    @Column(name = "commentdays", nullable = false)
    private int commentDays = 0;

    @Basic(optional = false)
    @NotNull
    @Column(name = "righttoleft", nullable = false)
    private boolean rightToLeft = false;

    @Basic(optional = false)
    @NotNull
    @Column(name = "pinnedtomain", nullable = false)
    private boolean pinnedToMain = false;

    @Size(max = 20)
    @Column(name = "locale", length = 20)
    private String locale;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "status", nullable = false, length = 20)
    private String status = "Not Saved";

    @Size(max = 2147483647)
    @Column(name = "summary", length = 2147483647)
    private String summary;

    @Size(max = 48)
    @Column(name = "content_type", length = 48)
    private String contentType;

    @Size(max = 255)
    @Column(name = "content_src", length = 255)
    private String contentSrc;

    @Size(max = 255)
    @Column(name = "search_description", length = 255)
    private String searchDescription;

    @JoinColumn(name = "websiteid", referencedColumnName = "id", nullable = false, updatable = true, insertable = true)
    @ManyToOne(optional = false)
    private Weblog website;

    @NotNull(message = "{WeblogEntry.category.NotNull}")
    @ManyToOne
    @JoinColumn(name = "categoryid", referencedColumnName = "id", nullable = false, updatable = true)
    private WeblogCategory category = new WeblogCategory();

    @OneToMany(mappedBy = "weblogEntry", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<WeblogEntryTag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "weblogEntry")
    private List<WeblogEntryComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "entry")
    private List<WeblogEntryAttribute> entryAttributes = new ArrayList<>();
    
     // set to true when switching between pending/draft/scheduled and published
    // either the aggregate table needs the entry's tags added (for published)
    // or subtracted (anything else)
    private Boolean   refreshAggregates = Boolean.FALSE;

    @Transient
    private Set<WeblogEntryTag> removedTags = new HashSet<WeblogEntryTag>();;

    @Transient
    private Set<WeblogEntryTag> addedTags = new HashSet<WeblogEntryTag>();

    public WeblogEntry() {
       
    }


    public WeblogEntry(String anchor, String creator, String title, String text, Calendar updatetime, boolean publishentry, boolean allowcomments, int commentdays, boolean righttoleft, boolean pinnedtomain, String status) {
        
        this.anchor = anchor;
        this.creatorUserName = creator;
        this.title = title;
        this.text = text;
        this.updateTime = updatetime;
        this.publishEntry = publishentry;
        this.allowComments = allowcomments;
        this.commentDays = commentdays;
        this.rightToLeft = righttoleft;
        this.pinnedToMain = pinnedtomain;
        this.status = status;
    }

    public WeblogEntry(WeblogEntry entry) {
        // TODO Auto-generated constructor stub
    }
    
    public WeblogEntry(Weblog weblog) {
        this.website = weblog;
        this.locale = weblog.getLocale();
    }
    
    public WeblogEntry(Weblog weblog, User creator) {
        this.website = weblog;
        this.creatorUserName = creator.getUserName();
        this.locale = weblog.getLocale();
    }
    
    @PostConstruct
    private void init(){
        
    }

    public String getId() {
        return id;
    }

    public Weblog getWebsite() {
        return website;
    }

    public void setWebsite(Weblog website) {
        this.website = website;
    }

    public String getAnchor() {
        return anchor;
    }

    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    public String getCreatorUserName() {
        return creatorUserName;
    }

    public void setCreatorUserName(String creatorUserName) {
        this.creatorUserName = creatorUserName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Calendar getPubTime() {
        return pubTime;
    }

    public void setPubTime(Calendar pubTime) {
        this.pubTime = pubTime;
    }

    public Calendar getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Calendar updateTime) {
        this.updateTime = updateTime;
    }
    
    public void setUpdateTime(Date date){
        Calendar calendar = Calendar.getInstance();
	calendar.setTime(date);
        this.updateTime = calendar;
    }

    public boolean isPublishEntry() {
        return publishEntry;
    }

    public void setPublishEntry(boolean publishEntry) {
        this.publishEntry = publishEntry;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPlugins() {
        return plugins;
    }

    public void setPlugins(String plugins) {
        this.plugins = plugins;
    }

    public boolean isAllowComments() {
        return allowComments;
    }

    public void setAllowComments(boolean allowComments) {
        this.allowComments = allowComments;
    }

    public int getCommentDays() {
        return commentDays;
    }

    public void setCommentDays(int commentDays) {
        this.commentDays = commentDays;
    }

    public boolean isRightToLeft() {
        return rightToLeft;
    }

    public void setRightToLeft(boolean rightToLeft) {
        this.rightToLeft = rightToLeft;
    }

    public boolean isPinnedToMain() {
        return pinnedToMain;
    }

    public void setPinnedToMain(boolean pinnedToMain) {
        this.pinnedToMain = pinnedToMain;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentSrc() {
        return contentSrc;
    }

    public void setContentSrc(String contentSrc) {
        this.contentSrc = contentSrc;
    }

    public String getSearchDescription() {
        return searchDescription;
    }

    public void setSearchDescription(String searchDescription) {
        this.searchDescription = searchDescription;
    }

    public WeblogCategory getCategory() {
        return category;
    }

    public void setCategory(WeblogCategory category) {
        this.category = category;
    }

    public Collection<WeblogEntryAttribute> getEntryAttributes() {
        return entryAttributes;
    }

    public void setEntryAttributes(List<WeblogEntryAttribute> entryAttributes) {
        this.entryAttributes = entryAttributes;
    }

    public Set<WeblogEntryTag> getRemovedTags() {
        return removedTags;
    }

    public void setRemovedTags(Set<WeblogEntryTag> removedTags) {
        this.removedTags = removedTags;
    }

    public Collection<WeblogEntryTag> getTags() {
        return tags;
    }
    
    public void addTag(WeblogEntryTag weTag){
        if(tags.contains(weTag))
            return;
        
        tags.add(weTag);
        weTag.setWeblogEntry(this);
    }
    
    public void removeTag(WeblogEntryTag weTag){
        if(!tags.contains(weTag)){
            log.info("weTag:" + weTag.getName() + "not found, nothing to delete");
            return;
        }
        log.info("removing tag:" + weTag.getName());
        tags.remove(weTag);
        weTag.setWeblog(null);
        weTag.setWeblogEntry(null);
    }
    
    public String getTagsAsString() {
        StringBuilder sb = new StringBuilder();
        // Sort by name
        Set<WeblogEntryTag> tmp = new TreeSet<WeblogEntryTag>(new WeblogEntryTagComparator());
        tmp.addAll(getTags());
        for (WeblogEntryTag entryTag : tmp) {
            sb.append(entryTag.getName()).append(" ");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    public void setTagsAsString(String tagNames) throws WebloggerException {
        if (tagNames.isEmpty()) {
            //removedTags.addAll(tags)
            tags.clear();
            return;
        }
        log.info("tagNames:" + tagNames.toString());
        List<String> incomingTagNames = WeblogEntryTagFixer.splitStringAsTags(tagNames);
        Set<String> incomingTags = new HashSet<String>(incomingTagNames.size());
        Locale localeObject = getWebsite() != null ? getWebsite().getLocaleInstance() : Locale.getDefault();

        for (String name : incomingTagNames) {
            incomingTags.add(WeblogEntryTagFixer.normalizeTag(name, localeObject));
            log.info("incomingTag added:" + name);
        }
        log.info("incomingTags:" + incomingTags.toString());
        //loop through originalTags
        List<WeblogEntryTag> existingTags = new ArrayList<>(tags);
        // remove old ones no longer passed.
        existingTags.forEach((existingTag) -> {
            //WeblogEntryTag tag = (WeblogEntryTag) it.next();
            log.info("existingTag:" + existingTag.getName());
            if (!incomingTags.contains(existingTag.getName())) {
                // tag no longer listed in UI, needs removal from DB
                log.info("removing tag:" + existingTag.getName());
                removedTags.add(existingTag);
                this.tags.remove(existingTag);
            } else {
                // already in persisted set, therefore isn't new
                incomingTags.remove(existingTag.getName());
                existingTag.setWeblog(website);
                existingTag.setWeblogEntry(null);
            }
        });

        for (String newTagName : incomingTags) {
            log.info("adding tag:" + newTagName);
            addTag(newTagName);
        }
    }
    
     /**
     * Roller lowercases all tags based on locale because there's not a 1:1 mapping
     * between uppercase/lowercase characters across all languages.  
     * @param name
     * @throws WebloggerException
     */
    public void addTag(String name) throws WebloggerException {
        Locale localeObject = getWebsite() != null ? getWebsite().getLocaleInstance() : Locale.getDefault();
        name = WeblogEntryTagFixer.normalizeTag(name, localeObject);
        if (name.length() == 0) {
            return;
        }
        
        for (WeblogEntryTag tag : getTags()) {
            if (tag.getName().equals(name)) {
                return;
            }
        }

        WeblogEntryTag tag = new WeblogEntryTag();
        tag.setName(name);
        tag.setCreator(getCreatorUserName());
        tag.setWeblog(getWebsite());
        tag.setWeblogEntry(this);
        tag.setCreateDate(new Date());
        tags.add(tag);
        log.info("tag " + tag.getId().concat("|name:").concat(tag.getName()).concat("|weblogEntryId:").concat(tag.getWeblogEntry().getId()));
        addedTags.add(tag);
    }
    

    public void setTags(List<WeblogEntryTag> tags) {
        this.tags = tags;
    }

    @XmlTransient
    public Collection<WeblogEntryComment> getComments() {
        return comments;
    }

    public void setComments(List<WeblogEntryComment> comments) {
        this.comments = comments;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WeblogEntry)) {
            return false;
        }
        WeblogEntry other = (WeblogEntry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.WeblogEntry[ id=" + id + " ]";
    }

    /**
     * Set bean properties based on other bean.
     */
    public void setData(WeblogEntry other) {

        this.setCategory(other.getCategory());
        this.setWebsite(other.getWebsite());
        this.setCreatorUserName(other.getCreatorUserName());
        this.setTitle(other.getTitle());
        this.setLink(other.getLink());
        this.setText(other.getText());
        this.setSummary(other.getSummary());
        this.setSearchDescription(other.getSearchDescription());
        this.setAnchor(other.getAnchor());
        this.setPubTime(other.getPubTime());
        this.setUpdateTime(other.getUpdateTime());
        this.setStatus(other.getStatus());
        this.setPlugins(other.getPlugins());
        this.setAllowComments(other.isAllowComments());
        this.setCommentDays(other.getCommentDays());
        this.setRightToLeft(other.isRightToLeft());
        this.setPinnedToMain(other.isPinnedToMain());
        this.setLocale(other.getLocale());
    }

    /**
     * Convenience method to transform mPlugins to a List
     *
     * @return
     */
    public List<String> getPluginsList() {
        if (getPlugins() != null) {
            return Arrays.asList(getPlugins().split(","));
        }
        return new ArrayList<String>();
    }
    
    public Boolean getRefreshAggregates() {
        return refreshAggregates;
    }

    public void setRefreshAggregates(Boolean refreshAggregates) {
        this.refreshAggregates = refreshAggregates;
    }
    
    
    public Set<WeblogEntryTag> getAddedTags() {
        return addedTags;
    }
    
        
}