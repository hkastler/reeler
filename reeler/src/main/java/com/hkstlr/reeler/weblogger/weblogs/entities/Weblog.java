/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.entities;

import com.hkstlr.reeler.weblogger.media.entities.MediaFileDirectory;
import com.hkstlr.reeler.app.entities.AbstractEntity;
import com.hkstlr.reeler.weblogger.weblogs.control.entitylisteners.WeblogEntityListener;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.hkstlr.reeler.weblogger.pings.entities.AutoPing;
import com.hkstlr.reeler.weblogger.plugins.entry.control.WeblogEntryPlugin;

import com.hkstlr.reeler.weblogger.weblogs.control.LocaleFixer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Logger;
import javax.persistence.Cacheable;
import javax.persistence.EntityListeners;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

/**
 *
 * @author henry.kastler
 */
@Entity
@EntityListeners(WeblogEntityListener.class)
@Cacheable
@Table(name = "weblog", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"handle"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Weblog.findAll", query = "SELECT w FROM Weblog w")
    , @NamedQuery(name = "Weblog.findById", query = "SELECT w FROM Weblog w WHERE w.id = :id")
    , @NamedQuery(name = "Weblog.findByName", query = "SELECT w FROM Weblog w WHERE w.name = :name")
    , @NamedQuery(name = "Weblog.findByHandle", query = "SELECT w FROM Weblog w WHERE w.handle = :handle")
    , @NamedQuery(name = "Weblog.findByTagline", query = "SELECT w FROM Weblog w WHERE w.tagline = :tagline")
    , @NamedQuery(name = "Weblog.findByCreator", query = "SELECT w FROM Weblog w WHERE w.creator = :creator")
    , @NamedQuery(name = "Weblog.findByEnableBloggerApi", query = "SELECT w FROM Weblog w WHERE w.enableBloggerApi = :enablebloggerapi")
    , @NamedQuery(name = "Weblog.findByEditorPage", query = "SELECT w FROM Weblog w WHERE w.editorPage = :editorpage")
    , @NamedQuery(name = "Weblog.findBybloggerCategory", query = "SELECT w FROM Weblog w WHERE w.bloggerCategory = :bloggerCategory")
    , @NamedQuery(name = "Weblog.findByAllowComments", query = "SELECT w FROM Weblog w WHERE w.allowComments = :allowcomments")
    , @NamedQuery(name = "Weblog.findByEmailComments", query = "SELECT w FROM Weblog w WHERE w.emailComments = :emailcomments")
    , @NamedQuery(name = "Weblog.findByEmailAddress", query = "SELECT w FROM Weblog w WHERE w.emailAddress = :emailaddress")
    , @NamedQuery(name = "Weblog.findByEditorTheme", query = "SELECT w FROM Weblog w WHERE w.editorTheme = :editortheme")
    , @NamedQuery(name = "Weblog.findByLocale", query = "SELECT w FROM Weblog w WHERE w.locale = :locale")
    , @NamedQuery(name = "Weblog.findByTimeZone", query = "SELECT w FROM Weblog w WHERE w.timeZone = :timezone")
    , @NamedQuery(name = "Weblog.findByDefaultplugins", query = "SELECT w FROM Weblog w WHERE w.defaultPlugins = :defaultplugins")
    , @NamedQuery(name = "Weblog.findByVisible", query = "SELECT w FROM Weblog w WHERE w.visible = :visible")
    , @NamedQuery(name = "Weblog.findByIsActive", query = "SELECT w FROM Weblog w WHERE w.isActive = :isactive")
    , @NamedQuery(name = "Weblog.findByDateCreated", query = "SELECT w FROM Weblog w WHERE w.dateCreated = :datecreated")
    , @NamedQuery(name = "Weblog.findByBlacklist", query = "SELECT w FROM Weblog w WHERE w.blacklist = :blacklist")
    , @NamedQuery(name = "Weblog.findByDefaultAllowComments", query = "SELECT w FROM Weblog w WHERE w.defaultAllowComments = :defaultallowcomments")
    , @NamedQuery(name = "Weblog.findBydefaultCommentDays", query = "SELECT w FROM Weblog w WHERE w.defaultCommentDays = :defaultcommentdays")
    , @NamedQuery(name = "Weblog.findByCommentMod", query = "SELECT w FROM Weblog w WHERE w.moderateComments = :commentmod")
    , @NamedQuery(name = "Weblog.findByEntryDisplayCount", query = "SELECT w FROM Weblog w WHERE w.entryDisplayCount = :displaycnt")
    , @NamedQuery(name = "Weblog.findByLastModified", query = "SELECT w FROM Weblog w WHERE w.lastModified = :lastmodified")
    , @NamedQuery(name = "Weblog.findByEnableMultiLang", query = "SELECT w FROM Weblog w WHERE w.enableMultiLang = :enablemultilang")
    , @NamedQuery(name = "Weblog.findByShowAllLangs", query = "SELECT w FROM Weblog w WHERE w.showAllLangs = :showalllangs")
    , @NamedQuery(name = "Weblog.findByAbout", query = "SELECT w FROM Weblog w WHERE w.about = :about")
    , @NamedQuery(name = "Weblog.findByIcon", query = "SELECT w FROM Weblog w WHERE w.iconPath = :icon")
    , @NamedQuery(name = "Weblog.findByAnalyticsCode", query = "SELECT w FROM Weblog w WHERE w.analyticsCode = :analyticscode")
    , @NamedQuery(name = "Weblog.getByHandle", query = "SELECT w FROM Weblog w WHERE w.handle = ?1")
    , @NamedQuery(name = "Weblog.getByLetterOrderByHandle", query = "SELECT w FROM Weblog w WHERE UPPER(w.handle) like ?1 ORDER BY w.handle")
    , @NamedQuery(name = "Weblog.getCountAllDistinct", query = "SELECT COUNT(w) FROM Weblog w")
    , @NamedQuery(name = "Weblog.getCountByHandleLike", query = "SELECT COUNT(w) FROM Weblog w WHERE UPPER(w.handle) like ?1")})
public class Weblog extends AbstractEntity implements Serializable {

    private transient static final Logger log = Logger.getLogger(Weblog.class.getName());
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "handle", nullable = false, length = 255)
    private String handle;

    @Size(max = 255)
    @Column(name = "tagline", length = 255)
    private String tagline;

    @NotNull
    @Size(max = 255)
    @Column(name = "creator", length = 255, nullable=false)
    private String creator;

    @Basic(optional = false)
    @NotNull
    @Column(name = "enablebloggerapi", nullable = false)
    private boolean enableBloggerApi = false;

    @Size(max = 255)
    @Column(name = "editorpage", length = 255)
    private String editorPage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bloggercatid")
    private WeblogCategory bloggerCategory;

    @Basic(optional = false)
    @NotNull
    @Column(name = "allowcomments", nullable = false)
    private boolean allowComments = true;

    @Basic(optional = false)
    @NotNull
    @Column(name = "emailcomments", nullable = false)
    private boolean emailComments = false;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "emailaddress", nullable = false, length = 255)
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
        +"[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
        +"(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
             message="{invalid.email}")
    private String emailAddress;

    @Size(max = 255)
    @Column(name = "editortheme", length = 255)
    private String editorTheme;

    @Basic
    @Size(max = 20)
    @Column(name = "locale", length = 20)
    private String locale;

    @Basic(optional = false)
    @Size(max = 50)
    @Column(name = "timezone", length = 50)
    private String timeZone;

    @Size(max = 255)
    @Column(name = "defaultplugins", length = 255)
    private String defaultPlugins;

    @Basic(optional = false)
    @NotNull
    @Column(name = "visible", nullable = false)
    private boolean visible = true;

    @Basic(optional = false)
    @NotNull
    @Column(name = "isactive", nullable = false)
    private boolean isActive = true;

    @Basic(optional = false)
    @NotNull
    @Column(name = "datecreated", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Size
    @Column(name = "blacklist")
    private String blacklist;

    @Basic(optional = false)
    @NotNull
    @Column(name = "defaultallowcomments", nullable = false)
    private boolean defaultAllowComments = true;

    @Basic(optional = false)
    @NotNull
    @Column(name = "defaultcommentdays", nullable = false)
    private int defaultCommentDays = 0;

    @Basic(optional = false)
    @NotNull
    @Column(name = "commentmod", nullable = false)
    private boolean moderateComments = false;

    @Basic(optional = false)
    @NotNull
    @Column(name = "displaycnt", nullable = false)
    private int entryDisplayCount = 15;

    @Column(name = "lastmodified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;

    @Basic(optional = false)
    @NotNull
    @Column(name = "enablemultilang", nullable = false)
    private boolean enableMultiLang = false;

    @Basic(optional = false)
    @NotNull
    @Column(name = "showalllangs", nullable = false)
    private boolean showAllLangs = true;

    @Size(max = 255)
    @Column(name = "about", length = 255)
    private String about;

    @Size(max = 255)
    @Column(name = "icon", length = 255)
    private String iconPath;

    @Size
    @Column(name = "analyticscode")
    private String analyticsCode;

    @OneToMany(mappedBy = "weblog", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<WeblogCategory> weblogCategories = new ArrayList<WeblogCategory>();

    @OneToMany(mappedBy = "weblog", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<WeblogBookmarkFolder> bookmarkFolders  = new ArrayList<WeblogBookmarkFolder>();

    

    @OneToMany(mappedBy = "website", fetch = FetchType.LAZY)
    private List<WeblogEntry> weblogEntries;

    @OneToMany(mappedBy = "weblog")
    private List<Newsfeed> newsfeeds;

    @OneToMany(mappedBy = "weblog", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<MediaFileDirectory> mediaFileDirectories;

    @OneToMany(mappedBy = "website")
    private List<AutoPing> autopings;
    
    @Transient
    private transient Map<String, WeblogEntryPlugin> initializedPlugins = null;
    
    public Weblog() {
        //need an empty constructor for entity
    }

    public Weblog(String name, String handle) {
        
        this.name = name;
        this.handle = handle;
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public boolean isEnableBloggerApi() {
        return enableBloggerApi;
    }

    public void setEnableBloggerApi(boolean enableBloggerApi) {
        this.enableBloggerApi = enableBloggerApi;
    }

    public String getEditorPage() {
        return editorPage;
    }

    public void setEditorPage(String editorPage) {
        this.editorPage = editorPage;
    }

    public WeblogCategory getBloggerCategory() {
        return bloggerCategory;
    }

    public void setBloggerCategory(WeblogCategory bloggerCategory) {
        this.bloggerCategory = bloggerCategory;
    }

    public boolean isAllowComments() {
        return allowComments;
    }

    public void setAllowComments(boolean allowComments) {
        this.allowComments = allowComments;
    }

    public boolean isEmailComments() {
        return emailComments;
    }

    public void setEmailComments(boolean emailComments) {
        this.emailComments = emailComments;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEditorTheme() {
        return editorTheme;
    }

    public void setEditorTheme(String editorTheme) {
        this.editorTheme = editorTheme;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getDefaultPlugins() {
        return defaultPlugins;
    }

    public void setDefaultPlugins(String defaultPlugins) {
        this.defaultPlugins = defaultPlugins;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(String blacklist) {
        this.blacklist = blacklist;
    }

    public boolean isDefaultAllowComments() {
        return defaultAllowComments;
    }

    public void setDefaultAllowComments(boolean defaultAllowComments) {
        this.defaultAllowComments = defaultAllowComments;
    }

    public int getDefaultCommentDays() {
        return defaultCommentDays;
    }

    public void setDefaultCommentDays(int defaultCommentDays) {
        this.defaultCommentDays = defaultCommentDays;
    }

    public boolean isModerateComments() {
        return moderateComments;
    }

    public void setModerateComments(boolean moderateComments) {
        this.moderateComments = moderateComments;
    }

    public int getEntryDisplayCount() {
        return entryDisplayCount;
    }

    public void setEntryDisplayCount(int entryDisplayCount) {
        this.entryDisplayCount = entryDisplayCount;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public boolean isEnableMultiLang() {
        return enableMultiLang;
    }

    public void setEnableMultiLang(boolean enableMultiLang) {
        this.enableMultiLang = enableMultiLang;
    }

    public boolean isShowAllLangs() {
        return showAllLangs;
    }

    public void setShowAllLangs(boolean showAllLangs) {
        this.showAllLangs = showAllLangs;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getAnalyticsCode() {
        return analyticsCode;
    }

    public void setAnalyticsCode(String analyticsCode) {
        this.analyticsCode = analyticsCode;
    }

    public List<WeblogCategory> getWeblogCategories() {
        return weblogCategories;
    }

    public void setWeblogCategories(List<WeblogCategory> weblogCategories) {
        this.weblogCategories = weblogCategories;
    }

    public List<WeblogBookmarkFolder> getBookmarkFolders() {
        return bookmarkFolders;
    }

    public void setBookmarkFolders(List<WeblogBookmarkFolder> bookmarkFolders) {
        this.bookmarkFolders = bookmarkFolders;
    }

    

    public List<WeblogEntry> getWeblogEntries() {
        return weblogEntries;
    }

    public void setWeblogEntries(List<WeblogEntry> weblogEntries) {
        this.weblogEntries = weblogEntries;
    }

    public List<Newsfeed> getNewsfeeds() {
        return newsfeeds;
    }

    public void setNewsfeeds(List<Newsfeed> newsfeeds) {
        this.newsfeeds = newsfeeds;
    }

    public List<MediaFileDirectory> getMediaFileDirectories() {
        return mediaFileDirectories;
    }

    public void setMediaFileDirectories(List<MediaFileDirectory> mediaFileDirectories) {
        this.mediaFileDirectories = mediaFileDirectories;
    }

    public List<AutoPing> getAutopings() {
        return autopings;
    }

    public void setAutopings(List<AutoPing> autopings) {
        this.autopings = autopings;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
     /**
     * Parse locale value and instantiate a Locale object,
     * otherwise return default Locale.
     *
     * @return Locale
     */
    public Locale getLocaleInstance() {
        if(getLocale() == null || getLocale().length() == 0){
            this.setLocale(Locale.getDefault().toString());
        }
        return LocaleFixer.toLocale(getLocale());
    }
    
    /**
     * Return TimeZone instance for value of timeZone,
     * otherwise return system default instance.
     * @return TimeZone
     */
    public TimeZone getTimeZoneInstance() {
        if (getTimeZone() == null) {
            this.setTimeZone( TimeZone.getDefault().getID() );
        }
        return TimeZone.getTimeZone(getTimeZone());
    }
    
    /**
     * Return Calendar instance for value of timeZone, locale
     * @return Calendar
     */
    public Calendar getCalendarInstance() {
        return Calendar.getInstance(getTimeZoneInstance(), getLocaleInstance());
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        //Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Weblog)) {
            return false;
        }
        Weblog other = (Weblog) object;
        if (!this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

        
    

}
